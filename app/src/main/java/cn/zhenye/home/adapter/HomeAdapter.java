package cn.zhenye.home.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import cn.zhenye.common.ui.DrawerArrowDrawable;
import cn.zhenye.home.R;

public class HomeAdapter {
    private Context mContext;
    private float offset;
    private boolean flipped;
    private boolean rounded = false;
    private DrawerArrowDrawable drawerArrowDrawable;

    private HomeAdapter() {
    }

    public HomeAdapter(Context context) {
        mContext = context;
    }

    /**
     * 侧拉栏动画，点击事件
     * @param drawerLayout
     * @param menuIv
     */
    public void initDrawer(final DrawerLayout drawerLayout, final ImageView menuIv) {
        drawerArrowDrawable = new DrawerArrowDrawable(mContext.getResources());
        drawerArrowDrawable.setStrokeColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        menuIv.setImageDrawable(drawerArrowDrawable);

        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }
        });
        menuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                rounded = !rounded;
                drawerArrowDrawable = new DrawerArrowDrawable(mContext.getResources(), rounded);
                drawerArrowDrawable.setParameter(offset);
                drawerArrowDrawable.setFlip(flipped);
                drawerArrowDrawable.setStrokeColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                menuIv.setImageDrawable(drawerArrowDrawable);

            }
        });
    }
}
