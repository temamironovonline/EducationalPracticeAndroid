package com.example.educationalpracticeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    public static String avatar;
    public static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();
        openRegistration();
        openSignIn();

        SharedPreferences prefs = this.getSharedPreferences(
                "Date", Context.MODE_PRIVATE);
        if(prefs != null)
        {
            if(!prefs.getString("NickName", "").equals(""))
            {
                avatar = prefs.getString("Avatar", "");
                userName = prefs.getString("NickName", "");
                startActivity(new Intent(this, MainPageActivity.class));
            }
        }

    }

    private void openRegistration() { // При нажатии на текст с регистрацией отправляет на заглушку с регистрацией
        TextView registrationText = findViewById(R.id.signUp);

       registrationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void openSignIn() { // При нажатии на кнопку с авторизации отправляет на форму с авторизацией
        Button signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }

}