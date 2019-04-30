package com.plus.video

import android.app.Activity
import android.util.Log
import com.common.basic.BaseApplication
import com.common.basic.mvp.BasePresenter
import com.common.basic.mvp.IBasePresenter
import com.common.net.exception.ExceptionHandle
import com.common.utils.DisplayManager
import com.common.utils.NetworkUtil
import com.plus.main.bean.HomeBean

/**
 * description:
 * author:  周昇辰
 * date:2019/4/4
 */

class VideoPresenter: BasePresenter<VideoContract.View>(),VideoContract.Presenter {
    val model:VideoModel by lazy { VideoModel() }


    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {
        val playInfo = itemInfo.data?.playInfo
        val netType = NetworkUtil.isWifi(BaseApplication.context)
        if (playInfo!!.size > 1) {
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                //否则就选标清的视频
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        /*             //Todo 待完善
                                     ( as Activity).showToast("本次消耗${(mRootView as Activity)
                                             .dataFormat(i.urlList[0].size)}流量")*/
                        break
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }
        //设置背景
        val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { mRootView?.setBackground(it) }
        mRootView?.setVideoInfo(itemInfo)
    }


    override fun requestReletedVideo(videoId: Long) {

        model.requestReletedVieoInfo(videoId).subscribe(
                { issue ->
                    mRootView?.apply {
                        setRelatedVideo(issue.itemList)
                    }

                }, { e ->
            mRootView?.apply {
                showError(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
            }
        })
    }



}