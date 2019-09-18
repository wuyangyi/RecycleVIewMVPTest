package com.zz.recycleviewmvptest.mvp.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zz.recycleviewmvptest.R;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mContanierFragment.onActivityResult(requestCode, resultCode, data);
    }
}
