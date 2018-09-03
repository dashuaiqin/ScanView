package ScanDrawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

public class ScanDrawableBuider {
    private static final float DEFAULT_RADIUS = 124.0f;
    private static ScanDrawableBuider instance = null;
    private GradientDrawable mGradientDrawable;

    public static ScanDrawableBuider getInstance() {
        if (instance == null) {
            instance = new ScanDrawableBuider();
        }
        return instance;
    }

    public void starAnimation() {
        if (mGradientDrawable == null) {
            return;
        }
        mGradientDrawable.start();
    }

    public void stopAnimation() {
        if (mGradientDrawable == null) {
            return;
        }
        mGradientDrawable.stop();
    }

    public boolean isRunning() {
        if (mGradientDrawable == null) {
            return false;
        }
        return mGradientDrawable.isRunning();
    }

    public LayerDrawable getScanDrawable(Context context, int centerResId, int color, float radius) {
        int defaultAlpha = 225;
        int totalCircle = 4;
        Drawable[] layers = new Drawable[totalCircle + 2];
        //4个圆环
        float[] strokwidths = {0.208f * radius, 0.192f * radius, 0.169f * radius, 0.142f * radius};
        float[] radiuses = {radius, radius - strokwidths[0], radius - strokwidths[0] - strokwidths[1], radius - strokwidths[0] - strokwidths[1] - strokwidths[2]};
        int[] alpha = {(int) (defaultAlpha * 0.15), (int) (defaultAlpha * 0.35), (int) (defaultAlpha * 0.45), (int) (defaultAlpha * 0.65)};

        for (int i = 0; i < totalCircle; i++) {
            ScanLevelDrawable drawable = new ScanLevelDrawable(context, color, radius * 2, strokwidths[i], radiuses[i], alpha[i]);
            layers[i] = drawable;
        }
        //中心的图片
        //最中心圆的图片
        float lastRadius = radiuses[totalCircle - 1] - strokwidths[totalCircle - 1];
        //width是最中心圆的圆内正方形的边长*2/3
        float centerWidth = (float) (lastRadius * Math.sqrt(2) / 2);
        CenterDrawable drawableCenter = new CenterDrawable(context, centerResId, radius, centerWidth, centerWidth);
        layers[totalCircle] = drawableCenter;

        //扇形渐变遮罩
        layers[totalCircle + 1] = mGradientDrawable = new GradientDrawable(context, radius, radius - (radiuses[totalCircle - 1] - strokwidths[totalCircle - 1]));
        return new LayerDrawable(layers);
    }

}
