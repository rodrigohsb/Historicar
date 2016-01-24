package com.historicar.app.service.impl;

import android.content.Context;

import com.historicar.app.bean.Driver;
import com.historicar.app.repository.DriverRepository;
import com.historicar.app.repository.impl.DriverRepositoryImpl;
import com.historicar.app.service.DriverService;

import java.util.List;

/**
 * Created by Rodrigo on 24/01/16.
 */
public class DriverServiceImpl implements DriverService
{
    private DriverRepository driverRepository;

    public DriverServiceImpl (Context ctx)
    {
        driverRepository = new DriverRepositoryImpl(ctx);
    }

    @Override
    public boolean save (Driver driver)
    {
        return driverRepository.save(driver);
    }

    @Override
    public boolean update (Driver driver)
    {
        return driverRepository.update(driver);
    }

    @Override
    public boolean delete (Driver driver)
    {
        return driverRepository.delete(driver);
    }

    @Override
    public List<Driver> getAll ()
    {
        return driverRepository.getAll();
    }
}
