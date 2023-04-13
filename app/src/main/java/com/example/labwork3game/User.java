package com.example.labwork3game;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.cardview.widget.CardView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements java.io.Serializable {
    protected transient Context context;
    public transient String filename;
    public String name;
    public String email;
    public User(Context context, String name, String email) {
        this.context = context;
        this.filename = name + ".user";
        this.name = name;
        this.email = email;
    }
    public User(Context context, String filename) {
        this.context = context;
        this.filename = filename;
        this.read();
    }

    private String generateEmailText(Integer score) {
        return String.format(
                "%s, Ваш результат логічної гри на визначення відповідності кольорів за відведений час: %d.",
                this.name,
                score
        );
    }

    public void sendEmail(Context context, Integer score) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + this.email + "?subject=Результат логічної гри" + "&body=" + generateEmailText(score)));
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent chooserIntent = Intent.createChooser(emailIntent, "Надіслати повідомлення за допомогою:");
        chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooserIntent);
    }

    public void read() {
        try {
            FileInputStream file = this.context.openFileInput(this.filename);
            ObjectInputStream objectInput = new ObjectInputStream(file);
            User fileObjectBuffer = (User) objectInput.readObject();
            updateFromObject(this.context, fileObjectBuffer);
            file.close();
            objectInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            FileOutputStream file = this.context.openFileOutput(this.filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutput = new ObjectOutputStream(file);
            objectOutput.writeObject(this);
            file.close();
            objectOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateFromObject(Context context, User object) {
        this.name = object.name;
        this.email = object.email;
        this.context = context;
    }

    public static List<User> getListOfUsers(Context context) {
        String[] filenames = context.fileList();
        List<User> users = new ArrayList<User>();
        for (String file : filenames) {
            User user = new User(context, file);
            user.read();
            users.add(user);
        }
        return users;
    }
}
