package com.zxm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.statelayout.R;

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

public class StateLayout_Old extends FrameLayout {
    //不同的状态
    public static final int STATE_LOADING = 0;
    public static final int STATE_ERROR = 1;
    public static final int STATE_SUCCESS = 2;

    public StateLayout_Old(Context context) {
        super(context);
    }

    public StateLayout_Old(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取在xml中写的属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateLayout_Old);
        int loadingLayout = typedArray.getResourceId(R.styleable.StateLayout_Old_error_layout, 0);
        int successLayout = typedArray.getResourceId(R.styleable.StateLayout_Old_success_layout, 0);
        int errorLayout = typedArray.getResourceId(R.styleable.StateLayout_Old_error_layout, 0);
        setStateLayout(STATE_LOADING, loadingLayout);
        setStateLayout(STATE_ERROR, errorLayout);
        setStateLayout(STATE_SUCCESS, successLayout);
    }

    //通过方法设置状态布局(资源ID)
    public void setStateLayout(int state, int layout) {
        if (layout == 0) {
            TextView textView = new TextView(getContext());
            if (state == STATE_LOADING) {
                textView.setText("正在玩命加载中。。。");
            } else if (state == STATE_SUCCESS) {
                textView.setText("加载成功");
            } else if (state == STATE_ERROR) {
                textView.setText("加载失败");
            }
            setStateView(state, textView);
        }else {
            setStateView(state, View.inflate(getContext(), layout, null));
        }
    }
    //当前状态
    int mCurrentState = -1;
    View loadingView;
    View successView;
    View errorView;

    //通过方法设置布局样式(布局Vi
    public void setStateView(int state, View view) {
        if (state == STATE_LOADING) {
            if (loadingView != null) {
                removeView(loadingView);
            }
            loadingView = view;
        } else if (state == STATE_SUCCESS) {
            if (successView != null) {
                removeView(successView);
            }
            successView = view;
        } else if (state == STATE_ERROR) {
            if (errorView != null) {
                removeView(errorView);
            }
            errorView = view;
        }
        if (mCurrentState == state){
            view.setVisibility(VISIBLE);
        }else {
            view.setVisibility(GONE);
        }
        addView(view);
    }

    //通过方法设置状态
    public void setState(int state){
     loadingView.setVisibility(GONE);
    errorView.setVisibility(GONE);
     successView.setVisibility(GONE);

        if (state == STATE_LOADING){
            loadingView.setVisibility(VISIBLE);
        }else if (state == STATE_SUCCESS){
            successView.setVisibility(VISIBLE);
        }else if (state == STATE_ERROR){
            errorView.setVisibility(VISIBLE);
        }
      mCurrentState = state;
    }

    //通过方法拿到当前的状态
    public int getState(){
        return mCurrentState;
    }

    //通过状态拿到对应的状态布局
    public View getStateView(int state){
        if (state == STATE_LOADING){
            return loadingView;
        }else if (state == STATE_SUCCESS){
            return successView;
        }else  if (state == STATE_ERROR){
            return errorView;
        }
        return null;
    }
}
