package com.example.labwork3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        Integer result = bundle.getInt("result");
        TextView ResultText = findViewById(R.id.result_text);
        ResultText.setText("Результат: " + String.format("%d", result));

        View RestartActivitySwitcher = findViewById(R.id.start_again_button);
        RestartActivitySwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToGameActivity();
            }
        });
    }

    private void switchToGameActivity() {
        Intent switchToGameActivityIntent = new Intent(this, GameActivity.class);
        startActivity(switchToGameActivityIntent);
        finish();
    }
}