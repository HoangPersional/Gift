package com.example.administrator.giftclient;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.giftclient.config.Config;
import com.example.administrator.giftclient.service.CameraService;
import com.example.administrator.giftclient.view.LogInActivity;

public class MainActivity extends AppCompatActivity {
    TextView v1, v2, v3, v4;
    public int REQUEST_CAMERA = 0;
    public int REQUEST_ACCESS_FINE_LOCATION = 1;
    public int REQUEST_ACCESS_COARSE_LOCATION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v1 = (TextView) findViewById(R.id.tv_welcome);
        v2 = (TextView) findViewById(R.id.tv_to);
        v3 = (TextView) findViewById(R.id.tv_gift_client);
        v4 = (TextView) findViewById(R.id.tv_allow_camera);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/IndieFlower.ttf");
        v1.setTypeface(typeface);
        v2.setTypeface(typeface);
        v3.setTypeface(typeface);
        v4.setTypeface(typeface);
        SharedPreferences sharedPreferences=getSharedPreferences(Config.PREF,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M&&!sharedPreferences.getBoolean("requested",false)) {
            CountDownTimer countDownTimer = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {

                        } else {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                        }
                    }
                }
            }.start();
        }
        else
        {
            CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    Intent i = new Intent(MainActivity.this, LogInActivity.class);
                    startActivity(i);
                    finish();
                }
            }.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
                }
            }
        } else if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
                    }
                }

            SharedPreferences sharedPreferences=getSharedPreferences(Config.PREF,0);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("requested",true).commit();
            Intent i = new Intent(this, LogInActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
