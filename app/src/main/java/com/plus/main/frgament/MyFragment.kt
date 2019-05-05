package com.plus.main.frgament


import com.common.basic.BaseLazyFragment
import com.google.zxing.QrCodeUtil
import com.plus.R
import com.tencent.bugly.crashreport.CrashReport


/**
 * description:
 * author:  周昇辰
 * date:2019/3/27
 */


class MyFragment :BaseLazyFragment(){

    companion object {
        fun getIntance():MyFragment{
            val fragment=MyFragment()
            return fragment
        }
    }

    override fun getLayoutId()=R.layout.fragment_my

    override fun onlazyLoad() {
        

   }





}
