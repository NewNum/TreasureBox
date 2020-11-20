package com.huxh.treasurebox.view.vplm.circlescale;

import com.huxh.treasurebox.view.vplm.VPLMBaseActivity;
import com.leochuan.CircleScaleLayoutManager;


/**
 * Created by Dajavu on 27/10/2017.
 */

public class CircleScaleLayoutActivity extends VPLMBaseActivity<CircleScaleLayoutManager, CircleScalePopUpWindow> {

    @Override
    protected CircleScaleLayoutManager createLayoutManager() {
        return new CircleScaleLayoutManager(this);
    }

    @Override
    protected CircleScalePopUpWindow createSettingPopUpWindow() {
        return new CircleScalePopUpWindow(this, getViewPagerLayoutManager(), getRecyclerView());
    }
}
