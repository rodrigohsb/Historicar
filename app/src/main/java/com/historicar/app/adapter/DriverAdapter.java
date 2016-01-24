package com.historicar.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.activity.InsertOrEditDriverActivity;
import com.historicar.app.bean.Driver;
import com.historicar.app.contants.Constants;

import java.util.List;

/**
 * Created by Rodrigo on 23/01/16.
 */
public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.LinearViewHolder>
{

    private final List<Driver> mList;
    private final Context mContext;

    public DriverAdapter (List<Driver> mList, Context mContext)
    {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public DriverAdapter.LinearViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        return new LinearViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_drivers_row, parent, false));
    }

    @Override
    public void onBindViewHolder (DriverAdapter.LinearViewHolder holder, int position)
    {
        Driver driver = mList.get(position);

        holder.mName.setText(driver.getName());
        holder.mCPF.setText(driver.getCpf());
        holder.mCNH.setText(String.valueOf(driver.getCnh()));
    }

    @Override
    public int getItemCount ()
    {
        return mList.size();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        private final TextView mName;

        private final TextView mCPF;

        private final TextView mCNH;

        public LinearViewHolder (View view)
        {
            super(view);
            mName = (TextView) view.findViewById(R.id.driverName);
            mCPF = (TextView) view.findViewById(R.id.driverCPF);
            mCNH = (TextView) view.findViewById(R.id.driverCNH);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick (View view)
        {

        }

        @Override
        public boolean onLongClick (View v)
        {
            Intent myIntent = new Intent(mContext, InsertOrEditDriverActivity.class);
            myIntent.putExtra(mContext.getString(R.string.driver), mList.get(getAdapterPosition()));
            ((Activity) mContext).startActivityForResult(myIntent, Constants.REQUEST_FOR_UPDATE_DRIVER);
            return true;
        }
    }
}
