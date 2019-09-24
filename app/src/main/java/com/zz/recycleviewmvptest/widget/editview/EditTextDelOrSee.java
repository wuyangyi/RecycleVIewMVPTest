package com.zz.recycleviewmvptest.widget.editview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zz.recycleviewmvptest.R;

/**
 * author: wuyangyi
 * date: 2019/8/10
 * 密码和账号文本框，带清除按钮和密码可见按钮
 */
public class EditTextDelOrSee extends FrameLayout {
    private EditText mEdtText;
    private ImageView mIvDel;
    private ImageView mIvSee;

    private boolean isShowPass = false; //是否显示密码

    public EditTextDelOrSee(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_edittext_widget, this);
        mEdtText = (EditText) findViewById(R.id.edt_text);
        mIvDel = (ImageView) findViewById(R.id.iv_del);
        mIvSee = (ImageView) findViewById(R.id.iv_see);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditTextDelOrSee);
        String hintText = array.getString(R.styleable.EditTextDelOrSee_edtHintText);
        int inputType = array.getInteger(R.styleable.EditTextDelOrSee_inputType, 0);
        int maxLength = array.getInteger(R.styleable.EditTextDelOrSee_maxLength, 0);
        array.recycle();

        if (!TextUtils.isEmpty(hintText)) {
            mEdtText.setHint(hintText);
        }

        if (maxLength != 0) {
            mEdtText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        }

        mIvDel.setVisibility(GONE);

        switch (inputType) {
            case 0:
                mEdtText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 1:
                mEdtText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case 2:
                mEdtText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 3: //密码框
                doPwdIco();
                mIvSee.setVisibility(VISIBLE);
                break;
        }

        mIvSee.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowPass = !isShowPass;
                doPwdIco();
            }
        });



        mEdtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEdtText.getText().toString().isEmpty()) {
                    mIvDel.setVisibility(GONE);
                } else {
                    mIvDel.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mIvDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtText.setText("");
                mIvDel.setVisibility(GONE);
            }
        });
    }

    private void doPwdIco() {
        if (isShowPass) {
            mEdtText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mIvSee.setImageResource(R.mipmap.ico_show_pass);
        } else {
            mEdtText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mIvSee.setImageResource(R.mipmap.ico_hide_pass);
        }
        mEdtText.setSelection(mEdtText.getEditableText().length());
    }

    public EditText getEdtCenter() {
        return mEdtText;
    }
}