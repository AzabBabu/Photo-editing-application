package com.example.picfilter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.picfilter.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    int GALLERY_IMAGE_REQUEST_CODE = 45;
    int CAMERA_IMAGE_REQUEST_CODE = 55;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.galleryBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getImageFromAlbum();

            }
        });

        binding.cameraBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFromCamera();
            }
        });
    }

    // Get Image From Camera.................

    private void getImageFromCamera() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 33);
        } else {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, CAMERA_IMAGE_REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
                // display error state to the user

                Log.i("Error", e.toString());
            }

        }
    }


    // Get Image From Gallery.................


    private void getImageFromAlbum() {
        try {


            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE);


        } catch (Exception exp) {
            Log.i("Error", exp.toString());
        }
    }

    // Get Result Activity .................


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {

            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Intent intent = new Intent(this, image_edit_activity.class);
                intent.putExtra("cameraImage", photo);
                startActivity(intent);
            } catch (Exception e) {

                e.printStackTrace();
            }

        }

        if (requestCode == GALLERY_IMAGE_REQUEST_CODE) {
            if (data.getData() != null) {

                try {

                    Uri imageUri = data.getData();
                    Intent intent = new Intent(this, image_edit_activity.class);
                    intent.putExtra("galleryImage", imageUri.toString());
                    startActivity(intent);
                    this.finish();
                    Toast.makeText(this, "image is gone ", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}