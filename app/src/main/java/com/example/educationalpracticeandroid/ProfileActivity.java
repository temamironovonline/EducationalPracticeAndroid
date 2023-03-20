package com.example.educationalpracticeandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class ProfileActivity extends AppCompatActivity {

    ImageView image;
    TextView userName;

    OutputStream outputStream;

    private ProfileImagesAdapter imageAdapter;
    private List<ProfileImages> imageList = new ArrayList<>();

    public static ProfileImages profileImage;

    ImageView sideMenu, homeButton, musicButton;

    TextView exitUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();

        userName = findViewById(R.id.userName);
        userName.setText(HomeActivity.userName);

        image = findViewById(R.id.avatar);
        new ElementsAdapter.DownloadImage((ImageView) image)
                .execute(HomeActivity.avatar);

        GridView profileImagesList = findViewById(R.id.profileImagesList);
        imageAdapter = new ProfileImagesAdapter(ProfileActivity.this, imageList);
        profileImagesList.setAdapter(imageAdapter);
        GetImageProfile();
        NavigationButtons();

        profileImagesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProfileImages mask = imageList.get(i);
                if(mask.getImageProfile() == null)
                {
                    addImage();
                }
                else
                {
                    profileImage = imageList.get(i);
                    startActivity(new Intent(ProfileActivity.this, PhotoActivity.class));
                }
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void NavigationButtons()
    {
        sideMenu = findViewById(R.id.menuButton);
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MenuActivity.class));
            }
        });

        exitUser = findViewById(R.id.exitProfile);
        exitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences(
                        "Date", Context.MODE_PRIVATE);
                prefs.edit().putString("Avatar", "").apply();
                prefs.edit().putString("NickName", "").apply();

                startActivity(new Intent(ProfileActivity.this, LogInActivity.class));
            }
        });

        homeButton = findViewById(R.id.logoButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainPageActivity.class));
            }
        });

        musicButton = findViewById(R.id.musicButton);
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MusicActivity.class));
            }
        });
    }

    private void GetImageProfile()
    {
        File dir = new File(getApplicationInfo().dataDir + "/MyPhotos/");
        dir.mkdirs();
        imageList.clear();
        imageAdapter.notifyDataSetInvalidated();
        String path = getApplicationInfo().dataDir + "/MyPhotos";
        File directory = new File(path);
        File[] files = directory.listFiles();
        int j = 0;
        for (int i = 0; i < files.length; i++)
        {
            Long last = files[i].lastModified();
            ProfileImages tempProduct = new ProfileImages(
                    j,
                    files[i].getAbsolutePath(),
                    files[i],
                    getFullTime(last)
            );
            imageList.add(tempProduct);
            imageAdapter.notifyDataSetInvalidated();
        }
        ProfileImages tempProduct = new ProfileImages(
                j,
                null,
                null,
                "null"
        );
        imageList.add(tempProduct);
        imageAdapter.notifyDataSetInvalidated();
    }

    public static final String getFullTime(final long timeInMillis)
    {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);
        c.setTimeZone(TimeZone.getDefault());
        return format.format(c.getTime());
    }

    public void addImage()
    {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        someActivityResultLauncher.launch(photoPickerIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Bitmap bitmap = null;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        try
                        {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        File dir = new File(getApplicationInfo().dataDir + "/MyPhotos/");
                        dir.mkdirs();
                        File file = new File(dir, System.currentTimeMillis() + ".jpg");
                        try {
                            outputStream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Toast.makeText(ProfileActivity.this, "Изображение успешно сохранено", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(ProfileActivity.this, "При сохранение изображения возникла ошибка!", Toast.LENGTH_LONG).show();
                        }
                        GetImageProfile();
                    }
                }
            });
}
