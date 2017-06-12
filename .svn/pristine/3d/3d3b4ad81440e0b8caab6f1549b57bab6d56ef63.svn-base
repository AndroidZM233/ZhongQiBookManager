package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.widget.Button;

import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import common.base.act.FragActBase;
import common.event.ViewMessage;

/**
 * Created by TER on 2016/6/22.
 */

@EActivity(R.layout.act_aboutus)
public class AboutUsAct extends FragActBase {
    @ViewById
    Button aboutusBackBtn;
    @Click
    void aboutusBackBtn(){
        finish();
    }

   @AfterViews
   protected void main(){
       App.getInstance().addActivity(AboutUsAct.this);
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
