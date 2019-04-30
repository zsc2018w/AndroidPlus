package com.plus.main.model

import com.common.net.HttpUtils
import com.common.net.rx.SchedulerUtils
import com.plus.core.ApiService
import com.plus.core.VideoApiService
import com.plus.main.bean.GirlData
import com.plus.main.bean.HomeBean
import io.reactivex.Observable

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */


class GirlModel{

   private  val apiService:ApiService by lazy {
        HttpUtils.instance.create(ApiService::class.java)
    }



    fun requestPhotoData(size:Int,page:Int):Observable<GirlData>{
       return apiService.getPhotoList(size,page).compose(SchedulerUtils.ioToMain())
    }
}

