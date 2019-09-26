package com.zz.recycleviewmvptest.mvp.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.ChatBean;
import com.zz.recycleviewmvptest.bean.ChatBottomMenuBean;
import com.zz.recycleviewmvptest.bean.FlListBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.MultiItemTypeAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.mvp.image_show.ImageShowActivity;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.DataUtils;
import com.zz.recycleviewmvptest.widget.SoftKeyBoardListener;
import com.zz.recycleviewmvptest.widget.button.SpeckButton;
import com.zz.recycleviewmvptest.widget.manager.MediaPlayManager;
import com.zz.recycleviewmvptest.widget.popwindow.ChatMenuPopWindow;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;
import com.zz.recycleviewmvptest.widget.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.zz.recycleviewmvptest.mvp.chat.ChatActivity.CHAT_USER_INFO;
import static com.zz.recycleviewmvptest.widget.Utils.REQUEST_SELECT_PICTURE;

/**
 * 聊天
 */
public class ChatFragment extends BaseFragment<ChatContract.Presenter> implements ChatContract.View, ChatItem.OnViewLongClick, ChatItem.ImageClick, View.OnClickListener, ChatItem.SoundClick {
    private RecyclerView mRvList;
    private EditText mEtImport;
    private ImageView mIvHelp;
    private ImageView mIvMore;
    private TextView mBtSend;
    private ImageView mIvDown; //键盘输入和语言切换
    private SpeckButton tvDownSpeck; //按下说话
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;
    private List<ChatBean> mListBean = new ArrayList<>();
    private View mLlMenu;
    private RecyclerView rvMenu;
    private CommonAdapter<ChatBottomMenuBean> adapter;
    private CommonAdapter<String> mBqAdapter; //表情adapter
    private List<ChatBottomMenuBean> chatBottomMenuBeans;

    private boolean bottomMenuIsShow = false; //底部菜单是否已显示

    private boolean isInput = true; //是否是键盘输入

    private FlListBean.ResultsListBean mUser; //聊天的用户

    private boolean downMore = false; //按下更多
    private boolean isSoftKeyBoardShow = false; //是否是软键盘弹出导致的列表滑动
    private boolean SoftKeyBoardIsShow = false; //软键盘是否弹出

    private boolean mSoundPlayerIsMe; //当前播放的是否是我的
    private ImageView mImageView; //当前播放语音的控件

    private ChatMenuPopWindow chatMenuPopWindow;

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
        mIvDown = rootView.findViewById(R.id.ivDown);
        tvDownSpeck = rootView.findViewById(R.id.tvDownSpeck);
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManager.setStackFromEnd(childBigToList());
        mRvList.setLayoutManager(layoutManager);
        mAdapter = getAdapter();
        mRvList.setAdapter(mAdapter);
        initMenuDate();
        rvMenu.setAdapter(initMenuAdapter());
        initListener();
        setInputType(isInput);
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

    /**
     * 语言与键盘输入切换
     * @param isInput
     */
    private void setInputType(boolean isInput) {
        if (isInput) { //键盘输入
            tvDownSpeck.setVisibility(View.GONE);
            mEtImport.setVisibility(View.VISIBLE);
            mEtImport.setFocusable(true);
            mIvDown.setImageDrawable(getResources().getDrawable(R.mipmap.ico_speck));
        } else {
            tvDownSpeck.setVisibility(View.VISIBLE);
            mEtImport.setVisibility(View.GONE);
            mEtImport.clearFocus();
            mIvDown.setImageDrawable(getResources().getDrawable(R.mipmap.ico_input_logo));
        }
    }

