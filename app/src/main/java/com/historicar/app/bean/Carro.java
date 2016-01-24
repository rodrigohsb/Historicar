package com.historicar.app.bean;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by Rodrigo on 16/04/15.
 */
public class Carro implements Serializable
{

    private long id;

    private String placa;

    private String description;

    private int type;

    public Carro ()
    {
    }

    public Carro (String placa, String description)
    {
        super();
        this.placa = placa;
        this.description = description;
    }

    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    public String getPlaca ()
    {
        return placa;
    }

    public void setPlaca (String placa)
    {
        this.placa = placa;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public int getType ()
    {
        return type;
    }

    public void setType (int type)
    {
        this.type = type;
    }

    @Override
    public String toString ()
    {
        return "Carro{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

    public static final class CarDB implements BaseColumns
    {

        public static final String DESCRIPTION = "DESCRIPTION";

        public static final String PLATE = "PLATE";

        public static final String TYPE = "TYPE";

    }
}
