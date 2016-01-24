package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.bean.Driver;
import com.historicar.app.contants.Constants;
import com.historicar.app.repository.DriverRepository;
import com.historicar.app.repository.impl.DriverRepositoryImpl;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 29/04/15.
 */
public class InsertOrEditDriverActivity extends AppCompatActivity
{

    private AlertDialog alertDialog;

    private DriverRepository driverRepository;

    private Driver driver;

    private Context ctx;

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.insertOrEditCNHTitle)
    protected TextView title;

    @Bind(R.id.insertOrEditCNHValue)
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
        setContentView(R.layout.activity_insert_or_edit_points);
        ButterKnife.bind(this);

        initActionBar();

        ctx = this;

        driverRepository = new DriverRepositoryImpl(ctx);

        cnh.addTextChangedListener(new CNHTextWatcher());
//        cpf.addTextChangedListener(new MaskTextWatcher());

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

            cnh.setText(String.valueOf(driver.getCnh()));
            cpf.setText(String.valueOf(driver.getCpf()));
        }

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                //Checar os dados
                driverRepository.save(driver);

                Driver driverAux = new Driver();
                driverAux.setCnh(Long.valueOf(cnh.getText().toString()));
                driverAux.setCpf(Long.valueOf(cpf.getText().toString()));

                if (driver == null)
                {
                    driverRepository.save(driverAux);
                }
                else
                {
                    driverAux.setId(driver.getId());
                    driverRepository.update(driverAux);
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
                        driverRepository.delete(driver);
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

    public class CNHTextWatcher implements TextWatcher
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

    public class MaskTextWatcher implements TextWatcher
    {
        boolean isUpdating;
        String old = "";

        @Override
        public void beforeTextChanged (CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged (CharSequence s, int start, int before, int count)
        {
            String str = "";
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

        @Override
        public void afterTextChanged (Editable s)
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.hint_example));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit (String s)
            {
                if (!ValidateUtils.isOnline(ctx))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    AlertDialog alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_connection), button);
                    alertDialog.show();
                    return false;
                }
                else if (!ValidateUtils.validatePlate(s))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    AlertDialog alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_plate), button);
                    alertDialog.show();
                    return false;
                }

                if (InsertOrEditDriverActivity.this.getCurrentFocus() != null)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(InsertOrEditDriverActivity.this.getCurrentFocus().getWindowToken(), 0);
                }

                Intent myIntent = new Intent(ctx, CaptchaActivity.class);
                myIntent.putExtra(Constants.PLACA_KEY, s);
                startActivity(myIntent);
                finish();
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

        return true;
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
