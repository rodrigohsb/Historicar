package com.historicar.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.historicar.app.R;
import com.historicar.app.activity.AboutActivity;
import com.historicar.app.activity.CaptchaActivity;
import com.historicar.app.activity.InsertOrEditActivity;
import com.historicar.app.adapter.HomeAdapter;
import com.historicar.app.bean.Carro;
import com.historicar.app.contants.Constants;
import com.historicar.app.persistence.Repository;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 20/01/16.
 */
public class TicketsFragment extends Fragment
{

    private HomeAdapter adapter;

    private List<Carro> carros;

    @Bind(R.id.information)
    protected ImageView informationView;

    @Bind(R.id.homeList)
    protected ListView list;

    @Bind(R.id.newPlate)
    protected ImageView newPlateView;

    private View mView;

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_ticket, container, false);

        ButterKnife.bind(this, mView);

        carros = new Repository(getActivity()).getAll();

        adapter = new HomeAdapter(getActivity(), carros);

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
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });

        newPlateView.setOnClickListener(new ImageClickListener());

        return mView;
    }

    private class ImageClickListener implements View.OnClickListener
    {
        @Override
        public void onClick (View v)
        {
            startActivityForResult(new Intent(getActivity(), InsertOrEditActivity.class), Constants.REQUEST_FOR_CREATE_PLATE);
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick (AdapterView<?> parent, View view, int position, long id)
        {
            Intent myIntent = new Intent(getActivity(), CaptchaActivity.class);
            myIntent.putExtra(Constants.PLACA_KEY, carros.get(position).getPlaca().replaceAll(" - ", ""));
            startActivity(myIntent);
        }
    }

    private class ItemLongClickListener implements AdapterView.OnItemLongClickListener
    {

        @Override
        public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
        {
            Intent myIntent = new Intent(getActivity(), InsertOrEditActivity.class);
            myIntent.putExtra(getString(R.string.carro), carros.get(position));
            startActivityForResult(myIntent, Constants.REQUEST_FOR_UPDATE_PLATE);
            return true;
        }
    }
}
