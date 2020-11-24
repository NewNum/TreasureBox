package com.huxh.treasurebox.ui.widget

import android.app.Activity

data class WidgetInfo(val name: String, val desc: String, val url: String,val baseClass:Class<out Activity>)