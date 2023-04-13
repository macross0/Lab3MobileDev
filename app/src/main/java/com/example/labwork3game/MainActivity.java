package com.example.labwork3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CheckBox showTimer;
    ToggleButton countdownTime;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showTimer = findViewById(R.id.checkBox);
        countdownTime = findViewById(R.id.toggleButton);

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(startGame);
    };

    View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startGameIntent = new Intent(MainActivity.this, GameActivity.class);
            startGameIntent.putExtra("is60SecondsTimer", !countdownTime.isChecked());
            startGameIntent.putExtra("showTimer", showTimer.isChecked());
            startActivity(startGameIntent);
            finish();
        }
    };
}