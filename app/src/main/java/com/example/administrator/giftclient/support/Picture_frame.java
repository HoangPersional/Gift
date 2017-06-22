package com.example.administrator.giftclient.support;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.giftclient.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 15/6/2017.
 */

public class Picture_frame extends View {
    protected Bitmap frame, framez;
    protected Bitmap image, imagez;
    protected ArrayList<String> messages;
    protected String[] message;
    protected Paint pFrame, pImage, pMessage;
    int w, h;
    private float textsize;
    public Picture_frame(Context context) {
        super(context);
        init();
    }

    public Picture_frame(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttributeSet(attrs);
    }

    public Picture_frame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttributeSet(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Picture_frame(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttributeSet(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//            int wMode=MeasureSpec.getMode(widthMeasureSpec);
//            int hMode=MeasureSpec.getMode(heightMeasureSpec);
        w = MeasureSpec.getSize(widthMeasureSpec);
        h = MeasureSpec.getSize(heightMeasureSpec);
        reSizeBitmap();
        setMeasuredDimension(w, h);
//        Log.v("HH", "width: " + w + " height: " + h);
    }

    protected void init() {
        pFrame = new Paint();
        pFrame.setStrokeWidth(1);
        pFrame.setStyle(Paint.Style.FILL);

        pImage = new Paint();
        pImage.setStrokeWidth(1);
        pImage.setStyle(Paint.Style.FILL);

        pMessage = new Paint();
        pMessage.setStrokeWidth(1);
        pMessage.setTextSize(getContext().getResources().getDimension(R.dimen.texts));
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/IndieFlower.ttf");
        pMessage.setTypeface(typeface);
        pMessage.setStyle(Paint.Style.FILL);
        pMessage.setColor(Color.WHITE);
        textsize=pMessage.getTextSize();
    }

    protected void initAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.picture_frame);
        Drawable frame = typedArray.getDrawable(R.styleable.picture_frame_frame);
        Drawable image = typedArray.getDrawable(R.styleable.picture_frame_image);
        this.framez = ((BitmapDrawable) frame).getBitmap();
        this.imagez = ((BitmapDrawable) image).getBitmap();
        String s = typedArray.getString(R.styleable.picture_frame_message);
        message=s.split("\\.");
        for(int i=0;i<message.length;++i)
        {
            message[i]=message[i].trim();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(image, 0, 0, pImage);
//        canvas.drawBitmap(frame, 0, 0, pFrame);
        int k=h/2;
        for(int i=0;i<message.length;++i)
        {
            float width=pMessage.measureText(message[i]);
            if(width>w)
            {
                pMessage.setTextSize(70);
                width=pMessage.measureText(message[i]);
            }
            canvas.drawText(message[i],w/2-width/2,k,pMessage);
            pMessage.setTextSize(getContext().getResources().getDimension(R.dimen.texts));
            k+=pMessage.getTextSize();
        }
    }

    private void reSizeBitmap() {
        image = Bitmap.createScaledBitmap(imagez, w, h, false);
        frame = Bitmap.createScaledBitmap(framez, w, h, false);
    }

    public void setBitmap(Bitmap bitmap) {
        if (w > 0 && h > 0) {
            image = Bitmap.createScaledBitmap(bitmap, w, h, false);
            postInvalidate();

        }
    }
    public void setText(String text)
    {
        message=text.split("\\.");
        postInvalidate();
    }
}
