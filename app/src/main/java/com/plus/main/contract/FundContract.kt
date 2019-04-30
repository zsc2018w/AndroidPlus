package com.plus.main.contract

import com.common.basic.mvp.IBasePresenter
import com.common.basic.mvp.IBaseView
import com.plus.main.bean.CategoryBean

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */

interface FundContract {

    interface View :IBaseView{

        fun setCategoryData(categoryDataList:ArrayList<CategoryBean>)

    }

    interface Presenter :IBasePresenter<View>{

        fun getCategoryData()
    }
}