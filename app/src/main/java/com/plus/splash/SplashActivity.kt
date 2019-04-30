package com.plus.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.common.basic.BaseActivity
import com.common.utils.PermissionsUtil
import com.common.utils.StatusBarUtil
import com.plus.BuildConfig
import com.plus.R
import com.plus.main.activity.MainActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int=R.layout.activity_splash


    override fun initView() {
        StatusBarUtil.immersive(this)
        copyrightMsg.text="2020 v."+BuildConfig.VERSION_NAME+"\n 法轮大法好, 真、善、忍 好"

      PermissionsUtil.requestPermission(this,object : PermissionsUtil.CallBack(){
          override fun hasP() {
             Log.d("xxx1","有权限")
              start()
          }

          override fun failP() {
              Log.d("xxx1","没权限")
              start()
          }

          override fun banP() {
              Log.d("xxx1","禁止访问")
              start()
          }
      },Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE)
    }


    private fun start(){
        window.decorView.postDelayed(runnable, 1500)
    }

    private val runnable = Runnable {
        goNextPage() }
    private fun goNextPage() {
       val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        this@SplashActivity.finish()
    }
}


