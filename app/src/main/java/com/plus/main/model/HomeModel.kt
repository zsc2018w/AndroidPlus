package com.plus.main.model

import com.common.net.HttpUtils
import com.common.net.rx.SchedulerUtils
import com.plus.core.VideoApiService
import com.plus.main.bean.HomeBean
import io.reactivex.Observable

/**
 * description:
 * author:  周昇辰
 * date:2019/4/3
 */


class HomeModel{

    private val apiService:VideoApiService by lazy {
        HttpUtils.instance.create(VideoApiService::class.java)
    }


  fun requestHomeData(num:Int): Observable<com.plus.main.bean.HomeBean> {

      return  apiService.getHomeData(num).compose(SchedulerUtils.ioToMain())
  }



    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean>{

        return apiService.getMoreHomeData(url).compose(SchedulerUtils.ioToMain())
    }

}
