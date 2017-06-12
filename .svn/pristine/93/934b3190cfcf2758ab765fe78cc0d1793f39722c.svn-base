/*
 *
 * @author Echo
 * @created 2016.6.3
 * @email bairu.xu@speedatagroup.com
 * @version $version
 *
 */


package com.speedata.zhongqi;

import android.app.Activity;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.application.AppConst;
import com.speedata.zhongqi.bean.LoginBean;
import com.speedata.zhongqi.model.HttpModel;

import java.util.LinkedList;
import java.util.List;

import common.base.BaseApplication;
import common.base.dialog.ToastUtils;
import common.utils.NetUtil;
import common.utils.SharedXmlUtil;

public class App extends BaseApplication implements AppConst {

    private static App instance = null;

    public static App getInstance() {
        if (null == instance) {
            instance = new App();
        }
        return instance;

    }

    private List<Activity> activityList = new LinkedList<Activity>();

    public App() {
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }
    //遍历所有Activity并finish

    //结束整个应用程序
    public void exit() {

        //遍历 链表，依次杀掉各个Activity
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        //杀掉，这个应用程序的进程，释放 内存
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NetUtil.init(this);
        HttpModel.getInstance().init(this);
        ToastUtils.init(this, new Handler());
    }

    LoginBean loginBean;

    public LoginBean getLoginBean() {
        if (loginBean == null) {
            String loginBeanstr = SharedXmlUtil.getInstance(this).read("loginBean", "");
            if (loginBean != null) {
                loginBean = JSON.parseObject(loginBeanstr, LoginBean.class);
            }
        }
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
        if (loginBean != null) {
            SharedXmlUtil.getInstance(this).write("loginBean", JSON.toJSONString(loginBean));
        }
    }

    /**
     * 异常退出处理
     */
    @Override
    protected void onAppExceptionDestroy() {

    }

}
