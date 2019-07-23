package com.zz.recycleviewmvptest.mvp.chat;

import android.util.Log;

import com.zz.recycleviewmvptest.base.BasePresenter;
import com.zz.recycleviewmvptest.bean.Ask;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.bean.TLInputBean;
import com.zz.recycleviewmvptest.bean.TLOutputBean;
import com.zz.recycleviewmvptest.bean.Take;
import com.zz.recycleviewmvptest.bean.local.ChatBeanDaoImpl;
import com.zz.recycleviewmvptest.network.RequestRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 聊天
 */
public class ChatPresenter extends BasePresenter<ChatContract.View> implements ChatContract.Presenter {
    private ChatBeanDaoImpl mChatBeanDaoImpl;
    private RequestRepository mRequestRepository;

    public ChatPresenter(ChatContract.View rootView) {
        super(rootView);
        mChatBeanDaoImpl = new ChatBeanDaoImpl();
        mRequestRepository = new RequestRepository();
    }

    @Override
    public void getChatInfoData(FlListBean.ResultsListBean user) {
        mRootView.getChatInfoData(mChatBeanDaoImpl.getAllList(user));
    }

    @Override
    public void sendMessage(String content) {
        ChatBean chatBean = new ChatBean(content);
        chatBean.setUser(mRootView.getUser());
        chatBean.setIsMe(true);
        chatBean.setUserId(mRootView.getUser().get_id());
        mChatBeanDaoImpl.insertOrReplace(chatBean);
        mRootView.sendMessageSuccess(chatBean);

        Ask ask = new Ask();
        Ask.UserInfoBean info = new Ask.UserInfoBean();
        info.setApiKey("73808218b0544165a57901cb4c4cf5f7");//将机器人的key值填入
        info.setUserId("484225");//将用户id填入
        ask.setUserInfo(info);
        Ask.PerceptionBean.InputTextBean pre = new Ask.PerceptionBean.InputTextBean(content);//将要发送给机器人书文本天趣
        ask.setPerception(new Ask.PerceptionBean(pre));
        mRequestRepository.sendMessage(ask)
                .enqueue(new Callback<Take>() {
                    @Override
                    public void onResponse(Call<Take> call, Response<Take> response) {
//                        ChatBean chatBean = new ChatBean(response.body().getResults().get(0).getValues().getText());
//                        chatBean.setUser(mRootView.getUser());
//                        chatBean.setIsMe(false);
//                        chatBean.setUserId(mRootView.getUser().get_id());
//                        mChatBeanDaoImpl.insertOrReplace(chatBean);
//                        mRootView.sendMessageSuccess(chatBean);
                        Log.d("输出", response.toString());
                    }

                    @Override
                    public void onFailure(Call<Take> call, Throwable t) {

                    }
                });
    }
}
