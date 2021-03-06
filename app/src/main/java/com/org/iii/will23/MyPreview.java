package com.org.iii.will23;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyPreview extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceHolder holder;

    public MyPreview(Context context, android.hardware.Camera camera) {
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
            Log.v("will", "Preview Error");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        try {
            camera.stopPreview();
        } catch (Exception e){
        }
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e){
            Log.v("will", " 錯誤設定觀景窗 ");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.release();
        camera = null;
    }
}
