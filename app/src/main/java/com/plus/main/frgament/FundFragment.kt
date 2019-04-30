package com.plus.main.frgament

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.util.Log
import com.common.basic.BaseFragment
import com.common.basic.BaseLazyFragment
import com.common.utils.StatusBarUtil
import com.plus.R
import com.plus.main.adapter.CategoryPagerAdapter
import com.plus.main.bean.CategoryBean
import com.plus.main.contract.FundContract
import com.plus.main.presenter.FundPresenter
import kotlinx.android.synthetic.main.fragment_fund.*


/**
 * description: 发现
 * author:  周昇辰
 * date:2019/3/27
 */


class FundFragment : BaseLazyFragment(),FundContract.View {

    val mPresenter:FundPresenter by lazy {
        FundPresenter()
    }
    companion object {
        fun getIntance(): FundFragment {
            val fragment= FundFragment()
            return fragment
        }
    }
    override fun getLayoutId()= R.layout.fragment_fund

    override fun onlazyLoad() {
        activity?.let {
            StatusBarUtil.setPaddingSmart(it,toolbar)
            StatusBarUtil.darkMode(it)
            toolbar.minimumHeight=StatusBarUtil.getStatusBarHeight(it)
        }

        mPresenter.attachView(this)
        setPresenter(mPresenter)

        mLayoutStatusView=fundStatusView

        fundStatusView?.setOnRetryClickListener {
            mPresenter.getCategoryData()
        }

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener({
            appBarLayout: AppBarLayout?, verticalOffset: Int ->
            fund_title_tv.setTextColor(changeAlpha(Color.BLACK, 1-Math.abs(verticalOffset * 1.0f) / appBarLayout!!.getTotalScrollRange()))

        }))

        mPresenter.getCategoryData()
    }


    override fun setCategoryData(categoryDataList: ArrayList<CategoryBean>) {
        activity?.let {
            val categoryAdapter= CategoryPagerAdapter(childFragmentManager,categoryDataList)
            viewPager.adapter=categoryAdapter
            tablayout.setupWithViewPager(viewPager)
            tablayout.tabMode=TabLayout.MODE_SCROLLABLE
        }

    }

    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

/*
    override fun onResume() {
        super.onResume()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {//页面可见时

        }
    }*/

}