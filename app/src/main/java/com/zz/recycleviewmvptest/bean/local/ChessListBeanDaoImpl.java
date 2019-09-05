package com.zz.recycleviewmvptest.bean.local;

import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.bean.ChatBeanDao;
import com.zz.recycleviewmvptest.bean.ChessListBean;
import com.zz.recycleviewmvptest.bean.ChessListBeanDao;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.database.CommonCacheImpl;

import java.util.List;

/**
 * 聊天的缓存
 */
public class ChessListBeanDaoImpl extends CommonCacheImpl<ChessListBean> {

    public ChessListBeanDaoImpl() {
    }
    @Override
    public long saveSingleData(ChessListBean singleData) {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        return chessListBeanDao.insert(singleData);
    }

    @Override
    public void saveMultiData(List<ChessListBean> multiData) {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        chessListBeanDao.insertOrReplaceInTx(multiData);
    }

    @Override
    public boolean isInvalide() {
        return false;
    }

    @Override
    public ChessListBean getSingleDataFromCache(Long primaryKey) {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        return chessListBeanDao.load(primaryKey);
    }

    @Override
    public List<ChessListBean> getMultiDataFromCache() {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        return chessListBeanDao.queryBuilder()
                .orderDesc(ChatBeanDao.Properties.Create_time)
                .list();
    }

    @Override
    public void clearTable() {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        chessListBeanDao.deleteAll();
    }

    @Override
    public void deleteSingleCache(Long primaryKey) {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        chessListBeanDao.deleteByKey(primaryKey);
    }

    @Override
    public void deleteSingleCache(ChessListBean dta) {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        chessListBeanDao.delete(dta);
    }

    @Override
    public void updateSingleData(ChessListBean newData) {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        chessListBeanDao.update(newData);
    }

    @Override
    public long insertOrReplace(ChessListBean newData) {
        ChessListBeanDao chessListBeanDao = getWDaoSession().getChessListBeanDao();
        return chessListBeanDao.insertOrReplace(newData);
    }

    public List<ChessListBean> getAllList() {
        return getWDaoSession().getChessListBeanDao().queryBuilder()
                .orderAsc(ChessListBeanDao.Properties.Create_time)
                .list();
    }
}
