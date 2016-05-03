package com.jameskelly.onhand.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import java.util.List;

public class CustomMoveBehaviour extends CoordinatorLayout.Behavior<LinearLayout> {

  private final static String TAG = CustomMoveBehaviour.class.getSimpleName();

  public CustomMoveBehaviour(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child,
      View dependency) {
    return dependency instanceof Snackbar.SnackbarLayout;
  }

  @Override
  public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child,
      View dependency) {
    if (dependency instanceof Snackbar.SnackbarLayout) {
      float translationY = getFabTranslationYForSnackbar(parent, child);
      child.setTranslationY(translationY);
    }

    return false;
  }

  private float getFabTranslationYForSnackbar(CoordinatorLayout parent,
      View view) {
    float minOffset = 0;
    final List<View> dependencies = parent.getDependencies(view);
    for (int i = 0, z = dependencies.size(); i < z; i++) {
      final View snackBar = dependencies.get(i);
      if (snackBar instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(view, snackBar)) {
        minOffset = Math.min(minOffset,
            ViewCompat.getTranslationY(snackBar) - snackBar.getHeight());
      }
    }

    return minOffset;
  }
}
