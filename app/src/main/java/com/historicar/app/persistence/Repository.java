package com.historicar.app.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.historicar.app.bean.Carro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 02/05/15.
 */
public class Repository extends SQLiteOpenHelper
{
    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS CAR";

    private static final String SCRIPT_DATABASE_CREATE = "create table CAR (_id integer primary key autoincrement, DESCRIPTION varchar not null, PLATE varchar not null, TYPE int null)";

    private static final String DATABASE_NAME = "historicar";

    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "CAR";

    private final SQLiteDatabase db;

    public Repository (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    public void save (Carro carro)
    {
        ContentValues values = new ContentValues();

        values.put(Carro.CarDB.DESCRIPTION, carro.getDescription());
        values.put(Carro.CarDB.PLATE, carro.getPlaca().toUpperCase());
        values.put(Carro.CarDB.TYPE, carro.getType());

        db.insert(TABLE_NAME, null, values);
        close();
    }

    public List<Carro> getAll ()
    {
        Cursor c = getCursor();

        List<Carro> carros = new ArrayList<>();

        if (c != null && c.moveToFirst())
        {
            int idxId = c.getColumnIndex(Carro.CarDB._ID);
            int idxDescription = c.getColumnIndex(Carro.CarDB.DESCRIPTION);
            int idxPlate = c.getColumnIndex(Carro.CarDB.PLATE);
            int idxType = c.getColumnIndex(Carro.CarDB.TYPE);
            do
            {
                Carro carro = new Carro();
                carro.setId(c.getLong(idxId));
                carro.setDescription(c.getString(idxDescription));
                carro.setPlaca(c.getString(idxPlate).toUpperCase());
                carro.setType(c.getInt(idxType));
                carros.add(carro);

            } while (c.moveToNext());
            c.close();
        }
        return carros;
    }

    public void update (Carro carro)
    {
        ContentValues values = new ContentValues();

        values.put(Carro.CarDB.DESCRIPTION, carro.getDescription());
        values.put(Carro.CarDB.PLATE, carro.getPlaca().toUpperCase());
        values.put(Carro.CarDB.TYPE, carro.getType());

        db.update(TABLE_NAME, values, "_id " + "=" + carro.getId(), null);
        close();
    }

    public void delete (Carro carro)
    {
        db.execSQL("delete from " + TABLE_NAME + " where _ID='" + carro.getId() + "'");
        close();
    }

    private Cursor getCursor ()
    {
        try
        {
            return db.rawQuery("select * from " + TABLE_NAME, null);
        } catch (Exception e)
        {
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
