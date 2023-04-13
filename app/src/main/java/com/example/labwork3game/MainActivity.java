package com.example.labwork3game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CheckBox showTimer;
    ToggleButton countdownTime;
    Button startButton;
    RadioGroup usersList;
    TextInputEditText usernameInput;
    TextInputEditText emailInput;
    Button addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersList = findViewById(R.id.usersList);
        showTimer = findViewById(R.id.checkBox);
        countdownTime = findViewById(R.id.toggleButton);
        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(startGame);
        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);
        addUser = findViewById(R.id.addUser);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = String.valueOf(usernameInput.getText());
                String email = String.valueOf(emailInput.getText());
                if (!name.equals("null") && !email.equals("null")) {
                    User user = new User(getApplicationContext(), name, email);
                    user.write();
                    updateUsersList(getApplicationContext(), false);
                }
            }
        });

        updateUsersList(getApplicationContext(), true);
    };

    private void updateUsersList(Context context, boolean isStartup) {
        usersList.removeAllViews();
        int checkByDefaultID = 0;
        List<User> users = User.getListOfUsers(context);
        for (int i = 0; i < users.size(); i++) {
            RadioButton userRB = new RadioButton(context);
            userRB.setText(users.get(i).name);
            userRB.setTextColor(getColor(R.color.white));
            usersList.addView(userRB);
            if (i == 0 && isStartup) {
                checkByDefaultID = userRB.getId();
            }
        }
        if (isStartup) {
            usersList.check(checkByDefaultID);
        }
    }

    View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startGameIntent = new Intent(MainActivity.this, GameActivity.class);
            RadioButton pickedUserRB = findViewById(usersList.getCheckedRadioButtonId());
            String pickedUserFilename = pickedUserRB.getText() + ".user";

            startGameIntent.putExtra("pickedUserFilename", pickedUserFilename);
            startGameIntent.putExtra("is60SecondsTimer", !countdownTime.isChecked());
            startGameIntent.putExtra("showTimer", showTimer.isChecked());
            startActivity(startGameIntent);
            finish();
        }
    };
}