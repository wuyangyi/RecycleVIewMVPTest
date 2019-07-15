package com.zz.recycleviewmvptest.mvp.mine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.base.BaseListFragment;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;

public class MineFragment extends BaseListFragment<MineContract.Presenter, UserInfoBean> implements MineContract.View {
    private CommonAdapter<UserInfoBean> adapter;
    private MineHeaderView mineHeaderView;
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
    protected void initData() {
        super.initData();
        mPresenter = new MinePresenter(this);
        mPresenter.requestNetData(0, false, 1);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        adapter = new CommonAdapter<UserInfoBean>(context, R.layout.item_user_list, mListData) {
            @Override
            protected void convert(ViewHolder holder, UserInfoBean userInfoBean, int position) {
                if (userInfoBean.getHead() == null || userInfoBean.getHead().isEmpty()) {
                    holder.setImageResource(R.id.iv_head, R.mipmap.ic_launcher);
                } else {
                    holder.setImageBitmap(R.id.iv_head, getBitmapForPath(userInfoBean.getHead()));
                }
                holder.setText(R.id.tv_name, userInfoBean.getNickname() == null || userInfoBean.getNickname().isEmpty() ? "" : userInfoBean.getNickname());
                holder.setText(R.id.tv_school, userInfoBean.getSchool() == null || userInfoBean.getSchool().isEmpty() ? "" : userInfoBean.getSchool());
                holder.setText(R.id.tv_time, userInfoBean.getCreate_time() + "");
            }
        };
        return adapter;
    }

    private Bitmap getBitmapForPath(String path) {
//        String headPath = android.os.Environment.getExternalStorageDirectory()
//                + "/" + "msg" + "/" + "head/"+"图片名" + ".jpg";
        Bitmap bmpDefaultPic;
        bmpDefaultPic = BitmapFactory.decodeFile(path, null);
        return bmpDefaultPic;
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
}
