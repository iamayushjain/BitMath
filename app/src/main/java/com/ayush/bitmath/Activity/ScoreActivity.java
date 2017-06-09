package com.ayush.bitmath.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ayush.bitmath.R;
import com.ayush.bitmath.Utils.Constants;
import com.ayush.bitmath.Utils.MyGoogleApiClient_Singleton;
import com.ayush.bitmath.Utils.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class ScoreActivity extends AppCompatActivity {

    private Button textViewScore, textViewHighScore;
    private ImageView buttonReplay, buttonStandings;
    LinearLayout option;
    int currentScore = 0;
    InterstitialAd mInterstitialAd;
 //   GoogleApiClient apiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        MobileAds.initialize(this, "ca-app-pub-8614462024900398~3850308663");


       /* apiClient = new GoogleApiClient.Builder(this)
                .addApi(Games.API)
                .addScope(Games.SCOPE_GAMES)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                       //  Log.e(getApplicationContext(), "Could not connect to Play games services");
                        // finish();
                    }
                }).build();*/
      //  HomeActivity.apiClient.connect();
        setContentView(R.layout.score_activity);
        Bundle bundle = getIntent().getExtras();
        currentScore = bundle.getInt(Constants.INTENT_CURRENT_SCORE);
        showAdd();
        //  Games.Leaderboards.submitScore(apiClient, getString(R.string.leaderboard_bit_math_global_rank), currentScore);
        init();

    }


    public void showAdd()
    {


        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        if (Utils.isNetworkAvailable(getApplicationContext())) {


            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    // Check the LogCat to get your test device ID
                    .addTestDevice(getResources().getString(R.string.test_id_1))
                    .addTestDevice(getResources().getString(R.string.test_id_2))
                    .addTestDevice(getResources().getString(R.string.test_id_3))
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }

        // Load ads into Interstitial Ads


        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
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


        textViewScore.setText("SCORE "+currentScore + "");

        SharedPreferences sharedPreferencesScore = getSharedPreferences(Constants.SHARED_PREF_SCORE, 0);
        int highScore = (sharedPreferencesScore.getInt(Constants.SHARED_PREF_SCORE_HIGH_SCORE, 0));
        if (highScore < currentScore) {
            SharedPreferences.Editor editor = sharedPreferencesScore.edit();
            editor.putInt(Constants.SHARED_PREF_SCORE_HIGH_SCORE, currentScore);
            editor.apply();
            textViewHighScore.setText("BEST "+currentScore + "");
          //  Games.Leaderboards.submitScore(apiClient, getString(R.string.leaderboard_my_leaderboard), currentScore);


        } else {
            textViewHighScore.setText("BEST "+highScore + "");

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

        //  return (apiClient != null && apiClient.isConnected());
        return true;
    }

}
