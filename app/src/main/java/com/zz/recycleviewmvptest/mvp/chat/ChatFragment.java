package com.zz.recycleviewmvptest.mvp.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.MultiItemTypeAdapter;
import com.zz.recycleviewmvptest.widget.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.zz.recycleviewmvptest.mvp.chat.ChatActivity.CHAT_USER_INFO;

/**
 * 聊天
 */
public class ChatFragment extends BaseFragment<ChatContract.Presenter> implements ChatContract.View {
    private RecyclerView mRvList;
    private EditText mEtImport;
    private ImageView mIvHelp;
    private ImageView mIvMore;
    private TextView mBtSend;
    private RecyclerView.Adapter mAdapter;
    private List<ChatBean> mListBean = new ArrayList<>();

    private FlListBean.ResultsListBean mUser; //聊天的用户

    public static ChatFragment newInstance(Bundle bundle){
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        mRvList = rootView.findViewById(R.id.rv_list);
        mEtImport = rootView.findViewById(R.id.et_import);
        mIvHelp = rootView.findViewById(R.id.iv_help);
        mIvMore = rootView.findViewById(R.id.iv_more);
        mBtSend = rootView.findViewById(R.id.bt_send);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        mRvList.setLayoutManager(layoutManager);
        mAdapter = getAdapter();
        mRvList.setAdapter(mAdapter);
        initListener();
    }

    private void initListener() {
        mBtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendMessage(mEtImport.getText().toString());
                mEtImport.setText("");
                showButton(false);
            }
        });

        mEtImport.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showButton(!mEtImport.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Utils.hideSoftKeyboard(context, mEtImport);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 发布按钮的显示
     * @param isCanSend
     */
    private void showButton(boolean isCanSend) {
        mBtSend.setVisibility(isCanSend ? View.VISIBLE : View.GONE);
        mIvMore.setVisibility(isCanSend ? View.GONE : View.VISIBLE);
        mIvHelp.setVisibility(isCanSend ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void initData() {
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(CHAT_USER_INFO);
            setCenterTitle(mUser.getWho());
        }
        if (mPresenter == null) {
            mPresenter = new ChatPresenter(this);
        }
        mPresenter.getChatInfoData(mUser);
    }

    @Override
    protected boolean setUseCenterLoading() {
        return true;
    }

    @Override
    protected boolean showToolbar() {
        return true;
    }

    private RecyclerView.Adapter getAdapter() {
        MultiItemTypeAdapter<ChatBean> adapter = new MultiItemTypeAdapter<>(context, mListBean);
        ChatItem chatItem = new ChatItem(context);
        adapter.addItemViewDelegate(chatItem);
        ChatOtherItem chatOtherItem = new ChatOtherItem(context);
        adapter.addItemViewDelegate(chatOtherItem);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Utils.hideSoftKeyboard(context, mEtImport);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }



    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void getChatInfoData(List<ChatBean> data) {
        mListBean.addAll(data);
        mAdapter.notifyDataSetChanged();
        /**
         * 滑动到底部
         */
        mRvList.scrollToPosition(mListBean.size() - 1);
        closeLoadingView();
    }

    @Override
    public void sendMessageSuccess(ChatBean chatBean) {
        mListBean.add(chatBean);
        mAdapter.notifyDataSetChanged();
        /**
         * 滑动到底部
         */
        mRvList.scrollToPosition(mListBean.size() - 1);
    }

    @Override
    public FlListBean.ResultsListBean getUser() {
        return mUser;
    }
}
