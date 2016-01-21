package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.historicar.app.R;
import com.historicar.app.bean.Carro;
import com.historicar.app.contants.Constants;
import com.historicar.app.fragment.PointsFragment;
import com.historicar.app.fragment.TicketsFragment;
import com.historicar.app.persistence.Repository;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 20/01/16.
 */
public class HomeActivity2 extends AppCompatActivity
{

    private AlertDialog alertDialog;

    private Context ctx;

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Bind(R.id.tabs)
    protected TabLayout tabLayout;

    @Bind(R.id.viewpager)
    protected ViewPager viewPager;

    private boolean leaving = false;

    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        ButterKnife.bind(this);

        ctx = this;

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.INTERSTITIAL | Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
        Appodeal.setTesting(true);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager (ViewPager viewPager)
    {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new TicketsFragment(), getString(R.string.homeActivityTicketsTitleTab));
        mViewPagerAdapter.addFragment(new PointsFragment(), getString(R.string.homeActivityPointsTitleTab));
        viewPager.setAdapter(mViewPagerAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter (FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem (int position)
        {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount ()
        {
            return mFragmentList.size();
        }

        public void addFragment (Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle (int position)
        {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_insert_or_edit:
                startActivityForResult(new Intent(ctx, InsertOrEditActivity.class), Constants.REQUEST_FOR_CREATE_PLATE);
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

    @Override
    public void onBackPressed ()
    {

        if (leaving)
        {
            super.onBackPressed();
            finish();
        }
        else
        {
            leaving = true;
            Appodeal.show(this, Appodeal.INTERSTITIAL);
            Appodeal.setInterstitialCallbacks(new InterstitialCallbacks()
            {
                @Override
                public void onInterstitialLoaded (boolean b)
                {

                }

                @Override
                public void onInterstitialFailedToLoad ()
                {

                }

                @Override
                public void onInterstitialShown ()
                {

                }

                @Override
                public void onInterstitialClicked ()
                {

                }

                @Override
                public void onInterstitialClosed ()
                {
                    onBackPressed();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_example));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit (String s)
            {

                if (!ValidateUtils.validatePlate(s))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_plate), button);
                    alertDialog.show();
                    return false;
                }

                if (HomeActivity2.this.getCurrentFocus() != null)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(HomeActivity2.this.getCurrentFocus().getWindowToken(), 0);
                }

                Intent myIntent = new Intent(ctx, CaptchaActivity.class);
                myIntent.putExtra(Constants.PLACA_KEY, s);
                startActivity(myIntent);
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
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == Constants.REQUEST_FOR_CREATE_PLATE || requestCode == Constants.REQUEST_FOR_UPDATE_PLATE) && resultCode == RESULT_OK)
        {
            TicketsFragment mTicketsFragment = (TicketsFragment) mViewPagerAdapter.getItem(0);
            mTicketsFragment.updateData();
        }
    }

}
