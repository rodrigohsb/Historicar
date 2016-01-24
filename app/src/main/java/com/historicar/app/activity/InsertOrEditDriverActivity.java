package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.bean.Driver;
import com.historicar.app.contants.Constants;
import com.historicar.app.service.DriverService;
import com.historicar.app.service.impl.DriverServiceImpl;
import com.historicar.app.util.AlertUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 29/04/15.
 */
public class InsertOrEditDriverActivity extends AppCompatActivity
{

    private AlertDialog alertDialog;

    private DriverService driverService;

    private Driver driver;

    private Context ctx;

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.insertOrEditCNHTitle)
    protected TextView title;

    @Bind(R.id.insertOrEditDriverName)
    protected EditText name;

    @Bind(R.id.insertOrEditCNH)
    protected EditText cnh;

    @Bind(R.id.insertOrEditCPFValue)
    protected EditText cpf;

    @Bind(R.id.saveButton)
    protected Button saveButton;
    @Bind(R.id.deleteButton)
    protected Button deleteButton;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_or_edit_driver);
        ButterKnife.bind(this);

        initActionBar();

        ctx = this;

        driverService = new DriverServiceImpl(ctx);

        name.addTextChangedListener(new NameTextWatcher());
        cnh.addTextChangedListener(new CNHTextWatcher());
        cpf.addTextChangedListener(new MaskTextWatcher());

        Bundle bundle = getIntent().getExtras();

        if (bundle == null)
        {
            title.setText(R.string.newDriverTitlerText);
            deleteButton.setVisibility(View.GONE);
        }
        else
        {
            saveButton.setText(R.string.updateTitleText);

            driver = (Driver) bundle.getSerializable(getString(R.string.driver));

            name.setText(driver.getName());
            cnh.setText(String.valueOf(driver.getCnh()));
            cpf.setText(String.valueOf(driver.getCpf()));
        }

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                //Checar os dados

                Driver driverAux = new Driver();
                driverAux.setName(name.getText().toString());
                driverAux.setCnh(Long.valueOf(cnh.getText().toString()));
                driverAux.setCpf(cpf.getText().toString());

                if (driver == null)
                {
                    driverService.save(driverAux);
                }
                else
                {
                    driverAux.setId(driver.getId());
                    driverService.update(driverAux);
                }

                setResult(RESULT_OK, new Intent(ctx, HomeActivity.class));
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                DialogInterface.OnClickListener positiveButton = new DialogInterface.OnClickListener()
                {
                    public void onClick (DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                        driverService.delete(driver);
                        setResult(RESULT_OK, new Intent(ctx, HomeActivity.class));
                        finish();
                    }
                };
                DialogInterface.OnClickListener negativeButton = new DialogInterface.OnClickListener()
                {
                    public void onClick (DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                };
                alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.are_you_sure) + " ?", positiveButton, negativeButton);
                alertDialog.show();
            }
        });

    }

    private void initActionBar ()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private class CNHTextWatcher implements TextWatcher
    {
        @Override
        public void onTextChanged (CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void beforeTextChanged (CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void afterTextChanged (Editable s)
        {
            if (cnh.getText().length() == 11)
            {
                cpf.requestFocus();
            }
        }
    }

    private class MaskTextWatcher implements TextWatcher
    {
        boolean isUpdating;
        String old = "";

        @Override
        public void beforeTextChanged (CharSequence s, int start, int count, int after)
        {

        }

        public void onTextChanged (CharSequence s, int start, int before, int count)
        {
            String str = this.unmask(s.toString());
            String mascara = "";
            if (isUpdating)
            {
                old = str;
                isUpdating = false;
                return;
            }
            int i = 0;
            for (char m : Constants.CPF_MASK.toCharArray())
            {
                if (m != '#' && str.length() > old.length())
                {
                    mascara += m;
                    continue;
                }
                try
                {
                    mascara += str.charAt(i);
                } catch (Exception e)
                {
                    break;
                }
                i++;
            }
            isUpdating = true;
            cpf.setText(mascara);
            cpf.setSelection(mascara.length());
        }

        public void afterTextChanged (Editable s)
        {
        }

        public String unmask (String s)
        {
            return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[)]", "");
        }
    }

    private class NameTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged (CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged (CharSequence s, int start, int before, int count)
        {
            if (name.getText().length() == 15)
            {
                cnh.requestFocus();
            }
        }

        @Override
        public void afterTextChanged (Editable s)
        {

        }
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
