package com.plus.video

import com.common.basic.mvp.IBasePresenter
import com.common.basic.mvp.IBaseView
import com.plus.main.bean.HomeBean

/**
 * description:
 * author:  周昇辰
 * date:2019/4/4
 */

class VideoContract {

    interface View : IBaseView {
        fun setVideo(url: String)

        fun setVideoInfo(itemInfo: HomeBean.Issue.Item)

        fun setBackground(url: String)

        fun setRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)
    }

    interface Presenter:IBasePresenter<View>{

         fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)

         fun requestReletedVideo(videoId: Long)
    }
}