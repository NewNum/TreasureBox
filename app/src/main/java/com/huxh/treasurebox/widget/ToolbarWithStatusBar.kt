package com.huxh.treasurebox.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.huxh.treasurebox.R
import com.huxh.treasurebox.baselib.utils.getStatusBarHeight

class ToolbarWithStatusBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val ivBack: ImageView
    private val ivRight: ImageView
    private val tvTitle: TextView
    private val tvSubTitle: TextView

    @IntDef(View.VISIBLE, View.INVISIBLE, View.GONE)
    annotation class Visibility

    init {
        View.inflate(context, R.layout.view_toolbar_with_status_bar, this)
        ivBack = findViewById(R.id.ivBack)
        ivRight = findViewById(R.id.ivRight)
        tvTitle = findViewById(R.id.tvTitle)
        tvSubTitle = findViewById(R.id.tvRight)
        findViewById<View>(R.id.vStatus).layoutParams.height = getStatusBarHeight(context)
        val typesArray = context.obtainStyledAttributes(attrs, R.styleable.ToolbarWithStatusBar)
        typesArray.getString(R.styleable.ToolbarWithStatusBar_title)?.let {
            setTitle(it)
        }
        setSubtitle(typesArray.getString(R.styleable.ToolbarWithStatusBar_subtitle))
        setBackImage(typesArray.getDrawable(R.styleable.ToolbarWithStatusBar_backSrc))
        setRightImage(typesArray.getDrawable(R.styleable.ToolbarWithStatusBar_rightSrc))
        setBackgroundColor(
            typesArray.getColor(
                R.styleable.ToolbarWithStatusBar_groundColor,
                ContextCompat.getColor(context, R.color.toolbar_bg)
            )
        )
        setBackVisibility(
            if (typesArray.getBoolean(R.styleable.ToolbarWithStatusBar_showBack, true)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        )
        typesArray.recycle()
    }



    fun setBackImage(drawable: Drawable?) {
        if (drawable != null)
            ivBack.setImageDrawable(drawable)
    }


    fun setBackVisibility(@Visibility visibility: Int) {
        ivBack.visibility = visibility
    }


    fun setOnBackClickListener(l: OnClickListener) {
        ivBack.setOnClickListener(l)
    }
    fun setRightImageVisibility(@Visibility visibility: Int) {
        ivRight.visibility = visibility
    }

    fun setRightImage(drawable: Drawable?) {
        if (drawable == null) {
            ivRight.visibility = View.GONE
        } else {
            ivRight.visibility = View.VISIBLE
            ivRight.setImageDrawable(drawable)
        }
    }
    fun setOnRightImageClickListener(l: OnClickListener) {
        ivRight.setOnClickListener(l)
    }

    fun setOnSubtitleClickListener(l: OnClickListener) {
        tvSubTitle.setOnClickListener(l)
    }

    fun setSubtitle(it: String?) {
        if (TextUtils.isEmpty(it)) {
            tvSubTitle.visibility = View.GONE
        } else {
            tvSubTitle.visibility = View.VISIBLE
            tvSubTitle.text = it
        }
    }

    fun setOntTitleClickListener(l:OnClickListener){
        tvTitle.setOnClickListener(l)
    }
    fun setTitle(title: String) {
        tvTitle.text = title
    }

}