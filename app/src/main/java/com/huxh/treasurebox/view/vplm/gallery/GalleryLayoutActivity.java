package com.huxh.treasurebox.view.vplm.gallery;

import com.huxh.treasurebox.view.vplm.VPLMBaseActivity;
import com.huxh.treasurebox.view.vplm.Util;
import com.leochuan.GalleryLayoutManager;

/**
 * Created by Dajavu on 27/10/2017.
 */

public class GalleryLayoutActivity extends VPLMBaseActivity<GalleryLayoutManager, GalleryPopUpWindow> {

    @Override
    protected GalleryLayoutManager createLayoutManager() {
        return new GalleryLayoutManager(this, Util.Dp2px(this, 10));
    }

    @Override
    protected GalleryPopUpWindow createSettingPopUpWindow() {
        return new GalleryPopUpWindow(this, getViewPagerLayoutManager(), getRecyclerView());
    }
}
