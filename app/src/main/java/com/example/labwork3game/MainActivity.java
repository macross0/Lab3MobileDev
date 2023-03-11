package com.example.labwork3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(startGame);
    };

    View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startGameIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(startGameIntent);
            finish();
        }
    };


}