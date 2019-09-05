package com.zz.recycleviewmvptest.mvp.mine.add_user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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
import com.zz.recycleviewmvptest.bean.UserInfoBean;
import com.zz.recycleviewmvptest.mvp.crop.CropActivity;
import com.zz.recycleviewmvptest.widget.AntiShakeUtils;
import com.zz.recycleviewmvptest.widget.Utils;
import com.zz.recycleviewmvptest.widget.imageview.MLImageView;
import com.zz.recycleviewmvptest.widget.popwindow.ActivePopWindow;
import com.zz.recycleviewmvptest.widget.view.CurrButtonFrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

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

    private static final int REQUEST_SELECT_PICTURE = 0x01;
    // 剪切后图像文件
    private Uri mDestinationUri;

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
                UserInfoBean u = new UserInfoBean(mEtName.getText().toString());
                u.setHead(headPath);
                u.setAge(Integer.parseInt(mClAge.getText().toString() == null ? "0" : mClAge.getText().toString()));
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
//                            Toast.makeText(context, "相册", Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                requestPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, "关闭该权限后部分功能将无法使用，是否继续？", REQUEST_STORAGE_READ_ACCESS_PERMISSION);
                            } else {
                                pickFromGallery();
                                activePopWindow.hide();
                            }
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

    /**
     * 选择图片
     */
    private void pickFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "选择图片"), REQUEST_SELECT_PICTURE);
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
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(context, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
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
//            mIvHead.setImageURI(resultUri);
            Bitmap bmp;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                mIvHead.setImageBitmap(bmp);
                headPath = "img"+System.currentTimeMillis();
                Utils.saveBmp2Gallery(bmp, headPath, getContext());
                headPath = Utils.galleryPath + headPath + ".jpg";
                Log.d("图片地址", headPath);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        } else {
            Toast.makeText(getActivity(), "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }
}
