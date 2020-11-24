package com.huxh.treasurebox.ui.features

import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.huxh.treasurebox.R
import com.huxh.treasurebox.baselib.base.fragment.BaseViewModelFragment
import com.huxh.treasurebox.baselib.net.ApiService
import com.huxh.treasurebox.baselib.utils.debug
import com.huxh.treasurebox.baselib.utils.saveToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class FeaturesFragment : BaseViewModelFragment() {

    override val viewModel by viewModels<FeaturesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        debug {
            val parent =
                requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath
            //开启协程并下载，为了方便调用，下载方法我封装成DSL,参考DownloadBuild
            val file = File(
                parent,
                "荒野行动.apk"
            )
            lifecycleScope.launch(Dispatchers.IO) {
                    ApiService.downloadFile("https://alissl.ucdl.pp.uc.cn/fs08/2020/11/16/5/109_24e58b75ce90526f8f568e1fb19dfdc3.apk")
                        .body()
                        ?.byteStream()
                        ?.saveToFile(file.absolutePath)
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_features
    }
}