package com.example.picfilter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class image_edit_activity extends AppCompatActivity {

    ImageView imageEditing;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit_activity);

        imageEditing = findViewById(R.id.editImgId);
        textView = findViewById(R.id.textId);

        if(getIntent().hasExtra("galleryImage")) {
            Toast.makeText(image_edit_activity.this , "it is here 1 ",Toast.LENGTH_LONG).show();
            try {

                Bundle extras = getIntent().getExtras();
                Uri myUri=  Uri.parse(extras.getString("galleryImage"));
                imageEditing.setImageURI(myUri);
                imageEditing.clearColorFilter();
                imageEditing.getScaleType();

            }catch (Exception e){

                Toast.makeText(image_edit_activity.this , e.toString()+" problemssss ",Toast.LENGTH_LONG).show();

            }

        }
        if(getIntent().hasExtra("cameraImage")) {

            try {

                Intent intent = getIntent();
                Bitmap bitmap = (Bitmap) intent.getParcelableExtra("cameraImage");
                imageEditing.setImageBitmap(bitmap);

            }catch (Exception e){

                Toast.makeText(image_edit_activity.this , e.toString(),Toast.LENGTH_LONG).show();

            }

        }

    }

    public abstract static class BitmapResLoader {

        public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }

        private static int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and width
                final int heightRatio = Math.round((float) height / (float) reqHeight);
                final int widthRatio = Math.round((float) width / (float) reqWidth);

                // Choose the smallest ratio as inSampleSize value, this will guarantee
                // a final image with both dimensions larger than or equal to the
                // requested height and width.
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }

            return inSampleSize;
        }
    }
}