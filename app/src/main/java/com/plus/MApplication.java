package com.plus;


import com.common.basic.BaseApplication;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * description:
 * author:  周昇辰
 * date:2019/4/1
 */


public class MApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

  /*      if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);*/

        CrashReport.initCrashReport(getApplicationContext(), "5e46d517a0", true);
    }
}
