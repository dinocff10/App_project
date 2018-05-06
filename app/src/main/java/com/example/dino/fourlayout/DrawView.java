package com.example.dino.fourlayout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dinocff10 on 2015/11/11.
 */

///////////////////////////////////參考http://blog.csdn.net/a2758963/article/details/7791474
public class DrawView extends View {
    private Canvas mCanvas = new Canvas();
    private Bitmap mBitmap;
    private Paint mPaint = new Paint();
    Path mPath = new Path();
    private int mColor;
    float x1=0,y1=600,x2=200,y2=700;
    float datalength=1;
//左上X   左上Y  右下X  右下Y
    private static String DATABASE_TABLE = "titles";
    public DrawView(Context context) {
        super(context);

        init();
    }
    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected  void init()
    {
        mBitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.RGB_565);
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(0x84420000);
        mPaint.setColor(0xFF777777);
        mPaint.setTextSize(20);
        mColor = Color.argb(192, 64, 128, 64); //定義顏色ARGB
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);




    }

protected void onDraw(Canvas canvas) {
    synchronized (this) {




            canvas.drawBitmap(mBitmap,0,0,null);


    }
}


    public void drawData(float value) {

       // mCanvas.drawRect(center_x - radius + value, center_y - radius, center_x + radius, center_y + radius, mPaint);
        y1=600-100*value;
        if(value==1)
            mPaint.setColor(0xFF272CFF);
        else if (value==2)
            mPaint.setColor(0xFF5096FF);
        else if(value==3)
            mPaint.setColor(0xFF63E3FF);
        mCanvas.drawRect(x1, y1, x1, y2, mPaint);
        mCanvas.drawBitmap(mBitmap,0,0,null);
        x1=x1+datalength;

      // invalidate();
    }
    public void drawstr(int x,int y,String str) {

        // mCanvas.drawRect(center_x - radius + value, center_y - radius, center_x + radius, center_y + radius, mPaint);
        mPaint.setColor(0xFFFFFFFF);
        mCanvas.drawText(str, x , y, mPaint);
        mCanvas.drawBitmap(mBitmap, 0, 0, null);


        // invalidate();
    }
    public void setdatalength(float value) {

        // mCanvas.drawRect(center_x - radius + value, center_y - radius, center_x + radius, center_y + radius, mPaint);
        datalength=700/value;

        // invalidate();
    }
    public void erase() {

        mBitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.RGB_565);
        mCanvas.setBitmap(mBitmap);
        mCanvas.drawColor(0x84420000);
        mPaint.setColor(0xFF777777);
        mPaint.setTextSize(20);
        mColor = Color.argb(192, 64, 128, 64); //定義顏色ARGB
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        x1=0;
        y1=600;
        x2=200;
        y2=700;
        // invalidate();
    }


}
