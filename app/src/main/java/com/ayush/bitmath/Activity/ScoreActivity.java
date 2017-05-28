package com.ayush.bitmath.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ayush.bitmath.R;
import com.ayush.bitmath.Utils.Constants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class ScoreActivity extends AppCompatActivity {

    private Button textViewScore, textViewHighScore;
    private ImageView buttonReplay, buttonStandings;
    LinearLayout option;
    int currentScore = 0;
    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        Bundle bundle = getIntent().getExtras();
        currentScore = bundle.getInt(Constants.INTENT_CURRENT_SCORE);
        init();

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
//        apiClient = new GoogleApiClient.Builder(getApplicationContext())
//                .addApi(Games.API)
//                .addScope(Games.SCOPE_GAMES)
//                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
//                    @Override
//                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//                        LogWrapper.e(getApplicationContext(), "Play Services", connectionResult.getErrorMessage() + "Could not connect to Play games services");
////                        finish();
//                    }
//                })
//                .build();

        textViewScore.setText(currentScore + "");
        SharedPreferences sharedPreferencesScore = getSharedPreferences(Constants.SHARED_PREF_SCORE, 0);
        int highScore = (sharedPreferencesScore.getInt(Constants.SHARED_PREF_SCORE_HIGH_SCORE, 0));
        if (highScore < currentScore) {
            SharedPreferences.Editor editor = sharedPreferencesScore.edit();
            editor.putInt(Constants.SHARED_PREF_SCORE_HIGH_SCORE, currentScore);
            editor.apply();
            textViewHighScore.setText(currentScore + "");
        } else {
            textViewHighScore.setText(highScore + "");
            if (isSignedIn()) {
                Games.Leaderboards.submitScore(apiClient,
                        getString(R.string.leaderboard_bit_math_global_rank),
                        currentScore);
            }
        }
        buttonReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonStandings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* startActivityForResult(
                        Games.Leaderboards.getLeaderboardIntent(apiClient,
                                getString(R.string.leaderboard_bit_math_global_rank)), 0);*/
                Intent i = new Intent(ScoreActivity.this, HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade, R.anim.fadeout);

            }
        });
    }

    private boolean isSignedIn() {
        return (apiClient != null && apiClient.isConnected());
    }

}
