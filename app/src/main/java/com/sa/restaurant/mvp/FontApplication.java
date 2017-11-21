package com.sa.restaurant.mvp;

import android.app.Application;

import com.sa.restaurant.mvp.utils.TypefaceUtil;

/**
 * Created by bansaripatel on 24/10/17.
 */

public class FontApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.setDefaultFont(getApplicationContext(), "MONOSPACE", "fonts/RobotoLight.ttf");
    }
}
