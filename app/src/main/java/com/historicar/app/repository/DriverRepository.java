package com.historicar.app.repository;

import com.historicar.app.bean.Carro;
import com.historicar.app.bean.Driver;

import java.util.List;

/**
 * Created by Rodrigo on 23/01/16.
 */
public interface DriverRepository
{

    boolean save (Driver driver);

    List<Driver> getAll ();

    boolean update (Driver driver);

    boolean delete (Driver driver);

}
