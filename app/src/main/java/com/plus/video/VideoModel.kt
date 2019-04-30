package com.plus.video

import com.common.net.HttpUtils
import com.common.net.rx.SchedulerUtils
import com.plus.core.VideoApiService
import com.plus.main.bean.HomeBean
import io.reactivex.Observable

/**
 * description:
 * author:  周昇辰
 * date:2019/4/4
 */


class VideoModel{

    private val apiService: VideoApiService by lazy {
        HttpUtils.instance.create(VideoApiService::class.java)
    }


    fun requestReletedVieoInfo(videoId:Long):Observable<HomeBean.Issue>{
      return  apiService.getRelatedData(videoId).compose(SchedulerUtils.ioToMain())
    }



}
