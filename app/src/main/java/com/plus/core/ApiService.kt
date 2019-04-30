package com.plus.core

import com.common.basic.BaseApiService
import com.plus.main.bean.GirlData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * description:
 * author:  周昇辰
 * date:2019/4/15
 */

interface ApiService {


    @GET(UrlConstant.URL_PHOTO_LIST)
     fun getPhotoList(
          /*  @Header("Cache-Control") cacheControl: String,*/
            @Path("size") size: Int,
            @Path("page") page: Int): Observable<GirlData> //美女
}