package ScanDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CenterDrawable extends Drawable {
    private Paint mPaint;
    private Context mContext;
    private float mHeight;
    private float mWidth;
    private float mRadius;
    private Bitmap mBitmap;
    private Rect mSrcRect;
    private RectF mDestRectF;

    public CenterDrawable(Context context, int resId, float radius, float width, float height) {
        this.mContext = context;
        this.mHeight = dp2px(context, height);
        this.mWidth = dp2px(context, width);
        this.mRadius = dp2px(context, radius);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 消除锯齿

        mSrcRect = new Rect(0, 0, mBitmap.getWidth(),mBitmap.getHeight());
        mDestRectF = new RectF(mRadius - mWidth / 2, mRadius - mHeight / 2, mRadius + mWidth / 2, mRadius + mHeight / 2);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRectF, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public float dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
}
