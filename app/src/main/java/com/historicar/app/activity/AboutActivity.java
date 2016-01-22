package com.historicar.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appodeal.ads.Appodeal;
import com.historicar.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 29/04/15.
 */
public class AboutActivity extends AppCompatActivity
{
    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initActionBar();

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    private void initActionBar ()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }
}
