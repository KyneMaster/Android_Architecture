package com.duobang.middleware.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class AvatarView extends ImageView {

    private String mUserName;

    private Paint mPaintText;

    public AvatarView(Context context) {
        super(context);
        init();
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setCircleBackground(getHeight() / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNameText(canvas);
    }

    /**
     * 绘制用户名文字
     *
     * @param canvas
     */
    private void drawNameText(Canvas canvas) {
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(getHeight() / 3);
        mPaintText.setColor(Color.parseColor("#298eff"));
        mPaintText.setTypeface(Typeface.DEFAULT_BOLD);
        Paint.FontMetrics fm = mPaintText.getFontMetrics();
        String text = handleUserName(mUserName);
        canvas.drawText(text, canvas.getWidth() / 2, canvas.getHeight() / 2 - fm.descent + (fm.descent - fm.ascent) / 2, mPaintText);
    }

    /**
     * 处理用户名
     *
     * @param userName
     * @return
     */
    private String handleUserName(String userName) {
        if (TextUtils.isEmpty(userName)) {
            return "";
        }
        if (isChinese(userName)) {
            int start = userName.length() > 1 ? userName.length() - 2 : userName.length();
            return userName.substring(start, userName.length());
        } else {
            return userName.substring(0, 1).toUpperCase();
        }
    }

    /**
     * 设置圆形背景
     *
     * @param cornerRadius
     */
    public void setCircleBackground(int cornerRadius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#1a298eff"));
        gd.setCornerRadius(cornerRadius);
        setBackground(gd);
    }

    /**
     * 设置用户名
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.mUserName = handleUserName(userName);
        invalidate();
    }

    /**
     * 验证输入的名字是否为“中文”或者是否包含“·”
     *
     * @param str w为用户输入的姓名
     * @return
     */
    public static boolean isChinese(String str) {
        if (str.contains("·") || str.contains("•")) {
            if (str.matches("^[\\u4e00-\\u9fa5]+[·•][\\u4e00-\\u9fa5]+$")) {
                return true;
            } else {
                return false;
            }
        } else {
            if (str.matches("^[\\u4e00-\\u9fa5]+$")) {
                return true;
            } else {
                return false;
            }
        }
    }

}
