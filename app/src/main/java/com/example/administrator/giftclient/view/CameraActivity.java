package com.example.administrator.giftclient.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.giftclient.R;
import com.example.administrator.giftclient.service.CameraService;
import com.example.administrator.giftclient.support.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

/**
 * Created by Administrator on 16/6/2017.
 */

public class CameraActivity extends AppCompatActivity implements View.OnTouchListener {
    public String TAG = "CameraActivity";
    Camera mCamera;
    //    CameraPreview cameraPreview;
    SurfaceView surfaceView;
    MediaRecorder mMediaRecorder;
    int z;
    boolean isRunning = false;
    SharedPreferences sharedPreferences;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("HH", "onCreate");
        setContentView(R.layout.camera);
        surfaceView = (SurfaceView) findViewById(R.id.cm_pr);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        surfaceView.setOnTouchListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception ư) {
        }
        return c;
    }


    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            z++;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("index", z).commit();
            camera.startPreview();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File folder = new File(Environment.getExternalStorageDirectory() + File.separator + ".A");
                    if (!folder.exists())
                        folder.mkdirs();
                    File pictureFile = new File(folder + File.separator + "anh" + z + ".png");
                    if (pictureFile == null) {
                        return;
                    }

                    try {
                        FileOutputStream fos = new FileOutputStream(pictureFile);
                        fos.write(data);
                        fos.close();
                    } catch (FileNotFoundException e) {
                    } catch (IOException e) {

                    }
                }
            }).start();
        }
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.cm_pr) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (isRunning) {
                    mMediaRecorder.stop();
                    releaseMediaRecorder();
                    Log.v("HH","stop");
                    mCamera.lock();
                    isRunning=false;
                } else {
                    if (prepareVideoRecorder()) {
                        isRunning=true;
                        mMediaRecorder.start();
                        Log.v("HH","start");
                    }
                }
            }
        }
        return true;
    }

    public void initData() {

        sharedPreferences = getSharedPreferences("PREF", 0);
        z = sharedPreferences.getInt("index", -1);
        if (z == -1) {
            z = 0;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("index", z).commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        Intent intent=new Intent(this,CameraService.class);
        startService(intent);
    }

    private boolean prepareVideoRecorder() {
        mCamera = Camera.open(1);
        mMediaRecorder = new MediaRecorder();

        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
        // Step 4: Set output file
        mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());

        // Step 5: Set the preview output
        mMediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
//            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
//            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    public File getOutputMediaFile(int type) {
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "myImage");
        if (!folder.exists())
            folder.mkdirs();
        File pictureFile = new File(folder + File.separator + "video" + z+".mp4");
        return pictureFile;
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
