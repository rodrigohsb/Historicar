package com.historicar.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.historicar.app.R;
import com.historicar.app.adapter.ResultAdapter;
import com.historicar.app.bean.Multa;
import com.historicar.app.contants.Constants;
import com.historicar.app.event.LoadTicketErrorEvent;
import com.historicar.app.event.LoadTicketRetryEvent;
import com.historicar.app.event.LoadTicketSuccessEvent;
import com.historicar.app.provider.BusProvider;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;
import com.historicar.app.webservice.WebServiceAPi;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rodrigo on 17/04/15.
 */
public class ResultActivity extends AppCompatActivity
{

    protected static final String TAG = ResultActivity.class.getSimpleName();

    private Context ctx;

    @Bind(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.snackbarlocation)
    protected CoordinatorLayout coordinatorLayout;

    private ProgressDialog dialog;

    public static void start(Activity activity, String placa, String captcha, String cookie)
    {
        Intent intent = new Intent(activity.getApplicationContext(), ResultActivity.class);
        intent.putExtra(Constants.PLACA_KEY, placa);
        intent.putExtra(Constants.CAPTCHA, captcha);
        intent.putExtra(Constants.COOKIE, cookie);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ButterKnife.bind(this);

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.INTERSTITIAL | Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        ctx = this;

        String placa = getIntent().getExtras().getString(Constants.PLACA_KEY);
        String captcha = getIntent().getExtras().getString(Constants.CAPTCHA);
        String cookie = getIntent().getExtras().getString(Constants.COOKIE);

        dialog = new ProgressDialog(ctx);
        dialog.setMessage("Buscando informações...");
        dialog.setCancelable(false);
        dialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebServiceAPi wsAPI = retrofit.create(WebServiceAPi.class);
        Call<List<Multa>> multas = wsAPI.getTickets(placa, captcha, cookie);
        multas.enqueue(new Callback<List<Multa>>()
        {
            @Override
            public void onResponse (Response<List<Multa>> response)
            {

                int httpResponseCode = response.code();

                Log.d(TAG,"[getTickets] StatusCode [" + httpResponseCode + "]");

                if(response.isSuccess())
                {
                    List<Multa> multas = response.body();

                    if (multas != null && !multas.isEmpty())
                    {
                        BusProvider.getInstance().post(new LoadTicketSuccessEvent(multas));
                        return;
                    }
                    //NO_CONTENT
                    if(httpResponseCode == 204)
                    {
                        BusProvider.getInstance().post(new LoadTicketSuccessEvent(new ArrayList<Multa>()));
                        return;
                    }
                }
                //BAD_REQUEST
                if(httpResponseCode == 400)
                {
                    BusProvider.getInstance().post(new LoadTicketRetryEvent(true));
                    return;
                }
                //UNAUTHORIZED
                if(httpResponseCode == 401)
                {
//                    String newUserToken = (String) response.body();

                    //TODO Pegar novo usertoken e chamar de novo

                    return;
                }
                //NOT_ACCEPTABLE
                if(httpResponseCode == 406)
                {
                    BusProvider.getInstance().post(new LoadTicketRetryEvent(false));
                    return;
                }
                //INTERNAL_SERVER_ERROR
                if(httpResponseCode == 500)
                {
                    BusProvider.getInstance().post(new LoadTicketErrorEvent());
                }
            }

            @Override
            public void onFailure (Throwable t)
            {
                Log.d(TAG,"[getTickets] Error [" + t.getMessage() + "]");
                BusProvider.getInstance().post(new LoadTicketErrorEvent());
            }
        });


    }

    @Subscribe
    public void onLoadTicketRetryEvent(LoadTicketRetryEvent event)
    {
        dialog.dismiss();

        if(event.isInvalidCode())
        {
            Toast.makeText(ctx, getString(R.string.invalid_code), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ctx, getString(R.string.try_again), Toast.LENGTH_SHORT).show();
        }

        CaptchaActivity.start(ResultActivity.this, getIntent().getExtras().getString(Constants.PLACA_KEY));
        finish();
    }

    @Subscribe
    public void onLoadTicketErrorEvent(LoadTicketErrorEvent event)
    {
        dialog.dismiss();

        ErrorActivity.start(ResultActivity.this, getIntent().getExtras().getString(Constants.PLACA_KEY));
    }

    @Subscribe
    public void onLoadTicketSuccessEvent(LoadTicketSuccessEvent event)
    {
        dialog.dismiss();

        List<Multa> multas = event.getMultas();

        if(multas.isEmpty())
        {
            NoMultaActivity.start(ResultActivity.this, getIntent().getExtras().getString(Constants.PLACA_KEY));
        }
        else
        {
            drawList(multas);
        }
    }

    /**
     *
     * @param multaList
     */
    private void drawList(List<Multa> multaList)
    {
        Appodeal.hide(((Activity) ctx), Appodeal.BANNER_BOTTOM);

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ctx);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new ResultAdapter(multaList, this);
        mRecyclerView.setAdapter(mAdapter);

        Snackbar snackbar;

        if(multaList.size() == 1)
        {
            snackbar = Snackbar.make(coordinatorLayout, ctx.getString(R.string.snackbar_singular, multaList.size()), Snackbar.LENGTH_LONG);
        }
        else
        {
            snackbar = Snackbar.make(coordinatorLayout, ctx.getString(R.string.snackbar_plural, multaList.size()), Snackbar.LENGTH_LONG);
        }

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);

        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(ctx.getResources().getColor(android.R.color.white));

        snackbar.show();

        snackbar.setCallback(new Snackbar.Callback()
        {
            @Override
            public void onDismissed (Snackbar snackbar, int event)
            {
                Appodeal.show(((Activity) ctx), Appodeal.BANNER_BOTTOM);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_example));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit (String s)
            {
                if (!ValidateUtils.isOnline(ctx))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    AlertDialog alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_connection), button);
                    alertDialog.show();
                    return false;
                }
                else if (!ValidateUtils.validatePlate(s))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    AlertDialog alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_plate), button);
                    alertDialog.show();
                    return false;
                }

                if(ResultActivity.this.getCurrentFocus() != null)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(ResultActivity.this.getCurrentFocus().getWindowToken(), 0);
                }

                CaptchaActivity.start(ResultActivity.this, s);
                finish();
                return true;

            }

            @Override
            public boolean onQueryTextChange (String s)
            {
                if (s.length() > 7)
                {
                    searchView.setQuery(s.substring(0, 7), false);
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public void onPause()
    {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
        BusProvider.getInstance().register(this);
    }
}
