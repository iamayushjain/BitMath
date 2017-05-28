package com.ayush.bitmath.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ayush.bitmath.MyBounceInterpolator;
import com.ayush.bitmath.R;
import com.ayush.bitmath.Utils.Constants;

public class HomeActivity extends AppCompatActivity {

    private Animation mAnimation;
    private RelativeLayout relativeLayoutLogo;
    private LinearLayout linearLayoutButton;
    private Button buttonPlay, buttonHighScore, buttonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);

        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.tansition);
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.transition2);
        myAnim.setFillAfter(true);
        mAnimation.setFillAfter(true);
        mAnimation.setInterpolator(interpolator);
        linearLayoutButton.setVisibility(View.VISIBLE);
        linearLayoutButton.setAnimation(mAnimation);
        relativeLayoutLogo.setAnimation(myAnim);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, GameActivity.class);
                startActivity(i);
            }
        });

    }

    private void init() {
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonHighScore = (Button) findViewById(R.id.buttonHighScore);
        buttonShare = (Button) findViewById(R.id.buttonShare);
        findViewById(R.id.buttonPlay).getBackground().setLevel(0);
        findViewById(R.id.buttonHighScore).getBackground().setLevel(1);
        findViewById(R.id.buttonShare).getBackground().setLevel(2);

        linearLayoutButton = (LinearLayout) findViewById(R.id.linearLayoutButton);
        relativeLayoutLogo = (RelativeLayout) findViewById(R.id.relativeLayoutLogo);

        Typeface face = Typeface.createFromAsset(getAssets(), Constants.TYPEFACE_VTKS_CHALK);
        buttonPlay.setTypeface(face);
        buttonHighScore.setTypeface(face);
        buttonShare.setTypeface(face);

    }
}
