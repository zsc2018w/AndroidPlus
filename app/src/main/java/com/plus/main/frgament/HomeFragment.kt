package com.plus.main.frgament

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.common.basic.BaseFragment
import com.common.basic.BaseLazyFragment
import com.common.utils.StatusBarUtil
import com.google.zxing.qrcode.encoder.QRCode
import com.plus.R
import com.plus.main.presenter.HomePresenter
import com.plus.main.adapter.HomeAdapter
import com.plus.main.bean.HomeBean
import com.plus.main.contract.HomeContract
import com.plus.main.model.TestCallBackEvent
import com.plus.main.model.TestUser
import kotlinx.android.synthetic.main.activity_video_details.*
import kotlinx.android.synthetic.main.fragment_category.*

import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
/**
 * description:
 * author:  周昇辰
 * date:2019/3/26
 */


class HomeFragment : BaseLazyFragment(),HomeContract.View{


    val homePresenter : HomePresenter by lazy {
        HomePresenter()
    }
    var homeAdapter:HomeAdapter?=null

    companion object {
        fun getInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }


    override fun getLayoutId()=R.layout.fragment_home


    override fun closeRefresh(refreshOrLoad: Boolean) {
       if(refreshOrLoad){
           refreshLayout.finishRefresh()
       }else{
           refreshLayout.finishLoadMore()
       }
    }

    override fun showMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
       homeAdapter?.loadItemData(itemList)
    }

    override fun showHomeData(homeBean: HomeBean?) {
        home_recycleview.layoutManager=LinearLayoutManager(activity)
        homeAdapter =HomeAdapter(context!!)
        home_recycleview.adapter=homeAdapter
        homeAdapter?.setBannerSize(homeBean?.issueList!![0].count)
        homeAdapter?.loadItemData(homeBean!!.issueList[0].itemList)
    }

    override fun onlazyLoad() {
        activity?.let {
            StatusBarUtil.setPaddingSmart(it,toolbar)
            StatusBarUtil.darkMode(it,false)
        }
        homePresenter.attachView(this)
        setPresenter(homePresenter)
        homePresenter.getHomeData(false)

        mLayoutStatusView=homeStatusView

        homeStatusView?.setOnRetryClickListener {
            homePresenter.getHomeData(true)
        }

        refreshLayout.setOnRefreshListener {
            homePresenter.getHomeData(false)
        }

        refreshLayout.setOnLoadMoreListener {
            homePresenter.loadMoreData()
        }


    }





}
