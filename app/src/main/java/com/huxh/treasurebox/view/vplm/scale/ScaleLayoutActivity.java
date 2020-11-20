package com.huxh.treasurebox.view.vplm.scale;

import com.huxh.treasurebox.view.vplm.VPLMBaseActivity;
import com.huxh.treasurebox.view.vplm.Util;
import com.leochuan.ScaleLayoutManager;


/**
 * Created by Dajavu on 27/10/2017.
 */

public class ScaleLayoutActivity extends VPLMBaseActivity<ScaleLayoutManager, ScalePopUpWindow> {

    @Override
    protected ScaleLayoutManager createLayoutManager() {
        return new ScaleLayoutManager(this, Util.Dp2px(this, 10));
    }

    @Override
    protected ScalePopUpWindow createSettingPopUpWindow() {
        return new ScalePopUpWindow(this, getViewPagerLayoutManager(), getRecyclerView());
    }
}
