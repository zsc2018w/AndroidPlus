package com.common.basic.mvp

import io.reactivex.disposables.Disposable

/**
 * description:
 * author:  周昇辰
 * date:2019/4/2
 */

interface IBasePresenter< V : IBaseView> {

    /**
     * 获得当前view
     */
    var mRootView: V?

    /**
     * 当前presenter是否绑定view
     * @return
     */
    val isAttachView: Boolean


    /**
     * 绑定view的方法
     * @param view
     */
     fun attachView(view: V)

    /**
     * 解绑view的方法
     */
     fun detachView()

    /**
     * 添加订阅管理
     * @param disposable
     */
     fun addDisposable(disposable: Disposable)

    /**
     * 取消订阅
     */
     fun removeDisposable()
}