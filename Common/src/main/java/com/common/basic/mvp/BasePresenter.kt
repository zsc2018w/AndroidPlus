package com.common.basic.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * description:
 * author:  周昇辰
 * date:2019/4/2
 */

 abstract class BasePresenter<V : IBaseView>  : IBasePresenter<V>{

    private var mViewReference: Reference<V>? = null

    private var mCompositeDisposable: CompositeDisposable? = null

    override var mRootView: V? = null
        get() =
            if(isAttachView) {
               this.mViewReference?.get()
            } else null

    override val isAttachView: Boolean
        get() {
            val b = this.mViewReference != null && this.mViewReference!!.get() != null
            return b
        }

    override fun attachView(view: V) {
        mViewReference=WeakReference<V>(view)
    }

    override fun detachView() {
       if(isAttachView){
           mViewReference?.clear()
           mViewReference=null
           mRootView=null
       }

    }

    override fun addDisposable(disposable: Disposable) {
        mCompositeDisposable?.add(disposable)
                ?: CompositeDisposable().let {
                    it.add(disposable)
                    mCompositeDisposable = it
                }
    }

    override fun removeDisposable() {
        mCompositeDisposable?.let {
            it.dispose()
            it.clear()
            mCompositeDisposable=null
        }
    }


}