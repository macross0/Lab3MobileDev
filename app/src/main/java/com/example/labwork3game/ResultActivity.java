package com.example.labwork3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    Boolean is60SecondsTimer;
    Boolean showTimer;
    String pickedUserFilename;
    Button sendResultToEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle = getIntent().getExtras();
        Integer result = bundle.getInt("result");
        is60SecondsTimer = bundle.getBoolean("is60SecondsTimer");
        showTimer = bundle.getBoolean("showTimer");
        pickedUserFilename = bundle.getString("pickedUserFilename");
        TextView ResultText = findViewById(R.id.result_text);

        sendResultToEmail = findViewById(R.id.sendResultToEmail);
        sendResultToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(getApplicationContext(), pickedUserFilename);
                user.read();
                user.sendEmail(getApplicationContext(), result);
            }
        });

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
        switchToGameActivityIntent.putExtra("is60SecondsTimer", is60SecondsTimer);
        switchToGameActivityIntent.putExtra("showTimer", showTimer);
        startActivity(switchToGameActivityIntent);
        finish();
    }
}