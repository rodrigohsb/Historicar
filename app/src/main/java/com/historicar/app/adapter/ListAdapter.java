package com.historicar.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.bean.Multa;

import java.util.List;

/**
 * Created by Rodrigo on 15/04/15.
 */
public class ListAdapter extends BaseAdapter
{

    private final List<Multa> multas;
    private static LayoutInflater inflater = null;

    private static final int TYPE_TOP = 0;
    private static final int TYPE_MIDDLE = 1;
    private static final int TYPE_BOTTOM = 2;

    private static final int TYPE_TOP_BOTTOM = 10;

    public ListAdapter (Context context, List<Multa> multas)
    {
        this.multas = multas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount ()
    {
        return multas.size();
    }

    @Override
    public Object getItem (int position)
    {
        return position;
    }

    @Override
    public long getItemId (int position)
    {
        return position;
    }

    @Override
    public int getItemViewType (int position)
    {
        if (position == 0 && position == (getCount() - 1))
        {
            return TYPE_TOP_BOTTOM;
        }
        else if (position == 0)
        {
            return TYPE_TOP;
        }
        else if (position == (getCount() - 1))
        {
            return TYPE_BOTTOM;
        }
        return TYPE_MIDDLE;
    }

    @Override
    public int getViewTypeCount ()
    {
        return multas.size();
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;

        ViewHolder holder;

        int type = getItemViewType(position);

        if (convertView == null)
        {
            holder = new ViewHolder();

            switch (type)
            {
                case TYPE_TOP_BOTTOM:
                    vi = inflater.inflate(R.layout.activity_result_row_top_bottom, null);
                    break;

                case TYPE_TOP:
                    vi = inflater.inflate(R.layout.activity_result_row_top, null);
                    break;

                case TYPE_MIDDLE:
                    vi = inflater.inflate(R.layout.activity_result_row_middle, null);
                    break;

                case TYPE_BOTTOM:
                    vi = inflater.inflate(R.layout.activity_result_row_bottom, null);
                    break;
            }

            holder.title = (TextView) vi.findViewById(R.id.title);
            holder.points = (TextView) vi.findViewById(R.id.points);
            holder.date = (TextView) vi.findViewById(R.id.date);
            holder.status = (ImageView) vi.findViewById(R.id.status);
            holder.price = (TextView) vi.findViewById(R.id.price);

            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Multa multa = multas.get(position);

        holder.title.setText(multa.getInfracao());
        holder.title.setSingleLine();
        holder.title.setEllipsize(TextUtils.TruncateAt.END);
        holder.points.setText("Ponto/Gravidade: " + multa.getPontosGravidade());
        holder.date.setText("Data/Hora: " + multa.getDateInfracao());

        if ("PENALIDADE PAGA".equalsIgnoreCase(multa.getSituacaoPagamento()))
        {
            holder.status.setImageResource(R.drawable.ic_green);
            holder.price.setText("Valor pago: R$ " + multa.getValorPago());
        }
        else
        {
            holder.status.setImageResource(R.drawable.ic_red);
            if (multa.getValorAPagar() != null)
            {
                holder.price.setVisibility(View.VISIBLE);
                holder.price.setText(multa.getValorAPagar().contains("R$") ? "Valor a pagar: " + multa.getValorAPagar() : "Valor a pagar: R$ " + multa.getValorAPagar());
            }
            else if (multa.getVencimento() != null)
            {
                holder.price.setVisibility(View.VISIBLE);
                holder.price.setText("Data de Vencimento: " + multa.getVencimento());
            }
        }

        return vi;
    }

    private static class ViewHolder
    {
        TextView title;
        TextView points;
        TextView date;
        ImageView status;
        TextView price;
    }
}
