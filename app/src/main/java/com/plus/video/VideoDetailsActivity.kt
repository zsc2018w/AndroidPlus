package com.plus.video


import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.util.Log
import android.util.LogPrinter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.common.basic.BaseActivity
import com.common.utils.CleanLeakUtils
import com.common.utils.StatusBarUtil
import com.plus.R
import com.plus.core.Constants
import com.plus.main.bean.HomeBean
import com.plus.utils.Log
import com.plus.utils.Log1
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import kotlinx.android.synthetic.main.activity_video_details.*


class VideoDetailsActivity : BaseActivity() ,VideoContract.View{

    private lateinit var itemData: HomeBean.Issue.Item
    private var orientationUtils: OrientationUtils? = null

    private var isTransition: Boolean = false

    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    private var transition:Transition?=null
    private var itemList =ArrayList<HomeBean.Issue.Item>()
    private val mAdapter by lazy { VideoDetailAdapter(this,itemList) }

    companion object {
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"

        fun startActivity(activity:Activity,itemInfo:HomeBean.Issue.Item,isTransition:Boolean){
            val intent= Intent(activity,VideoDetailsActivity::class.java)
            intent.putExtra(Constants.VIDE0_DETAIL_DATA,itemInfo)
            intent.putExtra(TRANSITION,isTransition)
            activity.startActivity(intent)
        }
    }

    val mPresenter:VideoPresenter by lazy {
        VideoPresenter()
    }
    override fun getLayoutId()= R.layout.activity_video_details

    override fun setVideo(url: String) {
        mVideoView.setUp(url, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()
    }

    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        itemData = itemInfo
        mAdapter.addData(itemInfo)
        // 请求相关的最新等视频
        mPresenter.requestReletedVideo(itemInfo.data?.id?:0)
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log("11",content = "videoDetail--->onNewIntent")
        initData(intent)
        loadVideoInfo()
     //   initView()
    }

    override fun setBackground(url: String) {
        val requestOptions=RequestOptions()
        requestOptions
                .centerCrop()
                .format(DecodeFormat.PREFER_ARGB_8888)
        Glide.with(this).load(url).apply(requestOptions).into(mVideoBackground)
    }

    override fun initView() {
      /*  StatusBarUtil.darkMode(this,false)
        StatusBarUtil.immersive(this, Color.parseColor("#000000"))*/
        mPresenter.attachView(this)
        setPresenter(mPresenter)
        initData(intent)
        initTransition()
        initVideoViewConfig()
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            loadVideoInfo()
            mRefreshLayout.finishRefresh(2000)
        }

    }

     fun initData(intent:Intent?) {
         itemData = intent?.getSerializableExtra(Constants.VIDE0_DETAIL_DATA) as HomeBean.Issue.Item
         isTransition = intent?.getBooleanExtra(TRANSITION, false)
         Log(content = "initData")
       // saveWatchVideoHistoryInfo(itemData)
    }
    private fun initVideoViewConfig(){
        //设置旋转
       orientationUtils= OrientationUtils(this,mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto=true
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).load(itemData.data?.cover?.feed).into(imageView)
        mVideoView.thumbImageView=imageView


        mVideoView.setStandardVideoAllCallBack(object : VideoListener {

            override fun onPrepared(url: String, vararg objects: Any) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils?.isEnable = true
                isPlay = true
            }
            override fun onPlayError(url: String, vararg objects: Any) {
                super.onPlayError(url, *objects)
                showToast("播放失败")
            }

            override fun onQuitFullscreen(url: String, vararg objects: Any) {
                super.onQuitFullscreen(url, *objects)
                //列表返回的样式判断
                orientationUtils?.backToProtVideo()
            }

        })

        mVideoView.backButton.setOnClickListener{
            onBackPressed()
        }
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mVideoView.startWindowFullscreen(this, true, true)
        }
        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }

        })

    }

    override fun setRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        Log.d("httpx","222"+itemList.size)
        mAdapter.addData(itemList)
        this.itemList = itemList
    }


    private fun initTransition(){
        if(isTransition&&Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()

        }else{
            loadVideoInfo()
        }
    }
    private fun getCurPlay(): GSYVideoPlayer {
        return if (mVideoView.fullWindowPlayer != null) {
            mVideoView.fullWindowPlayer
        } else mVideoView
    }

    private fun loadVideoInfo(){
        Log(content = "loadVideoInfo 加载数据")
        mPresenter.loadVideoInfo(itemData)
    }

    override fun onResume() {
        super.onResume()
        getCurPlay().onVideoResume()
    }

    override fun onPause() {
        super.onPause()
        getCurPlay().onVideoPause()
    }

    override fun onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.releaseListener()
    }
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils)
        }
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (StandardGSYVideoPlayer.backFromWindowFull(this))
            return
        //释放所有
        mVideoView.setStandardVideoAllCallBack(null)
        GSYVideoPlayer.releaseAllVideos()
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
           // overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

            override fun onTransitionEnd(p0: Transition?) {
              //  Logger.d("onTransitionEnd()------")

                loadVideoInfo()
                transition?.removeListener(this)
            }

        })
    }



}
