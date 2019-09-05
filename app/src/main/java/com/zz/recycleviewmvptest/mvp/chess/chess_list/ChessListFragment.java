package com.zz.recycleviewmvptest.mvp.chess.chess_list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.ChessListBean;
import com.zz.recycleviewmvptest.bean.local.ChessListBeanDaoImpl;
import com.zz.recycleviewmvptest.mvp.base_adapter.CommonAdapter;
import com.zz.recycleviewmvptest.mvp.base_adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * author: wuyangyi
 * date: 2019-09-05
 */
public class ChessListFragment extends BaseFragment {
    private ChessListBeanDaoImpl chessListBeanDao;
    private List<ChessListBean> chessListBeanList = new ArrayList<>();
    private CommonAdapter<ChessListBean> adapter;
    private RecyclerView rvList;

    @Override
    protected void initView(View rootView) {
        rvList = rootView.findViewById(R.id.rv_list);
    }

    @Override
    protected void initData() {
        chessListBeanDao = new ChessListBeanDaoImpl();
        chessListBeanList.addAll(chessListBeanDao.getAllList());
        initAdapter();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<ChessListBean>(getContext(), R.layout.item_chess_list, chessListBeanList) {
            @Override
            protected void convert(ViewHolder holder, ChessListBean chessListBean, int position) {
                holder.setText(R.id.tv_number, position + 1 + "");
                holder.setText(R.id.tv_start_time, chessListBean.getStart_time());
                holder.setText(R.id.tv_end_time, chessListBean.getEnd_time());
                holder.setText(R.id.tv_time, chessListBean.getTime_long());
                holder.setText(R.id.tv_win, chessListBean.getWinner());
            }
        };
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setAdapter(adapter);
        rvList.setLayoutManager(layoutManager);
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_chess_list;
    }

    @Override
    protected String setCenterTitle() {
        return "比赛记录";
    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
