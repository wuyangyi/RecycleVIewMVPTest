package com.zz.recycleviewmvptest.mvp.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.crop.UCrop;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.AnimationClick;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.bean.ChatBottomMenuBean;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.MultiItemTypeAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.mvp.image_show.ImageShowActivity;
import com.zz.recycleviewmvptest.widget.SoftKeyBoardListener;
import com.zz.recycleviewmvptest.widget.ToastUtils;
import com.zz.recycleviewmvptest.widget.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zz.recycleviewmvptest.mvp.chat.ChatActivity.CHAT_USER_INFO;
import static com.zz.recycleviewmvptest.widget.Utils.REQUEST_SELECT_PICTURE;

/**
 * 聊天
 */
public class ChatFragment extends BaseFragment<ChatContract.Presenter> implements ChatContract.View, ChatItem.OnViewLongClick, ChatItem.ImageClick {
    private RecyclerView mRvList;
    private EditText mEtImport;
    private ImageView mIvHelp;
    private ImageView mIvMore;
    private TextView mBtSend;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<ChatBean> mListBean = new ArrayList<>();
    private View mLlMenu;
    private RecyclerView rvMenu;
    private CommonAdapter<ChatBottomMenuBean> adapter;
    private List<ChatBottomMenuBean> chatBottomMenuBeans;

    private boolean bottomMenuIsShow = false; //底部菜单是否已显示

    private FlListBean.ResultsListBean mUser; //聊天的用户

    private boolean downMore = false; //按下更多
    private boolean isSoftKeyBoardShow = false; //是否是软键盘弹出导致的列表滑动
    private boolean SoftKeyBoardIsShow = false; //软键盘是否弹出

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
        mLlMenu = rootView.findViewById(R.id.ll_menu);
        rvMenu = rootView.findViewById(R.id.rvMenu);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManager.setStackFromEnd(childBigToList());
        mRvList.setLayoutManager(layoutManager);
        mAdapter = getAdapter();
        mRvList.setAdapter(mAdapter);
        initMenuDate();
        rvMenu.setAdapter(initMenuAdapter());
        initListener();
    }

    //判断所有子控件的高度是否大于RecyclerView的高度
    private boolean childBigToList() {
        int childHeight = 0;
        for (int i = 0; i < mListBean.size(); i++) {
            childHeight += mRvList.getChildAt(i).getHeight();
            if (childHeight >= mRvList.getHeight()) {
                return true;
            }
        }
        return false;
    }

    private void initListener() {
        mBtSend.setOnClickListener(v -> {
            mPresenter.sendMessage(mEtImport.getText().toString());
            mEtImport.setText("");
            showButton(false);
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
                if (!isSoftKeyBoardShow) {
                    Utils.hideSoftKeyboard(context, mEtImport);
                    hideBottomMenu();
                } else {
                    isSoftKeyBoardShow = false;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mRvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBottomMenu();
                Utils.hideSoftKeyboard(getContext(), mEtImport);
            }
        });

        mEtImport.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideBottomMenu();
                } else {
                    Utils.hideSoftKeyboard(getContext(), mEtImport);

                    /**
                     * 滑动到底部
                     */
                    mRvList.scrollToPosition(mListBean.size() - 1);
                }
            }
        });

        mIvMore.setOnClickListener(v -> {
            if (!SoftKeyBoardIsShow) {
                if (bottomMenuIsShow) {
                    hideBottomMenu();
                } else {
                    showBottomMenu();
                }
                mRvList.scrollToPosition(mListBean.size() - 1);
            } else {
                downMore = true;
            }
            mEtImport.clearFocus();

        });

        //软键盘监听
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                isSoftKeyBoardShow = true;
                /**
                 * 滑动到底部
                 */
                mRvList.scrollToPosition(mListBean.size() - 1);
                SoftKeyBoardIsShow = true;
            }

            @Override
            public void keyBoardHide(int height) {
                if (downMore) {
                    if (bottomMenuIsShow) {
                        hideBottomMenu();
                    } else {
                        showBottomMenu();
                    }
                    downMore = false;
                }
                /**
                 * 滑动到底部
                 */
                mRvList.scrollToPosition(mListBean.size() - 1);
                SoftKeyBoardIsShow = false;
            }
        });

        mIvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("正在开发中~");
            }
        });
    }

    //隐藏底部菜单栏
    private void hideBottomMenu() {
        if (bottomMenuIsShow) {
            mLlMenu.setVisibility(View.GONE);
            bottomMenuIsShow = false;
        }
    }

    //显示底部菜单栏
    private void showBottomMenu() {
        if (!bottomMenuIsShow) {
            mLlMenu.setVisibility(View.VISIBLE);
            bottomMenuIsShow = true;
        }
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
        ChatItem chatItem = new ChatItem(context, mListBean);
        chatItem.setOnViewLongClick(this);
        chatItem.setImageClick(this);
        adapter.addItemViewDelegate(chatItem);
        ChatOtherItem chatOtherItem = new ChatOtherItem(context, mListBean);
        chatOtherItem.setImageClick(this);
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
        mAdapter = getAdapter();
        mRvList.setAdapter(mAdapter);
        /**
         * 滑动到底部
         */
        mRvList.scrollToPosition(mListBean.size() - 1);
    }

    @Override
    public FlListBean.ResultsListBean getUser() {
        return mUser;
    }

    @Override
    public void onViewLongClickListener(int x, int y, int position) {
        if (mAdapter.getItemCount() > 0) {
            RecyclerView.ViewHolder holder = mRvList.findViewHolderForAdapterPosition(position);
            if (holder != null && holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder)holder;
                int[] location = new int[2];
                viewHolder.getView(R.id.tv_content).getLocationOnScreen(location);
                Log.d("坐标", "x:" + (location[0]+x) + "  y:" + (location[1]-y));
            }
        }
    }

    /**
     * 初始化底部菜单适配器
     */
    private RecyclerView.Adapter initMenuAdapter() {
        adapter = new CommonAdapter<ChatBottomMenuBean>(getContext(), R.layout.item_chat_bottom_menu, chatBottomMenuBeans) {
            @Override
            protected void convert(ViewHolder holder, ChatBottomMenuBean chatBottomMenuBean, int position) {
                holder.getImageViwe(R.id.iv_logo).setImageDrawable(getResources().getDrawable(chatBottomMenuBean.getImage()));
                holder.getTextView(R.id.tv_title).setText(chatBottomMenuBean.getTitle());
            }
        };
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), chatBottomMenuBeans.size() > 4 ? 4 : chatBottomMenuBeans.size());
        rvMenu.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position) {
                    case 0:  //相册
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                            requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, "关闭该权限后部分功能将无法使用，是否继续？", REQUEST_STORAGE_READ_ACCESS_PERMISSION);
                        } else {
                            Utils.pickFromGallery(getActivity());
                        }
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return adapter;
    }

    private void initMenuDate() {
        chatBottomMenuBeans = new ArrayList<>();
        chatBottomMenuBeans.add(new ChatBottomMenuBean("相册", R.mipmap.ico_chat_photo));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    String image = Utils.getRealPathFromUri(getContext(), selectedUri);
                    if (image == null) {
                        return;
                    }
                    ChatBean chatBean = new ChatBean("");
                    chatBean.setImagePath(image);
                    mPresenter.sendImage(chatBean);
                }
            }
        }
    }

    @Override
    public void onImageClickListener(String path) {
        ImageShowActivity.startToImageShowActivity(getActivity(), path);
        useAnimationIntent();
    }
}
