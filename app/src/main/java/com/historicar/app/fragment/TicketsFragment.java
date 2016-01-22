package com.historicar.app.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.historicar.app.R;
import com.historicar.app.activity.AboutActivity;
import com.historicar.app.activity.CaptchaActivity;
import com.historicar.app.activity.InsertOrEditActivity;
import com.historicar.app.adapter.TesteAdapter;
import com.historicar.app.bean.Carro;
import com.historicar.app.contants.Constants;
import com.historicar.app.persistence.Repository;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 20/01/16.
 */
public class TicketsFragment extends Fragment
{
    private AlertDialog alertDialog;

    private RecyclerView.Adapter adapter;

    private List<Carro> carros;

    @Bind(R.id.homeRecyclerView)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.newPlate)
    protected ImageView newPlateView;

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mView = inflater.inflate(R.layout.fragment_ticket, container, false);

        ButterKnife.bind(this, mView);

        carros = new Repository(getActivity()).getAll();

        adapter = new TesteAdapter(carros, getActivity());

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(adapter);

        if (carros.isEmpty())
        {
            newPlateView.setVisibility(View.VISIBLE);
            newPlateView.setOnClickListener(new ImageClickListener());
        }
        else
        {
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        return mView;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_tickets_fragment, menu);

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
                    alertDialog = new AlertUtils(getActivity()).getAlertDialog(getString(R.string.invalid_plate), button);
                    alertDialog.show();
                    return false;
                }

                if (getActivity().getCurrentFocus() != null)
                {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }

                Intent myIntent = new Intent(getActivity(), CaptchaActivity.class);
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
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.action_insert_or_edit:
                startActivityForResult(new Intent(getActivity(), InsertOrEditActivity.class), Constants.REQUEST_FOR_CREATE_PLATE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ImageClickListener implements View.OnClickListener
    {
        @Override
        public void onClick (View v)
        {
            startActivityForResult(new Intent(getActivity(), InsertOrEditActivity.class), Constants.REQUEST_FOR_CREATE_PLATE);
        }
    }

}
