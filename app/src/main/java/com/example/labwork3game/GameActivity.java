package com.example.labwork3game;

import static java.lang.Math.round;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    TextView timerTextView;
    ProgressBar progressBar;
    long startTime = 0;
    int score = 0;
    Map<String, Integer> colors = new HashMap<String, Integer>();
    Map<String, String> colorTexts = new HashMap<String, String>();
    Object[] colorKeys;

    boolean is60SecondsTimer;
    boolean showTimer;
    Integer countdownTime;
    Handler timerHandler = new Handler();
    Runnable timerRunnable;

    private void switchToResultActivity(int result) {
        Intent switchToResultActivityIntent = new Intent(this, ResultActivity.class);
        Bundle bundle = new Bundle();
        switchToResultActivityIntent.putExtra("result", result);
        switchToResultActivityIntent.putExtra("is60SecondsTimer", is60SecondsTimer);
        switchToResultActivityIntent.putExtra("showTimer", showTimer);
        switchToResultActivityIntent.putExtras(bundle);
        startActivity(switchToResultActivityIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.startTime = System.currentTimeMillis();
        timerTextView = (TextView) findViewById(R.id.timer);
        progressBar = findViewById(R.id.progressBar);

        colors.put("yellow", Color.parseColor("#fcd303"));
        colors.put("red", Color.parseColor("#fc0303"));
        colors.put("green", Color.parseColor("#14fc03"));
        colors.put("blue", Color.parseColor("#0b03fc"));
        colors.put("purple", Color.parseColor("#9403fc"));
        colors.put("pink", Color.parseColor("#fc03e3"));
        colors.put("orange", Color.parseColor("#fc5203"));
        colorTexts.put("yellow", "Жовтий");
        colorTexts.put("red", "Червоний");
        colorTexts.put("green", "Зелений");
        colorTexts.put("blue", "Синій");
        colorTexts.put("purple", "Пурпуровий");
        colorTexts.put("pink", "Рожевий");
        colorTexts.put("orange", "Оранджевий");
        colorKeys = colorTexts.keySet().toArray();

        Bundle bundle = getIntent().getExtras();
        is60SecondsTimer = bundle.getBoolean("is60SecondsTimer");
        showTimer = bundle.getBoolean("showTimer");

        if (is60SecondsTimer) {
            countdownTime = 60;
        } else {
            countdownTime = 15;
        }
        if (!showTimer) {
            timerTextView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }


        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);

                if (seconds >= countdownTime) {
                    switchToResultActivity(score);
                    finish();
                    return;
                }

                timerTextView.setText(String.format("%d", seconds));
                int percentage = seconds * 100 / countdownTime;
                progressBar.setProgress(percentage);
                timerHandler.postDelayed(this, 500);
            }
        };

        timerHandler.postDelayed(timerRunnable, 0);

        boolean yesIsARightAnswer = updateColors();
        updateAnswerButtons(yesIsARightAnswer);
    };

    private boolean updateColors() {

        TextView colorName = findViewById(R.id.color_name);
        TextView coloredText = findViewById(R.id.colored_text);

        boolean yesIsARightAnswer;

        String[] pickedColors = new String[4];

        pickedColors[0] = getRandomColorKey(pickedColors);
        pickedColors[1] = getRandomColorKey(pickedColors);
        pickedColors[2] = getRandomColorKey(pickedColors);
        pickedColors[3] = getRandomColorKey(pickedColors);

        String firstColorText = colorTexts.get(pickedColors[0]);
        String forthColorText = colorTexts.get(pickedColors[3]);

        Integer firstColor = colors.get(pickedColors[0]);
        Integer secondColor = colors.get(pickedColors[1]);
        Integer thirdColor = colors.get(pickedColors[2]);

        colorName.setText(firstColorText);
        colorName.setTextColor(secondColor);

        Random random = new Random();
        if (random.nextBoolean()) {
            yesIsARightAnswer = true;
            coloredText.setTextColor(firstColor);
        }
        else {
            yesIsARightAnswer = false;
            coloredText.setTextColor(thirdColor);
        }
        coloredText.setText(forthColorText);

        return yesIsARightAnswer;
    };

    private void updateAnswerButtons(boolean yesIsARightAnswer) {
        Button yesButton = findViewById(R.id.yes_button);
        Button noButton = findViewById(R.id.no_button);

        if (yesIsARightAnswer) {
            yesButton.setOnClickListener(answeredCorrectly);
            noButton.setOnClickListener(answeredIncorrectly);
            return;
        }
        yesButton.setOnClickListener(answeredIncorrectly);
        noButton.setOnClickListener(answeredCorrectly);
    }

    View.OnClickListener answeredCorrectly = new View.OnClickListener() {
        public void onClick(View v) {
            score++;
            boolean yesIsARightAnswer = updateColors();
            updateAnswerButtons(yesIsARightAnswer);
        }
    };

    View.OnClickListener answeredIncorrectly = new View.OnClickListener() {
        public void onClick(View v) {
            boolean yesIsARightAnswer = updateColors();
            updateAnswerButtons(yesIsARightAnswer);
        }
    };

    private String getRandomColorKey(String[] colorsToAvoid) {

        Random random = new Random();
        String colorKey;

        while (true) {
            colorKey = (String) colorKeys[random.nextInt(colorKeys.length)];
            if (colorsToAvoid.length == 0) {
                return colorKey;
            }
            boolean isUnique = true;
            for (int i = 0; i < colorsToAvoid.length; i++) {
                if (colorKey.equals(colorsToAvoid[i])) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                return colorKey;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    };

    @Override
    public void onResume() {
        super.onResume();
        timerHandler.postDelayed(timerRunnable, 0);
    };
}