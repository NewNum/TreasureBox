package com.huxh.treasurebox.baselib.base.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.huxh.treasurebox.baselib.dialog.LoadingDialog

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(inflater, container, savedInstanceState) ?: inflater.inflate(
            layoutId(),
            container,
            false
        )
    }

    @LayoutRes
    abstract fun layoutId(): Int

    open fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }

    private var mLoadingDialog: LoadingDialog? = null
    open fun showLoading() {
        if (mLoadingDialog?.isVisible == true) return
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog()
        }
        mLoadingDialog?.show(childFragmentManager)
    }

    open fun dismissLoading() {
        mLoadingDialog?.dismiss()
    }


    fun startActivity(cls: Class<out Activity>) {
        startActivity(Intent(requireContext(), cls))
    }

    fun startActivityForResult(cls: Class<out Activity>, requestCode: Int) {
        startActivityForResult(Intent(requireContext(), cls), requestCode)
    }

}