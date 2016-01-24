package com.historicar.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.historicar.app.R;
import com.historicar.app.activity.AboutActivity;
import com.historicar.app.activity.InsertOrEditPointsActivity;
import com.historicar.app.activity.InsertOrEditTicketsActivity;
import com.historicar.app.contants.Constants;

import butterknife.Bind;

/**
 * Created by Rodrigo on 20/01/16.
 */
public class DriverFragment extends Fragment
{

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
        return inflater.inflate(R.layout.fragment_points, container, false);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_points_fragment, menu);
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
                startActivityForResult(new Intent(getActivity(), InsertOrEditPointsActivity.class), Constants.REQUEST_FOR_CREATE_DRIVER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
