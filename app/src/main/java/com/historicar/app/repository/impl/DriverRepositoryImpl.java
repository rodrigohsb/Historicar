package com.historicar.app.repository.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    private static final String SCRIPT_DATABASE_CREATE = "create table DRIVER (_id integer primary key autoincrement, NAME varchar not null, CNH integer not null, CPF varchar not null)";

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
    public boolean save (Driver driver)
    {
        try
        {
            ContentValues values = new ContentValues();

            values.put(Driver.DriverDB.NAME, driver.getName());
            values.put(Driver.DriverDB.CNH, driver.getCnh());
            values.put(Driver.DriverDB.CPF, driver.getCpf());

            db.insert(TABLE_NAME, null, values);
            close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Driver> getAll ()
    {
        Cursor c = getCursor();

        List<Driver> drivers = new ArrayList<>();

        if (c != null && c.moveToFirst())
        {
            int idxId = c.getColumnIndex(Driver.DriverDB._ID);
            int idxName = c.getColumnIndex(Driver.DriverDB.NAME);
            int idxCNH = c.getColumnIndex(Driver.DriverDB.CNH);
            int idxCPF = c.getColumnIndex(Driver.DriverDB.CPF);
            do
            {
                Driver driver = new Driver();
                driver.setId(c.getLong(idxId));
                driver.setName(c.getString(idxName));
                driver.setCnh(c.getLong(idxCNH));
                driver.setCpf(c.getString(idxCPF));
                drivers.add(driver);

            } while (c.moveToNext());
            c.close();
        }
        return drivers;
    }

    @Override
    public boolean update (Driver driver)
    {
        try
        {

            ContentValues values = new ContentValues();

            values.put(Driver.DriverDB.NAME, driver.getName());
            values.put(Driver.DriverDB.CNH, driver.getCnh());
            values.put(Driver.DriverDB.CPF, driver.getCpf());

            db.update(TABLE_NAME, values, "_id " + "=" + driver.getId(), null);
            close();
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean delete (Driver driver)
    {
        try
        {
            db.execSQL("delete from " + TABLE_NAME + " where _ID='" + driver.getId() + "'");
            close();
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    private Cursor getCursor ()
    {
        try
        {
            return db.rawQuery("select * from " + TABLE_NAME, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public void close ()
    {
        if (db != null)
        {
            db.close();
        }
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
}
