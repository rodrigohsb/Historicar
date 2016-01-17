package com.historicar.app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.activity.DetailsActivity;
import com.historicar.app.bean.Multa;

import java.util.List;

/**
 * Created by Rodrigo on 01/01/16.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.LinearViewHolder>
{

    private final List<Multa> mList;
    private final Activity mActivity;

    public ResultAdapter (List<Multa> mList, Activity activity)
    {
        this.mList = mList;
        mActivity = activity;
    }

    @Override
    public ResultAdapter.LinearViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        return new LinearViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_result_row, parent, false));
    }

    @Override
    public void onBindViewHolder (ResultAdapter.LinearViewHolder holder, int position)
    {
        Multa multa = mList.get(position);

        holder.mDescription.setText(multa.getDescricao().trim());
        holder.mDescription.setSingleLine();
        holder.mDescription.setEllipsize(TextUtils.TruncateAt.END);
        holder.mPoints.setText("Pontos: " + multa.getPontos());
        holder.mGravidade.setText("Gravidade: " + multa.getGravidade());
        holder.mDate.setText("Data/Hora: " + multa.getDataHoraInfracao());

        if ("PENALIDADE PAGA".equalsIgnoreCase(multa.getSituacaoDoPagamento()))
        {
            holder.mStatus.setImageResource(R.drawable.ic_green);
            holder.mPrice.setText(multa.getValorPago().contains("R$") ? "Valor pago: " + multa.getValorPago() : "Valor pago: R$ " + multa.getValorPago());
        }
        else
        {
            holder.mStatus.setImageResource(R.drawable.ic_red);
            if (multa.getValorAPagar() != null)
            {
                holder.mPrice.setVisibility(View.VISIBLE);
                holder.mPrice.setText(multa.getValorAPagar().contains("R$") ? "Valor a pagar: " + multa.getValorAPagar() : "Valor a pagar: R$ " + multa.getValorAPagar());
            }
            else if (multa.getVencimento() != null)
            {
                holder.mPrice.setVisibility(View.VISIBLE);
                holder.mPrice.setText("Data de Vencimento: " + multa.getVencimento());
            }
        }
    }

    @Override
    public int getItemCount ()
    {
        return mList.size();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private final TextView mDescription;

        private final TextView mPoints;

        private final TextView mGravidade;

        private final TextView mDate;

        private final ImageView mStatus;

        private final TextView mPrice;

        public LinearViewHolder(View view)
        {
            super(view);
            mDescription = (TextView) view.findViewById(R.id.title);
            mPoints = (TextView) view.findViewById(R.id.points);
            mGravidade = (TextView) view.findViewById(R.id.gravidade);
            mDate = (TextView) view.findViewById(R.id.date);
            mStatus = (ImageView) view.findViewById(R.id.status);
            mPrice = (TextView) view.findViewById(R.id.price);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            DetailsActivity.start(mActivity, mList.get(getAdapterPosition()));
        }
    }
}
