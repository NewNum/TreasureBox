package com.huxh.treasurebox.ui.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huxh.treasurebox.baselib.base.fragment.BaseViewModelFragment
import com.huxh.treasurebox.baselib.base.viewmodel.BaseViewModel

class FeaturesViewModel : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}