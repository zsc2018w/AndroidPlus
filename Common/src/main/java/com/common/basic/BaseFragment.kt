package com.common.basic

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.common.basic.mvp.BasePresenter
import com.common.basic.mvp.IBaseView
import com.common.net.exception.ErrorStatus
import com.common.widget.LoadingDialog
import com.common.widget.MultipleStatusView

/**
 * description:
 * author:  周昇辰
 * date:2019/3/28
 */


abstract class BaseFragment : Fragment(),IBaseView{
    /**
     * 页面状态
     */
    protected var mLayoutStatusView:MultipleStatusView?=null

    private var loadingDialog:LoadingDialog?=null

    private var mPresenter: BasePresenter<*>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView=inflater.inflate(getLayoutId(),container,false)
        return mView
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    @LayoutRes
    abstract fun getLayoutId():Int

    abstract fun initView()

    override fun loadPageData(){}

    override fun showContentView() {
       mLayoutStatusView?.showContent()
    }
    override fun showLoading(isCancel: Boolean) {
        mLayoutStatusView?.showLoading()
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        when(errorCode){
            ErrorStatus.NETWORK_ERROR->
                mLayoutStatusView?.showNoNetwork()
            else->
                mLayoutStatusView?.showError()
        }
        showToast(errorMsg)
    }


    override fun endLoading() {
           if(loadingDialog!=null&&loadingDialog!!.isShowing){
               loadingDialog?.apply {
                   dismiss()
               }
           }
    }

    override fun showToast(content: String) {
        if(!content.isEmpty()){
            activity?.let {
                Toast.makeText(it,content, Toast.LENGTH_SHORT).show()
            }
        }
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
