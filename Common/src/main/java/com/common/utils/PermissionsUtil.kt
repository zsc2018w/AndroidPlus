package com.common.utils

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import java.io.StringReader

/**
 * description:
 * author:  周昇辰
 * date:2019/4/18
 */

class PermissionsUtil {

    companion object {


        fun requestPermission(activity: Activity,callBack:CallBack,vararg permission:String){
            RxPermissions(activity)
                    .requestEachCombined(*permission)
                    .subscribe(object : Consumer<Permission>{
                        override fun accept(p: Permission) {
                           if(p.granted){
                               callBack.hasP()
                           }else if(p.shouldShowRequestPermissionRationale){
                               callBack.failP()
                           }else{
                               callBack.banP()
                           }
                        }
                    })

        }



    }

    abstract class CallBack(){

        abstract fun hasP()

        abstract fun failP()

        abstract fun banP()

    }
}