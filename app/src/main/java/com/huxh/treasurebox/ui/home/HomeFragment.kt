package com.huxh.treasurebox.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.huxh.treasurebox.R
import com.huxh.treasurebox.baselib.base.fragment.BaseViewModelFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseViewModelFragment() {

    override val viewModel by viewModels<HomeViewModel>()

    override fun layoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.widgets.observe(viewLifecycleOwner) {
            rvHome.adapter = HomeAdapter(it).apply {
                onItemClickListener = { data, _, _ ->
                    startActivity(data.baseClass)
                }
            }
        }
    }
}