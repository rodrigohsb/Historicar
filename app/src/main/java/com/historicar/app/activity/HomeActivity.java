package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.historicar.app.R;
import com.historicar.app.adapter.HomeAdapter;
import com.historicar.app.bean.Carro;
import com.historicar.app.contants.Constants;
import com.historicar.app.persistence.Repository;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 16/04/15.
 */
public class HomeActivity extends AppCompatActivity
{

    private AlertDialog alertDialog;

    private Context ctx;

    private HomeAdapter adapter;

    private List<Carro> carros;

    @Bind(R.id.information)
    protected ImageView informationView;

    @Bind(R.id.homeList)
    protected ListView list;

    @Bind(R.id.newPlate)
    protected ImageView newPlateView;

    @Bind(R.id.validText)
    protected TextView validText;

    private boolean leaving = false;

    @Override
    protected void onCreate (final Bundle bundle)
    {
        super.onCreate(bundle);

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.INTERSTITIAL | Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);

        ctx = this;

        carros = new Repository(ctx).getAll();

        adapter = new HomeAdapter(ctx, carros);

        list.setAdapter(adapter);

        if (carros.isEmpty())
        {
            newPlateView.setVisibility(View.VISIBLE);
        }
        else
        {
            list.setVisibility(View.VISIBLE);
            list.setOnItemClickListener(new ItemClickListener());
            list.setOnItemLongClickListener(new ItemLongClickListener());
        }

        informationView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startActivity(new Intent(ctx, AboutActivity.class));
            }
        });

        newPlateView.setOnClickListener(new ImageClickListener());
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

        if(leaving)
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
            List<Carro> carrosRepo = new Repository(ctx).getAll();

            if (carrosRepo == null || carrosRepo.isEmpty())
            {
                list.setVisibility(View.GONE);
                newPlateView.setVisibility(View.VISIBLE);
                newPlateView.setOnClickListener(new ImageClickListener());
            }
            else
            {
                carros.clear();
                carros.addAll(carrosRepo);


                list.setVisibility(View.VISIBLE);
                newPlateView.setVisibility(View.GONE);
                list.setOnItemClickListener(new ItemClickListener());
                list.setOnItemLongClickListener(new ItemLongClickListener());
                adapter.notifyDataSetChanged();
            }
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

                if(HomeActivity.this.getCurrentFocus() != null)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(HomeActivity.this.getCurrentFocus().getWindowToken(), 0);
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

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener()
        {
            @Override
            public boolean onMenuItemActionCollapse (MenuItem item)
            {
                validText.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand (MenuItem item)
            {
                validText.setVisibility(View.GONE);
                return true;
            }
        });

        return true;
    }

    private class ImageClickListener implements View.OnClickListener
    {
        @Override
        public void onClick (View v)
        {
            startActivityForResult(new Intent(ctx, InsertOrEditActivity.class), Constants.REQUEST_FOR_CREATE_PLATE);
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick (AdapterView<?> parent, View view, int position, long id)
        {
            Intent myIntent = new Intent(ctx, CaptchaActivity.class);
            myIntent.putExtra(Constants.PLACA_KEY, carros.get(position).getPlaca().replaceAll(" - ", ""));
            startActivity(myIntent);
        }
    }

    private class ItemLongClickListener implements AdapterView.OnItemLongClickListener
    {

        @Override
        public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
        {
            Intent myIntent = new Intent(ctx, InsertOrEditActivity.class);
            myIntent.putExtra(getString(R.string.carro), carros.get(position));
            startActivityForResult(myIntent, Constants.REQUEST_FOR_UPDATE_PLATE);
            return true;
        }
    }

}
