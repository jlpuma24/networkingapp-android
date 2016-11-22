package com.elitgon.nw.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.elitgon.R;
import com.elitgon.nw.adapters.OnBoardingPagerAdapter;
import com.elitgon.nw.util.SwipeType;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Usuario on 16/09/2016.
 */
public class OnBoardingActivity extends BaseActivity {

    private ViewPager pagerSlides;
    private CircleIndicator indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        pagerSlides = (ViewPager) findViewById(R.id.viewPagerSlides);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        pagerSlides.setAdapter(new OnBoardingPagerAdapter(getSupportFragmentManager()));
        indicator.setViewPager(pagerSlides);
        pagerSlides.setOnTouchListener(new ActivitySwipeDetector(this));
    }

    public void passViewPagerPage(SwipeType swipeType){
        switch (pagerSlides.getCurrentItem()){
            case 0: if (swipeType == SwipeType.RIGHT_TO_LEFT)
                pagerSlides.setCurrentItem(1);
                break;
            case 1: if (swipeType == SwipeType.RIGHT_TO_LEFT)
                pagerSlides.setCurrentItem(2);
            else if (swipeType == SwipeType.LEFT_TO_RIGHT)
                pagerSlides.setCurrentItem(0);
                break;
            case 2: if (swipeType == SwipeType.LEFT_TO_RIGHT)
                pagerSlides.setCurrentItem(1);
            else if (swipeType == SwipeType.RIGHT_TO_LEFT){
                startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_right);
                finish();
            }
                break;
        }
    }

    public class ActivitySwipeDetector implements View.OnTouchListener {

        private static final int MIN_DISTANCE = 100;
        private float downX, downY, upX, upY;
        private OnBoardingActivity onBoardingActivity;

        public ActivitySwipeDetector(OnBoardingActivity onBoardingActivity){
            this.onBoardingActivity = onBoardingActivity;
        }

        public void onRightSwipe(){
            onBoardingActivity.passViewPagerPage(SwipeType.RIGHT_TO_LEFT);
        }

        public void onLeftSwipe(){
            onBoardingActivity.passViewPagerPage(SwipeType.LEFT_TO_RIGHT);
        }

        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    downY = event.getY();
                    return true;
                }
                case MotionEvent.ACTION_UP: {
                    upX = event.getX();
                    upY = event.getY();
                    float deltaX = downX - upX;
                    float deltaY = downY - upY;
                    // swipe horizontal?
                    if(Math.abs(deltaX) > Math.abs(deltaY))
                    {
                        if(Math.abs(deltaX) > MIN_DISTANCE){
                            // left or right
                            if(deltaX > 0) { this.onRightSwipe(); return true; }
                            if(deltaX < 0) { this.onLeftSwipe(); return true; }
                        }
                        else {
                            return false; // We don't consume the event
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
