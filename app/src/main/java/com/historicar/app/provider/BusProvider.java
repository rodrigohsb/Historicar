package com.historicar.app.provider;

import com.squareup.otto.Bus;

/**
 * Created by Rodrigo on 16/01/16.
 */
public class BusProvider
{
    private static Bus mBus;

    public static Bus getInstance()
    {
        if(mBus == null)
        {
            mBus = new Bus();
        }
        return mBus;
    }
}
