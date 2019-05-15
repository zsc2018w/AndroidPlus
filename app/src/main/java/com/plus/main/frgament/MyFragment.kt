package com.plus.main.frgament


import android.util.Log
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.common.basic.BaseLazyFragment
import com.google.zxing.QrCodeUtil
import com.plus.R
import com.plus.test.Person
import com.plus.utils.IPutils
import com.tencent.bugly.crashreport.CrashReport
import kotlinx.android.synthetic.main.fragment_my.*
import kotlin.concurrent.thread


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

     //   CrashReport.testJavaCrash()

        val person:Person= Person()
        person.name="中华人民共和国"

        person.save(object : SaveListener<String>() {
            override fun done(objectId: String?, e: BmobException?) {
               if(e==null){
                 Log.d("ttt1","成功返回"+objectId)
               }else{
                   Log.d("ttt1","失败"+e.message)
               }
            }
        })


        layout1.setOnClickListener({
            Thread(object :Runnable{
                override fun run() {

                    try {
                        //    var localIPAddress = IPutils.getLocalIPAddress()
                        //    Log.i("iiip","IP地址："+localIPAddress)
                    } catch (e : Exception){
                        e.printStackTrace()
                    }

                    var  s:String= IPutils.GetNetIp()
                    Log.i("iiip","外网IP："+s)
                }

            }).start()
        })


}





}
