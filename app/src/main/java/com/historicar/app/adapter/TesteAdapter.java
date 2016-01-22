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
import com.historicar.app.activity.CaptchaActivity;
import com.historicar.app.activity.InsertOrEditActivity;
import com.historicar.app.bean.Carro;
import com.historicar.app.contants.Constants;

import java.util.List;

/**
 * Created by Rodrigo on 01/01/16.
 */
public class TesteAdapter extends RecyclerView.Adapter<TesteAdapter.LinearViewHolder>
{

    private final List<Carro> mList;
    private final Context mContext;

    public TesteAdapter (List<Carro> mList, Context mContext)
    {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public TesteAdapter.LinearViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        return new LinearViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_row, parent, false));
    }

    @Override
    public void onBindViewHolder (TesteAdapter.LinearViewHolder holder, int position)
    {
        Carro carro = mList.get(position);

        holder.mDescription.setText(carro.getDescription());
        holder.mPlaca.setText(carro.getPlaca());
    }

    @Override
    public int getItemCount ()
    {
        return mList.size();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        private final TextView mDescription;

        private final TextView mPlaca;

        public LinearViewHolder (View view)
        {
            super(view);
            mDescription = (TextView) view.findViewById(R.id.homeDescription);
            mPlaca = (TextView) view.findViewById(R.id.homePlacaValue);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick (View view)
        {
            Intent it = new Intent(mContext, CaptchaActivity.class);
            it.putExtra(Constants.PLACA_KEY, mList.get(getAdapterPosition()).getPlaca().replaceAll(" - ", ""));
            mContext.startActivity(it);
        }

        @Override
        public boolean onLongClick (View v)
        {
            Intent myIntent = new Intent(mContext, InsertOrEditActivity.class);
            myIntent.putExtra(mContext.getString(R.string.carro), mList.get(getAdapterPosition()));
            ((Activity) mContext).startActivityForResult(myIntent, Constants.REQUEST_FOR_UPDATE_PLATE);
            return true;
        }
    }
}
