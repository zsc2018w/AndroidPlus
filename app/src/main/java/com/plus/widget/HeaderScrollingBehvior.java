package com.plus.widget;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import com.plus.R;

import java.lang.ref.WeakReference;

/**
 * description: behavior 处理headview 和 recycler 之间联动
 * author:  周昇辰
 * date:2019/5/16
 */


public class HeaderScrollingBehvior extends CoordinatorLayout.Behavior<RecyclerView> {

    //是否展开
    private boolean isExpanded=false;
    //是否滚动
    private boolean isScrolling=false;
    //依赖视图
    private WeakReference<View> dependentView;
    //scroller
    private Scroller scroller;
    //handler
    private Handler handler;

    //init 初始化
    public HeaderScrollingBehvior(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller=new Scroller(context);
        handler =new Handler();
    }
    //获取当前状态
    public boolean isExpanded() {
        return isExpanded;
    }

    //判断当前 是否依赖 headview
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        if(dependency.getId()== R.id.scrolling_header){
            dependentView=new WeakReference<View>(dependency);
            return true;
        }
        return false;
    }

    //负责对behavior控制的view 进行布局调整 (就是相当于onLayout )
    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, int layoutDirection) {
        CoordinatorLayout.LayoutParams lp= (CoordinatorLayout.LayoutParams) child.getLayoutParams();
         if(lp.height==CoordinatorLayout.LayoutParams.MATCH_PARENT){
          child.layout(0,0,parent.getWidth(),(int)(parent.getHeight()-getDependentViewCollapsedHeight()));
          return true;
         }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    //得到高度
    private float getDependentViewCollapsedHeight() {
        return getDependentView().getResources().getDimension(R.dimen.collapsed_header_height);
    }
    //获取依赖view
    private View getDependentView() {
        return dependentView.get();
    }
}
