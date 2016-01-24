package com.historicar.app.bean;

import android.provider.BaseColumns;

import java.io.Serializable;

/**
 * Created by Rodrigo on 23/01/16.
 */
public class Driver implements Serializable
{

    private long id;

    private String name;

    private long cnh;

    private String cpf;

    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public long getCnh ()
    {
        return cnh;
    }

    public void setCnh (long cnh)
    {
        this.cnh = cnh;
    }

    public String getCpf ()
    {
        return cpf;
    }

    public void setCpf (String cpf)
    {
        this.cpf = cpf;
    }

    @Override
    public String toString ()
    {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cnh=" + cnh +
                ", cpf=" + cpf +
                '}';
    }

    public static final class DriverDB implements BaseColumns
    {

        public static final String NAME = "NAME";

        public static final String CNH = "CNH";

        public static final String CPF = "CPF";

    }
}
