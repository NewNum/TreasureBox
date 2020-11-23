package com.huxh.treasurebox.view.vplm;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.huxh.treasurebox.R;
import com.huxh.treasurebox.baselib.base.activity.BaseActivity;
import com.huxh.treasurebox.view.vplm.carousel.CarouselLayoutActivity;
import com.huxh.treasurebox.view.vplm.circle.CircleLayoutActivity;
import com.huxh.treasurebox.view.vplm.circlescale.CircleScaleLayoutActivity;
import com.huxh.treasurebox.view.vplm.gallery.GalleryLayoutActivity;
import com.huxh.treasurebox.view.vplm.rotate.RotateLayoutActivity;
import com.huxh.treasurebox.view.vplm.scale.ScaleLayoutActivity;


public class VPLMActivity extends BaseActivity implements View.OnClickListener {
    public final static String INTENT_TITLE = "title";


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_circle:
                startActivity(CircleLayoutActivity.class, v);
                break;
            case R.id.bt_circle_scale:
                startActivity(CircleScaleLayoutActivity.class, v);
                break;
            case R.id.bt_elevate_scale:
                startActivity(CarouselLayoutActivity.class, v);
                break;
            case R.id.bt_gallery:
                startActivity(GalleryLayoutActivity.class, v);
                break;
            case R.id.bt_rotate:
                startActivity(RotateLayoutActivity.class, v);
                break;
            case R.id.bt_scale:
                startActivity(ScaleLayoutActivity.class, v);
                break;
        }
    }

    private void startActivity(Class clz, View view) {
        Intent intent = new Intent(this, clz);
        if (view instanceof AppCompatButton) {
            intent.putExtra(INTENT_TITLE, ((AppCompatButton) view).getText());
        }
        startActivity(intent);
    }

    @Override
    public void onViewCreate() {
        findViewById(R.id.bt_circle).setOnClickListener(this);
        findViewById(R.id.bt_circle_scale).setOnClickListener(this);
        findViewById(R.id.bt_elevate_scale).setOnClickListener(this);
        findViewById(R.id.bt_gallery).setOnClickListener(this);
        findViewById(R.id.bt_rotate).setOnClickListener(this);
        findViewById(R.id.bt_scale).setOnClickListener(this);
    }

    @Override
    public int layoutId() {
        return R.layout.activity_vplm;
    }
}
