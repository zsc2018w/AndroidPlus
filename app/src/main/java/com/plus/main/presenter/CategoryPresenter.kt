package com.plus.main.presenter

import com.common.basic.mvp.BasePresenter
import com.common.net.exception.ExceptionHandle
import com.plus.main.bean.HomeBean
import com.plus.main.contract.CategoryContract
import com.plus.main.model.CategoryModel

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */

class CategoryPresenter :BasePresenter<CategoryContract.View>(),CategoryContract.Presenter {

    private val model: CategoryModel by lazy {
         CategoryModel()
    }


    override fun getCategoryDetailData(id: String) {
        mRootView?.showLoading(true)
        model.requestCategoryDetail(id).subscribe({ issue ->
            mRootView?.apply {
                showContentView()
                setCategoryDetailData(issue)
            }
        }, { e ->
            mRootView?.apply {
                showError(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
                closeRefresh()
                closeLoad()
            }
        },{
            mRootView?.apply {
                closeRefresh()
                closeLoad()
            }
        })
    }
}