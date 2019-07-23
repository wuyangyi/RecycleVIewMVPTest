package com.zz.recycleviewmvptest.bean.local;

import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.bean.ChatBeanDao;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBeanDao;
import com.zz.recycleviewmvptest.database.CommonCacheImpl;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 聊天的缓存
 */
public class ChatBeanDaoImpl extends CommonCacheImpl<ChatBean> {

    public ChatBeanDaoImpl() {
    }
    @Override
    public long saveSingleData(ChatBean singleData) {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        return chatBeanDao.insert(singleData);
    }

    @Override
    public void saveMultiData(List<ChatBean> multiData) {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        chatBeanDao.insertOrReplaceInTx(multiData);
    }

    @Override
    public boolean isInvalide() {
        return false;
    }

    @Override
    public ChatBean getSingleDataFromCache(Long primaryKey) {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        return chatBeanDao.load(primaryKey);
    }

    @Override
    public List<ChatBean> getMultiDataFromCache() {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        return chatBeanDao.queryBuilder()
                .orderDesc(ChatBeanDao.Properties.Create_time)
                .list();
    }

    @Override
    public void clearTable() {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        chatBeanDao.deleteAll();
    }

    @Override
    public void deleteSingleCache(Long primaryKey) {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        chatBeanDao.deleteByKey(primaryKey);
    }

    @Override
    public void deleteSingleCache(ChatBean dta) {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        chatBeanDao.delete(dta);
    }

    @Override
    public void updateSingleData(ChatBean newData) {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        chatBeanDao.update(newData);
    }

    @Override
    public long insertOrReplace(ChatBean newData) {
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        return chatBeanDao.insertOrReplace(newData);
    }

    public List<ChatBean> getAllList(FlListBean.ResultsListBean user) {
        return getWDaoSession().getChatBeanDao().queryBuilder()
                .where(ChatBeanDao.Properties.UserId.eq(user.get_id()))
                .orderAsc(ChatBeanDao.Properties.Create_time)
                .list();
    }

    /**
     * 保存记录
     */
    public void saveData(ChatBean chatBean){
        ChatBeanDao chatBeanDao = getWDaoSession().getChatBeanDao();
        chatBeanDao.insert(chatBean);
    }
}
