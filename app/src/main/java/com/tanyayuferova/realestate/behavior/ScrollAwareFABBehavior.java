package com.tanyayuferova.realestate.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Hides FAB when scrolling down
 *
 * Created by Tanya Yuferova on 1/25/2018.
 */

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    long animationDuration = 400;

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull final FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);

        //When scroll down - hide, when scroll up - show
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
            animation.setDuration(animationDuration);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    child.setVisibility(View.INVISIBLE);
                    child.setAlpha(0.0f);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            child.startAnimation(animation);

// This code doesn't work because it seems that an android animation is not truly finished when the onAnimationEnd event is fired
//            child.animate()
//                    .alpha(0.0f)
//                    .setDuration(animationDuration)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            super.onAnimationEnd(animation);
//                            child.clearAnimation();
//                            //FixMe doesn't work
//                            //child.setVisibility(View.INVISIBLE);
//                        }
//                    }).start();

        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.setVisibility(View.VISIBLE);
            child.animate()
                    .alpha(1.0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            child.clearAnimation();
                        }
                    }).start();
        }
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
}

