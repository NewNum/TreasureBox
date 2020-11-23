package com.huxh.treasurebox.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.huxh.treasurebox.baselib.base.viewmodel.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ViewPagerLayoutManager \n https://github.com/leochuan/ViewPagerLayoutManager"
    }
    val text: LiveData<String> = _text
}