package com.historicar.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.historicar.app.contants.Constants;
import com.historicar.app.event.captcha.LoadCaptchaErrorEvent;
import com.historicar.app.event.captcha.LoadCaptchaRetryEvent;
import com.historicar.app.event.captcha.LoadCaptchaSuccessEvent;
import com.historicar.app.provider.BusProvider;
import com.historicar.app.util.ValidateUtils;
import com.historicar.app.webservice.WebServiceAPi;
import com.squareup.otto.Subscribe;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rodrigo on 10/01/16.
 */
public class CaptchaActivity extends AppCompatActivity
{

    protected static final String TAG = CaptchaActivity.class.getSimpleName();

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

    String cookie;

    public static void start (Activity activity, String placa)
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
        Appodeal.setTesting(true);

        ctx = this;

        dialog = new ProgressDialog(ctx);
        dialog.setMessage(getString(R.string.captchaActivityrecoveringImage));
        dialog.setCancelable(false);
        dialog.show();

        getCaptcha();

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

                ResultActivity.start(CaptchaActivity.this, getIntent().getExtras().getString(Constants.PLACA_KEY), text.getText().toString().trim(), cookie);
            }
        });
    }

    private void getCaptcha ()
    {
        getCookie();
    }

    private void getCookie ()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SMTR_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        WebServiceAPi wsAPI = retrofit.create(WebServiceAPi.class);
        Call<ResponseBody> cookieRequest = wsAPI.getCookie();

        cookieRequest.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse (Response<ResponseBody> response)
            {

                cookie = "";

                if (response.headers() != null && response.headers().get("Set-Cookie") != null)
                {
                    String[] cookies = response.headers().get("Set-Cookie").split(";");

                    for (String c : cookies)
                    {
                        if (c.contains("ASPSESSIONID"))
                        {
                            cookie = cookie.concat(c);
                        }
                    }
                }

                Log.i(TAG, "Cookie recebido foi = " + cookie);
                getCaptcha(cookie);
            }

            @Override
            public void onFailure (Throwable t)
            {
                t.printStackTrace();
            }
        });
    }

    private void getCaptcha (String cookie)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.SMTR_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        WebServiceAPi wsAPI = retrofit.create(WebServiceAPi.class);
        Call<ResponseBody> captcha = wsAPI.getCaptcha(cookie);

        captcha.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse (Response<ResponseBody> response)
            {
                InputStream is = response.body().byteStream();

                Drawable drawable = BitmapDrawable.createFromStream(is, "");

                BusProvider.getInstance().post(new LoadCaptchaSuccessEvent(drawable));
            }

            @Override
            public void onFailure (Throwable t)
            {

            }
        });
    }

    @Subscribe
    public void onLoadCaptchaErrorEvent (LoadCaptchaErrorEvent event)
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
    public void onLoadCaptchaSuccessEvent (LoadCaptchaSuccessEvent event)
    {
        dialog.dismiss();

        title.setVisibility(View.VISIBLE);

        image.setVisibility(View.VISIBLE);
        image.setImageDrawable(event.getDrawable());

        text.setVisibility(View.VISIBLE);
        text.setEnabled(true);
        text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                Appodeal.hide(CaptchaActivity.this, Appodeal.BANNER_BOTTOM);
            }
        });

        button.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void onLoadCaptchaRetryEvent (LoadCaptchaRetryEvent event)
    {
        getCaptcha();
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
    protected void onPause ()
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
