package com.historicar.app.service;

import com.historicar.app.bean.Carro;

import java.util.List;

/**
 * Created by Rodrigo on 24/01/16.
 */
public interface VehicleService
{

    boolean save (Carro carro);

    boolean update (Carro carro);

    boolean delete (Carro carro);

    List<Carro> getAll ();
}
