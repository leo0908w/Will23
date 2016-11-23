package com.org.iii.will23;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private File sdroot, photoRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sdroot = Environment.getExternalStorageDirectory();
        imageView = (ImageView) findViewById(R.id.img);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 123);
        }
    }

    public void test1(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    public void test2(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoRoot = new File(sdroot,  "will.jpg");
        Uri photoUri = Uri.fromFile(photoRoot);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, 2);
    }

    public void test3(View v){
        photoRoot = new File(sdroot,  "will.jpg");
        Intent intent = new Intent(this, Camera2Activity.class);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            take1(data);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            take2(data);
        } else if (requestCode == 3 && resultCode == RESULT_OK) {
            take3(data);
        }
    }

    private void take1(Intent it) {
        Bitmap bmp = (Bitmap)it.getExtras().get("data");
        imageView.setImageBitmap(bmp);
    }

    private void take2(Intent it) {
        Bitmap bmp = BitmapFactory.decodeFile(photoRoot.getAbsolutePath());
        imageView.setImageBitmap(bmp);
    }

    private void take3(Intent it) {
        Bitmap bmp = (Bitmap)it.getExtras().get("data");
        imageView.setImageBitmap(bmp);
    }
}
