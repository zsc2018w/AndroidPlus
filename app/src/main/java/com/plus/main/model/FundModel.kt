package com.plus.main.model

import com.common.net.HttpUtils
import com.common.net.rx.SchedulerUtils
import com.plus.core.VideoApiService
import com.plus.main.bean.CategoryBean
import io.reactivex.Observable

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */


class FundModel{

    val apiService:VideoApiService by lazy {
        HttpUtils.instance.create(VideoApiService::class.java)
    }


    fun requestCategory():Observable<ArrayList<CategoryBean>>{
       return apiService.getCategory().compose(SchedulerUtils.ioToMain())
    }
}

