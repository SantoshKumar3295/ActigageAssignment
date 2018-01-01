package com.actigageassignment;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.actigageassignment.database.DaoMaster;
import com.actigageassignment.database.DaoSession;

/**
 * Created by santo on 1/1/18.
 * <p>
 * Initialize application's library
 */

public class ApplicationLoader extends Application {


    public DaoSession daoSession;
    private static ApplicationLoader mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
        mInstance = this;
    }

    public static synchronized ApplicationLoader getInstance() {
        return mInstance;
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "actigage-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
