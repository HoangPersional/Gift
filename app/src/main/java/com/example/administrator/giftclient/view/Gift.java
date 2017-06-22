package com.example.administrator.giftclient.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.administrator.giftclient.R;
import com.example.administrator.giftclient.model.Event;
import com.example.administrator.giftclient.support.Picture_frame;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 15/6/2017.
 */

public class Gift extends AppCompatActivity {
    Picture_frame picture_frame;
    Event event;
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift);
        picture_frame = (Picture_frame) findViewById(R.id.pf);
        Bundle bundle = getIntent().getBundleExtra("data");
        if (bundle != null) {
            event = bundle.getParcelable("event");
            if (event.getImage() != null&&!event.getImage().isEmpty()) {
                Download download=new Download();
                download.execute();
            }
            if(event.getMessage()!=null)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        picture_frame.setText(event.getMessage());
                    }
                }).start();
            }
            CountDownTimer countDownTimer=new CountDownTimer(2000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (event.getMp3() != null&&!event.getMp3().isEmpty()) {
                        if (mediaPlayer == null) {
                            mediaPlayer = MediaPlayer.create(Gift.this, Uri.parse(event.getMp3()));
                            mediaPlayer.start();
                        } else if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                            mediaPlayer.release();
                            mediaPlayer = MediaPlayer.create(Gift.this, Uri.parse(event.getMp3()));
                            mediaPlayer.start();
                        }
                    }
                }
            }.start();
        }
    }

    class Download extends AsyncTask<String,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap=null;
            try {
                bitmap = Glide.with(Gift.this)
                        .load(event.getImage())
                        .asBitmap()
                        .into(-1, -1)
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
           if(bitmap!=null)
               picture_frame.setBitmap(bitmap);
        }
    }
}

