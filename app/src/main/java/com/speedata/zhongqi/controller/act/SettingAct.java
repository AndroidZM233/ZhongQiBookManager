/*
 *
 * @author Echo
 * @created 2016.6.3
 * @email bairu.xu@speedatagroup.com
 * @version v1.0
 *
 */

package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.crash.utils.SysInfoUtil;
import com.speedata.zhongqi.view.CustomTitlebar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import common.base.act.FragActBase;
import common.event.ViewMessage;
import common.updateversion.UpdateVersion;

@EActivity(R.layout.act_setting)
public class SettingAct extends FragActBase {


//    @ViewById
//    TextView settingBackTv;
    @ViewById
    Button settingCheckversionBtn;
    @ViewById
    Button settingAboutusBtn;
    @ViewById
    CustomTitlebar titlebar;
    @ViewById
    TextView version_tx;
    @ViewById
    Button setting_version_btn;
    @ViewById
    Button setting_ipLocation_btn;


    @Click
    void settingCheckversionBtn(){
        UpdateVersion updateVersion = new UpdateVersion(mContext);
        updateVersion.startUpdate();
    }

    @Click
    void settingAboutusBtn(){
        openAct(AboutUsAct.class,true);
    }


    @Click
    void setting_version_btn(){
        openAct(VersionActivity.class,true);
    }


    @Click
    void setting_ipLocation_btn(){
        openAct(IPSetting.class,true);
    }

    @AfterViews
    protected void main() {
        initTitlebar();
        App.getInstance().addActivity(SettingAct.this);
//        version_tx.setText("版本号:" + SysInfoUtil.getVersionName(this));
    }

    @Override
    protected Context regieterBaiduBaseCount() {
        return null;
    }

    @Override
    protected void initTitlebar() {

        titlebar.setTitlebarStyle(CustomTitlebar.TITLEBAR_STYLE_NORMAL);
        titlebar.setAttrs(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, "设置", null);
    }

    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }



}
