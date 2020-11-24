package com.huxh.treasurebox.ui.widget

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.huxh.treasurebox.R
import com.huxh.treasurebox.baselib.base.fragment.BaseViewModelFragment
import com.huxh.treasurebox.ui.web.WebActivity
import kotlinx.android.synthetic.main.fragment_widget.*

class WidgetFragment : BaseViewModelFragment() {

    override val viewModel by viewModels<WidgetViewModel>()

    override fun layoutId(): Int {
        return R.layout.fragment_widget
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.widgets.observe(viewLifecycleOwner) {
            rvHome.adapter = WidgetAdapter(it).apply {
                onItemClickListener = { data, _, _ ->
                    startActivity(data.baseClass)
                }
                onItemUrlClickListener =  { data, _, _ ->
                    startActivity(WebActivity.newIntent(requireContext(),data.url,data.name))
                }
            }
        }
    }
}