package com.plus.main.frgament


import android.support.v7.widget.StaggeredGridLayoutManager
import com.common.basic.BaseLazyFragment
import com.plus.R
import com.plus.main.adapter.PhotoAdapter
import com.plus.main.bean.PhotoGirl
import com.plus.main.contract.GirlContract
import com.plus.main.presenter.GirlPresenter
import kotlinx.android.synthetic.main.fragment_girl.*


/**
 * description:
 * author:  周昇辰
 * date:2019/3/27
 */


class GirlFragment :BaseLazyFragment(),GirlContract.View{

    private var photoAdapter:PhotoAdapter?=null
    private val mPresenter: GirlPresenter by lazy {
        GirlPresenter()
    }
    companion object {
        fun getIntance():GirlFragment{
            val fragment=GirlFragment()
            return fragment
        }
    }

    override fun getLayoutId()=R.layout.fragment_girl

    override fun onlazyLoad() {
       mPresenter.attachView(this)
       setPresenter(mPresenter)

        activity?.let {
            photoAdapter=PhotoAdapter(it)
            val manager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            manager.gapStrategy=StaggeredGridLayoutManager.GAP_HANDLING_NONE
            photoRecycler.layoutManager=manager
            photoRecycler.adapter=photoAdapter
        }

        refreshLayout.setOnLoadMoreListener {
            mPresenter.getPhotoData()
        }

        mPresenter.getPhotoData()

    }

    override fun setPhotoData(items: ArrayList<PhotoGirl>) {
        photoAdapter?.loadItemData(items)
    }

    override fun closeLoadMore() {
        refreshLayout.finishLoadMore()
    }
}
