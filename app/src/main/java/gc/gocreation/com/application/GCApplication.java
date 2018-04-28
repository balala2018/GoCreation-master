package gc.gocreation.com.application;

import android.app.Application;

/**
 * Created by 包子 on 2018/4/5
 * Function
 * 1.程序入口
 * 2.初始化工作
 * 3.为整个应用的其它模块提供上下文环境
 */

public class GCApplication extends Application{
    private static GCApplication mApplication=null;
    @Override
    public void onCreate(){
        super.onCreate();
        mApplication=this;
    }
    public static GCApplication getInstance(){
        return mApplication;
    }

}
