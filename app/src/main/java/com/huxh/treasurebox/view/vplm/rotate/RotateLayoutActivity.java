package com.huxh.treasurebox.view.vplm.rotate;

import com.huxh.treasurebox.view.vplm.VPLMBaseActivity;
import com.huxh.treasurebox.view.vplm.Util;
import com.leochuan.RotateLayoutManager;

/**
 * Created by Dajavu on 27/10/2017.
 */

public class RotateLayoutActivity extends VPLMBaseActivity<RotateLayoutManager, RotatePopUpWindow> {

    @Override
    protected RotateLayoutManager createLayoutManager() {
        return new RotateLayoutManager(this, Util.Dp2px(this, 10));
    }

    @Override
    protected RotatePopUpWindow createSettingPopUpWindow() {
        return new RotatePopUpWindow(this, getViewPagerLayoutManager(), getRecyclerView());
    }
}
