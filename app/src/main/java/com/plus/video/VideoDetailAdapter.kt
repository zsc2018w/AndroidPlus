package com.plus.video

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.hazz.kotlinmvp.glide.GlideRoundTransform
import com.plus.R
import com.plus.main.adapter.HomeAdapter
import com.plus.main.bean.HomeBean
import com.plus.utils.durationFormat

/**
 * description:
 * author:  周昇辰
 * date:2019/4/8
 */

class VideoDetailAdapter(val mContext:Context,var data: ArrayList<HomeBean.Issue.Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {
        private const val ITEM_TYPE_DETAIL = 1
        private const val ITEM_TYPE_TEXT_CARD = 2
        private const val ITEM_TYPE_SMALL_VIDEO = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            ITEM_TYPE_DETAIL->
               return ViewHolder(inflaterView(parent,R.layout.item_video_detail_info))
            ITEM_TYPE_TEXT_CARD->
                return ViewHolder(inflaterView(parent,R.layout.item_video_text_card))
            ITEM_TYPE_SMALL_VIDEO->
                return ViewHolder(inflaterView(parent,R.layout.item_video_small_card))
            else->
                throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mData=data.get(position)
       when{
           position==0
           ->   setVideoDetailInfo(data.get(position), holder as ViewHolder)
           data[position].type == "textCard"
           ->   holder.itemView.findViewById<TextView>(R.id.tv_text_card).text=data.get(position).data?.text
           data[position].type == "videoSmallCard"
           ->{
               holder.itemView.findViewById<TextView>(R.id.tv_title).text=data.get(position)?.data?.title
               holder.itemView.findViewById<TextView>(R.id.tv_tag).text="#${mData?.data?.category!!} / ${durationFormat(mData?.data?.duration)}"
               Glide.with(mContext).load(mData.data?.cover?.detail).into(holder.itemView.findViewById<ImageView>(R.id.iv_video_small_card))

           }

       }
    }

    private fun inflaterView(parent:ViewGroup,viewId:Int):View{
        return  LayoutInflater.from(mContext).inflate(viewId,parent,false)
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->
                VideoDetailAdapter.ITEM_TYPE_DETAIL
            data[position].type == "textCard" ->
                VideoDetailAdapter.ITEM_TYPE_TEXT_CARD
            data[position].type == "videoSmallCard" ->
                VideoDetailAdapter.ITEM_TYPE_SMALL_VIDEO
            else ->
                throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }


   private  class ViewHolder(itemView: View)  : RecyclerView.ViewHolder(itemView)


    private fun setVideoDetailInfo(data: HomeBean.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let { holder.itemView.findViewById<TextView>(R.id.tv_title).text=it }
        //视频简介
        data.data?.description?.let { holder.itemView.findViewById<TextView>(R.id.expandable_text).text=it }
        //标签
        holder.itemView.findViewById<TextView>(R.id.tv_tag).text= "#${data.data?.category} / ${durationFormat(data.data?.duration)}"
        //喜欢
        holder.itemView.findViewById<TextView>(R.id.tv_action_favorites).text=data.data?.consumption?.collectionCount.toString()
        //分享
        holder.itemView.findViewById<TextView>(R.id.tv_action_share).text= data.data?.consumption?.shareCount.toString()
        //评论
        holder.itemView.findViewById<TextView>(R.id.tv_action_reply).text=data.data?.consumption?.replyCount.toString()

        if (data.data?.author != null) {
            with(holder) {
                itemView.findViewById<TextView>(R.id.tv_author_name).text= data.data.author.name
                itemView.findViewById<TextView>(R.id.tv_author_desc).text= data.data.author.description
                Glide.with(mContext).load(data.data.author.icon).into(itemView.findViewById<ImageView>(R.id.iv_avatar))
                }
        } else {
            holder.itemView.findViewById<View>(R.id.layout_author_view).visibility=View.GONE
        }
/*
        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(MyApplication.context, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(MyApplication.context, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(MyApplication.context, "评论", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    fun addData(item: ArrayList<HomeBean.Issue.Item>) {
        data.addAll(item)
        notifyDataSetChanged()
        //notifyItemRangeInserted(1, item.size)

    }

    fun addData(item: HomeBean.Issue.Item) {
        data.clear()
        notifyDataSetChanged()
        data.add(item)
        notifyItemInserted(0)

    }


}