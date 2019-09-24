package com.zz.recycleviewmvptest.bean.local;

import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.MyInfoBeanDao;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBeanDao;
import com.zz.recycleviewmvptest.database.CommonCacheImpl;

import java.util.List;

public class MyInfoBeanDaoImpl extends CommonCacheImpl<MyInfoBean> {

    public MyInfoBeanDaoImpl() {
    }
    @Override
    public long saveSingleData(MyInfoBean singleData) {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        return myInfoBeanDao.insert(singleData);
    }

    @Override
    public void saveMultiData(List<MyInfoBean> multiData) {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        myInfoBeanDao.insertOrReplaceInTx(multiData);
    }

    @Override
    public boolean isInvalide() {
        return false;
    }

    @Override
    public MyInfoBean getSingleDataFromCache(Long primaryKey) {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        return myInfoBeanDao.load(primaryKey);
    }

    @Override
    public List<MyInfoBean> getMultiDataFromCache() {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        return myInfoBeanDao.queryBuilder()
                .orderDesc(MyInfoBeanDao.Properties.Create_time)
                .list();
    }

    @Override
    public void clearTable() {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        myInfoBeanDao.deleteAll();
    }

    @Override
    public void deleteSingleCache(Long primaryKey) {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        myInfoBeanDao.deleteByKey(primaryKey);
    }

    @Override
    public void deleteSingleCache(MyInfoBean dta) {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        myInfoBeanDao.delete(dta);
    }

    @Override
    public void updateSingleData(MyInfoBean newData) {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        myInfoBeanDao.update(newData);
    }

    @Override
    public long insertOrReplace(MyInfoBean newData) {
        MyInfoBeanDao myInfoBeanDao = getWDaoSession().getMyInfoBeanDao();
        return myInfoBeanDao.insertOrReplace(newData);
    }

    public List<MyInfoBean> getAllList() {
        return getWDaoSession().getMyInfoBeanDao().queryBuilder()
                .orderAsc(MyInfoBeanDao.Properties.Create_time)
                .list();
    }
}
