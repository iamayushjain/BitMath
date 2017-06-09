package com.ayush.bitmath.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.ayush.bitmath.Utils.FirebaseConstants;
import com.ayush.bitmath.Utils.LogWrapper;
import com.ayush.bitmath.Utils.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private Animation mAnimation;
    private InterstitialAd mInterstitialAd;
    private RelativeLayout relativeLayoutLogo;
    private LinearLayout linearLayoutButton;
    private Button buttonPlay, buttonHighScore, buttonShare;
    public static GoogleApiClient apiClient;
    private SharedPreferences sharedPreferencesAds;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReferenceBasic;
    private int adsCountLimit, currentAdsCount;
    private boolean isCurrentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        apiClient = new GoogleApiClient.Builder(this)
//                .addApi(Games.API)
//                .addScope(Games.SCOPE_GAMES)
//                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//                        // Log.e(getApplicationContext(), "Could not connect to Play games services");
//                        // finish();
//                    }
//                }).build();
        //  MyGoogleApiClient_Singleton.getInstance(apiClient);

        setContentView(R.layout.activity_home);
        init();
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceBasic = firebaseDatabase.getReference(FirebaseConstants.GENERAL);


    }


    private void loadInterstitialAds() {

        sharedPreferencesAds = getSharedPreferences(Constants.SHARED_PREF_ADS_COUNT_TABLE, 0);
        String adSavedOccurrence = (sharedPreferencesAds.getString(Constants.SHARED_PREF_LOGIN_ADS_COUNT, "0"));
        currentAdsCount = Integer.parseInt(adSavedOccurrence);
        currentAdsCount++;


        databaseReferenceBasic.child(FirebaseConstants.ADS_COUNT_HOME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                adsCountLimit = Integer.parseInt(snapshot.getValue().toString());

                if (currentAdsCount >= adsCountLimit) {
                    mInterstitialAd = new InterstitialAd(HomeActivity.this);
                    mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
                    if (Utils.isNetworkAvailable(getApplicationContext())) {


                        AdRequest adRequest = new AdRequest.Builder()
                                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                                .addTestDevice(getResources().getString(R.string.test_id_1))
                                .addTestDevice(getResources().getString(R.string.test_id_2))
                                .addTestDevice(getResources().getString(R.string.test_id_3))
                                .build();
                        mInterstitialAd.loadAd(adRequest);
                    }


                    mInterstitialAd.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            if (isCurrentActivity) {
                                if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                                    currentAdsCount = 0;
                                    mInterstitialAd.show();
                                }
                            } else {
                                LogWrapper.d(getApplicationContext(), "ads", "activity change");
                            }
                        }
                    });
                }

                SharedPreferences.Editor editor = sharedPreferencesAds.edit();
                editor.putString(Constants.SHARED_PREF_LOGIN_ADS_COUNT, currentAdsCount + "");
                editor.apply();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                LogWrapper.out(getApplicationContext(), databaseError.getMessage());
            }
        });
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
        loadInterstitialAds();


    }


    public void showLeaderboard(View v) {
        SharedPreferences sharedPreferencesScore = getSharedPreferences(Constants.SHARED_PREF_SCORE, 0);

        int hScore = Integer.parseInt(String.valueOf((sharedPreferencesScore.getInt(Constants.SHARED_PREF_SCORE_HIGH_SCORE, 0))));
        //Toast.makeText(this,""+hScore,Toast.LENGTH_SHORT).show();
        // Games.Leaderboards.submitScore(apiClient,
        //    getString(R.string.leaderboard_my_leaderboard),
        //      15);
        startActivityForResult(
                Games.Leaderboards.getLeaderboardIntent(apiClient,
                        getString(R.string.leaderboard_my_leaderboard)), 0);
    }

    public void share(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_link));
        startActivity(Intent.createChooser(intent, "Share using"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        isCurrentActivity = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCurrentActivity = true;
    }

    /*
   PASTE UNDER SCORE ++ FOR ACHIEVEMENT UNLOCK LIGHTNING FAST
    * if(score>20)
	{
   	 Games.Achievements.unlock(apiClient,getString(R.string.achievement_lightning_fast));
	}
    * */
}
