package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.historicar.app.R;
import com.historicar.app.adapter.HomeAdapter;
import com.historicar.app.bean.Carro;
import com.historicar.app.contants.Constants;
import com.historicar.app.persistence.Repository;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;

import java.util.List;

/**
 * Created by Rodrigo on 16/04/15.
 */
public class HomeActivity extends ActionBarActivity
{

    private AlertDialog alertDialog;

    private Context ctx;

    @Override
    protected void onCreate (final Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);

        ctx = this;

        Repository repository = new Repository(ctx);

        final List<Carro> carros = repository.getAll();

        ListView list = (ListView) findViewById(R.id.homeList);

        ImageView newPlateView1 = (ImageView) findViewById(R.id.newPlate);

        if (carros.isEmpty())
        {
            list.setVisibility(View.GONE);
        }
        else
        {

            newPlateView1.setVisibility(View.GONE);

            HomeAdapter adapter = new HomeAdapter(ctx, carros);

            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick (AdapterView<?> parent, View view, int position, long id)
                {

                    Carro carro = carros.get(position);

                    Intent myIntent = new Intent(ctx, ResultActivity.class);
                    myIntent.putExtra(Constants.PLACA_KEY, carro.getPlaca().replaceAll(" - ", ""));
                    startActivity(myIntent);
                }
            });
            list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
                {
                    Carro carro = carros.get(position);

                    Intent myIntent = new Intent(ctx, InsertOrEditActivity.class);
                    myIntent.putExtra(Constants.CARRO, carro);
                    startActivity(myIntent);
                    return true;
                }
            });
        }


        ImageView informationView = (ImageView) findViewById(R.id.information);

        informationView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(ctx, AboutActivity.class);
                startActivity(intent);
            }
        });

        ImageView newPlateView = (ImageView) findViewById(R.id.newPlate);

        newPlateView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(ctx, InsertOrEditActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("EX: AAA1234");

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
                    alertDialog = new AlertUtils(ctx).getAlertDialog(Constants.INVALID_CONNECTION, button);
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
                    alertDialog = new AlertUtils(ctx).getAlertDialog(Constants.INVALID_PLACA, button);
                    alertDialog.show();
                    return false;
                }

                Intent myIntent = new Intent(ctx, ResultActivity.class);
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

        // When using the support library, the setOnActionExpandListener() method is static and accepts the MenuItem object as an argument
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener()
        {
            @Override
            public boolean onMenuItemActionCollapse (MenuItem item)
            {
                // Do something when collapsed
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand (MenuItem item)
            {
                // Do something when expanded
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        // Handle action bar item clicks here. 
        // The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId())
        {
            case R.id.action_insert_or_edit:
                Intent intent = new Intent(ctx, InsertOrEditActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed ()
    {
    }
}
