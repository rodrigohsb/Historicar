package com.historicar.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.bean.Carro;

import java.util.List;

/**
 * Created by Rodrigo on 29/04/15.
 */
public class HomeAdapter extends BaseAdapter
{

    private final List<Carro> carros;
    private static LayoutInflater inflater = null;

    public HomeAdapter (Context context, List<Carro> carros)
    {
        this.carros = carros;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount ()
    {
        return carros.size();
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
    public View getView (int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;

        ViewHolder holder;

        if (convertView == null)
        {
            holder = new ViewHolder();

            vi = inflater.inflate(R.layout.activity_home_row, null);

            holder.description = (TextView) vi.findViewById(R.id.homeDescription);
            holder.placaValue = (TextView) vi.findViewById(R.id.homePlacaValue);

            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Carro carro = carros.get(position);

        holder.description.setText(carro.getDescription());
        holder.placaValue.setText(carro.getPlaca());

        return vi;
    }


    private static class ViewHolder
    {
        TextView description;
        TextView placaValue;
    }
}
