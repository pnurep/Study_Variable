package com.dev.gold.viewpagerduolingo;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gold on 2018. 1. 16..
 */

public class MyAdapter extends PagerAdapter {

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.item_cardview, container, false);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}
