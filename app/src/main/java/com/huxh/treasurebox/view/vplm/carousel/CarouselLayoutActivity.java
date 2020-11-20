package com.huxh.treasurebox.view.vplm.carousel;

import com.huxh.treasurebox.view.vplm.VPLMBaseActivity;
import com.huxh.treasurebox.view.vplm.Util;
import com.leochuan.CarouselLayoutManager;

/**
 * Created by Dajavu on 27/10/2017.
 */

public class CarouselLayoutActivity extends VPLMBaseActivity<CarouselLayoutManager, CarouselPopUpWindow> {

    @Override
    protected CarouselLayoutManager createLayoutManager() {
        return new CarouselLayoutManager(this, Util.Dp2px(this, 100));
    }

    @Override
    protected CarouselPopUpWindow createSettingPopUpWindow() {
        return new CarouselPopUpWindow(this, getViewPagerLayoutManager(), getRecyclerView());
    }
}
