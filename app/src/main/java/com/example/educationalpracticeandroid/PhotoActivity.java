package com.example.educationalpracticeandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.Objects;

public class PhotoActivity extends AppCompatActivity {

    SubsamplingScaleImageView imageView;
    View view;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Objects.requireNonNull(getSupportActionBar()).hide();

        imageView = findViewById(R.id.image);
        if (ProfileActivity.profileImage.getImageProfile().exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(ProfileActivity.profileImage.getImageProfile().getAbsolutePath());
            imageView.setImage(ImageSource.bitmap(myBitmap));
        }

        imageView.setOnTouchListener(new OnSwipeTouchListener(PhotoActivity.this) {
            public void onSwipeRight() {
                ProfileActivity.profileImage = null;
                startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
            }
            public void onSwipeLeft() {
                try {
                    ProfileActivity.profileImage.imageProfile.delete();
                    ProfileActivity.profileImage = null;
                    Toast.makeText(PhotoActivity.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
                }
                catch (Exception e)
                {
                    Toast.makeText(PhotoActivity.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view = findViewById(R.id.view);
        view.setOnTouchListener(new OnSwipeTouchListener(PhotoActivity.this) {
            public void onSwipeRight() {
                ProfileActivity.profileImage = null;
                startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
            }
            public void onSwipeLeft() {
                try {
                    ProfileActivity.profileImage.imageProfile.delete();
                    ProfileActivity.profileImage = null;
                    Toast.makeText(PhotoActivity.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
                }
                catch (Exception e)
                {
                    Toast.makeText(PhotoActivity.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Close(View view)
    {
        ProfileActivity.profileImage = null;
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void Delete(View view)
    {
        try {
            ProfileActivity.profileImage.imageProfile.delete();
            ProfileActivity.profileImage = null;
            Toast.makeText(PhotoActivity.this, "Фотография успешно удалена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PhotoActivity.this, ProfileActivity.class));
        }
        catch (Exception e)
        {
            Toast.makeText(PhotoActivity.this, "При удаление фотографии возникла ошибка!", Toast.LENGTH_SHORT).show();
        }
    }
}
