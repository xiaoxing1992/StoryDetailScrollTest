package com.rz.storydetailscrolltest.interpolator;

import android.animation.TimeInterpolator;

import static java.lang.Math.pow;

public class DecelerateAccelerateInterpolator implements TimeInterpolator {

    //@Override
    //public float getInterpolation(float x) {
    //    return x < 0.5f ? 4f * x * x * x : (float) (1f - pow(-2f * x + 2f, 3f) / 2f);
    //}

    @Override
    public float getInterpolation(float x) {
        return x < 0.5f ? 7f * x * x * x* x : (float) (1f - pow(-2f * x + 2f, 4f) / 2f);
    }


    //
    //@Override public float getInterpolation(float x) {
    //    return x < 0.5f ? 8f * x * x * x * x : (float) (1f - pow(-2f * x + 2f, 4f) / 2f);
    //}

    //@Override
    //public float getInterpolation(float x) {
    //    return (float) (x < 0.5f
    //                    ? (1f - sqrt(1f - pow(2f * x, 2f))) / 2f
    //                    : (sqrt(1f - pow(-2f * x + 2f, 2f)) + 1f) / 2f);
    //}
}

