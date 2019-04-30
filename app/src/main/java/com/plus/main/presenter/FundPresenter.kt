package com.plus.main.presenter

import com.common.basic.mvp.BasePresenter
import com.common.net.exception.ExceptionHandle
import com.plus.main.contract.FundContract
import com.plus.main.model.FundModel

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */

class FundPresenter : BasePresenter<FundContract.View>(),FundContract.Presenter {
    private val model:FundModel by lazy {
        FundModel()
    }


    override fun getCategoryData() {
        model.requestCategory()
                .subscribe({ dataList ->
                    mRootView?.apply {

                        showContentView()
                        setCategoryData(dataList)
                    }

                }, { e ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(e),ExceptionHandle.errorCode)
                    }
                },{
                    mRootView?.apply {
                    }
                })
    }




}