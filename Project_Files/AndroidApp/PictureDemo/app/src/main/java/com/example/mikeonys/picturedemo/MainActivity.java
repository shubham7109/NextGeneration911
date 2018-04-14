package com.example.mikeonys.picturedemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private final int requestCode = 20;
    private ImageView imageHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(this, requestCode);

        imageHolder = (ImageView) findViewById(R.id.captured_photo);
        imageHolder.setVisibility(View.INVISIBLE);
        Button capturedImageButton = (Button) findViewById(R.id.photo_button);
        capturedImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoCaptureIntent, requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageHolder.setImageBitmap(bitmap);
            imageHolder.setVisibility(View.VISIBLE);
        }
    }

    public static void requestPermissions(Activity activity, int requestCode) {
        ArrayList<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.CAMERA);
        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        String[] perms = permissionList.toArray(new String[0]);

        ActivityCompat.requestPermissions(activity, perms, requestCode);
    }
}