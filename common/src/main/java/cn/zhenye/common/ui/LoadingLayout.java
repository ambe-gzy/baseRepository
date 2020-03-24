package cn.zhenye.common.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import cn.zhenye.common.R;

/**
 * @author WilliamChik on 2019/3/12.
 */
public class LoadingLayout extends LinearLayout {

    private AnimatorSet animatorSet;

    private View point1,point2,point3;

    private static int ANIMATOR_DURATION = 800;

    private static int DIVIDE_DURATION = ANIMATOR_DURATION/4;

    public boolean isPlayAnimator = false;

    public LoadingLayout(Context context) {
        super(context);
        initUI(context);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }

    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.loading_view,this);
        initView();
    }


    private void initView(){
        point1 = findViewById(R.id.loading_point1);
        point2 = findViewById(R.id.loading_point2);
        point3 = findViewById(R.id.loading_point3);

        animatorSet = new AnimatorSet();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(point1,"translationY",0,-30,10,5,0);
        animator1.setDuration(ANIMATOR_DURATION);
        animator1.setRepeatCount(-1);
        animator1.setRepeatMode(ValueAnimator.RESTART);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(point2,"translationY",0,-30,10,5,0);
        animator2.setDuration(ANIMATOR_DURATION);
        animator2.setRepeatCount(-1);
        animator2.setRepeatMode(ValueAnimator.RESTART);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(point3,"translationY",0,-30,10,5,0);
        animator3.setDuration(ANIMATOR_DURATION);
        animator3.setRepeatCount(-1);
        animator3.setRepeatMode(ValueAnimator.RESTART);
        animator3.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorSet.play(animator1).after(DIVIDE_DURATION);
        animatorSet.play(animator2).after(DIVIDE_DURATION*2);
        animatorSet.play(animator3).after(DIVIDE_DURATION*3);

        post(new Runnable() {
            @Override
            public void run() {
                if (getVisibility() == VISIBLE) {
                    startAnimator();
                }
            }
        });
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            startAnimator();
        } else {
            stopAnimator();
        }
    }

    private void startAnimator( ) {
        if (isPlayAnimator) {
            return;
        }
        isPlayAnimator = true;
        animatorSet.start();
    }

    private void stopAnimator() {
        isPlayAnimator = false;
        animatorSet.cancel();
    }
}
