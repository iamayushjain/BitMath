package com.ayush.bitmath;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ayush.bitmath.Utils.LogWrapper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class ScoreActivity extends AppCompatActivity {

    private TextView textViewScore, textViewHighScore;
    private Button buttonReplay, buttonStandings;
    int currentScore = 0;
    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_activity);
        Bundle b = getIntent().getExtras();
        currentScore = b.getInt("currentScore");
        init();

    }

    private void init() {
        textViewScore = (TextView) findViewById(R.id.scoreText);
        textViewHighScore = (TextView) findViewById(R.id.highScoreText);
        buttonReplay = (Button) findViewById(R.id.buttonReplay);
        buttonStandings = (Button) findViewById(R.id.buttonStandings);
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
        SharedPreferences sharedPreferencesScore = getSharedPreferences("Score", 0);
        int highScore = (sharedPreferencesScore.getInt("HighScore", 0));
        if (highScore < currentScore) {
            SharedPreferences.Editor editor = sharedPreferencesScore.edit();
            editor.putInt("HighScore", currentScore);
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
                startActivityForResult(
                        Games.Leaderboards.getLeaderboardIntent(apiClient,
                                getString(R.string.leaderboard_bit_math_global_rank)), 0);
            }
        });
    }

    private boolean isSignedIn() {
        return (apiClient != null && apiClient.isConnected());
    }

}
