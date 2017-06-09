package com.ayush.bitmath.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayush.bitmath.Logical.Addition;
import com.ayush.bitmath.R;
import com.ayush.bitmath.Utils.Constants;
import com.ayush.bitmath.Utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

public class GameActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    private TextView textViewTimer, textViewEquation, textViewScore;
    private EditText outputEditText;
    private Addition addition;
    private int score = 0;
    boolean isTimerStart = false;
    private ProgressBar progressBarTimer;
    private AdView mAdView;
    private NativeExpressAdView mNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        init();
    }

    private void init() {
        textViewTimer = (TextView) findViewById(R.id.textTimer);
        textViewEquation = (TextView) findViewById(R.id.textEquation);
        textViewScore = (TextView) findViewById(R.id.textScore);
        outputEditText = (EditText) findViewById(R.id.outputEditText);
        progressBarTimer = (ProgressBar) findViewById(R.id.progressBarCircle);
        mAdView = (AdView) findViewById(R.id.adView);
        loadBannerAds();
        progressBarTimer.setMax(Constants.GAME_TIMER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isTimerStart = false;
        textViewTimer.setText(Constants.GAME_TIMER + "");
        score = 0;
        textViewScore.setText(score + "");
        outputEditText.setText("");
        addition = new Addition();
        addition.innerLoop();
        progressBarTimer.setProgress(Constants.GAME_TIMER);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(outputEditText.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        outputEditText.requestFocus();

        final CountDownTimer timer = new CountDownTimer(Constants.GAME_TIMER * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(millisUntilFinished / 1000 + "");
                progressBarTimer.setProgress((int) (millisUntilFinished / 1000));
            }

            public void onFinish() {
                textViewTimer.setText(0 + "");
                progressBarTimer.setProgress(0);
                Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
                intent.putExtra(Constants.INTENT_CURRENT_SCORE, score);
                startActivity(intent);

            }
        };


        textViewEquation.setText(addition.getEquation());
        outputEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!(s == null || (s.toString()).equals(""))) {

                    int editTextOutput = Integer.parseInt(s.toString());
                    if (editTextOutput == addition.getOutput()) {
                        score++;
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(Constants.VIBRATOR_DURATION);
                        textViewScore.setText(score + "");
                        addition.innerLoop();
                        textViewEquation.setText(addition.getEquation());
                        outputEditText.setText(null);
                        if (!isTimerStart) {
                            isTimerStart = true;
                            timer.start();
                        }
                    }
                }
            }
        });
    }


    private void loadBannerAds() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            AdRequest adRequest1 = new AdRequest.Builder()
                    .addTestDevice(getResources().getString(R.string.test_id_1))
                    .addTestDevice(getResources().getString(R.string.test_id_2))
                    .addTestDevice(getResources().getString(R.string.test_id_3))
                    .build();
            mAdView.loadAd(adRequest1);
        } else {
            mAdView.setVisibility(View.GONE);
        }
    }

}
