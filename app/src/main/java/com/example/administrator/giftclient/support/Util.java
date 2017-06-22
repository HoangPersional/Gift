package com.example.administrator.giftclient.support;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 16/6/2017.
 */

public class Util {
    public static int convertSpToPixels(float sp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }
}
