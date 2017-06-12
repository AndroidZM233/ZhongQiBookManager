package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.crash.utils.SysInfoUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import common.base.act.FragActBase;
import common.event.ViewMessage;

@EActivity(R.layout.activity_version)
public class VersionActivity extends FragActBase {
    @ViewById
    Button version_back_btn;
    @ViewById
    TextView device_id;
    @ViewById
    TextView version_tx;

    @Click
    void version_back_btn(){
        finish();
    }

    @AfterViews
    protected void main(){
        App.getInstance().addActivity(VersionActivity.this);
        version_tx.setText("版本号:" + SysInfoUtil.getVersionName(this));
        device_id.setText("设备号:" + SysInfoUtil.getDeviceId(this));
    }

    @Override
    protected Context regieterBaiduBaseCount() {
        return null;
    }

    @Override
    protected void initTitlebar() {

    }

    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }

}
