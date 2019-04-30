package com.common.basic

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.common.basic.mvp.BasePresenter
import com.common.basic.mvp.IBaseView
import com.common.utils.ScreenAdapter

/**
 * description:
 * author:  周昇辰
 * date:2019/3/26
 */

  abstract class BaseActivity :BusinessBasicActivity(),IBaseView{

    private var mPresenter: BasePresenter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScreenAdapter.setDefault(this)
        setContentView(getLayoutId())
        initView()
    }
   @LayoutRes
   abstract fun getLayoutId():Int

   abstract fun initView()



    override fun showContentView() {

    }


    override fun loadPageData() {

    }
    override fun showLoading(isCancel: Boolean) {

    }

    override fun endLoading() {

    }

    override fun showToast(content: String) {
        if(!content.isEmpty()){
            Toast.makeText(this,content,Toast.LENGTH_SHORT).show()
        }
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
    }


    fun setPresenter(presenter: BasePresenter<*>){
        this.mPresenter=presenter
    }
    fun  detachPresenter(){
        mPresenter?.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        detachPresenter()
    }

}