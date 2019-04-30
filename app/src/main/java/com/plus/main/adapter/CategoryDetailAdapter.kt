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
import android.widget.TextView
import android.widget.Toast
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.plus.R
import com.plus.core.Constants
import com.plus.main.bean.CategoryBean
import com.plus.main.bean.HomeBean
import com.plus.utils.durationFormat
import com.plus.utils.showToast
import com.plus.video.VideoDetailsActivity
import io.reactivex.Observable

/**
 * description:
 * author:  周昇辰
 * date:2019/4/2
 */


class CategoryDetailAdapter(val mContext:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mData: ArrayList<HomeBean.Issue.Item> = arrayListOf()
    private var categoryBean:CategoryBean?=null
    companion object {
        private const val ITEM_TYPE_HEAD = 1    //HEAD 类型
        private const val ITEM_TYPE_CONTENT = 2    //item
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_TYPE_HEAD->
                    BannerViewHolder(inflaterView(parent,R.layout.item_category_head))
            else->
                    ContentViewHolder(inflaterView(parent,R.layout.item_home_content))
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_HEAD -> {
                categoryBean?.let {

                    Glide.with(mContext).load(it.bgPicture).into(viewHolder.itemView.findViewById(R.id.headIv))
                    viewHolder.itemView.findViewById<TextView>(R.id.descriptiontv).text=categoryBean?.description
                    viewHolder.itemView.findViewById<TextView>(R.id.nameTv).text=categoryBean?.name
                }

            }
            ITEM_TYPE_CONTENT -> {
                setContentItem(viewHolder as ContentViewHolder, mData[position - 1])

            }
        }
    }


    fun setContentItem(holder: ContentViewHolder, item: HomeBean.Issue.Item){

        val itemData = item.data

     //   val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }

        Glide.with(mContext).load(cover).into(holder.itemView.findViewById(R.id.iv_cover_feed))

        Glide.with(mContext).load(avatar).into(holder.itemView.findViewById(R.id.iv_avatar))

        holder.itemView.findViewById<TextView>(R.id.tv_title).text= itemData?.title ?: ""

        //遍历标签
        itemData?.tags?.take(3)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.itemView.findViewById<TextView>(R.id.tv_tag).text=tagText

        holder.itemView.findViewById<TextView>(R.id.tv_category).text= "#" + itemData?.category

        holder.itemView.setOnClickListener{
            goToVideoPlayer(mContext as Activity,holder.itemView.findViewById(R.id.iv_cover_feed),item)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->
                ITEM_TYPE_HEAD
            else ->
                ITEM_TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int {
        return when {
            mData.size > 0 -> mData.size+1
            mData.isEmpty() -> 0
            else -> 0
        }
    }


    /**
     * 加载数据
     */
    fun loadItemData(itemData: ArrayList<HomeBean.Issue.Item>?){
        itemData?.let {
            mData.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun setHeadData(categoryBean: CategoryBean?){
        this.categoryBean=categoryBean
    }


    private fun inflaterView(parent:ViewGroup,viewId:Int):View{
      return  LayoutInflater.from(mContext).inflate(viewId,parent,false)
    }


    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item){
        val intent = Intent(activity, VideoDetailsActivity::class.java)
        intent.putExtra(Constants.VIDE0_DETAIL_DATA, itemData)
        intent.putExtra(VideoDetailsActivity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailsActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            //activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}



