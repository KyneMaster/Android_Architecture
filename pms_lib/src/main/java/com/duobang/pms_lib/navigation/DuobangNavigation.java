package com.duobang.pms_lib.navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.duobang.pms_lib.R;
import com.duobang.pms_lib.navigation.adapter.ViewPagerAdapter;
import com.duobang.pms_lib.navigation.constant.Anim;
import com.duobang.pms_lib.utils.MeasureUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class DuobangNavigation extends LinearLayout {

    private RelativeLayout AddContainerLayout;

    //Tab数量
    private int tabCount = 0;

    private LinearLayout navigationLayout;
    private RelativeLayout contentView;
    //分割线
    private View lineView;

    //红点集合
    private List<View> hintPointList = new ArrayList<>();

    //消息数量集合
    private List<TextView> msgPointList = new ArrayList<>();

    //底部Image集合
    private List<ImageView> imageViewList = new ArrayList<>();

    //底部Text集合
    private List<TextView> textViewList = new ArrayList<>();

    //底部TabLayout（除中间加号）
    private List<View> tabList = new ArrayList<>();

    private ScrollViewPager mViewPager;
    //private GestureDetector detector;

    private ViewGroup addViewLayout;

    //文字集合
    private String[] titleItems;
    //未选择 图片集合
    private int[] normalIconItems;
    //已选择 图片集合
    private int[] selectIconItems;
    //fragment集合
    private List<Fragment> fragmentList = new ArrayList<>();

    private FragmentManager fragmentManager;

    //Tab点击动画效果
    private Techniques anim = null;
    //ViewPager切换动画
    private boolean smoothScroll = false;
    //图标大小
    private int iconSize = 24; // 20

    //提示红点大小
    private float hintPointSize = 6;
    //提示红点距Tab图标右侧的距离
    private float hintPointLeft = -3;
    //提示红点距图标顶部的距离
    private float hintPointTop = -3;

    private DuobangNavigation.OnTabClickListener onTabClickListener;
    //消息红点字体大小
    private float msgPointTextSize = 9;
    //消息红点大小
    private float msgPointSize = 18;
    //消息红点距Tab图标右侧的距离   默认为Tab图标的一半
    private float msgPointLeft = -10;
    //消息红点距图标顶部的距离  默认为Tab图标的一半
    private float msgPointTop = -10;
    //Tab文字距Tab图标的距离
    private float tabTextTop = 2;
    //Tab文字大小
    private float tabTextSize = 12;
    //未选中Tab字体颜色
    private int normalTextColor = ContextCompat.getColor(getContext(), R.color.black_icon); // R.color.colorLess
    //选中字体颜色
    private int selectTextColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
    //分割线高度
    private float lineHeight = 1;
    //分割线颜色
    private int lineColor = Color.parseColor("#cfcfcf");

    private int navigationBackground = Color.parseColor("#ffffff");
    private float navigationHeight = 50; // 60

    private ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER_INSIDE;

    private boolean canScroll;
    private ViewPagerAdapter adapter;

    //Add
    private DuobangNavigation.OnAddClickListener onAddClickListener;
    private float addIconSize = 36;
    private float addLayoutHeight = navigationHeight;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_ADD = 1;
    public static final int MODE_ADD_VIEW = 2;

    private float addLayoutBottom = 10;

    //RULE_CENTER 居中只需调节addLayoutHeight 默认和navigationHeight相等 此时addLayoutBottom属性无效
    //RULE_BOTTOM addLayoutHeight属性无效、自适应、只需调节addLayoutBottom距底部的距离
    private int addLayoutRule = RULE_BOTTOM;

    public static final int RULE_CENTER = 0;
    public static final int RULE_BOTTOM = 1;

    //true  ViewPager在Navigation上面
    //false  ViewPager和Navigation重叠
    private boolean hasPadding = true;

    //1、普通的Tab 2、中间带按钮（如加号）3、自定义view
    private int mode;

    //true 点击加号切换fragment
    //false 点击加号不切换fragment进行其他操作（跳转界面等）
    private boolean addAsFragment = false;
    //自定义加号view
    private View customAddView;
    private float addTextSize;
    //加号文字未选中颜色（默认同其他tab）
    private int addNormalTextColor;
    //加号文字选中颜色（默认同其他tab）
    private int addSelectTextColor;
    //加号文字距离顶部加号的距离
    private float addTextTopMargin = 3;
    //是否和其他tab文字底部对齐
    private boolean addAlignBottom = true;
    private ImageView addImage;
    private View empty_line;

    public DuobangNavigation(Context context) {
        super(context);
        initViews(context, null);
    }

    public DuobangNavigation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public DuobangNavigation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DuobangNavigation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        contentView = (RelativeLayout) View.inflate(context, R.layout.navigation_container_layout, null);
        addViewLayout = contentView.findViewById(R.id.add_view_ll);
        AddContainerLayout = contentView.findViewById(R.id.add_rl);
        empty_line = contentView.findViewById(R.id.empty_line);
        navigationLayout = contentView.findViewById(R.id.navigation_ll);
        mViewPager = contentView.findViewById(R.id.mViewPager);
        lineView = contentView.findViewById(R.id.common_horizontal_line);
        lineView.setTag(-100);
        empty_line.setTag(-100);
        navigationLayout.setTag(-100);

        toDp();

        @SuppressLint("CustomViewStyleable") TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DuobangNavigation);
        parseStyle(attributes);

        addView(contentView);
    }

    private void parseStyle(TypedArray attributes) {
        if (attributes != null) {
            navigationHeight = attributes.getDimension(R.styleable.DuobangNavigation_navigationHeight, navigationHeight);
            navigationBackground = attributes.getColor(R.styleable.DuobangNavigation_navigationBackground, navigationBackground);

            tabTextSize = attributes.getDimension(R.styleable.DuobangNavigation_tabTextSize, tabTextSize);
            tabTextTop = attributes.getDimension(R.styleable.DuobangNavigation_tabTextTop, tabTextTop);
            iconSize = (int) attributes.getDimension(R.styleable.DuobangNavigation_tabIconSize, iconSize);
            hintPointSize = attributes.getDimension(R.styleable.DuobangNavigation_hintPointSize, hintPointSize);
            msgPointSize = attributes.getDimension(R.styleable.DuobangNavigation_msgPointSize, msgPointSize);
            hintPointLeft = attributes.getDimension(R.styleable.DuobangNavigation_hintPointLeft, hintPointLeft);
            msgPointTop = attributes.getDimension(R.styleable.DuobangNavigation_msgPointTop, -iconSize / 2);
            hintPointTop = attributes.getDimension(R.styleable.DuobangNavigation_hintPointTop, hintPointTop);

            msgPointLeft = attributes.getDimension(R.styleable.DuobangNavigation_msgPointLeft, -iconSize / 2);
            msgPointTextSize = attributes.getDimension(R.styleable.DuobangNavigation_msgPointTextSize, msgPointTextSize);
            addIconSize = attributes.getDimension(R.styleable.DuobangNavigation_addIconSize, addIconSize);
            addLayoutBottom = attributes.getDimension(R.styleable.DuobangNavigation_addLayoutBottom, addLayoutBottom);

            //加号属性
            addSelectTextColor = attributes.getColor(R.styleable.DuobangNavigation_addSelectTextColor, addSelectTextColor);
            addNormalTextColor = attributes.getColor(R.styleable.DuobangNavigation_addNormalTextColor, addNormalTextColor);
            addTextSize = attributes.getDimension(R.styleable.DuobangNavigation_addTextSize, addTextSize);
            addTextTopMargin = attributes.getDimension(R.styleable.DuobangNavigation_addTextTopMargin, addTextTopMargin);
            addAlignBottom = attributes.getBoolean(R.styleable.DuobangNavigation_addAlignBottom, addAlignBottom);

            lineHeight = attributes.getDimension(R.styleable.DuobangNavigation_lineHeight, lineHeight);
            lineColor = attributes.getColor(R.styleable.DuobangNavigation_lineColor, lineColor);

            addLayoutHeight = attributes.getDimension(R.styleable.DuobangNavigation_addLayoutHeight, navigationHeight + lineHeight);

            normalTextColor = attributes.getColor(R.styleable.DuobangNavigation_tabNormalColor, normalTextColor);
            selectTextColor = attributes.getColor(R.styleable.DuobangNavigation_tabSelectColor, selectTextColor);

            int type = attributes.getInt(R.styleable.DuobangNavigation_scaleType, 0);
            if (type == 0) {
                scaleType = ImageView.ScaleType.CENTER_INSIDE;
            } else if (type == 1) {
                scaleType = ImageView.ScaleType.CENTER_CROP;
            } else if (type == 2) {
                scaleType = ImageView.ScaleType.CENTER;
            } else if (type == 3) {
                scaleType = ImageView.ScaleType.FIT_CENTER;
            } else if (type == 4) {
                scaleType = ImageView.ScaleType.FIT_END;
            } else if (type == 5) {
                scaleType = ImageView.ScaleType.FIT_START;
            } else if (type == 6) {
                scaleType = ImageView.ScaleType.FIT_XY;
            } else if (type == 7) {
                scaleType = ImageView.ScaleType.MATRIX;
            }

            addLayoutRule = attributes.getInt(R.styleable.DuobangNavigation_addLayoutRule, addLayoutRule);
            hasPadding = attributes.getBoolean(R.styleable.DuobangNavigation_hasPadding, hasPadding);

            addAsFragment = attributes.getBoolean(R.styleable.DuobangNavigation_addAsFragment, addAsFragment);

            attributes.recycle();
        }
    }

    //将dp、sp转换成px
    private void toDp() {
        navigationHeight = MeasureUtils.dip2px(getContext(), navigationHeight);
        iconSize = MeasureUtils.dip2px(getContext(), iconSize);
        hintPointSize = MeasureUtils.dip2px(getContext(), hintPointSize);
        hintPointTop = MeasureUtils.dip2px(getContext(), hintPointTop);
        hintPointLeft = MeasureUtils.dip2px(getContext(), hintPointLeft);

        msgPointLeft = MeasureUtils.dip2px(getContext(), msgPointLeft);
        msgPointTop = MeasureUtils.dip2px(getContext(), msgPointTop);
        msgPointSize = MeasureUtils.dip2px(getContext(), msgPointSize);
        msgPointTextSize = MeasureUtils.sp2px(getContext(), msgPointTextSize);

        tabTextTop = MeasureUtils.dip2px(getContext(), tabTextTop);
        tabTextSize = MeasureUtils.sp2px(getContext(), tabTextSize);

        //Add
        addIconSize = MeasureUtils.dip2px(getContext(), addIconSize);
        addLayoutHeight = MeasureUtils.dip2px(getContext(), addLayoutHeight);
        addLayoutBottom = MeasureUtils.dip2px(getContext(), addLayoutBottom);
        addTextSize = MeasureUtils.sp2px(getContext(), addTextSize);
        addTextTopMargin = MeasureUtils.dip2px(getContext(), addTextTopMargin);
    }


    public void build() {

        if (addLayoutHeight < navigationHeight + lineHeight)
            addLayoutHeight = navigationHeight + lineHeight;

        if (addLayoutRule == RULE_CENTER) {
            RelativeLayout.LayoutParams addLayoutParams = (RelativeLayout.LayoutParams) AddContainerLayout.getLayoutParams();
            addLayoutParams.height = (int) addLayoutHeight;
            AddContainerLayout.setLayoutParams(addLayoutParams);
        } else if (addLayoutRule == RULE_BOTTOM) {
           /* RelativeLayout.LayoutParams addLayoutParams = (RelativeLayout.LayoutParams) AddContainerLayout.getLayoutParams();
            if ((addIconSize + addIconBottom) > (navigationHeight + 1))
                addLayoutParams.height = (int) (addIconSize + addIconBottom);
            else
                addLayoutParams.height = (int) (navigationHeight + 1);
            AddContainerLayout.setLayoutParams(addLayoutParams);*/
        }

        navigationLayout.setBackgroundColor(navigationBackground);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) navigationLayout.getLayoutParams();
        params.height = (int) navigationHeight;
        navigationLayout.setLayoutParams(params);

        if (hasPadding) {
            mViewPager.setPadding(0, 0, 0, (int) (navigationHeight + lineHeight));
        }

        RelativeLayout.LayoutParams lineParams = (RelativeLayout.LayoutParams) lineView.getLayoutParams();
        lineParams.height = (int) lineHeight;
        lineView.setBackgroundColor(lineColor);
        lineView.setLayoutParams(lineParams);

        //若没有设置中间添加的文字字体大小、颜色、则同其他Tab一样
        if (addTextSize == 0) {
            addTextSize = tabTextSize;
        }
        if (addNormalTextColor == 0) {
            addNormalTextColor = normalTextColor;
        }
        if (addSelectTextColor == 0) {
            addSelectTextColor = selectTextColor;
        }

        if (mode == MODE_NORMAL) {
            buildNavigation();
        } else if (mode == MODE_ADD) {
            buildAddNavigation();
        } else if (mode == MODE_ADD_VIEW) {
            buildAddViewNavigation();
        }
        if (canScroll) {
            getViewPager().setCanScroll(true);
        } else {
            getViewPager().setCanScroll(false);
        }
    }

    public void buildNavigation() {
        if ((titleItems.length != normalIconItems.length) || (titleItems.length != selectIconItems.length) || (normalIconItems.length != selectIconItems.length)) {
            Log.e("DuobangNavigation", "请传入相同数量的Tab文字集合、未选中图标集合、选中图标集合");
            return;
        }

        tabCount = titleItems.length;

        removeNavigationAllView();

        setViewPagerAdapter();

        for (int i = 0; i < tabCount; i++) {
            View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.width = MeasureUtils.getScreenWidth(getContext()) / tabCount;

            itemView.setLayoutParams(params);
            itemView.setId(i);

            TextView text = itemView.findViewById(R.id.tab_text_tv);
            ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
            icon.setScaleType(scaleType);
            LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
            iconParams.width = (int) iconSize;
            iconParams.height = (int) iconSize;
            icon.setLayoutParams(iconParams);

            View hintPoint = itemView.findViewById(R.id.red_point);

            //提示红点
            RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
            hintPointParams.bottomMargin = (int) hintPointTop;
            hintPointParams.width = (int) hintPointSize;
            hintPointParams.height = (int) hintPointSize;
            hintPointParams.leftMargin = (int) hintPointLeft;
            hintPoint.setLayoutParams(hintPointParams);

            //消息红点
            TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
            msgPoint.setTextSize(MeasureUtils.px2sp(getContext(), msgPointTextSize));
            RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
            msgPointParams.bottomMargin = (int) msgPointTop;
            msgPointParams.width = (int) msgPointSize;
            msgPointParams.height = (int) msgPointSize;
            msgPointParams.leftMargin = (int) msgPointLeft;
            msgPoint.setLayoutParams(msgPointParams);

            hintPointList.add(hintPoint);
            msgPointList.add(msgPoint);

            imageViewList.add(icon);
            textViewList.add(text);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTabClickListener != null) {
                        if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    } else {
                        mViewPager.setCurrentItem(view.getId(), smoothScroll);
                    }
                }
            });

            LayoutParams textParams = (LayoutParams) text.getLayoutParams();
            textParams.topMargin = (int) tabTextTop;
            text.setLayoutParams(textParams);
            text.setText(titleItems[i]);
            text.setTextSize(MeasureUtils.px2sp(getContext(), tabTextSize));

            tabList.add(itemView);
            navigationLayout.addView(itemView);
        }
        select(0, false);
    }

    private void setViewPagerAdapter() {
        adapter = new ViewPagerAdapter(fragmentManager, fragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //构建中间带按钮的navigation
    public void buildAddNavigation() {

        if ((titleItems.length != normalIconItems.length) || (titleItems.length != selectIconItems.length) || (normalIconItems.length != selectIconItems.length)) {
            Log.e("DuobangNavigation", "请传入相同数量的Tab文字集合、未选中图标集合、选中图标集合");
            return;
        }
        tabCount = titleItems.length;
        if (tabCount % 2 == 0) {
            Log.e("DuobangNavigation", "MODE_ADD模式下请传入奇数数量的Tab文字集合、未选中图标集合、选中图标集合");
            return;
        }

        if (addAsFragment) {
            if (fragmentList.size() < tabCount) {
                Log.e("DuobangNavigation", "MODE_ADD模式下/addAsFragment=true时Fragment的数量应和传入tab集合数量相等");
                return;
            }
        } else {
            if (fragmentList.size() < tabCount - 1) {
                Log.e("DuobangNavigation", "MODE_ADD模式下/addAsFragment=false时Fragment的数量应比传入tab集合数量少一个");
                return;
            }
        }

        removeNavigationAllView();

        setViewPagerAdapter();

        for (int i = 0; i < tabCount; i++) {

            if (i == tabCount / 2) {
                RelativeLayout addItemView = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams addItemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                addItemParams.width = MeasureUtils.getScreenWidth(getContext()) / tabCount;
                addItemView.setLayoutParams(addItemParams);
                navigationLayout.addView(addItemView);

                final LinearLayout addLinear = new LinearLayout(getContext());
                addLinear.setOrientation(VERTICAL);
                addLinear.setGravity(Gravity.CENTER);
                final RelativeLayout.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                addImage = new ImageView(getContext());
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageParams.width = (int) addIconSize;
                imageParams.height = (int) addIconSize;
                addImage.setLayoutParams(imageParams);

                TextView addText = new TextView(getContext());
                addText.setTextSize(MeasureUtils.px2sp(getContext(), addTextSize));
                LinearLayout.LayoutParams addTextParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                addTextParams.topMargin = (int) addTextTopMargin;
                if (TextUtils.isEmpty(titleItems[i])) {
                    addText.setVisibility(GONE);
                } else {
                    addText.setVisibility(VISIBLE);
                }
                addText.setLayoutParams(addTextParams);
                addText.setText(titleItems[i]);

                if (addLayoutRule == RULE_CENTER) {
                    linearParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                } else if (addLayoutRule == RULE_BOTTOM) {
                    linearParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
                    if (addAlignBottom) {
                        if (textViewList != null && textViewList.size() > 0) {
                            textViewList.get(0).post(new Runnable() {
                                @Override
                                public void run() {
                                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) empty_line.getLayoutParams();
                                    params.height = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                                    empty_line.setLayoutParams(params);
                                    //linearParams.bottomMargin = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                                }
                            });

                        }
                    } else {
                        linearParams.bottomMargin = (int) addLayoutBottom;
                    }
                }

                addImage.setId(i);
                addImage.setImageResource(normalIconItems[i]);
                addImage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                                if (addAsFragment)
                                    mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        } else {
                            if (addAsFragment)
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                        if (onAddClickListener != null)
                            onAddClickListener.OnAddClickEvent(view);
                    }
                });

                imageViewList.add(addImage);
                textViewList.add(addText);


                addLinear.addView(addImage);
                addLinear.addView(addText);

                tabList.add(addLinear);

                AddContainerLayout.addView(addLinear, linearParams);
            } else {
                int index = i;

                View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.width = MeasureUtils.getScreenWidth(getContext()) / tabCount;

                itemView.setLayoutParams(params);
                itemView.setId(index);

                TextView text = itemView.findViewById(R.id.tab_text_tv);
                ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
                icon.setScaleType(scaleType);
                LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
                iconParams.width = (int) iconSize;
                iconParams.height = (int) iconSize;
                icon.setLayoutParams(iconParams);

                imageViewList.add(icon);
                textViewList.add(text);
                itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        final int tabPosition = view.getId();
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, view.getId())) {

                                if (tabPosition > tabCount / 2 && !addAsFragment) {
                                    mViewPager.setCurrentItem(tabPosition - 1, smoothScroll);
                                } else {
                                    mViewPager.setCurrentItem(view.getId(), smoothScroll);
                                }
                            }
                        } else {
                            if (tabPosition > tabCount / 2 && !addAsFragment) {
                                mViewPager.setCurrentItem(tabPosition - 1, smoothScroll);
                            } else {
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        }
                    }
                });

                LayoutParams textParams = (LayoutParams) text.getLayoutParams();
                textParams.topMargin = (int) tabTextTop;
                text.setLayoutParams(textParams);
                text.setText(titleItems[index]);
                text.setTextSize(MeasureUtils.px2sp(getContext(), tabTextSize));


                View hintPoint = itemView.findViewById(R.id.red_point);

                //提示红点
                RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
                hintPointParams.bottomMargin = (int) hintPointTop;
                hintPointParams.width = (int) hintPointSize;
                hintPointParams.height = (int) hintPointSize;
                hintPointParams.leftMargin = (int) hintPointLeft;
                hintPoint.setLayoutParams(hintPointParams);

                //消息红点
                TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
                msgPoint.setTextSize(MeasureUtils.px2sp(getContext(), msgPointTextSize));
                RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
                msgPointParams.bottomMargin = (int) msgPointTop;
                msgPointParams.width = (int) msgPointSize;
                msgPointParams.height = (int) msgPointSize;
                msgPointParams.leftMargin = (int) msgPointLeft;
                msgPoint.setLayoutParams(msgPointParams);


                hintPointList.add(hintPoint);
                msgPointList.add(msgPoint);


                tabList.add(itemView);
                navigationLayout.addView(itemView);
            }
        }
        select(0, false);
    }

    private void removeNavigationAllView() {

        for (int i = 0; i < AddContainerLayout.getChildCount(); i++) {
            if (AddContainerLayout.getChildAt(i).getTag() == null) {
                AddContainerLayout.removeViewAt(i);
            }
        }

        msgPointList.clear();
        hintPointList.clear();
        imageViewList.clear();
        textViewList.clear();
        tabList.clear();

        navigationLayout.removeAllViews();
    }

    private void addTabView(final int index) {
        View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.width = MeasureUtils.getScreenWidth(getContext()) / tabCount;

        itemView.setLayoutParams(params);
        itemView.setId(index);

        TextView text = itemView.findViewById(R.id.tab_text_tv);
        ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
        icon.setScaleType(scaleType);
        LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
        iconParams.width = (int) iconSize;
        iconParams.height = (int) iconSize;
        icon.setLayoutParams(iconParams);

        imageViewList.add(icon);
        textViewList.add(text);

        if (mode == MODE_ADD) {
            itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    final int tabPosition = view.getId();
                    if (onTabClickListener != null) {
                        if (!onTabClickListener.onTabClickEvent(view, view.getId())) {

                            if (tabPosition > tabCount / 2 && !addAsFragment) {
                                mViewPager.setCurrentItem(tabPosition - 1, smoothScroll);
                            } else {
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        }
                    } else {
                        if (tabPosition > tabCount / 2 && !addAsFragment) {
                            mViewPager.setCurrentItem(tabPosition - 1, smoothScroll);
                        } else {
                            mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                    }
                }
            });
        } else if (mode == MODE_ADD_VIEW) {
            itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    final int tabPosition = view.getId();
                    if (onTabClickListener != null) {
                        if (!onTabClickListener.onTabClickEvent(view, index)) {

                            if (index > tabCount / 2 && !addAsFragment) {
                                mViewPager.setCurrentItem(tabPosition, smoothScroll);
                            } else {
                                mViewPager.setCurrentItem(index, smoothScroll);
                            }
                        }
                    } else {
                        if (index > tabCount / 2 && !addAsFragment) {
                            mViewPager.setCurrentItem(tabPosition, smoothScroll);
                        } else {
                            mViewPager.setCurrentItem(index, smoothScroll);
                        }
                    }
                }
            });
        }

        LayoutParams textParams = (LayoutParams) text.getLayoutParams();
        textParams.topMargin = (int) tabTextTop;
        text.setLayoutParams(textParams);
        text.setText(titleItems[index]);
        text.setTextSize(MeasureUtils.px2sp(getContext(), tabTextSize));

        View hintPoint = itemView.findViewById(R.id.red_point);

        //提示红点
        RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
        hintPointParams.bottomMargin = (int) hintPointTop;
        hintPointParams.width = (int) hintPointSize;
        hintPointParams.height = (int) hintPointSize;
        hintPointParams.leftMargin = (int) hintPointLeft;
        hintPoint.setLayoutParams(hintPointParams);

        //消息红点
        TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
        msgPoint.setTextSize(MeasureUtils.px2sp(getContext(), msgPointTextSize));
        RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
        msgPointParams.bottomMargin = (int) msgPointTop;
        msgPointParams.width = (int) msgPointSize;
        msgPointParams.height = (int) msgPointSize;
        msgPointParams.leftMargin = (int) msgPointLeft;
        msgPoint.setLayoutParams(msgPointParams);

        hintPointList.add(hintPoint);
        msgPointList.add(msgPoint);

        tabList.add(itemView);
        navigationLayout.addView(itemView);
    }

    //自定义中间按钮
    public void buildAddViewNavigation() {

        if ((titleItems.length != normalIconItems.length) || (titleItems.length != selectIconItems.length) || (normalIconItems.length != selectIconItems.length)) {
            Log.e("DuobangNavigation", "请传入相同数量的Tab文字集合、未选中图标集合、选中图标集合");
            return;
        }
        tabCount = titleItems.length + 1;
        if (tabCount % 2 == 0) {
            Log.e("DuobangNavigation", "MODE_ADD_VIEW模式下请传入偶数数量的Tab文字集合、未选中图标集合、选中图标集合");
            return;
        }
        if (addAsFragment) {
            if (fragmentList.size() < tabCount) {
                Log.e("DuobangNavigation", "MODE_ADD_VIEW模式下/addAsFragment=true时Fragment的数量应比传入tab集合数量多一个");
                return;
            }
        } else {
            if (fragmentList.size() < tabCount - 1) {
                Log.e("DuobangNavigation", "MODE_ADD_VIEW模式下/addAsFragment=false时,Fragment的数量应和传入tab集合数量相等");
                return;
            }
        }

        removeNavigationAllView();

        setViewPagerAdapter();

        for (int i = 0; i < tabCount; i++) {

            if (i == tabCount / 2) {
                RelativeLayout addItemView = new RelativeLayout(getContext());
                RelativeLayout.LayoutParams addItemParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                addItemParams.width = MeasureUtils.getScreenWidth(getContext()) / tabCount;
                addItemView.setLayoutParams(addItemParams);
                navigationLayout.addView(addItemView);

                final RelativeLayout.LayoutParams linearParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //linearParams.width = MeasureUtils.getScreenWidth(getContext()) / tabCount;

                if (addLayoutRule == RULE_CENTER) {
                    linearParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                } else if (addLayoutRule == RULE_BOTTOM) {
                    linearParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    if (addAlignBottom) {
                        linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
                        if (textViewList != null && textViewList.size() > 0) {
                            textViewList.get(0).post(new Runnable() {
                                @Override
                                public void run() {
                                    linearParams.bottomMargin = (int) ((navigationHeight - textViewList.get(0).getHeight() - iconSize - tabTextTop) / 2);
                                }
                            });

                        }
                    } else {
                        linearParams.addRule(RelativeLayout.ABOVE, R.id.empty_line);
                        linearParams.bottomMargin = (int) addLayoutBottom;
                    }
                }
                customAddView.setId(i);
                customAddView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, view.getId())) {
                                if (addAsFragment)
                                    mViewPager.setCurrentItem(view.getId(), smoothScroll);
                            }
                        } else {
                            if (addAsFragment)
                                mViewPager.setCurrentItem(view.getId(), smoothScroll);
                        }
                        if (onAddClickListener != null)
                            onAddClickListener.OnAddClickEvent(view);
                    }
                });

                AddContainerLayout.addView(customAddView, linearParams);
            } else {
                int index;

                if (i > tabCount / 2) {
                    index = i - 1;
                } else {
                    index = i;
                }

                View itemView = View.inflate(getContext(), R.layout.navigation_tab_layout, null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                params.width = MeasureUtils.getScreenWidth(getContext()) / tabCount;

                itemView.setLayoutParams(params);
                itemView.setId(index);

                TextView text = itemView.findViewById(R.id.tab_text_tv);
                ImageView icon = itemView.findViewById(R.id.tab_icon_iv);
                icon.setScaleType(scaleType);
                LayoutParams iconParams = (LayoutParams) icon.getLayoutParams();
                iconParams.width = (int) iconSize;
                iconParams.height = (int) iconSize;
                icon.setLayoutParams(iconParams);

                imageViewList.add(icon);
                textViewList.add(text);
                final int finalI = i;
                itemView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        final int tabPosition = view.getId();
                        if (onTabClickListener != null) {
                            if (!onTabClickListener.onTabClickEvent(view, finalI)) {

                                if (finalI > tabCount / 2 && !addAsFragment) {
                                    mViewPager.setCurrentItem(tabPosition, smoothScroll);
                                } else {
                                    mViewPager.setCurrentItem(finalI, smoothScroll);
                                }
                            }
                        } else {
                            if (finalI > tabCount / 2 && !addAsFragment) {
                                mViewPager.setCurrentItem(tabPosition, smoothScroll);
                            } else {
                                mViewPager.setCurrentItem(finalI, smoothScroll);
                            }
                        }
                    }
                });

                LayoutParams textParams = (LayoutParams) text.getLayoutParams();
                textParams.topMargin = (int) tabTextTop;
                text.setLayoutParams(textParams);
                text.setText(titleItems[index]);
                text.setTextSize(MeasureUtils.px2sp(getContext(), tabTextSize));

                View hintPoint = itemView.findViewById(R.id.red_point);

                //提示红点
                RelativeLayout.LayoutParams hintPointParams = (RelativeLayout.LayoutParams) hintPoint.getLayoutParams();
                hintPointParams.bottomMargin = (int) hintPointTop;
                hintPointParams.width = (int) hintPointSize;
                hintPointParams.height = (int) hintPointSize;
                hintPointParams.leftMargin = (int) hintPointLeft;
                hintPoint.setLayoutParams(hintPointParams);

                //消息红点
                TextView msgPoint = itemView.findViewById(R.id.msg_point_tv);
                msgPoint.setTextSize(MeasureUtils.px2sp(getContext(), msgPointTextSize));
                RelativeLayout.LayoutParams msgPointParams = (RelativeLayout.LayoutParams) msgPoint.getLayoutParams();
                msgPointParams.bottomMargin = (int) msgPointTop;
                msgPointParams.width = (int) msgPointSize;
                msgPointParams.height = (int) msgPointSize;
                msgPointParams.leftMargin = (int) msgPointLeft;
                msgPoint.setLayoutParams(msgPointParams);

                hintPointList.add(hintPoint);
                msgPointList.add(msgPoint);

                tabList.add(itemView);
                navigationLayout.addView(itemView);

            }
        }

        select(0, false);

    }

    public ScrollViewPager getViewPager() {
        return mViewPager;
    }

    public void setAddViewLayout(View addViewLayout) {
        FrameLayout.LayoutParams addParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addViewLayout.addView(addViewLayout, addParams);
    }

    public ViewGroup getAddViewLayout() {
        return addViewLayout;
    }

    /**
     * tab图标、文字变换
     *
     * @param position
     */
    private void select(int position, boolean showAnim) {
        if (mode == MODE_NORMAL) {
            for (int i = 0; i < tabCount; i++) {
                if (i == position) {
                    if (anim != null && showAnim)
                        YoYo.with(anim).duration(300).playOn(tabList.get(i));
                    imageViewList.get(i).setImageResource(selectIconItems[i]);
                    textViewList.get(i).setTextColor(selectTextColor);
                    if (i == 2) {
                        textViewList.get(i).setTextColor(Color.parseColor("#FFA313"));
                    }
                } else {
                    imageViewList.get(i).setImageResource(normalIconItems[i]);
                    textViewList.get(i).setTextColor(normalTextColor);
                }
            }
        } else if (mode == MODE_ADD) {
            if (addAsFragment) {
                for (int i = 0; i < tabCount; i++) {
                    if (i == position) {
                        if (anim != null && showAnim && (position != tabCount / 2))
                            YoYo.with(anim).duration(300).playOn(tabList.get(i));
                        if (i == tabCount / 2) {
                            textViewList.get(i).setTextColor(addSelectTextColor);
                        } else {
                            textViewList.get(i).setTextColor(selectTextColor);
                        }
                        imageViewList.get(i).setImageResource(selectIconItems[i]);
                    } else {
                        imageViewList.get(i).setImageResource(normalIconItems[i]);
                        if (i == tabCount / 2) {
                            textViewList.get(i).setTextColor(addNormalTextColor);
                        } else {
                            textViewList.get(i).setTextColor(normalTextColor);
                        }
                    }
                }
            } else {
                if ((position > ((tabCount - 2) / 2))) {
                    position = position + 1;
                }
                for (int i = 0; i < tabCount; i++) {
                    if (i == position) {
                        if (anim != null && showAnim && (i != tabCount / 2))
                            YoYo.with(anim).duration(300).playOn(tabList.get(i));
                        imageViewList.get(i).setImageResource(selectIconItems[i]);
                        if (i == tabCount / 2) {
                            textViewList.get(i).setTextColor(addSelectTextColor);
                        } else {
                            textViewList.get(i).setTextColor(selectTextColor);
                        }
                    } else {
                        imageViewList.get(i).setImageResource(normalIconItems[i]);
                        if (i == tabCount / 2) {
                            textViewList.get(i).setTextColor(addNormalTextColor);
                        } else {
                            textViewList.get(i).setTextColor(normalTextColor);
                        }
                    }
                }
            }
        } else if (mode == MODE_ADD_VIEW) {
            int realPosition;

            if (addAsFragment) {
                for (int i = 0; i < tabCount; i++) {
                    if (i == tabCount / 2) {
                        continue;
                    } else if (i > tabCount / 2) {
                        realPosition = i - 1;
                    } else {
                        realPosition = i;
                    }
                    if (i == position) {
                        if (anim != null && showAnim)
                            YoYo.with(anim).duration(300).playOn(tabList.get(realPosition));
                        imageViewList.get(realPosition).setImageResource(selectIconItems[realPosition]);
                        textViewList.get(realPosition).setTextColor(selectTextColor);
                    } else {
                        imageViewList.get(realPosition).setImageResource(normalIconItems[realPosition]);
                        textViewList.get(realPosition).setTextColor(normalTextColor);
                    }
                }
            } else {
                for (int i = 0; i < tabCount - 1; i++) {
                    if (i == position) {
                        if (anim != null && showAnim)
                            YoYo.with(anim).duration(300).playOn(tabList.get(i));
                        imageViewList.get(i).setImageResource(selectIconItems[i]);
                        textViewList.get(i).setTextColor(selectTextColor);
                    } else {
                        imageViewList.get(i).setImageResource(normalIconItems[i]);
                        textViewList.get(i).setTextColor(normalTextColor);
                    }
                }
            }
        }
    }

    public void selectTab(int position) {
        getViewPager().setCurrentItem(position, smoothScroll);
    }

    /**
     * 设置是否显示小红点
     *
     * @param position 第几个tab
     * @param isShow   是否显示
     */
    public void setHotPoint(int position, boolean isShow) {
        if (hintPointList == null || hintPointList.size() < (position + 1))
            return;
        if (isShow) {
            hintPointList.get(position).setVisibility(VISIBLE);
        } else {
            hintPointList.get(position).setVisibility(GONE);
        }
    }

    /**
     * 设置消息数量
     *
     * @param position 第几个tab
     * @param count    显示的数量  99个以上显示99+  少于1则不显示
     */
    public void setMsgPointCount(int position, int count) {
        if (msgPointList == null || msgPointList.size() < (position + 1))
            return;
        if (count > 99) {
            msgPointList.get(position).setText("99+");
            msgPointList.get(position).setVisibility(VISIBLE);
        } else if (count < 1) {
            msgPointList.get(position).setVisibility(GONE);
        } else {
            msgPointList.get(position).setText(count + "");
            msgPointList.get(position).setVisibility(VISIBLE);
        }
    }

    /**
     * 清除数字消息
     *
     * @param position
     */
    public void clearMsgPoint(int position) {
        if (msgPointList == null || msgPointList.size() < (position + 1))
            return;
        msgPointList.get(position).setVisibility(GONE);
    }

    /**
     * 清除提示红点
     *
     * @param position
     */
    public void clearHintPoint(int position) {
        if (hintPointList == null || hintPointList.size() < (position + 1))
            return;
        hintPointList.get(position).setVisibility(GONE);
    }

    /**
     * 清空所有提示红点
     */
    public void clearAllHintPoint() {
        for (int i = 0; i < hintPointList.size(); i++) {
            hintPointList.get(i).setVisibility(GONE);
        }
    }

    /**
     * 清空所有消息红点
     */
    public void clearAllMsgPoint() {
        for (int i = 0; i < msgPointList.size(); i++) {
            msgPointList.get(i).setVisibility(GONE);
        }
    }

    public interface OnTabClickListener {
        boolean onTabClickEvent(View view, int position);
    }

    public interface OnAddClickListener {
        boolean OnAddClickEvent(View view);
    }

    public DuobangNavigation addLayoutHeight(int addLayoutHeight) {
        this.addLayoutHeight = MeasureUtils.dip2px(getContext(), addLayoutHeight);
        return this;
    }

    public DuobangNavigation scaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    public DuobangNavigation mode(int mode) {
        this.mode = mode;
        return this;
    }

    public DuobangNavigation hasPadding(boolean hasPadding) {
        this.hasPadding = hasPadding;
        return this;
    }

    public DuobangNavigation addIconSize(int addIconSize) {
        this.addIconSize = MeasureUtils.dip2px(getContext(), addIconSize);
        return this;
    }

    public DuobangNavigation onAddClickListener(DuobangNavigation.OnAddClickListener onAddClickListener) {
        this.onAddClickListener = onAddClickListener;
        return this;
    }

    public DuobangNavigation navigationBackground(int navigationBackground) {
        this.navigationBackground = navigationBackground;
        return this;
    }

    public DuobangNavigation navigationHeight(int navigationHeight) {
        this.navigationHeight = MeasureUtils.dip2px(getContext(), navigationHeight);
        return this;
    }

    public DuobangNavigation normalTextColor(int normalTextColor) {
        this.normalTextColor = normalTextColor;
        return this;
    }

    public DuobangNavigation selectTextColor(int selectTextColor) {
        this.selectTextColor = selectTextColor;
        return this;
    }

    public DuobangNavigation lineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public DuobangNavigation lineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public DuobangNavigation tabTextSize(int tabTextSize) {
        this.tabTextSize = MeasureUtils.sp2px(getContext(), tabTextSize);
        return this;
    }

    public DuobangNavigation tabTextTop(int tabTextTop) {
        this.tabTextTop = MeasureUtils.dip2px(getContext(), tabTextTop);
        return this;
    }

    public DuobangNavigation msgPointTextSize(int msgPointTextSize) {
        this.msgPointTextSize = MeasureUtils.sp2px(getContext(), msgPointTextSize);
        return this;
    }

    public DuobangNavigation msgPointSize(int msgPointSize) {
        this.msgPointSize = MeasureUtils.dip2px(getContext(), msgPointSize);
        return this;
    }

    public DuobangNavigation msgPointLeft(int msgPointLeft) {
        this.msgPointLeft = MeasureUtils.dip2px(getContext(), msgPointLeft);
        return this;
    }

    public DuobangNavigation msgPointTop(int msgPointTop) {
        this.msgPointTop = MeasureUtils.dip2px(getContext(), msgPointTop);
        return this;
    }

    public DuobangNavigation hintPointSize(int hintPointSize) {
        this.hintPointSize = MeasureUtils.dip2px(getContext(), hintPointSize);
        return this;
    }

    public DuobangNavigation hintPointLeft(int hintPointLeft) {
        this.hintPointLeft = MeasureUtils.dip2px(getContext(), hintPointLeft);
        return this;
    }

    public DuobangNavigation hintPointTop(int hintPointTop) {
        this.hintPointTop = MeasureUtils.dip2px(getContext(), hintPointTop);
        return this;
    }

    public DuobangNavigation titleItems(String[] titleItems) {
        this.titleItems = titleItems;
        return this;
    }

    public DuobangNavigation normalIconItems(int[] normalIconItems) {
        this.normalIconItems = normalIconItems;
        return this;
    }

    public DuobangNavigation selectIconItems(int[] selectIconItems) {
        this.selectIconItems = selectIconItems;
        return this;
    }

    public DuobangNavigation fragmentList(List<Fragment> fragments) {
        fragmentList.clear();
        fragmentList.addAll(fragments);
        return this;
    }

    public DuobangNavigation fragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        return this;
    }

    public DuobangNavigation anim(Anim anim) {
        if (anim != null) {
            this.anim = anim.getYoyo();
        } else {
            this.anim = null;
        }
        return this;
    }

    public DuobangNavigation addLayoutRule(int addLayoutRule) {
        this.addLayoutRule = addLayoutRule;
        return this;
    }

    public DuobangNavigation canScroll(boolean canScroll) {
        this.canScroll = canScroll;
        return this;
    }

    public DuobangNavigation smoothScroll(boolean smoothScroll) {
        this.smoothScroll = smoothScroll;
        return this;
    }

    public DuobangNavigation onTabClickListener(DuobangNavigation.OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        return this;
    }

    public DuobangNavigation iconSize(int iconSize) {
        this.iconSize = MeasureUtils.dip2px(getContext(), iconSize);
        return this;
    }

    public DuobangNavigation addLayoutBottom(int addLayoutBottom) {
        this.addLayoutBottom = MeasureUtils.dip2px(getContext(), addLayoutBottom);
        return this;
    }

    public DuobangNavigation addAsFragment(boolean addAsFragment) {
        this.addAsFragment = addAsFragment;
        return this;
    }

    public DuobangNavigation addCustomView(View customAddView) {
        this.customAddView = customAddView;
        return this;
    }

    public DuobangNavigation addTextSize(int addTextSize) {
        this.addTextSize = MeasureUtils.sp2px(getContext(), addTextSize);
        return this;
    }

    public DuobangNavigation addNormalTextColor(int addNormalTextColor) {
        this.addNormalTextColor = addNormalTextColor;
        return this;
    }

    public DuobangNavigation addSelectTextColor(int addSelectTextColor) {
        this.addSelectTextColor = addSelectTextColor;
        return this;
    }

    public DuobangNavigation addTextTopMargin(int addTextTopMargin) {
        this.addTextTopMargin = MeasureUtils.dip2px(getContext(), addTextTopMargin);
        return this;
    }

    public DuobangNavigation addAlignBottom(boolean addAlignBottom) {
        this.addAlignBottom = addAlignBottom;
        return this;
    }

    public String[] getTitleItems() {
        return titleItems;
    }

    public int[] getNormalIconItems() {
        return normalIconItems;
    }

    public int[] getSelectIconItems() {
        return selectIconItems;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public Techniques getAnim() {
        return anim;
    }

    public boolean isSmoothScroll() {
        return smoothScroll;
    }

    public DuobangNavigation.OnTabClickListener getOnTabClickListener() {
        return onTabClickListener;
    }

    public int getIconSize() {
        return iconSize;
    }

    public float getHintPointSize() {
        return hintPointSize;
    }

    public float getHintPointLeft() {
        return hintPointLeft;
    }

    public float getHintPointTop() {
        return hintPointTop;
    }

    public float getMsgPointTextSize() {
        return msgPointTextSize;
    }

    public float getMsgPointSize() {
        return msgPointSize;
    }

    public float getMsgPointLeft() {
        return msgPointLeft;
    }

    public float getMsgPointTop() {
        return msgPointTop;
    }

    public float getTabTextTop() {
        return tabTextTop;
    }

    public float getTabTextSize() {
        return tabTextSize;
    }

    public int getNormalTextColor() {
        return normalTextColor;
    }

    public int getSelectTextColor() {
        return selectTextColor;
    }

    public float getLineHeight() {
        return lineHeight;
    }

    public int getLineColor() {
        return lineColor;
    }

    public DuobangNavigation.OnAddClickListener getOnAddClickListener() {
        return onAddClickListener;
    }

    public float getAddIconSize() {
        return addIconSize;
    }

    public float getAddLayoutHeight() {
        return addLayoutHeight;
    }

    public int getNavigationBackground() {
        return navigationBackground;
    }

    public float getNavigationHeight() {
        return navigationHeight;
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public ViewPagerAdapter getAdapter() {
        return adapter;
    }


    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public int getMode() {
        return mode;
    }

    public LinearLayout getNavigationLayout() {
        return navigationLayout;
    }

    public RelativeLayout getContentView() {
        return contentView;
    }

    public View getLineView() {
        return lineView;
    }

    public ViewGroup getAddLayout() {
        return addViewLayout;
    }

    public float getAddLayoutBottom() {
        return addLayoutBottom;
    }

    public int getAddLayoutRule() {
        return addLayoutRule;
    }

    public RelativeLayout getAddContainerLayout() {
        return AddContainerLayout;
    }

    public boolean isHasPadding() {
        return hasPadding;
    }

    public boolean isAddAsFragment() {
        return addAsFragment;
    }

    public View getCustomAddView() {
        return customAddView;
    }

    public float getAddTextSize() {
        return addTextSize;
    }

    public int getAddNormalTextColor() {
        return addNormalTextColor;
    }

    public int getAddSelectTextColor() {
        return addSelectTextColor;
    }

    public float getAddTextTopMargin() {
        return addTextTopMargin;
    }

    public boolean isAddAlignBottom() {
        return addAlignBottom;
    }

    public ImageView getAddImage() {
        return addImage;
    }

    // --addIconBottom
}
