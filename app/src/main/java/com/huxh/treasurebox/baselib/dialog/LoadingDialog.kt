package com.huxh.treasurebox.baselib.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.huxh.treasurebox.R

class LoadingDialog : DialogFragment(), DialogInterface.OnShowListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Dialog_Transparent)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext()).setView(R.layout.view_loading).create()
        dialog.setOnShowListener(this)
        return dialog
    }

    override fun onShow(dialog: DialogInterface?) {
        val dialogWindow = getDialog()?.window
        dialogWindow?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun show(supportFragmentManager: FragmentManager) {
        try {
            if (isAdded) return
            val fragment = supportFragmentManager.findFragmentByTag(TAG)
            if (fragment==null){
                show(supportFragmentManager, TAG)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object{
        private const val TAG = "LoadingDialog"
    }
}