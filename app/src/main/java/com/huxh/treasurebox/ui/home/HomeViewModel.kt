package com.huxh.treasurebox.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ViewPagerLayoutManager \n https://github.com/leochuan/ViewPagerLayoutManager"
    }
    val text: LiveData<String> = _text
}