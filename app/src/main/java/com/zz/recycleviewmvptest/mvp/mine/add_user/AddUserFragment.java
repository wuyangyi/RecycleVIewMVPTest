package com.zz.recycleviewmvptest.mvp.mine.add_user;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.imageview.MLImageView;
import com.zz.recycleviewmvptest.widget.popwindow.ActivePopWindow;
import com.zz.recycleviewmvptest.widget.view.CurrButtonFrameLayout;

public class AddUserFragment extends BaseFragment<AddUserContract.Presenter> implements AddUserContract.View  {
    public final static String USER_ADD_SUCCESS = "user_add_success";
    private LinearLayout mLlHead;
    private MLImageView mIvHead;
    private EditText mEtName;
    private EditText mClAge;
    private CurrButtonFrameLayout mClSex;
    private EditText mEdSchool;
    private TextView mTvSave;
    private String headPath = ""; //头像的地址

    private ActivePopWindow activePopWindow; //头像选择
    private ActivePopWindow sexActivePopWindow; //性别

    @Override
    protected void initView(View rootView) {
        mLlHead = rootView.findViewById(R.id.ll_head);
        mIvHead = rootView.findViewById(R.id.iv_head);
        mEtName = rootView.findViewById(R.id.edt_name);
        mClAge = rootView.findViewById(R.id.edt_age);
        mClSex = rootView.findViewById(R.id.cl_sex);
        mEdSchool = rootView.findViewById(R.id.ed_school);
        mTvSave = rootView.findViewById(R.id.tv_save);
        initListener();
    }

    private void initListener() {
        mLlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v)) { //防抖
                    return;
                }
                showPopWindow();
            }
        });

        mClSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v)) { //防抖
                    return;
                }
                showSexPopWindow();
            }
        });

        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShakeUtils.isInvalidClick(v)) {
                    return;
                }
                UserInfoBean u = new UserInfoBean();
                u.setHead(headPath);
                u.setNickname(mEtName.getText().toString());
                u.setAge(Integer.parseInt(mClAge.getText().toString()));
                int sex = 0;
                if (mClSex.getmTvRight().getText().equals("男")) {
                    sex = 1;
                } else if (mClSex.getmTvRight().getText().equals("女")) {
                    sex = 2;
                }
                u.setSex(sex);
                u.setSchool(mEdSchool.getText().toString());
                mPresenter.saveUser(u);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new AddUserPresenter(this);
    }

    @Override
    protected String setCenterTitle() {
        return "添加用户";
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.fragment_add_user;
    }

//    @Override
//    protected boolean useEventBus() {
//        return true;
//    }
//
    @Override
    public void saveSuccess() {
//        EventBus.getDefault().post(true, USER_ADD_SUCCESS);
        mActivity.finish();
    }

    private void showPopWindow() {
        if (activePopWindow == null) {
            activePopWindow = ActivePopWindow.builder()
                    .item1Str("相册")
                    .item2Str("相机")
                    .bottomStr("取消")
                    .isOutsideTouch(true)
                    .isFocus(true)
                    .with(mActivity)
                    .bottomClickListener(new ActivePopWindow.ActionPopupWindowBottomClickListener() {
                        @Override
                        public void onItemClicked() {
                            activePopWindow.hide();
                        }
                    })
                    .item1ClickListener(new ActivePopWindow.ActionPopupWindowItem1ClickListener() {
                        @Override
                        public void onItemClicked() {
                            Toast.makeText(context, "相册", Toast.LENGTH_SHORT).show();
                            activePopWindow.hide();
                        }
                    })
                    .item2ClickListener(new ActivePopWindow.ActionPopupWindowItem2ClickListener() {
                        @Override
                        public void onItemClicked() {
                            Toast.makeText(context, "相机", Toast.LENGTH_SHORT).show();
                            activePopWindow.hide();
                        }
                    })
                    .build();
        }
        activePopWindow.show();
        Utils.hideSoftKeyboard(context, mEtName);
    }

    private void showSexPopWindow() {
        if (sexActivePopWindow == null) {
            sexActivePopWindow = ActivePopWindow.builder()
                    .item1Str("男")
                    .item2Str("女")
                    .bottomStr("取消")
                    .isOutsideTouch(true)
                    .isFocus(true)
                    .with(mActivity)
                    .bottomClickListener(new ActivePopWindow.ActionPopupWindowBottomClickListener() {
                        @Override
                        public void onItemClicked() {
                            sexActivePopWindow.hide();
                        }
                    })
                    .item1ClickListener(new ActivePopWindow.ActionPopupWindowItem1ClickListener() {
                        @Override
                        public void onItemClicked() {
                            mClSex.getmTvRight().setText("男");
                            sexActivePopWindow.hide();
                        }
                    })
                    .item2ClickListener(new ActivePopWindow.ActionPopupWindowItem2ClickListener() {
                        @Override
                        public void onItemClicked() {
                            mClSex.getmTvRight().setText("女");
                            sexActivePopWindow.hide();
                        }
                    })
                    .build();
        }
        sexActivePopWindow.show();
        Utils.hideSoftKeyboard(context, mEtName);
    }
}
