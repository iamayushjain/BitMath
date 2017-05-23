package com.ayush.bitmath;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HomeActivity extends AppCompatActivity {
    ImageView im2;
    private Animation mAnimation;
    RelativeLayout rv;
    LinearLayout rv2;
    Button bt1,bt2,bt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //   getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        bt1= (Button) findViewById(R.id.button1);
        bt2= (Button) findViewById(R.id.button2);
        bt3= (Button) findViewById(R.id.button3);
        findViewById(R.id.button1).getBackground().setLevel(0);
        findViewById(R.id.button2).getBackground().setLevel(1);
        findViewById(R.id.button3).getBackground().setLevel(2);

        rv2= (LinearLayout) findViewById(R.id.downLayout);
        rv= (RelativeLayout) findViewById(R.id.upLayout);
       MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
     //im2= (ImageView) findViewById(R.id.imageView2);

        Typeface face= Typeface.createFromAsset(getAssets(), "vtks _chalk.ttf");
        bt1.setTypeface(face);
        bt2.setTypeface(face);
        bt3.setTypeface(face);

       // final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
         final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.tansition);
        mAnimation=AnimationUtils.loadAnimation(this,R.anim.transition2);
       myAnim.setFillAfter(true);
        mAnimation.setFillAfter(true);
        mAnimation.setInterpolator(interpolator);
        rv2.setVisibility(View.VISIBLE);
        rv2.setAnimation(mAnimation);
        rv.setAnimation(myAnim);
       // rv2.setPivotX(0f);

     /*   ObjectAnimator scaleY = ObjectAnimator.ofFloat(rv2, "scaleX", 200f);
        scaleY.setInterpolator(new AccelerateInterpolator());
        scaleY.start();*/

      //  myAnim.setInterpolator(interpolator);
       // im2.startAnimation(myAnim);//bg transition and bounce
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
