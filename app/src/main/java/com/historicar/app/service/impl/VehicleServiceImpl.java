package com.historicar.app.service.impl;

import android.content.Context;

import com.historicar.app.bean.Carro;
import com.historicar.app.repository.VehicleRepository;
import com.historicar.app.repository.impl.VehicleRepositoryImpl;
import com.historicar.app.service.VehicleService;

import java.util.List;

/**
 * Created by Rodrigo on 24/01/16.
 */
public class VehicleServiceImpl implements VehicleService
{

    private VehicleRepository vehicleRepository;

    public VehicleServiceImpl (Context ctx)
    {
        vehicleRepository = new VehicleRepositoryImpl(ctx);
    }

    @Override
    public boolean save (Carro carro)
    {
        return vehicleRepository.save(carro);
    }

    @Override
    public boolean update (Carro carro)
    {
        return vehicleRepository.update(carro);
    }

    @Override
    public boolean delete (Carro carro)
    {
        return vehicleRepository.delete(carro);
    }

    @Override
    public List<Carro> getAll ()
    {
        return vehicleRepository.getAll();
    }
}
