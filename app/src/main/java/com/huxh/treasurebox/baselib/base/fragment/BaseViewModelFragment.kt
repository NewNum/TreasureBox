package com.huxh.treasurebox.baselib.base.fragment

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.huxh.treasurebox.R
import com.huxh.treasurebox.baselib.TAG
import com.huxh.treasurebox.baselib.base.viewmodel.BaseViewModel
import com.huxh.treasurebox.baselib.base.viewmodel.ErrorState
import com.huxh.treasurebox.baselib.base.viewmodel.LoadState
import com.huxh.treasurebox.baselib.base.viewmodel.SuccessState
import com.huxh.treasurebox.baselib.utils.longToast

abstract class BaseViewModelFragment : BaseFragment() {

    protected abstract val viewModel: BaseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelAction()
    }

    private fun initViewModelAction() {
        viewModel.let { baseViewModel ->
            baseViewModel.mStateLiveData.observe(this, Observer { stateActionState ->
                when (stateActionState) {
                    LoadState -> showLoading()
                    SuccessState -> dismissLoading()
                    is ErrorState -> {
                        dismissLoading()
                        stateActionState.message?.apply {
                            Log.d(TAG, "initViewModelAction: $this")
                            longToast(
                                when (this) {
                                    BaseViewModel.NET_WORK_EXCEPTION -> {
                                        getString(R.string.network_error)
                                    }
                                    BaseViewModel.UNKNOWN_EXCEPTION -> {
                                        getString(R.string.a_little_problem)
                                    }
                                    else -> {
                                        getString(R.string.a_little_problem)
                                    }
                                }
                            )
                            handleError()
                        }
                    }
                }
            })
        }
    }

    open fun handleError() {}

}