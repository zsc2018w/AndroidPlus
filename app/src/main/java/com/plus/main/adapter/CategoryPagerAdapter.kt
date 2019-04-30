package com.plus.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup
import com.plus.main.bean.CategoryBean
import com.plus.main.frgament.CategoryFragment

/**
 * description:
 * author:  周昇辰
 * date:2019/4/9
 */

class CategoryPagerAdapter(fragmentManager:FragmentManager,val categoryDataList: ArrayList<CategoryBean>?) :FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(index: Int): Fragment {
        return CategoryFragment.getIntance(categoryDataList?.get(index))
    }

    override fun getCount(): Int {
         return   categoryDataList?.size?:0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return categoryDataList?.get(position)?.name
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

  /*  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, `object`)
    }*/
}