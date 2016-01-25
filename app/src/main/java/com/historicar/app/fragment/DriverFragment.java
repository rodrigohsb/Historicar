package com.historicar.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.historicar.app.R;
import com.historicar.app.activity.AboutActivity;
import com.historicar.app.activity.InsertOrEditDriverActivity;
import com.historicar.app.activity.InsertOrEditVehicleActivity;
import com.historicar.app.adapter.DriverAdapter;
import com.historicar.app.adapter.TicketAdapter;
import com.historicar.app.bean.Driver;
import com.historicar.app.contants.Constants;
import com.historicar.app.service.DriverService;
import com.historicar.app.service.impl.DriverServiceImpl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 20/01/16.
 */
public class DriverFragment extends Fragment
{

    private List<Driver> drivers;

    private RecyclerView.Adapter adapter;

    @Bind(R.id.driverRecyclerView)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.driverNewCNHImage)
    protected ImageView driverNewCNHImage;

    private DriverService driverService;

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
        View mView = inflater.inflate(R.layout.fragment_driver, container, false);

        ButterKnife.bind(this, mView);

        driverService = new DriverServiceImpl(getActivity());

        drivers = driverService.getAll();

        if (drivers == null || drivers.isEmpty())
        {
            driverNewCNHImage.setVisibility(View.VISIBLE);
            driverNewCNHImage.setOnClickListener(new ImageClickListener());

            mRecyclerView.setVisibility(View.GONE);
        }
        else
        {
            driverNewCNHImage.setVisibility(View.GONE);

            mRecyclerView.setVisibility(View.VISIBLE);
            adapter = new DriverAdapter(drivers, getActivity());

            mRecyclerView.setHasFixedSize(true);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mRecyclerView.setAdapter(adapter);
        }

        return mView;
    }

    public void updateData ()
    {

        List<Driver> driversRepo = driverService.getAll();

        if (driversRepo == null || driversRepo.isEmpty())
        {
            mRecyclerView.setVisibility(View.GONE);
            driverNewCNHImage.setVisibility(View.VISIBLE);
            driverNewCNHImage.setOnClickListener(new ImageClickListener());
        }
        else
        {
            drivers.clear();
            drivers.addAll(driversRepo);

            mRecyclerView.setVisibility(View.VISIBLE);
            driverNewCNHImage.setVisibility(View.GONE);

            if(adapter == null)
            {
                mRecyclerView.setHasFixedSize(true);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);

                adapter = new DriverAdapter(drivers, getActivity());
            }
            else
            {
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_driver_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_about:
                getActivity().startActivity(new Intent(getActivity(), AboutActivity.class));
                break;

            case R.id.action_insert_or_edit:
                getActivity().startActivityForResult(new Intent(getActivity(), InsertOrEditDriverActivity.class), Constants.REQUEST_FOR_CREATE_DRIVER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ImageClickListener implements View.OnClickListener
    {
        @Override
        public void onClick (View v)
        {
            getActivity().startActivityForResult(new Intent(getActivity(), InsertOrEditDriverActivity.class), Constants.REQUEST_FOR_CREATE_DRIVER);
        }
    }
}
