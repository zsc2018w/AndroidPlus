package com.plus.main.presenter

import com.common.basic.mvp.BasePresenter
import com.common.net.exception.ExceptionHandle
import com.plus.main.bean.HomeBean
import com.plus.main.contract.CategoryContract
import com.plus.main.contract.GirlContract
import com.plus.main.model.CategoryModel
import com.plus.main.model.GirlModel

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */

class GirlPresenter :BasePresenter<GirlContract.View>(),GirlContract.Presenter {

    private val SIZE = 20
    private var mStartPage = 1

    private val model: GirlModel by lazy {
        GirlModel()
    }


    override fun getPhotoData() {

        val disposable = model.requestPhotoData(SIZE, mStartPage).subscribe({ it ->

            mRootView?.apply {
                mStartPage ++
                setPhotoData(it.results)
                closeLoadMore()
            }
        }, { e ->
            mRootView?.apply {
                showError(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
                closeLoadMore()
            }
        }, {

        })
        addDisposable(disposable)
    }


}