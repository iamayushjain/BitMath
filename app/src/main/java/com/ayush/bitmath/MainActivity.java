package com.ayush.bitmath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayush.bitmath.Logical.Addition;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer, textViewEquation, textViewScore;
    private EditText outputEditText;
    Addition addition;
    int score = 0;
    boolean isTimerStart = false;
    ProgressBar progressBarTimer;
    private AdView mAdView;

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
        progressBarTimer.setMax(30);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isTimerStart = false;
        textViewTimer.setText("Timer");
        score = 0;
        textViewScore.setText(score + "");
        outputEditText.setText("");
        addition = new Addition();
        addition.innerLoop();
        progressBarTimer.setProgress(30);
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(outputEditText.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        outputEditText.requestFocus();

        final CountDownTimer timer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(millisUntilFinished / 1000 + "");
                progressBarTimer.setProgress((int) (millisUntilFinished / 1000));
            }

            public void onFinish() {
                textViewTimer.setText("done");
                progressBarTimer.setProgress(0);
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra("currentScore", score);
                startActivity(intent);

            }
        };


        textViewEquation.setText(addition.getEquation());
//        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                        v.vibrate(40);
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


    @Override
    protected void onPause() {
        super.onPause();
//        Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Toast.makeText(getApplicationContext(), "Stop", Toast.LENGTH_LONG).show();
    }

    private void loadBannerAds() {
        AdRequest adRequest1 = new AdRequest.Builder()
                .addTestDevice(getResources().getString(R.string.test_id_1))
                .addTestDevice(getResources().getString(R.string.test_id_2))
                .addTestDevice(getResources().getString(R.string.test_id_3))
                .build();
        mAdView.loadAd(adRequest1);
    }

}
