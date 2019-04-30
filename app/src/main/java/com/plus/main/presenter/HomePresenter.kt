package com.plus.main.presenter

import android.util.Log
import com.common.basic.mvp.BasePresenter
import com.common.net.exception.ExceptionHandle
import com.plus.main.model.HomeModel
import com.plus.main.bean.HomeBean
import com.plus.main.contract.HomeContract


/**
 * description:
 * author:  周昇辰
 * date:2019/4/3
 */


class HomePresenter : BasePresenter<HomeContract.View>(),HomeContract.Presenter{

    private var bannerHomeBean: HomeBean? = null

    private var nextPageUrl:String?=null

    private val model : HomeModel by lazy {
        HomeModel()
    }

    override fun getHomeData(isShow:Boolean) {
        if(isShow){
            mRootView?.showLoading(true)
        }
        val disposable = model.requestHomeData(1)
                .flatMap({ homeBean ->
                    Log.d("http", "fffff111111------------------------")
                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = homeBean.issueList[0].itemList

                    Log.d("https", "banner过滤之前" + bannerItemList.size)
                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        bannerItemList.remove(item)
                    }
                    Log.d("https", "banner过滤之后" + bannerItemList.size)
                    bannerHomeBean = homeBean
                    model.loadMoreData(homeBean.nextPageUrl)
                })
                .subscribe({ homeBean ->
                    mRootView?.apply {
                        showContentView()
                        nextPageUrl = homeBean.nextPageUrl

                        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                        val newBannerItemList = homeBean.issueList[0].itemList

                        newBannerItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //移除 item
                            newBannerItemList.remove(item)
                        }
                        // 重新赋值 Banner 长度
                        bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size

                        //赋值过滤后的数据 + banner 数据
                        bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)

                        showHomeData(bannerHomeBean)
                        closeRefresh(true)
                    }
                }, { e ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
                        closeRefresh(true)
                    }

                })
        addDisposable(disposable)
    }


    override fun loadMoreData() {

        nextPageUrl?.let {
            val disposable = model.loadMoreData(it)
                    .subscribe({ homeBean ->
                        mRootView?.apply {
                            //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                            val newItemList = homeBean.issueList[0].itemList

                            newItemList.filter { item ->
                                item.type == "banner2" || item.type == "horizontalScrollCard"
                            }.forEach { item ->
                                //移除 item
                                newItemList.remove(item)
                            }

                            nextPageUrl = homeBean.nextPageUrl
                            showMoreData(newItemList)
                            closeRefresh(false)
                        }
                    }, { e ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(e), ExceptionHandle.errorCode)
                            closeRefresh(false)
                        }
                    })
            addDisposable(disposable)
        }

    }

}
