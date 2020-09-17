package com.duobang.middleware.weight;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * 验证码
 *
 */
public class SSCodeInputView extends LinearLayout {

    private Context mContext;
    private int codeNumber = 4;//验证码长度
    private StringBuilder codes;
    private List<EditText> inputList;
    private OnInputViewCallback onInputViewCallback;

    public SSCodeInputView(Context context) {
        super(context);
        this.mContext = context;
        codes = new StringBuilder();
        loadView();
    }

    public SSCodeInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        codes = new StringBuilder();
        loadView();
    }

    public SSCodeInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        codes = new StringBuilder();
        inputList = new ArrayList<>();
        loadView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SSCodeInputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        codes = new StringBuilder();
        loadView();
    }

    private void loadView() {
        inputList = new ArrayList<>();
        for (int i = 0; i < codeNumber; i++) {
            EditText input = new EditText(mContext);
            initEdit(input);
            inputList.add(input);
            addView(input);
            if (i == 0){
                input.setEnabled(true);
                input.requestFocus();
            }
        }

        for (int i = 0; i < inputList.size(); i++) {
            EditText editText = inputList.get(i);
            final int finalI = i;
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null && !s.toString().equals("")){
                        codes.append(s.toString());
                        if (finalI == inputList.size() - 1) {
                            if (codes.length() == 4)
                            onInputViewCallback.putCodes(codes.toString());
                        }else {
                            EditText editText1 = inputList.get(finalI + 1);
                            editText1.setEnabled(true);
                            editText1.requestFocus();
                        }
                    }
                }
            });

            editText.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                        backFocus();
                    }
                    return false;
                }
            });
        }
    }

    private void backFocus() {
        EditText editText;
        for (int i = codeNumber - 1; i >= 0; i--) {
            editText = inputList.get(i);
            if (editText.getText().length() >= 1) {
                editText.setText("");
                codes.deleteCharAt(i);
                editText.requestFocus();
                if (i != 4){
                    if (i + 1 < inputList.size()) {
                        inputList.get(i + 1).setEnabled(false);
                    }
                }
                return;
            }
        }
    }

    private void initEdit(EditText input) {
        LinearLayout.LayoutParams params = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        input.setLayoutParams(params);
        input.setTextSize(20);
        input.setMaxLines(1);
        input.setMaxEms(1);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setGravity(Gravity.CENTER);
        input.setEnabled(false);
    }

    public void setOnInputViewCallback(OnInputViewCallback onInputViewCallback) {
        this.onInputViewCallback = onInputViewCallback;
    }

    public interface OnInputViewCallback{
        void putCodes(String codes);
    }

}
