package com.zz.recycleviewmvptest.mvp.chess;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.ChessListBean;
import com.zz.recycleviewmvptest.bean.local.ChessListBeanDaoImpl;
import com.zz.recycleviewmvptest.mvp.chess.chess_list.ChessListActivity;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.chinese_chess.ChessView;
import com.zz.recycleviewmvptest.widget.popwindow.CenterPopWindow;

/**
 * author: wuyangyi
 * date: 2019-09-05
 */
public class ChessFragment extends BaseFragment {
    private ChessView chessView;
    private TextView tvReStart;
    private ChessListBeanDaoImpl chessListBeanDao;
    private String startTime; //开始时间

    private CenterPopWindow mCenterPopWindow;
    @Override
    protected void initView(View rootView) {
        chessListBeanDao = new ChessListBeanDaoImpl();
        startTime = Utils.getNowSystemTime();
        chessView = rootView.findViewById(R.id.chessView);
        tvReStart = rootView.findViewById(R.id.tv_reStart);
        tvReStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chessView.reStart();
            }
        });

        chessView.setChessFinishClick(new ChessView.ChessFinishClick() {
            @Override
            public void chessFinishClickListener(String message, boolean redWin) {
                showWinDialog(message);
                ChessListBean chessListBean = new ChessListBean(redWin ? "红方" : "黑方");
                chessListBean.setStart_time(startTime);
                chessListBean.setEnd_time(Utils.getNowSystemTime());
                chessListBean.setTime_long(Utils.getDateByString(chessListBean.getStart_time(), chessListBean.getEnd_time()));
                chessListBeanDao.insertOrReplace(chessListBean);
            }
        });
    }

    private void showWinDialog(String message) {
        mCenterPopWindow = new CenterPopWindow.Builder()
                .hintString(message)
                .cancelString("取消")
                .sureString("重新开始")
                .cancelClickListener(new CenterPopWindow.CancelClickListener() {
                    @Override
                    public void onItemClicked() {
                        mCenterPopWindow.dismiss();
                    }
                })
                .sureClickListener(new CenterPopWindow.SureClickListener() {
                    @Override
                    public void onItemClicked() {
                        mCenterPopWindow.dismiss();
                        chessView.reStart();
                    }
                })
                .isOutsideTouch(false)
                .isFocus(false)
                .with(mActivity)
                .build();
        mCenterPopWindow.show();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String setCenterTitle() {
        return "中国象棋";
    }

    @Override
    protected int setRightImage() {
        return R.mipmap.ico_more;
    }

    @Override
    protected void setRightImageClick() {
        startActivity(new Intent(getActivity(), ChessListActivity.class));
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_chess;
    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
