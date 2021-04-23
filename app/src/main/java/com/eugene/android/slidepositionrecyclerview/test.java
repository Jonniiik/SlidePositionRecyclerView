package com.eugene.android.slidepositionrecyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

public class test {

    CompositePageTransformer compositePageTransformer = new CompositePageTransformer();

    void test() {
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

            }
        });
    }
}
