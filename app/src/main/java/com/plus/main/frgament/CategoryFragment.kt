package com.plus.main.frgament

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.common.basic.BaseFragment
import com.common.basic.BaseLazyFragment
import com.common.widget.LoadingDialog
import com.plus.R
import com.plus.R.id.pageStatusView
import com.plus.core.Constants
import com.plus.main.adapter.CategoryDetailAdapter
import com.plus.main.adapter.HomeAdapter
import com.plus.main.bean.CategoryBean
import com.plus.main.bean.HomeBean
import com.plus.main.contract.CategoryContract
import com.plus.main.presenter.CategoryPresenter
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */


class CategoryFragment :BaseLazyFragment(),CategoryContract.View{

    private var detailId:String=""
    private var headUrl:String=""
    private var categoryBean:CategoryBean?=null
    private var adapter:CategoryDetailAdapter?=null


    private val mPresenter:CategoryPresenter by lazy {
        CategoryPresenter()
    }
    companion object {
        fun getIntance(categoryBean: CategoryBean?):CategoryFragment{
            val categoryFragment=CategoryFragment()
            val bundle=Bundle()
            bundle.putSerializable(Constants.CATEGORY_DETAIL_DATA,categoryBean)
            categoryFragment.arguments=bundle
            return categoryFragment
        }
    }
    override fun getLayoutId(): Int =R.layout.fragment_category

    override fun onlazyLoad() {
        arguments?.let {
            categoryBean= it.getSerializable(Constants.CATEGORY_DETAIL_DATA) as CategoryBean?
            categoryBean?.let {
                detailId=it.id.toString()
                headUrl=it.bgPicture
            }
        }

        Log.d("testx","detailId=="+detailId+"加载")

        //attach
        mPresenter.attachView(this)
        mLayoutStatusView=pageStatusView
        setPresenter(mPresenter)

        //error click
        pageStatusView?.setOnRetryClickListener{
          //  v->
            loadPageData()
        }

        swipeRefresh.setOnRefreshListener {
            loadPageData()
        }

        //init  Adapter
        activity?.let {
            adapter =CategoryDetailAdapter(it)
            categoryRecycler.layoutManager=LinearLayoutManager(it)
            categoryRecycler.adapter=adapter
            adapter?.setHeadData(categoryBean)
        }

        loadPageData()
    }

    override fun loadPageData() {
        //load
        if(!TextUtils.isEmpty(detailId)){
            mPresenter.getCategoryDetailData(detailId)
        }
    }

    override fun setCategoryDetailData(detailData: HomeBean.Issue) {
        activity?.let {
            adapter?.loadItemData(detailData.itemList)
        }
    }

    override fun closeRefresh() {
       swipeRefresh.isRefreshing=false
    }

    override fun closeLoad() {

    }

}
