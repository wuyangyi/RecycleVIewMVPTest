package com.zz.recycleviewmvptest.mvp.mine.add_user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.crop.UCrop;
import com.zz.recycleviewmvptest.R;
import com.zz.recycleviewmvptest.base.BaseFragment;
import com.zz.recycleviewmvptest.bean.MyInfoBean;
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.mvp.crop.CropActivity;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.toast.ToastUtils;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.imageview.MLImageView;
import com.zz.recycleviewmvptest.widget.popwindow.ActivePopWindow;
import com.zz.recycleviewmvptest.widget.view.CurrButtonFrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.zz.recycleviewmvptest.mvp.mine.add_user.AddUserActivity.LOGIN_PHONE;
import static com.zz.recycleviewmvptest.widget.Utils.REQUEST_SELECT_PICTURE;

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
    private String mPhone; //手机号


    private ActivePopWindow activePopWindow; //头像选择
    private ActivePopWindow sexActivePopWindow; //性别

    // 剪切后图像文件
    private Uri mDestinationUri;

    private String fileName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPhone = getArguments().getString(LOGIN_PHONE);
        }
    }

    @Override
    protected void initView(View rootView) {
        mLlHead = rootView.findViewById(R.id.ll_head);
        mIvHead = rootView.findViewById(R.id.iv_head);
        mEtName = rootView.findViewById(R.id.edt_name);
        mClAge = rootView.findViewById(R.id.edt_age);
        mClSex = rootView.findViewById(R.id.cl_sex);
        mEdSchool = rootView.findViewById(R.id.ed_school);
        mTvSave = rootView.findViewById(R.id.tv_save);
        mDestinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), "cropImage.jpeg"));
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
                if (mEtName.getText().toString().isEmpty()) {
                    ToastUtils.showToast("请输入昵称");
                    return;
                }
                UserInfoBean u = new UserInfoBean(mEtName.getText().toString());
                u.setPhone(mPhone);
                u.setHead(headPath);
                if (mClAge.getText().toString().isEmpty()) {
//                    Toast.makeText(getActivity(), "请输入年龄",Toast.LENGTH_SHORT).show();
                    ToastUtils.showToast("请输入年龄");
                    return;
                }
                u.setAge(Integer.parseInt(mClAge.getText().toString().isEmpty() ? "0" : mClAge.getText().toString()));
                if (mClSex.getmIvRight().toString().isEmpty()) {
                    ToastUtils.showToast("请选择性别");
                    return;
                }
                int sex = 0;
                if (mClSex.getmTvRight().getText().equals("男")) {
                    sex = 1;
                } else if (mClSex.getmTvRight().getText().equals("女")) {
                    sex = 2;
                }
                u.setSex(sex);
                if (mEdSchool.getText().toString().isEmpty()) {
//                    Toast.makeText(getActivity(), "请输入学校",Toast.LENGTH_SHORT).show();
                    ToastUtils.showToast("请输入学校");
                    return;
                }
                u.setSchool(mEdSchool.getText().toString());
                u.setLogin(true);
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
        return "完善信息";
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
        goHome(true);
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, "您已禁用内存读写权限，当前无法打开相册，是否打开权限？", REQUEST_STORAGE_READ_ACCESS_PERMISSION);
                            } else {
                                Utils.pickFromGallery(getActivity());
                                activePopWindow.hide();
                            }
                        }
                    })
                    .item2ClickListener(new ActivePopWindow.ActionPopupWindowItem2ClickListener() {
                        @Override
                        public void onItemClicked() {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                                            != PackageManager.PERMISSION_GRANTED) {
                                requestPermission(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, "您已禁用相机权限，当前无法打开相机，是否打开权限？", REQUEST_STORAGE_CAMERA_TAKE_PHOTO);
                            } else {
                                fileName = Utils.takePicture(getActivity());
                                activePopWindow.hide();
                            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) { //相册
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    ToastUtils.showToast("无法剪切选择图片");
                }
            } else if (requestCode == UCrop.REQUEST_CROP) { //图片裁剪
                handleCropResult(data);
            } else if (requestCode == REQUEST_STORAGE_CAMERA_TAKE_PHOTO) { //相机
                Uri selectedUri = Uri.fromFile(new File(fileName));
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    ToastUtils.showToast("无法剪切选择图片");
                }
            }
        }
    }


    /**
     * 开始剪切图片
     * @param uri
     */
    private void startCropActivity(Uri uri) {
        UCrop.of(uri, mDestinationUri)
                .withTargetActivity(CropActivity.class)
                .withAspectRatio(1, 1)
//                .withMaxResultSize(300, 300)
                .start(getActivity());

    }

    /**
     * 处理剪切后的返回值
     * @param result
     */
    private void handleCropResult(Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            Bitmap bmp;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                mIvHead.setImageBitmap(bmp);
                headPath = "img"+System.currentTimeMillis();
                Utils.saveBmp2Gallery(bmp, headPath, getContext());
                headPath = Utils.galleryPath + headPath + ".jpg";
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        } else {
            Toast.makeText(getActivity(), "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }
}
