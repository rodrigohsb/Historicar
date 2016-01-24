package com.historicar.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.historicar.app.R;
import com.historicar.app.contants.Constants;
import com.historicar.app.fragment.DriverFragment;
import com.historicar.app.fragment.TicketFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 16/04/15.
 */
public class HomeActivity extends AppCompatActivity
{

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
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

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
        mViewPagerAdapter.addFragment(new TicketFragment(), getString(R.string.homeActivityTicketsTitleTab));
        mViewPagerAdapter.addFragment(new DriverFragment(), getString(R.string.homeActivityPointsTitleTab));
        viewPager.setAdapter(mViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels)
            {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onPageSelected (int position)
            {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged (int state)
            {
                supportInvalidateOptionsMenu();
            }
        });
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
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == Constants.REQUEST_FOR_CREATE_PLATE || requestCode == Constants.REQUEST_FOR_UPDATE_PLATE) && resultCode == RESULT_OK)
        {
            TicketFragment mTicketFragment = (TicketFragment) mViewPagerAdapter.getItem(0);
            mTicketFragment.updateData();
        }
        else if ((requestCode == Constants.REQUEST_FOR_CREATE_DRIVER || requestCode == Constants.REQUEST_FOR_UPDATE_DRIVER) && resultCode == RESULT_OK)
        {
            TicketFragment mDriverFragments = (TicketFragment) mViewPagerAdapter.getItem(1);
            mDriverFragments.updateData();
        }
    }

}
