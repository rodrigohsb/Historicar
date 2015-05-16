package com.historicar.app.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.historicar.app.contants.Constants;

/**
 * Created by Rodrigo on 19/04/15.
 */
public class AlertUtils
{
    private final Context context;

    public AlertUtils (Context context)
    {
        this.context = context;
    }

    public AlertDialog getAlertDialog (String message, DialogInterface.OnClickListener button)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(Constants.WARNING);

        if (message != null)
        {
            builder.setMessage(message);
        }

        builder.setPositiveButton(Constants.OK, button);
        return builder.create();
    }

    public AlertDialog getAlertDialog (String message, DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(Constants.WARNING);
        if (message != null)
        {
            builder.setMessage(message);
        }

        if (negativeButton == null)
        {
            builder.setPositiveButton(Constants.OK, positiveButton);
        }
        else
        {
            builder.setPositiveButton(Constants.YES, positiveButton);
            builder.setNegativeButton(Constants.NO, negativeButton);
        }

        return builder.create();
    }


}
