package ScanDrawable;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;

public class GradientDrawable extends Drawable implements Animatable {
    private Paint mPaint;
    private Context mContext;
    private float mRadius;
    private float mStrokeWidth;
    private RectF mRectF;
    private ValueAnimator mAnimator;
    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener;
    private int mStartAngle = 0;
    private final int defaultmStartangle = 270;
    private final int sweep = 90;
    private int[] colors;
    private Matrix matrix;


    public GradientDrawable(Context context, float radius, float strokeWidth) {
        this.mContext = context;
        this.mRadius = dp2px(context, radius);
        this.mStrokeWidth = dp2px(context, strokeWidth);
        initPaint();
        initAnimator();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(mStrokeWidth); // 设置圆环的宽度
        colors = new int[]{Color.parseColor("#00000000"), Color.parseColor("#1a000000")};
        float startPosition = defaultmStartangle / 360.0f;
        float positions[] = {startPosition, 1.0f};
        SweepGradient lg = new SweepGradient(mRadius, mRadius, colors, positions);
        mPaint.setShader(lg);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        // 用于定义的圆弧的形状和大小的界限
        mRectF = new RectF(mStrokeWidth / 2, mStrokeWidth / 2, mRadius * 2 - mStrokeWidth / 2, mRadius * 2 - mStrokeWidth / 2);
        //初始化矩阵
        matrix = new Matrix();
    }

    private void initAnimator() {
        mAnimator = new ValueAnimator();
        mAnimator = ValueAnimator.ofInt(0, 360);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(1500);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setInterpolator(new LinearInterpolator());
        // 设置动画的回调
        mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStartAngle = (int) animation.getAnimatedValue();
                //重置矩阵
                matrix.reset();
                //设置旋转角度
                matrix.setRotate(mStartAngle, mRadius, mRadius);
                invalidateSelf();
            }
        };
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.concat(matrix);
        canvas.drawArc(mRectF, defaultmStartangle, sweep, false, mPaint); //画圆弧
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

    @Override
    public void start() {
        mAnimator.addUpdateListener(mAnimatorUpdateListener);
        mAnimator.start();
    }

    @Override
    public void stop() {
        mAnimator.removeAllUpdateListeners();
        mAnimator.end();
    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning();
    }
}
