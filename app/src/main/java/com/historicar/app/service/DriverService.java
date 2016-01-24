package com.historicar.app.service;

import com.historicar.app.bean.Driver;

import java.util.List;

/**
 * Created by Rodrigo on 24/01/16.
 */
public interface DriverService
{

    boolean save (Driver driver);

    boolean update (Driver driver);

    boolean delete (Driver driver);

    List<Driver> getAll ();
}
