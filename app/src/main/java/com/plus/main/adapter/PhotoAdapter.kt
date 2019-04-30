package com.plus.main.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.common.utils.DisplayManager
import com.common.utils.ScreenUtils
import com.plus.R
import com.plus.main.bean.PhotoGirl
import com.plus.photo.PhotoBrowseActivity
import com.plus.video.VideoDetailsActivity
import java.util.*

/**
 * description:
 * author:  周昇辰
 * date:2019/4/16
 */

class PhotoAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList:ArrayList<PhotoGirl> = arrayListOf()
    private val heightList= arrayListOf<Int>(300,280,200)
    private var windowWidth:Int = ScreenUtils.getScreenWidth(mContext)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return  PhotoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_photo,parent,false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
         viewHolder.itemView.setTag(position)
        val photoGirl = dataList.get(position)
        val params = viewHolder.itemView.findViewById<ImageView>(R.id.iv_photo).layoutParams
        var h:Float?=null
        if(photoGirl.height >0) {
            h=photoGirl.height
        } else{
         h= heightList.get(Random().nextInt(heightList.size)).toFloat()
            photoGirl.height=h
        }
        params.width = windowWidth/2
        params.height = DisplayManager.dip2px(h)!!
        viewHolder.itemView.findViewById<ImageView>(R.id.iv_photo).layoutParams = params
        val requestOptions=RequestOptions()
        requestOptions.placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)

        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(photoGirl.url).into(viewHolder.itemView.findViewById<ImageView>(R.id.iv_photo))
        viewHolder.itemView.findViewById<ImageView>(R.id.iv_photo).setOnClickListener {

            val intent = Intent(mContext, PhotoBrowseActivity::class.java)
            var list= arrayListOf<String>()
            list.add(photoGirl.url!!)
            intent.putExtra(PhotoBrowseActivity.URLS,list)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val pair = Pair(it, "tansition_view")
                val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        mContext as Activity, pair)
                ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle())
            } else {
                mContext.startActivity(intent)
                //activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
            }
        }
    }


    class PhotoViewHolder(view: View) :RecyclerView.ViewHolder(view)


   fun loadItemData(items: ArrayList<PhotoGirl>){
       dataList.addAll(items)
       notifyDataSetChanged()
   }
}