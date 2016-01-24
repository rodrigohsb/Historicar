package com.historicar.app.repository;

import com.historicar.app.bean.Carro;

import java.util.List;

/**
 * Created by Rodrigo on 23/01/16.
 */
public interface VehicleRepository
{

    boolean save (Carro carro);

    List<Carro> getAll ();

    boolean update (Carro carro);

    boolean delete (Carro carro);
}
