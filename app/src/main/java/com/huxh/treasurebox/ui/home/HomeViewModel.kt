package com.huxh.treasurebox.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huxh.treasurebox.baselib.base.viewmodel.BaseViewModel
import com.huxh.treasurebox.view.vplm.VPLMActivity

class HomeViewModel : BaseViewModel() {

    private val _widgets = MutableLiveData<List<WidgetInfo>>().apply {
        value = listOf(
            WidgetInfo(
                "ViewPagerLayoutManager",
                "VPLM is a ViewPager like LayoutManager which implements some common animations. If you need some other effects feel free to raise an issue or PR.",
                "https://github.com/leochuan/ViewPagerLayoutManager",
                VPLMActivity::class.java
            )
        )
    }
    val widgets: LiveData<List<WidgetInfo>> = _widgets
}