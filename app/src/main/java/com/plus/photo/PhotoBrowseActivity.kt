package com.plus.photo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.common.basic.BaseActivity
import com.common.widget.BaseAnimCloseViewPager
import com.plus.R
import kotlinx.android.synthetic.main.activity_photo_browse.*
import kotlinx.android.synthetic.main.layout_browse.*

class PhotoBrowseActivity : BaseActivity() {

    private var pictureList:ArrayList<String>?=null
    private val firstDisplayImageIndex:Int=0
    private var newPageSelected = false
    private var vp:BaseAnimCloseViewPager?=null
    private  var canDrag = false

    companion object {
        val URLS:String="Image_Url_List"
    }
    override fun getLayoutId(): Int = R.layout.activity_photo_browse

    override fun initView() {
        intent?.let {
            pictureList=it.getStringArrayListExtra(URLS)
        }

        vp= viewPager as BaseAnimCloseViewPager

        setViewPagerAdapter()
        setEnterSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(names: List<String>?, sharedElements: MutableMap<String, View>?) {
                val layout = vp?.findViewWithTag(vp?.getCurrentItem()) as ViewGroup

                val sharedView = layout.findViewById<ImageView>(R.id.image_view)
                sharedElements!!.clear()
                sharedElements["tansition_view"] = sharedView
            }
        })
    }

    private fun onViewPagerSelected(position: Int) {
        updateCurrentImageView(position)
        setImageView(pictureList?.get(position)!!)
    }
    fun setViewPagerAdapter(){


        vp?.adapter = adapter
        vp?.offscreenPageLimit = 1
        vp?.currentItem = firstDisplayImageIndex
        vp?.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                if (positionOffset == 0f && newPageSelected) {
                    newPageSelected = false
                    onViewPagerSelected(position)
                }
            }

            override fun onPageSelected(position: Int) {

            }
        })

        vp?.setiAnimClose(object : BaseAnimCloseViewPager.IAnimClose{
            override fun onPictureClick() {
                finishAfterTransition()
            }

            override fun onPictureRelease(view: View?) {
                finishAfterTransition()
            }

            override fun canDrag(): Boolean =  canDrag
        })
    }

    private val adapter=object :PagerAdapter(){

        override fun getCount(): Int =pictureList?.size?:0

        override fun notifyDataSetChanged() {
            super.notifyDataSetChanged()
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            val layout = obj as View
            container.removeView(layout)
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val layout: View
            layout = LayoutInflater.from(this@PhotoBrowseActivity).inflate(R.layout.layout_browse, null)
            //                layout.setOnClickListener(onClickListener);
            container.addView(layout)
            layout.tag = position

            if (position == firstDisplayImageIndex) {
                onViewPagerSelected(position)
            }

            return layout

        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }

    }


    protected fun updateCurrentImageView(position: Int) {
        val currentLayout:View?= vp?.findViewWithTag(position)
        if (currentLayout == null) {
            ViewCompat.postOnAnimation(viewPager) { updateCurrentImageView(position) }
            return
        }
        vp?.setCurrentShowView(image_view)
    }

    private fun setImageView(path: String) {
        if (image_view.getDrawable() != null)
        //判断是否已经加载了图片，避免闪动
            return
        if (TextUtils.isEmpty(path)) {
            image_view.setBackgroundColor(Color.GRAY)
            return
        }
        canDrag = false
        Glide.with(this).load(path).into(image_view)
    }


    override fun finishAfterTransition() {
        val intent = Intent()
       // intent.putExtra("index", imageViewPager.getCurrentItem())
        setResult(Activity.RESULT_OK, intent)
        super.finishAfterTransition()
    }
}
