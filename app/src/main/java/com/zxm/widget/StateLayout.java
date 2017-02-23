package com.zxm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.statelayout.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * **
 * 状态布局的 核心
 * 维护多种状态（正在加载、加载失败、加载成功）及其对应的View；通过设置状态，就可以设置View的可见性
 * 需求：
 * 0 三种状态
 * 1 这些状态所对应的View是外部指定的
 * 1.1 可以通过代码指定
 * 1.2 可以通过自定义xml属性在布局中指定
 * 1.3 具备默认的（TextView）
 * 2 可以根据状态切换View
 * 3 提供一些get方法
 * 类的设计：
 * 0 用int类型的常量来表示三种状态
 * 1.1
 * public void setStateView(int state, View view)
 * public void setStateLayout(int state, int layout)
 * 1.2 自定义属性，在构造方法中获取，并使用
 * 1.3 通过代码实现
 * 2  public void setState(int state)
 * 3
 * public int getState();
 * public View getStateView(int state)
 */

public class StateLayout extends FrameLayout {
    public static final int STATE_NONE = -1;
    public static final int STATE_LOADING = 0;
    public static final int STATE_ERROR = 1;
    public static final int STATE_SUCCESS = 2;
    public static final int STATE_EMPTY = 3;
    public static final int COUNT_STATE = 3;
    //用注解来规范一些参数
    @IntDef({STATE_LOADING, STATE_ERROR, STATE_SUCCESS,STATE_EMPTY,STATE_NONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    public StateLayout(Context context) {
        this(context, null);
    }

    int[] mLayouts = new int[COUNT_STATE];

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            // 获取在xml中写的属性的值
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateLayout_Old);
            for (int i = 0; i < COUNT_STATE; i++) {
                mLayouts[i] = typedArray.getResourceId(i, 0);
            }
        }
        for (int i = 0; i < COUNT_STATE; i++) {
            setStateLayout(i, mLayouts[i]);
        }
    }

    String[] mDefaultContents = new String[]{"正在玩命加载", "加载出错了，请检查网络连接", "恭喜你，答对了"};
    //对外提供设置状态以及状态样式的方法（资源ID）
    public void setStateLayout( int state,@LayoutRes int layout) {
        if (layout == 0) {
            TextView textView = new TextView(getContext());
            textView.setText(mDefaultContents[state]);
            setStateView(state, textView);
        } else {
            setStateView(state, View.inflate(getContext(), layout, null));
        }
    }

    View[] mViews = new View[COUNT_STATE];
   //对外提供设置状态以及状态样式的方法（View）
    public void setStateView(@State int state, @NonNull View view) {

        if (mViews[state] != null) {
            removeView(mViews[state]);
        }
        mViews[state] = view;

        // 如果当前状态与给定状态相等，显示，不一致，隐藏
        if (state == mCurrentState) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        addView(view);
    }


    int mCurrentState = STATE_NONE;
//对外提供设置当界面状态的方法
    public void setState(int state) {
        for (int i = 0; i < mViews.length; i++) {
            mViews[i].setVisibility(i == state ? View.VISIBLE : View.GONE);
        }
        mCurrentState = state;
    }
  //对外提供获取当前状态的方法
    public @State int getState() {
        return mCurrentState;
    }
  //对外提供通过状态获取布局的方法
    public View getStateView(int state) {
        return mViews[state];
    }
}
