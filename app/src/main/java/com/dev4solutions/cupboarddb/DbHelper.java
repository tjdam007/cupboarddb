package com.dev4solutions.cupboarddb;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dev4solutions.cupboarddb.model.Data;

import java.util.ArrayList;

import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * The type Database SQLite Helper.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = "Database";
    private static final String DATABASE_NAME = "main.db";
    private static final int DATABASE_VERSION = 1;
    /**
     * The constant DESC.
     */
    public static final String DESC = "DESC";
    /**
     * The constant ASC.
     */
    public static final String ASC = "ASC";

    private static DbHelper dbHelper = null;
    private static SQLiteDatabase db = null;

    /**
     * @param context
     */
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static DbHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
            openConnection();
        }
        return dbHelper;
    }
    // will be called only once when singleton is created

    private static void openConnection() {
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }
    }
    // be sure to call this method by: DatabaseHelper.getInstance.closeConnection() when application is closed by    somemeans most likely
    // onDestroy method of application

    /**
     * Close connection.
     */
    public synchronized void closeConnection() {
        if (dbHelper != null) {
            dbHelper.close();
            db.close();
            dbHelper = null;
            db = null;
        }
    }

    static {
        // register our models
        CupboardFactory.setCupboard(new CupboardBuilder().useAnnotations().build());
        // Register
        cupboard().register(Data.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // this will ensure that all tables are created

        cupboard().withDatabase(db).createTables();
        // add indexes and other database tweaks in this method if you want
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this will upgrade tables, adding columns and new tables.
        // Note that existing columns will not be converted
        cupboard().withDatabase(db).upgradeTables();
        // do migration work if you have an alteration to make to your schema here
    }

    /**
     * Insert Data
     * @param data  instance of {@link Data}
     */
    public void insertOrUpdateData(Data data){
        cupboard().withDatabase(db).put(data);
    }

    public void insertOrUpdateData(ArrayList<Data> datas){
        cupboard().withDatabase(db).put(datas);
    }

    public void deleteData(Data data){
        cupboard().withDatabase(db).delete(data);
    }

    public void deleteAllData(){
        cupboard().withDatabase(db).delete(Data.class,null);
    }
}

