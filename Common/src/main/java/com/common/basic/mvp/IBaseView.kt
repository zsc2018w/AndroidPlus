package com.common.basic.mvp

/**
 * description:
 * author:  周昇辰
 * date:2019/4/2
 */

interface IBaseView {

    /**
     * 显示加载
     * isCancel  是否可以取消
     */
    fun showLoading(isCancel:Boolean)
    /**
     * 关闭加载
     */
    fun endLoading()

    /**
     * toast
     */
    fun showToast(content:String)


    /**
     * 显示错误视图
     */
    fun showError(errorMsg:String,errorCode:Int)

    /**
     * 显示内容视图
     */
    fun showContentView()

    /**
     * 请求页面数据
     */
    fun loadPageData()

}