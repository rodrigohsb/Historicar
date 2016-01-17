package com.historicar.app.event;

/**
 * Created by Rodrigo on 16/01/16.
 */
public class LoadTicketRetryEvent
{
    private boolean isInvalidCode = false;

    public LoadTicketRetryEvent (boolean isInvalidCode)
    {
        this.isInvalidCode = isInvalidCode;
    }

    public boolean isInvalidCode ()
    {
        return isInvalidCode;
    }
}
