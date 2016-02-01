package com.historicar.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.historicar.app.R;
import com.historicar.app.connection.Connection;
import com.historicar.app.contants.Constants;
import com.historicar.app.event.captcha.LoadCaptchaErrorEvent;
import com.historicar.app.event.captcha.LoadCaptchaRetryEvent;
import com.historicar.app.event.captcha.LoadCaptchaSuccessEvent;
import com.historicar.app.provider.BusProvider;
import com.historicar.app.util.ValidateUtils;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 10/01/16.
 */
public class CaptchaActivity extends AppCompatActivity
{

    private static final String TAG = CaptchaActivity.class.getSimpleName();

    private ProgressDialog dialog;

    @Bind(R.id.captchaTitle)
    protected TextView title;

    @Bind(R.id.captchaImage)
    protected ImageView image;

    @Bind(R.id.captchaTextView)
    protected TextView text;

    @Bind(R.id.captchaButton)
    protected Button button;

    private Context ctx;

    private String cookies;

    public static void start(Activity activity, String placa)
    {
        Intent intent = new Intent(activity.getApplicationContext(), CaptchaActivity.class);
        intent.putExtra(Constants.PLACA_KEY, placa);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        ButterKnife.bind(this);

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        ctx = this;

        new CaptchaAsyncTask(ctx).execute();

        text.addTextChangedListener(new NumberTextWatcher());

        text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                Appodeal.hide(CaptchaActivity.this, Appodeal.BANNER_BOTTOM);
            }
        });

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {

                if (CaptchaActivity.this.getCurrentFocus() != null)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(CaptchaActivity.this.getCurrentFocus().getWindowToken(), 0);
                }

                if (text.getText() == null || "".equalsIgnoreCase(text.getText().toString().trim()))
                {
                    Toast.makeText(ctx, "O código não pode ser vazio!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ResultActivity.start(CaptchaActivity.this, getIntent().getExtras().getString(Constants.PLACA_KEY), text.getText().toString(), cookies);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onLoadCaptchaErrorEvent(LoadCaptchaErrorEvent event)
    {
        dialog.dismiss();

        if (!ValidateUtils.isOnline(ctx))
        {
            Toast.makeText(ctx, "Não foi possível encontrar conexão ativa.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ctx, "Não foi possível recuperar imagem. Por favor, tente mais tarde.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Subscribe
    public void onLoadCaptchaSuccessEvent(LoadCaptchaSuccessEvent event)
    {
        dialog.dismiss();

        title.setVisibility(View.VISIBLE);

        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(event.getDrawable());

        text.setVisibility(View.VISIBLE);
        text.setEnabled(true);

        button.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void onLoadCaptchaRetryEvent(LoadCaptchaRetryEvent event)
    {
        new CaptchaAsyncTask(ctx).execute();
    }

    public class NumberTextWatcher implements TextWatcher
    {
        @Override
        public void onTextChanged (CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void beforeTextChanged (CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void afterTextChanged (Editable s)
        {
            if (text.getText() != null && (!"".equalsIgnoreCase(text.getText().toString().trim())))
            {
                button.setEnabled(true);
            }
            else
            {
                //Caso o usuario apague o que escreveu
                button.setEnabled(false);
            }
        }
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }

    private class CaptchaAsyncTask extends AsyncTask<String, String, Drawable>
    {

        private final String TAG = CaptchaAsyncTask.class.getSimpleName();

        private final Context ctx;

        public CaptchaAsyncTask (Context ctx)
        {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute ()
        {
            super.onPreExecute();

            dialog = new ProgressDialog(ctx);
            dialog.setMessage(getString(R.string.captchaActivityrecoveringImage));
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Drawable doInBackground (String... params)
        {
            cookies = Connection.getCookies();
            return Connection.getCaptcha(cookies);
        }

        @Override
        protected void onPostExecute (Drawable drawable)
        {
            super.onPostExecute(drawable);

            if(drawable != null)
            {
                Log.d(TAG,"Chamando LoadCaptchaSuccessEvent");

                BusProvider.getInstance().post(new LoadCaptchaSuccessEvent(drawable));
                return;
            }
            Log.d(TAG,"Chamando LoadCaptchaErrorEvent");
            BusProvider.getInstance().post(new LoadCaptchaErrorEvent());
        }
    }

}