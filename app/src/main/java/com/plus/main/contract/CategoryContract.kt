package com.plus.main.contract

import com.common.basic.mvp.IBasePresenter
import com.common.basic.mvp.IBaseView
import com.plus.main.bean.CategoryBean
import com.plus.main.bean.HomeBean

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */

interface CategoryContract {

    interface View :IBaseView{

        fun setCategoryDetailData(detailData: HomeBean.Issue)

        fun closeRefresh()

        fun closeLoad()
    }

    interface Presenter :IBasePresenter<View>{

        fun getCategoryDetailData(id:String)
    }
}