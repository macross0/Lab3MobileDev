package com.example.labwork3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View GameActivitySwitcher = findViewById(R.id.start_button);
        GameActivitySwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToGameActivity();
            }
        });
    }

    private void switchToGameActivity() {
        Intent switchToGameActivityIntent = new Intent(this, GameActivity.class);
        startActivity(switchToGameActivityIntent);
    }
}