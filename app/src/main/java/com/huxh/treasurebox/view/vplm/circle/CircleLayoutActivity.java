package com.huxh.treasurebox.view.vplm.circle;

import com.huxh.treasurebox.view.vplm.VPLMBaseActivity;
import com.leochuan.CircleLayoutManager;

/**
 * Created by Dajavu on 25/10/2017.
 */

public class CircleLayoutActivity extends VPLMBaseActivity<CircleLayoutManager, CirclePopUpWindow> {

    @Override
    protected CircleLayoutManager createLayoutManager() {
        return new CircleLayoutManager(this);
    }

    @Override
    protected CirclePopUpWindow createSettingPopUpWindow() {
        return new CirclePopUpWindow(this, getViewPagerLayoutManager(), getRecyclerView());
    }
}
