package scanview.qin.com.scanview;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import ScanDrawable.ScanDrawableBuider;

public class MainActivity extends AppCompatActivity {
    private ImageView ivScan;
    private TextView tvStartScan;
    private TextView tvStopScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivScan = findViewById(R.id.iv_scan);
        ivScan.setImageDrawable(ScanDrawableBuider.getInstance().getScanDrawable(this, R.drawable.icon_search_red, Color.parseColor("#e91e63"), 124));
        tvStartScan = findViewById(R.id.tv_start_scan);
        tvStopScan = findViewById(R.id.tv_stop_scan);
        tvStartScan.setOnClickListener(clickListener);
        tvStopScan.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_start_scan:
                    ScanDrawableBuider.getInstance().starAnimation();
                    break;
                case R.id.tv_stop_scan:
                    ScanDrawableBuider.getInstance().stopAnimation();
                    break;
            }
        }
    };
}
