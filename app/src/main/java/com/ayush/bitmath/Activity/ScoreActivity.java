package com.ayush.bitmath.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ayush.bitmath.R;
import com.ayush.bitmath.Utils.Constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

public class ScoreActivity extends AppCompatActivity {

    private Button textViewScore, textViewHighScore;
    private ImageView buttonReplay, buttonStandings;
    private LinearLayout option;
    private int currentScore = 0;
    private boolean isSingleBackPress;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.score_activity);
        init();
        Bundle bundle = getIntent().getExtras();
        currentScore = bundle.getInt(Constants.INTENT_CURRENT_SCORE);
        loadNativeAd();
    }

    private void loadNativeAd() {
        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);

        AdRequest request = new AdRequest.Builder()
                .addTestDevice(getResources().getString(R.string.test_id_1))
                .addTestDevice(getResources().getString(R.string.test_id_2))
                .addTestDevice(getResources().getString(R.string.test_id_3))
                .build();

        adView.loadAd(request);
    }

    private void init() {
        textViewScore = (Button) findViewById(R.id.scoreText);
        textViewHighScore = (Button) findViewById(R.id.highScoreText);
        buttonReplay = (ImageView) findViewById(R.id.buttonReplay);
        buttonStandings = (ImageView) findViewById(R.id.buttonStandings);
        option = (LinearLayout) findViewById(R.id.option);
        findViewById(R.id.scoreText).getBackground().setLevel(0);
        findViewById(R.id.highScoreText).getBackground().setLevel(1);
        findViewById(R.id.option).getBackground().setLevel(2);
        findViewById(R.id.parentRelativeLayout).setBackgroundColor(Color.TRANSPARENT);

        textViewScore.setText("SCORE " + currentScore + "");

        SharedPreferences sharedPreferencesScore = getSharedPreferences(Constants.SHARED_PREF_SCORE, 0);
        int highScore = (sharedPreferencesScore.getInt(Constants.SHARED_PREF_SCORE_HIGH_SCORE, 0));
        if (highScore < currentScore) {
            SharedPreferences.Editor editor = sharedPreferencesScore.edit();
            editor.putInt(Constants.SHARED_PREF_SCORE_HIGH_SCORE, currentScore);
            editor.apply();
            textViewHighScore.setText("BEST " + currentScore + "");


        } else {
            textViewHighScore.setText("BEST " + highScore + "");

            if (isSignedIn()) {
                //  Games.Leaderboards.submitScore(apiClient, getString(R.string.leaderboard_bit_math_global_rank), currentScore);
            }
            // startActivityForResult(Games.Leaderboards.getLeaderboardIntent(apiClient, getString(R.string.leaderboard_bit_math_global_rank)), 0);
        }
        buttonReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  SharedPreferences sharedPreferencesScore = getSharedPreferences(Constants.SHARED_PREF_SCORE, 0);
                int highScore = (sharedPreferencesScore.getInt(Constants.SHARED_PREF_SCORE_HIGH_SCORE, 0));
                if (highScore < currentScore){
                Games.Leaderboards.submitScore(HomeActivity.apiClient,
                        getString(R.string.leaderboard_my_leaderboard),
                        currentScore);}*/
                finish();
            }
        });

        buttonStandings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   startActivityForResult(
                //         Games.Leaderboards.getLeaderboardIntent(apiClient,
                //               getString(R.string.leaderboard_my_leaderboard)), 0);
               /* startActivityForResult(
                        Games.Leaderboards.getLeaderboardIntent(apiClient,
                                getString(R.string.leaderboard_bit_math_global_rank)), 0);*/
                Intent i = new Intent(ScoreActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fade, R.anim.fadeout);


            }
        });
    }

    private boolean isSignedIn() {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (isSingleBackPress) {
            finishAffinity();
        } else {
            Toast.makeText(getApplicationContext(), "Press again to Exit", Toast.LENGTH_SHORT).show();
            isSingleBackPress = true;
        }
    }
}
