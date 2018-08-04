package ScanDrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ScanLevelDrawable extends Drawable {
    private Paint mPaint;
    private Context mContext;
    private float mRadius;
    private int mColor;
    private float mStrokeWidth;
    private float mWidth;
    private RectF mRectF;
    private int mAlpha;

    public ScanLevelDrawable(Context context, int color, float width, float strokeWidth, float radius, int alpha){
        this.mContext=context;
        this.mRadius=dp2px(mContext,radius);
        this.mColor=color;
        this.mStrokeWidth=dp2px(mContext,strokeWidth);
        this.mAlpha=alpha;
        this.mWidth=dp2px(mContext,width);
        initPaint();
    }

    private void initPaint(){
        this.mPaint=new Paint();
        mPaint.setColor(mColor);
        mPaint.setAlpha(mAlpha);
        mPaint.setStrokeWidth(mStrokeWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        // 用于定义的圆弧的形状和大小的界限
        mRectF = new RectF(mWidth/2+mStrokeWidth/2-mRadius, mWidth/2+mStrokeWidth/2-mRadius, mWidth/2-mStrokeWidth/2+mRadius, mWidth/2-mStrokeWidth/2+mRadius);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawArc(mRectF,0, 360, false, mPaint); //画圆弧
    }

    @Override
    public void setAlpha(int alpha) {
        this.mAlpha=alpha;
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

    public void setmRadius(float radius) {
        this.mRadius = dp2px(mContext,radius);
        invalidateSelf();
    }
}
