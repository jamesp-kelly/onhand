package com.jameskelly.onhand.util;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class AppLayoutCustomBehavior extends AppBarLayout.ScrollingViewBehavior {

  public AppLayoutCustomBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AppLayoutCustomBehavior() {

  }

  @Override public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child,
      View directTargetChild, View target, int nestedScrollAxes) {
    return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
        nestedScrollAxes);
  }
}
