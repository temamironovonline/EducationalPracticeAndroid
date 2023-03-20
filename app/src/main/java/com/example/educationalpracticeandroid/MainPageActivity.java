package com.example.educationalpracticeandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainPageActivity extends AppCompatActivity {

    private final List<Elements> listElements = new ArrayList<>();
    private final List<Feelings> listFeeling = new ArrayList<>();
    private ElementsAdapter elementsAdapter;
    private FeelingsAdapter feelingsAdapter;
    final static String userVariableKey = "USER_VARIABLE";
    public static UserBack currentUser;

    ImageView sideMenu, userAvatar, userProfile;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Objects.requireNonNull(getSupportActionBar()).hide();

        sideMenu = findViewById(R.id.sideMenu);

        userAvatar = findViewById(R.id.userAvatar);
        new ElementsAdapter.DownloadImage((ImageView) userAvatar)
                .execute(HomeActivity.avatar);

        userProfile = findViewById(R.id.userProfile);;

        NavigationButtons();

        TextView userHelloMessage = findViewById(R.id.userHelloMessage);
        userHelloMessage.setText(userHelloMessage.getText().toString() + HomeActivity.userName + "!");

        ListView blocksList = findViewById(R.id.blocksList);
        elementsAdapter = new ElementsAdapter(MainPageActivity.this, listElements);
        blocksList.setAdapter(elementsAdapter);
        new GetQuotes().execute();

        RecyclerView fillingView = findViewById(R.id.fillingView);
        fillingView.setHasFixedSize(true);
        fillingView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        feelingsAdapter = new FeelingsAdapter(listFeeling, MainPageActivity.this);
        fillingView.setAdapter(feelingsAdapter);
        new GetFeeling().execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putAll(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentUser = (UserBack) savedInstanceState.get(userVariableKey);
    }

    private void NavigationButtons()
    {
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPageActivity.this, MenuActivity.class));
            }
        });

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPageActivity.this, ProfileActivity.class));
            }
        });

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPageActivity.this, ProfileActivity.class));
            }
        });
    }

    private class GetQuotes extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/quotes");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (Exception exception) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                listElements.clear();
                elementsAdapter.notifyDataSetInvalidated();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray = object.getJSONArray("data");

                for (int i = 0; i < tempArray.length(); i++) {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Elements tempProduct = new Elements(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getString("description")
                    );
                    listElements.add(tempProduct);
                    elementsAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception exception) {
                Toast.makeText(MainPageActivity.this, "Ошибка при отображении", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetFeeling extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("http://mskko2021.mad.hakta.pro/api/feelings");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (Exception exception) {
                return null;
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                listFeeling.clear();
                feelingsAdapter.notifyDataSetChanged();

                JSONObject object = new JSONObject(s);
                JSONArray tempArray = object.getJSONArray("data");

                for (int i = 0; i < tempArray.length(); i++) {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Feelings tempProduct = new Feelings(
                            productJson.getInt("id"),
                            productJson.getString("title"),
                            productJson.getString("image"),
                            productJson.getInt("position")
                    );
                    listFeeling.add(tempProduct);
                    feelingsAdapter.notifyDataSetChanged();
                }
            } catch (Exception exception) {
                Toast.makeText(MainPageActivity.this, "Ошибка при отображении", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
