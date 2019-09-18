package com.zz.recycleviewmvptest.mvp.chat;

import com.zz.recycleviewmvptest.base.BaseView;
import com.zz.recycleviewmvptest.base.IBasePresenter;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.bean.FlListBean;

import java.util.List;

/**
 * 聊天
 */
public interface ChatContract {
    interface View extends BaseView<Presenter> {
        void getChatInfoData(List<ChatBean> data);

        void sendMessageSuccess(ChatBean chatBean);

        FlListBean.ResultsListBean getUser();
    }

    interface Presenter extends IBasePresenter {
        void getChatInfoData(FlListBean.ResultsListBean user);

        /**
         * 发布消息
         * @param content
         */
        void sendMessage(String content);

        /**
         * 发布图片
         * @param chatBean
         */
        void sendImage(ChatBean chatBean);
    }
}
