package com.common.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;

import com.common.basic.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class AppManager {

	/*private static Stack<Activity> activityStack;

	private AppManager() {
	}

	private static class ManagerHolder {
		private static final AppManager instance = new AppManager();
	}

	*//**
	 * 单一实例
	 *//*
	public static AppManager getAppManager() {
		return ManagerHolder.instance;
	}

	*//**
	 * 添加Activity到堆栈
	 *//*
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
		Log.d("AppManager","添加"+activity.getClass().getName());
	}

	*//**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 *//*
	public Activity currentActivity() {
		if (activityStack == null || activityStack.size() == 0) {
			return null;
		}
		return activityStack.lastElement();
	}

	*//**
	 * 结束指定的Activity
	 *//*
	public void finishActivity(Activity activity) {
		if (activityStack == null || activityStack.size() == 0) {
			return;
		}
		if (activity != null) {
			activityStack.remove(activity);
			Log.d("AppManager", "finishActivity 关闭" + activity.getClass().getName());
		}
	}

	*//**
	 * 退出到主界面MainActivity
	 * @param me 当前界面的Activity
	 *//*
	public void backToMainActivity(Activity me, Class<BaseActivity> mClass){
		List<Activity> removeList = new ArrayList<>();
		Iterator<Activity> iterator = activityStack.iterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if(activity.getClass().equals(mClass)){
				//保留MainActivity
			}else if(activity.getClass().equals(me.getClass())){
				//保留me自己
			}else{
				iterator.remove();
				removeList.add(activity);
			}
		}
		for(Activity activity:removeList){
			if(activity != null){
				activity.finish();
				Log.d("AppManager", "backToMainActivity 关闭" + activity.getClass().getName());
			}
		}
	}



	*//**
	 * 退出到指定Activity
	 * @param me 当前界面的Activity
	 *//*
	public void backPointActivity(Class<?> cls, Activity me){
		List<Activity> removeList = new ArrayList<>();
		Iterator<Activity> iterator = activityStack.iterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if(activity.getClass().equals(cls)){
				//保留MainActivity
			}else if(activity.getClass().equals(me.getClass())){
				//保留me自己
			}else{
				iterator.remove();
				removeList.add(activity);
			}
		}
		for(Activity activity:removeList){
			if(activity != null){
				activity.finish();
				Log.d("AppManager", "backToMainActivity 关闭" + activity.getClass().getName());
			}
		}
	}

	*//**
	 * 退出到指定Activity,但保留MainActivity
	 * @param me 当前界面的Activity
	 *//*
	public void backPointActivityKeepMain(Class<?> cls, Activity me,Class mainClass){
		List<Activity> removeList = new ArrayList<>();
		Iterator<Activity> iterator = activityStack.iterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if(activity.getClass().equals(cls)){
				//保留MainActivity
			}else if(activity.getClass().equals(me.getClass())){
				//保留me自己
			}else if(activity.getClass().equals(mainClass)){
				//保留MainActivity
			}else{
				iterator.remove();
				removeList.add(activity);
			}
		}
		for(Activity activity:removeList){
			if(activity != null){
				activity.finish();
				Log.d("AppManager", "backToMainActivity 关闭" + activity.getClass().getName());
			}
		}
	}

	*//**
	 * 结束指定类名的Activity
	 *//*
	public void finishActivitybyClass(Class<?> cls) {
		Activity removeActivity = null;
		Iterator<Activity> iterator = activityStack.iterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if (activity.getClass().equals(cls)) {
				iterator.remove();
				removeActivity = activity;
			}
		}
		if(removeActivity != null){
			removeActivity.finish();
			Log.d("AppManager", "finishActivitybyClass 关闭" + removeActivity.getClass().getName());
		}
	}

	*//**
	 * 返回指定类名的Activity
	 * @param cls
	 * @return
	 *//*
	public Activity findActivityByClass(Class<?> cls) {
		Iterator<Activity> iterator = activityStack.iterator();
		while (iterator.hasNext()) {
			Activity activity = iterator.next();
			if (activity.getClass().equals(cls)) {
				return activity;
			}
		}
		return null;
	}

	*//**
	 * 结束所有Activity
	 *//*
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			Log.d("AppManager", "位置"+i+"名称"+activityStack.get(i).getClass().getName());
		}
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			Log.d("AppManager", "结束个数" + activityStack.size() + "将要结束" + activityStack.get(i).getClass().getName());
			if (null != activityStack.get(i)) {
				Log.d("AppManager", "结束掉" + activityStack.get(i).getClass().getName());
				if(BaseActivity.class.isAssignableFrom(activityStack.get(i).getClass())){
					BaseActivity ba = (BaseActivity)(activityStack.get(i));
					//ba.finishOnly();
				}else{
					activityStack.get(i).finish();
				}
			}
		}
		activityStack.clear();
	}

	*//**
	 * 退出应用程序
	 *//*
	public void AppExit(Context context) {
		try {
			// 关闭所有界面
			finishAllActivity();
			if (VERSION.SDK_INT < VERSION_CODES.FROYO) {
				ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
				// 系统会将，该包下的 ，所有进程，服务，全部杀掉，就可以杀干净了
				activityMgr.restartPackage(context.getPackageName());
			} else {
				ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
				activityMgr.killBackgroundProcesses(context.getPackageCodePath());
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}*/
}