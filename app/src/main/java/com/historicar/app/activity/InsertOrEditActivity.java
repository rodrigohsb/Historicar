package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.historicar.app.R;
import com.historicar.app.bean.Carro;
import com.historicar.app.contants.Constants;
import com.historicar.app.persistence.Repository;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rodrigo on 29/04/15.
 */
public class InsertOrEditActivity extends AppCompatActivity
{

    private AlertDialog alertDialog;

    private Context ctx;

    private Carro carro;

    private Repository repository;

    @Bind(R.id.insertOrEditTitle) protected TextView textView;
    @Bind(R.id.insertOrEditPlacaDescriptionValue) protected EditText descriptionValue;
    @Bind(R.id.insertOrEditPlacaLetras) protected EditText placaLetras;
    @Bind(R.id.insertOrEditPlacaNumeros) protected EditText placaNumeros;
    @Bind(R.id.saveButton) protected Button saveButton;
    @Bind(R.id.deleteButton) protected Button deleteButton;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_or_edit);
        ButterKnife.bind(this);

        Appodeal.initialize(this, getString(R.string.appodeal_key), Appodeal.INTERSTITIAL | Appodeal.BANNER);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
        Appodeal.setTesting(true);

        ctx = this;

        repository = new Repository(ctx);

        Bundle bundle = getIntent().getExtras();

        descriptionValue.addTextChangedListener(new DescriptionTextWatcher());
        placaLetras.addTextChangedListener(new LetterTextWatcher());
        placaNumeros.addTextChangedListener(new NumberTextWatcher());

        if (bundle == null)
        {
            textView.setText(R.string.newPlateText);
            deleteButton.setVisibility(View.GONE);
        }
        else
        {
            saveButton.setText(R.string.updatePlateText);

            carro = (Carro) bundle.getSerializable(getString(R.string.carro));

            descriptionValue.setText(carro.getDescription());

            String placa = carro.getPlaca().replaceAll(" - ", "");

            placaLetras.setText(placa.substring(0, 3));

            placaNumeros.setText(placa.substring(3, 7));
        }

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {

                if (!isValidDescription(descriptionValue.getText().toString()))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_description), button);
                    alertDialog.show();
                }
                else if (!isValidPlacaLetras(placaLetras.getText().toString()))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_plate), button);
                    alertDialog.show();
                }
                else if (!isValidPlacaNumeros(placaNumeros.getText().toString()))
                {
                    DialogInterface.OnClickListener button = new DialogInterface.OnClickListener()
                    {
                        public void onClick (DialogInterface dialog, int id)
                        {
                            dialog.dismiss();
                        }
                    };
                    alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.invalid_plate_number), button);
                    alertDialog.show();
                }
                else
                {
                    Carro carroAux = new Carro(placaLetras.getText().toString() + " - " + placaNumeros.getText().toString(), descriptionValue.getText().toString());

                    Intent it = new Intent(ctx, HomeActivity.class);
                    
                    if (carro == null)
                    {
                        repository.save(carroAux);
                    }
                    else
                    {
                        carroAux.setId(carro.getId());
                        repository.update(carroAux);
                        it.putExtra("old", carro);
                        it.putExtra("update", true);
                    }
                    
                    it.putExtra(getString(R.string.carro), carroAux);
                    setResult(RESULT_OK, it);
                    finish();
                }
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
                        repository.delete(carro);
                        Intent it = new Intent(ctx, HomeActivity.class);
                        it.putExtra(getString(R.string.carro), carro);
                        it.putExtra("delete", true);
                        setResult(RESULT_OK, it);
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
                alertDialog = new AlertUtils(ctx).getAlertDialog(getString(R.string.are_you_sure) + " " + carro.getPlaca() + " ?", positiveButton, negativeButton);
                alertDialog.show();
            }
        });
    }

    public class DescriptionTextWatcher implements TextWatcher
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (descriptionValue.getText().length() == 10)
            {
                placaLetras.requestFocus();
            }
        }
    }

    public class LetterTextWatcher implements TextWatcher
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (placaLetras.getText().length() == 3)
            {
                placaNumeros.requestFocus();
            }
        }
    }

    public class NumberTextWatcher implements TextWatcher
    {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (placaLetras.getText().length() == 4)
            {
                saveButton.setEnabled(true);
            }
        }
    }


    private boolean isValidDescription (String description)
    {
        return description.trim().length() != 0;
    }

    private boolean isValidPlacaLetras (String letras)
    {
        return ValidateUtils.validateLetters(letras);
    }

    private boolean isValidPlacaNumeros (String numeros)
    {
        if (ValidateUtils.validateNumbers(numeros))
        {
            try
            {
                Double.parseDouble(numeros);

            } catch (NumberFormatException nfe)
            {
                return false;
            }
            return true;
        }
        return false;

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

                if(InsertOrEditActivity.this.getCurrentFocus() != null)
                {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(InsertOrEditActivity.this.getCurrentFocus().getWindowToken(), 0);
                }

                Intent myIntent = new Intent(ctx, ResultActivity.class);
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
            case R.id.action_insert_or_edit:
                startActivity(new Intent(ctx, InsertOrEditActivity.class));
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume ()
    {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
    }
}
