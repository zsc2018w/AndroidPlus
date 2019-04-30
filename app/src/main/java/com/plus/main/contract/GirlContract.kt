package com.plus.main.contract

import com.common.basic.mvp.IBasePresenter
import com.common.basic.mvp.IBaseView
import com.plus.main.bean.CategoryBean
import com.plus.main.bean.HomeBean
import com.plus.main.bean.PhotoGirl

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */

interface GirlContract {

    interface View :IBaseView{

        fun setPhotoData(items: ArrayList<PhotoGirl>)

        fun closeLoadMore()
    }

    interface Presenter :IBasePresenter<View>{

        fun getPhotoData()
    }
}