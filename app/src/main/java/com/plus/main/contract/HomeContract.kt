package com.plus.main.contract

import com.common.basic.mvp.BasePresenter
import com.common.basic.mvp.IBasePresenter
import com.common.basic.mvp.IBaseView
import com.plus.main.bean.HomeBean

/**
 * description:
 * author:  周昇辰
 * date:2019/4/2
 */

interface HomeContract {

   interface View :IBaseView{

       fun closeRefresh(refreshOrLoad:Boolean)

       fun showHomeData(homeBean:HomeBean?)

       fun showMoreData(itemList:ArrayList<HomeBean.Issue.Item>)
   }

    interface Presenter : IBasePresenter<View> {

        fun getHomeData(isShowLoad:Boolean)

        fun loadMoreData()
    }


}