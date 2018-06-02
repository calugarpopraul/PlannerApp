package com.example.calug.plannerapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by calug on 3/25/2018.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.man,
            R.drawable.goal,
            R.drawable.calendar,
            R.drawable.chart
    };

    public String[] slide_headings = {
            "User Experience",
            "Set your goals",
            "Don't miss a thing",
            "Boost your productivity"
    };

    public String[] slide_descs = {
            "We offer you the simplest UI interface so we can make your experience better and easier",
            "We help you to set and register your goals,meetings,and even payments so you can't miss a thing",
            "As you saw previously, you can't miss a thing, and event better, we let you to personalize your event by adding more information about it",
            "And what's more important, PlannerApp it's boosting your productivity day by day.         Enjoy our App"
    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view ==(RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);


         return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
