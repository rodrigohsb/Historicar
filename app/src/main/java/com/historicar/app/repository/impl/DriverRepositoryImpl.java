package com.historicar.app.repository.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.historicar.app.bean.Driver;
import com.historicar.app.repository.DriverRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 23/01/16.
 */
public class DriverRepositoryImpl extends SQLiteOpenHelper implements DriverRepository
{
    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS DRIVER";

    private static final String SCRIPT_DATABASE_CREATE = "create table DRIVER (_id integer primary key autoincrement, DESCRIPTION varchar not null, PLATE varchar not null, TYPE int null)";

    private static final String DATABASE_NAME = "historicar";

    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "DRIVER";

    private final SQLiteDatabase db;

    public DriverRepositoryImpl (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate (SQLiteDatabase db)
    {
        db.execSQL(SCRIPT_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SCRIPT_DATABASE_DELETE);
        onCreate(db);
    }

    @Override
    public boolean save (Driver driver)
    {
        return false;
    }

    @Override
    public List<Driver> getAll ()
    {
        return new ArrayList<>();
    }

    @Override
    public boolean update (Driver driver)
    {
        return false;
    }

    @Override
    public boolean delete (Driver driver)
    {
        return false;
    }
}
