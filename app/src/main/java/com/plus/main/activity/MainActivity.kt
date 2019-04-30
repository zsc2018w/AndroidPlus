package com.plus.main.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.common.basic.BaseActivity
import com.common.utils.StatusBarUtil
import com.plus.BottomNavigationViewHelper
import com.plus.R
import com.plus.main.frgament.HomeFragment
import com.plus.main.frgament.FundFragment
import com.plus.main.frgament.GirlFragment
import com.plus.main.frgament.MyFragment
import com.plus.main.model.TestCallBackEvent
import com.plus.main.model.TestUser
import com.plus.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : BaseActivity() {
    companion object {
        val HOME_TAG="home_tag"
        val FUND_TAG="fund_tag"
        val GIRL_TAG="girl_tag"
        val MY_TAG="my_tag"
    }
    var currentIndex:Int = 0
    var homeFragment: HomeFragment? = null
    var fundFragment: FundFragment? = null
    var girlFragment: GirlFragment? = null
    var myFragment: MyFragment? = null
    var fragmentManager: FragmentManager? = null
    val FRAM_VIEW_ID= R.id.frame_layout

    override fun getLayoutId()=R.layout.activity_main

    override fun initView() {
       // BottomNavigationViewHelper.disableShiftMode(bottom_navigation)
        StatusBarUtil.immersive(this)
        StatusBarUtil.darkMode(this,true)
        bottom_navigation.setOnNavigationItemSelectedListener(onLister)
        onTabSelected(0)

    }


    private val onLister = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.tab_home -> {
                onTabSelected(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_fund -> {
                onTabSelected(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_photo->{
                onTabSelected(2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.tab_my -> {
                onTabSelected(3)
                return@OnNavigationItemSelectedListener  true
            }
        }
        return@OnNavigationItemSelectedListener false
    }
    fun onTabSelected(index: Int) {
        hideFragmentAll(index)
        currentIndex=index
        when (index) {
            0 -> {
                homeFragment?.let {
                    showFragment(it)
                } ?: HomeFragment.getInstance().let {
                    homeFragment = it
                    addFragment(it, HOME_TAG)
                }
            }
            1 -> {
                fundFragment?.let {
                    showFragment(it)
                } ?: FundFragment.getIntance().let {
                    fundFragment = it
                    addFragment(it, FUND_TAG)
                }
            }
            2->{
                girlFragment?.let {
                    showFragment(it)
                }?:GirlFragment.getIntance().let {
                    girlFragment  =it
                    addFragment(it, GIRL_TAG)
                }
            }
            3 -> {
                myFragment?.let {
                    showFragment(it)
                } ?: MyFragment.getIntance().let {
                    myFragment = it
                    addFragment(it, MY_TAG)
                }

            }

        }
    }


    fun getTransaction(): FragmentTransaction? {
        if(fragmentManager==null){
            fragmentManager=supportFragmentManager
        }
       return fragmentManager?.beginTransaction()
    }

    fun addFragment(fragment: Fragment,tag : String) {
        getTransaction()?.add(FRAM_VIEW_ID, fragment,tag)?.commit()
    }

    fun showFragment(fragment: Fragment) {
        getTransaction()?.show(fragment)?.commit()
    }

    fun hideFragmentAll(index:Int){
        val repeatClick=currentIndex==index
        if(!repeatClick){
            homeFragment?.let {
                getTransaction()?.hide(it)?.commit()
            }
            fundFragment?.let {
                getTransaction()?.hide(it)?.commit()
            }
            girlFragment?.let {
                getTransaction()?.hide(it)?.commit()
            }
            myFragment?.let {
                getTransaction()?.hide(it)?.commit()
            }

        }
    }



}
