package com.jameskelly.onhand.util;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.jameskelly.onhand.R;
import java.util.ArrayList;
import java.util.List;

public class CustomMoveBehaviour extends CoordinatorLayout.Behavior<LinearLayout> {

  private final static String TAG = CustomMoveBehaviour.class.getSimpleName();
  final Handler handler = new Handler();
  List<FloatingActionButton> fabs = new ArrayList<>();

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

  @Override
  public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, LinearLayout child,
      View directTargetChild, View target, final int nestedScrollAxes) {

    RecyclerView recyclerView;
    try {
      recyclerView = (RecyclerView) target;
    } catch (ClassCastException e) {
      Log.e(TAG, "Attempted to cast view to RecyclerView", e);
      return false;
    }

    final LinearLayout fabLinearLayout = child;

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, final int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (fabs.isEmpty()) {
          for (int i = 0; i < fabLinearLayout.getChildCount(); i++) {
            try {
              fabs.add((FloatingActionButton) fabLinearLayout.getChildAt(i));
            } catch (ClassCastException e) {
              Log.e(TAG, "Attempted to cast view to FAB", e);
            }
          }
        }

        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
          for (final FloatingActionButton fab : fabs) {
            fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
              @Override public void onHidden(FloatingActionButton fab) {
                ViewCompat.animate(fab).rotation(0f).start();
                fab.setVisibility(View.INVISIBLE); //because views were reporting back as visible after the animation
              }
            });
          }
        } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          for (final FloatingActionButton fab : fabs) {

            //only show the main button. no fab.getFabSize() available
            if (fab.getId() == R.id.add_content) {
              handler.postDelayed(() -> fab.show(), 500);
            }
          }
        }
      }
    });

    return true;
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
