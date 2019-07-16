package com.zz.recycleviewmvptest.database;

import android.annotation.SuppressLint;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zz.recycleviewmvptest.base.BaseApplication;
import com.zz.recycleviewmvptest.bean.DaoMaster;
import com.zz.recycleviewmvptest.bean.DaoSession;

public abstract class CommonCacheImpl<T> implements IDataBaseOperate<T> {
    @SuppressLint("StaticFieldLeak")
    private static final UpDBHelper sUpDBHelper = new UpDBHelper(BaseApplication.getContext(), DBConfig.DB_NAME);

    public CommonCacheImpl() {
    }

    /**
     * 获取可读数据库
     */
    protected SQLiteDatabase getReadableDatabase() {
        return sUpDBHelper.getReadableDatabase();

    }

    /**
     * 获取可写数据库
     */
    protected SQLiteDatabase getWritableDatabase() {
        return sUpDBHelper.getWritableDatabase();
    }

    /**
     * 获取可写数据库的DaoMaster
     */
    protected DaoMaster getWDaoMaster() {
        return new DaoMaster(getWritableDatabase());
    }

    /**
     * 获取可写数据库的DaoSession
     */
    protected DaoSession getWDaoSession() {
        return getWDaoMaster().newSession();
    }

    /**
     * 获取可写数据库的DaoMaster
     */
    protected DaoMaster getRDaoMaster() {
        return new DaoMaster(getWritableDatabase());
    }

    /**
     * 获取可写数据库的DaoSession
     */
    protected DaoSession getRDaoSession() {
        return getRDaoMaster().newSession();
    }
}
