package com.zz.recycleviewmvptest.mvp.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zz.recycleviewmvptest.base.BaseActivity;
import com.zz.recycleviewmvptest.bean.FlListBean;

/**
 * 聊天
 */
public class ChatActivity extends BaseActivity<ChatPresenter, ChatFragment> {
    public final static String CHAT_USER_INFO = "chat_user_info";

    @Override
    protected ChatFragment getFragment() {
        return ChatFragment.newInstance(getIntent().getExtras());
    }

    public static void startToChatActivity(Context context, FlListBean.ResultsListBean user) {
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAT_USER_INFO, user);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
