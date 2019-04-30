package com.plus.main.model

import com.common.net.HttpUtils
import com.common.net.rx.SchedulerUtils
import com.plus.core.VideoApiService
import com.plus.main.bean.HomeBean
import io.reactivex.Observable

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */


class CategoryModel{

    val apiService:VideoApiService by lazy {
        HttpUtils.instance.create(VideoApiService::class.java)
    }


    fun requestCategoryDetail(id:String):Observable<HomeBean.Issue>{
       return apiService.getCategoryDeatilsList(id).compose(SchedulerUtils.ioToMain())
    }
}

