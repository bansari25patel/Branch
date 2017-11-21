package com.sa.restaurant.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sa.restaurant.MainActivity;
import com.sa.restaurant.R;
import com.sa.restaurant.mvp.service.LocationService;


public class SplashActivity extends AppCompatActivity
{
    private Handler splashHandler;


    final Runnable splashRunnable =new Runnable() {
        @Override
        public void run()
        {
            final Intent splashIntent=new Intent(SplashActivity.this, MainActivity.class);
            startActivity(splashIntent);
            finish();


        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashHandler=new Handler();
    }


    @Override
    protected void onResume() {
        super.onResume();
        splashHandler.postDelayed(splashRunnable,5000);
    }
}
