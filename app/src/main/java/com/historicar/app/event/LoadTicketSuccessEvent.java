package com.historicar.app.event;

import com.historicar.app.bean.Multa;

import java.util.List;

/**
 * Created by Rodrigo on 16/01/16.
 */
public class LoadTicketSuccessEvent
{

    List<Multa> multas;

    public LoadTicketSuccessEvent (List<Multa> multas)
    {
        this.multas = multas;
    }

    public List<Multa> getMultas ()
    {
        return multas;
    }

    public void setMultas (List<Multa> multas)
    {
        this.multas = multas;
    }
}