    private void initListener() {
        mBtSend.setOnClickListener(this);
        mIvMore.setOnClickListener(this);
        mRvList.setOnClickListener(this);
        mIvHelp.setOnClickListener(this);
        mIvDown.setOnClickListener(this);
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

        //录制完成回调
        tvDownSpeck.setOnWellFinish(new SpeckButton.OnWellFinish() {
            @Override
            public void onFinish(String path, float time) {
                ChatBean chatBean = new ChatBean("");
                chatBean.setSoundPath(path);
                chatBean.setSoundTime(time);
                mPresenter.sendImage(chatBean);
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
        chatItem.setSoundClick(this);
        adapter.addItemViewDelegate(chatItem);
        ChatOtherItem chatOtherItem = new ChatOtherItem(context, mListBean);
        chatOtherItem.setImageClick(this);
        chatOtherItem.setSoundClick(this);
        chatOtherItem.setOnViewLongClick(this);
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
    public void removeMessageSuccess(int position) {
        mListBean.remove(position);
        mAdapter = getAdapter();
        mRvList.setAdapter(mAdapter);
        /**
         * 滑动
         */
        mRvList.scrollToPosition(position - 1);
    }

    @Override
    public FlListBean.ResultsListBean getUser() {
        return mUser;
    }

    @Override
    public void onViewLongClickListener(int x, int y, int position, boolean isMe, String type) {
        if (mAdapter.getItemCount() > 0) {
            RecyclerView.ViewHolder holder = mRvList.findViewHolderForAdapterPosition(position);
            if (holder != null && holder instanceof ViewHolder) {
                ViewHolder viewHolder = (ViewHolder)holder;
                int[] location = new int[2];
                viewHolder.getView(R.id.tv_content).getLocationOnScreen(location);
                boolean isBottom = (location[1] - y) > (Utils.getScreenHeight(context) / 2);
                showMenuPopWindow(isMe ? x : x+location[0], isBottom ? location[1] + y : location[1] - y, isMe, isBottom, position, type);
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

    /**
     * 表情adapter
     * @return
     */
    private RecyclerView.Adapter initBqAdapter() {
        mBqAdapter = new CommonAdapter<String>(getContext(), R.layout.item_bq, DataUtils.getImageListName()) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setImageBitmap(R.id.iv_image, Utils.getBitmapByName(getContext(), DataUtils.BQ_START + s));
            }
        };
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 6);
        rvMenu.setLayoutManager(layoutManager);
        mBqAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ChatBean chatBean = new ChatBean("");
                chatBean.setImagePath(DataUtils.BQ_START + DataUtils.getImageListName().get(position));
                mPresenter.sendImage(chatBean);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return mBqAdapter;
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

    @Override
    public void onClick(View v) {
        if (AntiShakeUtils.isInvalidClick(v)) {
            return;
        }
        switch (v.getId()) {
            case R.id.bt_send: //消息发布按钮
                mPresenter.sendMessage(mEtImport.getText().toString());
                mEtImport.setText("");
                showButton(false);
                break;
            case R.id.rv_list: //列表点击
                hideBottomMenu();
                Utils.hideSoftKeyboard(getContext(), mEtImport);
                break;
            case R.id.iv_more: //更多
                rvMenu.setAdapter(initMenuAdapter());
                showBottomMenuUtil();
                break;
            case R.id.iv_help: //表情
                rvMenu.setAdapter(initBqAdapter());
                showBottomMenuUtil();
                break;
            case R.id.ivDown: //语言和聊天切换按钮
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermission(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, "您已禁止麦克风权限，您将无法使用语音聊天功能，是否需要开启权限？", REQUEST_RECORD_AUDIO);
                } else {
                    isInput = !isInput;
                    setInputType(isInput);
                }
                break;
            default:
                break;
        }
    }

    //是否显示底部菜单
    private void showBottomMenuUtil() {
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
    }

    //语音播放回调
    @Override
    public void OnSoundClickListener(String path, ImageView imageView, boolean isMe) {
        if (mImageView != null && mImageView == imageView) { //点击当前控件

        }
        if (mImageView != null) { //当前有其他语音播放
            mImageView.setImageDrawable(getResources().getDrawable(mSoundPlayerIsMe ? R.mipmap.tt_voice_node_mine : R.mipmap.tt_voice_node_other));
            if (mImageView == imageView) { //点击当前控件
                mImageView = null;
                return;
            }
            mImageView = null;
        }
        mImageView = imageView;
        mSoundPlayerIsMe = isMe;
        mImageView.setImageResource(isMe ? R.drawable.anim_player_me : R.drawable.anim_player_other);
        AnimationDrawable anim = (AnimationDrawable) mImageView.getDrawable();
        anim.start();
        MediaPlayManager.playSound(path, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mImageView.setImageDrawable(getResources().getDrawable(mSoundPlayerIsMe ? R.mipmap.tt_voice_node_mine : R.mipmap.tt_voice_node_other));
            }
        });
    }

    private void showMenuPopWindow(int x, int y, boolean isMe, boolean isBottom, int index, String type) {
        ChatMenuAdapter chatMenuAdapter = new ChatMenuAdapter(getContext(), R.layout.item_chat_menu, DataUtils.getChatMenuData(type));
        chatMenuAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position == 3) { //复制
                    Utils.Copy(getContext(), mListBean.get(index).getContext());
                } else if (position == 0) { //转发
                    ToastUtils.showToast("正在建设中~");
                } else if (position == 1) { //撤回
                    mPresenter.removeMessage(mListBean.get(index), index);
                } else if (position == 2) { //删除
                    mPresenter.removeMessage(mListBean.get(index), index);
                }
                chatMenuPopWindow.hide();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        chatMenuPopWindow = ChatMenuPopWindow.builder()
                .isOutsideTouch(true)
                .isFocus(true)
                .with(getActivity())
                .adater(chatMenuAdapter)
                .layoutManager(layoutManager)
                .gravity(isMe ? Gravity.RIGHT : Gravity.LEFT)
                .leftMargin(isMe ? 0 : x)
                .rightMargin(isMe ? Utils.getScreenWidth(getContext()) - x: 0)
                .topMargin(!isBottom ? Utils.dp2px(getContext(), 10) : 0)
                .bottomMargin(!isBottom ? 0 : Utils.dp2px(getContext(), 10))
                .build();

        chatMenuPopWindow.show(x, isBottom ? Utils.getScreenHeight(getContext()) - y : y, isBottom, isMe, type);
    }

    @Override
    public void onPause() {
        super.onPause();
        MediaPlayManager.getInstance().pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        MediaPlayManager.getInstance().resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MediaPlayManager.getInstance().release();
        dissMissPop(chatMenuPopWindow);
    }
}
