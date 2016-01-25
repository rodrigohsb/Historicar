package com.historicar.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.historicar.app.util.ValidateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 10/01/16.
 */
public class CaptchaActivity extends AppCompatActivity
{

    @Bind(R.id.captchaTitle)
    protected TextView title;

    @Bind(R.id.captchaImage)
    protected ImageView image;

    @Bind(R.id.captchaTextView)
    protected TextView text;

    @Bind(R.id.captchaButton)
    protected Button button;

    private Context ctx;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captcha);
        ButterKnife.bind(this);

        new CaptchaAsyncTask(this).execute();

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.INTERSTITIAL | Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        ctx = this;

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

                Intent myIntent = new Intent(ctx, ResultActivity.class);
                myIntent.putExtra(Constants.PLACA_KEY, getIntent().getExtras().getString(Constants.PLACA_KEY));
                myIntent.putExtra(Constants.CAPTCHA, text.getText().toString().trim());
                startActivity(myIntent);
                finish();
            }
        });
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

        private final Context ctx;

        private ProgressDialog dialog;

        public CaptchaAsyncTask (Context ctx)
        {
            this.ctx = ctx;
        }

        @Override
        protected void onPreExecute ()
        {
            super.onPreExecute();

            dialog = new ProgressDialog(ctx);
            dialog.setMessage("Buscando imagem...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Drawable doInBackground (String... params)
        {
            return Connection.getCaptcha();
        }

        @Override
        protected void onPostExecute (Drawable drawable)
        {
            super.onPostExecute(drawable);

            if (drawable != null)
            {
                title.setVisibility(View.VISIBLE);

                image.setVisibility(View.VISIBLE);
                image.setImageDrawable(drawable);

                text.setVisibility(View.VISIBLE);
                text.setEnabled(true);

                button.setVisibility(View.VISIBLE);

                dialog.dismiss();

                return;
            }
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
    }

}
