package com.common.widget

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.common.R
import com.zyao89.view.zloading.Z_TYPE
import kotlinx.android.synthetic.main.loading_layout.*

/**
 * description:
 * author:  周昇辰
 * date:2019/3/28
 */


class LoadingDialog(private val mContext: Context) : Dialog(mContext){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) //设置dialog的大小
        this.window!!.setBackgroundDrawable(ColorDrawable(0))
        this.window!!.setGravity(Gravity.CENTER)
        window.setContentView(R.layout.loading_layout)

        setCanceledOnTouchOutside(true)
        setCancelable(false)

        loading_view.setLoadingBuilder(Z_TYPE.STAR_LOADING)
        loading_hint_tv.setText("Loading...")

    }

    fun closeDialog(){
        if(isShowing)
        {
            dismiss()
        }
    }
}
