package com.plus.core

/**
 * Created by xuhao on 2017/11/16.
 */
object UrlConstant{
    const val BASE_URL = "http://baobab.kaiyanapp.com/api/"

    //首页精选
    const val URL_HOME_CHOICENESS= BASE_URL+"v2/feed?"
    //相关视频
    const val URL_RELATED_VIDEO= BASE_URL+"v4/video/related?"
    //分类
    const val URL_CATEGORY = BASE_URL+"v4/categories"
    //分类详情
    const val URL_CATEGORY_LIST= BASE_URL+"v4/categories/videoList?"


    //图片
    const val URL_PHOTO_LIST="http://gank.io/api/data/福利/{size}/{page}"



}