package com.ayush.bitmath.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ayush.bitmath.R;
import com.ayush.bitmath.Utils.Constants;
import com.ayush.bitmath.Utils.LogWrapper;

public class SplashActivity extends AppCompatActivity {
    private ImageView imageViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        imageViewTitle = (ImageView) findViewById(R.id.imageViewTitle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Thread timer = new Thread() {
            public void run() {
                try {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade1);
                    imageViewTitle.startAnimation(animation);
                    sleep(Constants.SPLASH_ACTIVITY_TIMEOUT);
                } catch (InterruptedException e) {
                    LogWrapper.printStackTrace(getApplicationContext(), e);
                } finally {

                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade, R.anim.fadeout);

                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
