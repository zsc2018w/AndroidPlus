package com.plus.core

import com.common.basic.BaseApiService
import com.plus.main.bean.CategoryBean
import com.plus.main.bean.HomeBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * description:
 * author:  周昇辰
 * date:2019/4/1
 */

interface VideoApiService  {

    /**
     * 获取首页数据
     */
    @GET(UrlConstant.URL_HOME_CHOICENESS)
    fun  getHomeData(@Query("num") num :Int):Observable<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>


    /**
     * 根据item id获取相关视频
     */
    @GET(UrlConstant.URL_RELATED_VIDEO)
    fun getRelatedData(@Query("id") id: Long): Observable<HomeBean.Issue>

    /**
     * 获取分类
     */
    @GET(UrlConstant.URL_CATEGORY)
    fun getCategory():Observable<ArrayList<CategoryBean>>

    /**
     * 获取分类详情
     */
    @GET(UrlConstant.URL_CATEGORY_LIST)
    fun  getCategoryDeatilsList(@Query("id") id: String):Observable<HomeBean.Issue>

}