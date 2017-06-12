package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.view.CustomTitlebar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import common.base.act.FragActBase;
import common.event.ViewMessage;

@EActivity(R.layout.activity_ipsetting)
public class IPSetting extends FragActBase {
    @ViewById
    CustomTitlebar titlebar;
    @ViewById
    Button btn_ip;
    @ViewById
    EditText et_ip;
    @AfterViews
    protected void main() {
        initTitlebar();
        App.getInstance().addActivity(IPSetting.this);
        et_ip.setText(getXml("IP","http://121.69.42.34:12501/ZQTWebService.asmx"));
    }

    @Click
    void btn_ip(){
        String etText = et_ip.getText()+"";
        if (etText.equals("")){
            showToast("请输入IP地址");
            return;
        }
        setXml("IP",etText);
//        getIP();
        finish();
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
        }, "设置IP地址", null);
    }

    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }
}
