package com.example.jcompia.tutoralnavi3.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.RSRuntimeException;
import android.util.Log;

import com.squareup.picasso.Transformation;

import java.util.Map;

/**
 * Created by yongbeom on 2018. 5. 5..
 */

public class CustomTransformation implements Transformation {

    private static int MAX_RADIUS = 25;
    private static int DEFAULT_DOWN_SAMPLING = 1;

    private Context mContext;

    private int mRadius;
    private int mSampling;
    private Map mOption;

    public CustomTransformation(Context context,Map option) {
        this(context, MAX_RADIUS, DEFAULT_DOWN_SAMPLING,option);
    }

    public CustomTransformation(Context context, int radius,Map option) {
        this(context, radius, DEFAULT_DOWN_SAMPLING,option);
    }

    public CustomTransformation(Context context, int radius, int sampling,Map option) {
        mContext = context.getApplicationContext();
        mRadius = radius;
        if (mRadius > 25) {
            mRadius = 25;
        } else if (mRadius <= 0) {
            mRadius = 1;
        }
        mSampling = sampling;
        mOption = option;
    }


    @Override
    public Bitmap transform(Bitmap source) {
        Log.d("CustomTransformation","transform");
        int scaledWidth = source.getWidth() / mSampling;
        int scaledHeight = source.getHeight() / mSampling;

        Bitmap bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);

        if("true".equals(mOption.get("blur"))){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    bitmap = RenderScriptBlur.blur(mContext, bitmap, mRadius);
                } catch (RSRuntimeException e) {

                }
            }
        }

        source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "BlurTransformation(radius=" + mRadius + ", sampling=" + mSampling + ")";
    }
}
