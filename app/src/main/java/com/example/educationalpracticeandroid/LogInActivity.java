package com.example.educationalpracticeandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {

    private TextView email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);

        Objects.requireNonNull(getSupportActionBar()).hide();

        SharedPreferences prefs = this.getSharedPreferences(
                "Date", Context.MODE_PRIVATE); // Получение данных о пользователе
        if(prefs != null)
        {
            email.setText(prefs.getString("Email", "")); // Заполнение почты ранее входившего пользователя
            password.requestFocus();
        }

        openRegistration();
        getSignIn();
    }


    Button signIn, profileSignIn;

    private void getSignIn() {

        signIn = findViewById(R.id.signInButton);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals(""))
                {
                    Toast.makeText(LogInActivity.this, "Поле email не может быть пустым", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().equals(""))
                {
                    Toast.makeText(LogInActivity.this, "Поле password не может быть пустым", Toast.LENGTH_SHORT).show();
                }
                else if (!email.getText().toString().contains("@"))
                {
                    Toast.makeText(LogInActivity.this, "Mail должен содержать символ @", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getUser();
                }
            }
        });

        profileSignIn = findViewById(R.id.profileButton);

        profileSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals(""))
                {
                    Toast.makeText(LogInActivity.this, "Поле email не может быть пустым", Toast.LENGTH_SHORT).show();
                }
                else if (password.getText().toString().equals(""))
                {
                    Toast.makeText(LogInActivity.this, "Поле password не может быть пустым", Toast.LENGTH_SHORT).show();
                }
                else if (!email.getText().toString().contains("@"))
                {
                    Toast.makeText(LogInActivity.this, "Mail должен содержать символ @", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getUser();
                }
            }
        });
    }


    private void openRegistration() {
        TextView registrationText = findViewById(R.id.registerText);

        registrationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (LogInActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUser()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mskko2021.mad.hakta.pro/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiMethods retrofitAPI = retrofit.create(ApiMethods.class);

        User user = new User(email.getText().toString(), password.getText().toString());

        Call<UserBack> call = retrofitAPI.postUser(user);

        call.enqueue(new Callback<UserBack>() {
            @Override
            public void onResponse(Call<UserBack> call, Response<UserBack> response) {
                if (response.isSuccessful()){

                    if(response.body() != null)
                    {
                        if(response.body().getToken() != null)
                        {
                            SharedPreferences prefs = getSharedPreferences( // Сохранение данных
                                    "Date", Context.MODE_PRIVATE);
                            prefs.edit().putString("Email", "" + email.getText()).apply();
                            prefs.edit().putString("Avatar", "" + response.body().getAvatar()).apply();
                            prefs.edit().putString("NickName", "" + response.body().getNickName()).apply();

                            HomeActivity.avatar = response.body().getAvatar();
                            HomeActivity.userName = response.body().getNickName();
                            Intent intent = new Intent(LogInActivity.this, MainPageActivity.class);
                            Bundle b = new Bundle();
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    }

                    MainPageActivity.currentUser = response.body();
                    startActivity(new Intent(LogInActivity.this, MainPageActivity.class));
                }
                else{
                    Toast.makeText(LogInActivity.this, "Пользователь не найден", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<UserBack> call, Throwable t) {
                Toast.makeText(LogInActivity.this, "Ошибка: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
