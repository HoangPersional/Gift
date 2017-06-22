package com.example.administrator.giftclient.support;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Administrator on 16/6/2017.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        Camera.Parameters parameters = mCamera.getParameters();
//        Display display = ((WindowManager)getContext().getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

//        if(display.getRotation() == Surface.ROTATION_0)
//        {
//            parameters.setPreviewSize(height, width);
//            mCamera.setDisplayOrientation(90);
//        }

//        if(display.getRotation() == Surface.ROTATION_90)
//        {
//            parameters.setPreviewSize(width, height);
//        }
//
//        if(display.getRotation() == Surface.ROTATION_180)
//        {
//            parameters.setPreviewSize(height, width);
//        }

//        if(display.getRotation() == Surface.ROTATION_270)
//        {
//            parameters.setPreviewSize(width, height);
//            mCamera.setDisplayOrientation(180);
//        }

//        mCamera.setParameters(parameters);
        if (mHolder.getSurface() == null)
            return;
        mCamera.stopPreview();
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.getHolder().removeCallback(this);
        mCamera.stopPreview();
        mCamera.release();
    }
}
