package com.zz.recycleviewmvptest.mvp.mine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.MultiItemTypeAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.popwindow.ActivePopWindow;

import org.simple.eventbus.Subscriber;

import java.util.List;

import static com.zz.recycleviewmvptest.mvp.mine.add_user.AddUserFragment.USER_ADD_SUCCESS;

public class MineFragment extends BaseListFragment<MineContract.Presenter, UserInfoBean> implements MineContract.View {
    private CommonAdapter<UserInfoBean> adapter;
    private MineHeaderView mineHeaderView;
    private ActivePopWindow itemActivePopWindow; //操作
    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        initHeader();
    }

    private void initHeader() {
        mineHeaderView = new MineHeaderView(context);
        mHeaderAndFooterWrapper.addHeaderView(mineHeaderView.getMineHeaderView());
    }

    @Override
    protected boolean setUseStatusView() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new MinePresenter(this);
        mineHeaderView.setPresenter((MinePresenter) mPresenter);
        mPresenter.requestNetData(0, false, 1);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new CommonAdapter<UserInfoBean>(context, R.layout.item_user_list, mListData) {
            @Override
            protected void convert(ViewHolder holder, UserInfoBean userInfoBean, int position) {
                if (userInfoBean.getHead() == null || userInfoBean.getHead().isEmpty()) {
                    holder.setImageResource(R.id.iv_head, R.mipmap.ic_logo);
                } else {
                    holder.setImageBitmap(R.id.iv_head, Utils.getBitmapForPath(userInfoBean.getHead()));
                }
                holder.setText(R.id.tv_name, userInfoBean.getNickname() == null || userInfoBean.getNickname().isEmpty() ? "" : userInfoBean.getNickname());
                holder.setText(R.id.tv_school, userInfoBean.getSchool() == null || userInfoBean.getSchool().isEmpty() ? "" : userInfoBean.getSchool());
                holder.setText(R.id.tv_time, userInfoBean.getCreate_time() + "");
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                mineHeaderView.setData(mListData.get(position - mHeaderAndFooterWrapper.getHeadersCount()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                position = position - mHeaderAndFooterWrapper.getHeadersCount();
                showItemPopWindow(position);
                return true;
            }
        });
        return adapter;
    }

    private void showItemPopWindow(int position) {
        if (itemActivePopWindow == null) {
            itemActivePopWindow = ActivePopWindow.builder()
                    .item1Str("是否删除当前用户？")
                    .item1Color(getResources().getColor(R.color.home_bottom))
                    .item2Str("删除")
                    .bottomStr("取消")
                    .isOutsideTouch(true)
                    .isFocus(true)
                    .with(mActivity)
                    .bottomClickListener(() -> itemActivePopWindow.hide())
                    .item2ClickListener(() -> {
                        mPresenter.delUser(mListData.get(position));
                        startRefrsh();
                        itemActivePopWindow.hide();
                    })
                    .build();
        }
        itemActivePopWindow.show();
    }

    @Override
    public void onNetSuccess(List<UserInfoBean> data, boolean isLoadMore) {
        super.onNetSuccess(data, isLoadMore);
        mineHeaderView.setSeekBar(mListData.size());
        if (mListData.size() > 0 && !isLoadMore) {
            mineHeaderView.setData(mListData.get(0));
        }
        adapter.notifyDataSetChanged();
        mHeaderAndFooterWrapper.notifyDataSetChanged();

    }

//    private Bitmap getBitmapForPath(String path) {
////        String headPath = android.os.Environment.getExternalStorageDirectory()
////                + "/" + "msg" + "/" + "head/"+"图片名" + ".jpg";
//        Bitmap bmpDefaultPic;
//        bmpDefaultPic = BitmapFactory.decodeFile(path, null);
//        return bmpDefaultPic;
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            startRefrsh();
        }
    }

    @Override
    protected boolean isNeedLoadMore() {
        return false;
    }

    @Override
    protected boolean isNeedRefresh() {
        return false;
    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

//    @Override
//    protected boolean useEventBus() {
//        return true;
//    }

//    @Subscriber(tag = USER_ADD_SUCCESS)
//    public void resuleData(boolean isAdd) {
//        if (isAdd) {
//            mPresenter.requestNetData(0, false, 1);
//            Toast.makeText(context, "监听", Toast.LENGTH_SHORT).show();
//        }
//    }
}
