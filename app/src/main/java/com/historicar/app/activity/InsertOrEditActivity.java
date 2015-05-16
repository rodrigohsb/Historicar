package com.historicar.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.historicar.app.R;
import com.historicar.app.bean.Carro;
import com.historicar.app.contants.Constants;
import com.historicar.app.persistence.Repository;
import com.historicar.app.util.AlertUtils;
import com.historicar.app.util.ValidateUtils;

/**
 * Created by Rodrigo on 29/04/15.
 */
public class InsertOrEditActivity extends ActionBarActivity
{

    private AlertDialog alertDialog;

    private Context ctx;

    private Carro carro;

    private Repository repository;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_or_edit);

        ctx = this;

        repository = new Repository(ctx);

        final Bundle bundle = getIntent().getExtras();

        TextView textView = (TextView) findViewById(R.id.insertOrEditTitle);
        final EditText descriptionValue = (EditText) findViewById(R.id.insertOrEditPlacaDescriptionValue);
        final EditText placaLetras = (EditText) findViewById(R.id.insertOrEditPlacaLetras);
        final EditText placaNumeros = (EditText) findViewById(R.id.insertOrEditPlacaNumeros);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);

        if (bundle == null)
        {
            textView.setText("Nova Placa");
            deleteButton.setVisibility(View.GONE);
        }
        else
        {
            saveButton.setText("Atualizar");

            carro = (Carro) bundle.getSerializable(Constants.CARRO);

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
                    alertDialog = new AlertUtils(ctx).getAlertDialog(Constants.INVALID_DESCRIPTION, button);
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
                    alertDialog = new AlertUtils(ctx).getAlertDialog(Constants.INVALID_PLACA_LETRAS, button);
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
                    alertDialog = new AlertUtils(ctx).getAlertDialog(Constants.INVALID_PLACA_NUMEROS, button);
                    alertDialog.show();
                }
                else
                {

                    Carro carroAux = new Carro(placaLetras.getText().toString() + " - " + placaNumeros.getText().toString(), descriptionValue.getText().toString());

                    if (carro == null)
                    {
                        repository.save(carroAux);
                    }
                    else
                    {
                        carroAux.setId(carro.getId());
                        repository.update(carroAux);
                    }

                    Intent myIntent = new Intent(ctx, HomeActivity.class);
                    startActivity(myIntent);
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
                        Intent myIntent = new Intent(ctx, HomeActivity.class);
                        startActivity(myIntent);
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
                alertDialog = new AlertUtils(ctx).getAlertDialog(Constants.ARE_YOU_SURE + carro.getPlaca() + " ?", positiveButton, negativeButton);
                alertDialog.show();
            }
        });
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
    public void onBackPressed ()
    {
        finish();
        super.onBackPressed();
    }
}
