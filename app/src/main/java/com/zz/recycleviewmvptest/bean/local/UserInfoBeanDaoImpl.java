package com.zz.recycleviewmvptest.bean.local;

import android.app.Application;

import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBeanDao;
import com.zz.recycleviewmvptest.database.CommonCacheImpl;

import java.util.List;

public class UserInfoBeanDaoImpl extends CommonCacheImpl<UserInfoBean> {

    public UserInfoBeanDaoImpl() {
    }
    @Override
    public long saveSingleData(UserInfoBean singleData) {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        return userInfoBeanDao.insert(singleData);
    }

    @Override
    public void saveMultiData(List<UserInfoBean> multiData) {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        userInfoBeanDao.insertOrReplaceInTx(multiData);
    }

    @Override
    public boolean isInvalide() {
        return false;
    }

    @Override
    public UserInfoBean getSingleDataFromCache(Long primaryKey) {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        return userInfoBeanDao.load(primaryKey);
    }

    @Override
    public List<UserInfoBean> getMultiDataFromCache() {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        return userInfoBeanDao.queryBuilder()
                .orderDesc(UserInfoBeanDao.Properties.Create_time)
                .list();
    }

    @Override
    public void clearTable() {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        userInfoBeanDao.deleteAll();
    }

    @Override
    public void deleteSingleCache(Long primaryKey) {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        userInfoBeanDao.deleteByKey(primaryKey);
    }

    @Override
    public void deleteSingleCache(UserInfoBean dta) {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        userInfoBeanDao.delete(dta);
    }

    @Override
    public void updateSingleData(UserInfoBean newData) {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        userInfoBeanDao.update(newData);
    }

    @Override
    public long insertOrReplace(UserInfoBean newData) {
        UserInfoBeanDao userInfoBeanDao = getWDaoSession().getUserInfoBeanDao();
        return userInfoBeanDao.insertOrReplace(newData);
    }

    public List<UserInfoBean> getAllList() {
        return getWDaoSession().getUserInfoBeanDao().queryBuilder()
                .orderAsc(UserInfoBeanDao.Properties.Create_time)
                .list();
    }

    public UserInfoBean pwdLogin(String phone, String pwd) {
        List<UserInfoBean> userInfoBeans = getWDaoSession().getUserInfoBeanDao().queryBuilder()
                .orderAsc(UserInfoBeanDao.Properties.Create_time)
                .where(UserInfoBeanDao.Properties.Phone.eq(phone))
                .where(UserInfoBeanDao.Properties.Password.eq(pwd))
                .list();
        if (userInfoBeans != null && userInfoBeans.size() > 0) {
            return userInfoBeans.get(0);
        }
        return null;
    }

    public UserInfoBean selectByPhone(String phone) {
        List<UserInfoBean> userInfoBeans = getWDaoSession().getUserInfoBeanDao().queryBuilder()
                .orderAsc(UserInfoBeanDao.Properties.Create_time)
                .where(UserInfoBeanDao.Properties.Phone.eq(phone))
                .list();
        if (userInfoBeans != null && userInfoBeans.size() > 0) {
            return userInfoBeans.get(0);
        }
        return null;
    }
}
