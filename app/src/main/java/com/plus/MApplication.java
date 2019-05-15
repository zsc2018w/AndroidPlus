package com.plus;


import com.common.basic.BaseApplication;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

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

        Bmob.initialize(this, "4666abb36a74a96aa445547379de1c20");
    }
}
