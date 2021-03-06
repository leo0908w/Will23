package com.org.iii.will23;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Camera2Activity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private Camera camera;
    private MyPreview myPreview;
    private File sdroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 123);
        } else {
            init();
        }
    }

    private void init() {
        sdroot = Environment.getExternalStorageDirectory();

        camera = camera.open();
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        myPreview = new MyPreview(this, camera);
        frameLayout.addView(myPreview);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(new MyShutter(), null, new MyPicCallback());
            }
        });
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    private class MyShutter implements Camera.ShutterCallback {
        @Override
        public void onShutter() {

        }
    }

    private class MyPicCallback implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            try {
                FileOutputStream fout = new FileOutputStream(new File(sdroot, "will.jpg"));
                fout.write(bytes);
                fout.flush();
                fout.close();
                Toast.makeText(Camera2Activity.this, "Save OK", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.v("will", e.toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }
}
