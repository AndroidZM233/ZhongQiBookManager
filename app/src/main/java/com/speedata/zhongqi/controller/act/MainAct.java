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
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.AppConst;
import com.speedata.zhongqi.view.CustomTitlebar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import common.base.act.FragActBase;
import common.base.dialog.ToastUtils;
import common.event.ViewMessage;

import static common.utils.LogUtil.DEGUG_MODE;

@EActivity(R.layout.act_main)
public class MainAct extends FragActBase {
    private static final String TAG = DEGUG_MODE ? "MainAct" : MainAct.class.getSimpleName();
    private static final boolean debug = true;

    @ViewById
    CustomTitlebar titlebar;
    @ViewById
    TextView num1;
    @ViewById
    TextView num2;
    @ViewById
    TextView num3;
    @ViewById
    TextView num4;
    @ViewById
    TextView num5;
    @ViewById
    TextView num6;
    @ViewById
    TextView textView;
    @ViewById
    TextView textView2;

    @Click
    void num1() {

        openAct(SearchAct.class, true);
    }
    @Click
    void num2() {

        openAct(CheckAct.class, true);
    }

    @Click
    void num3(){
        openAct(GoodsCollectAct.class,true);
    }

    @Click
    void num4(){
        openAct(CustomerFirstAct.class,true);
    }

    @Click
    void num5(){
        openAct(TrimAct.class,true);
    }

    @Click
    void num6(){
        openAct(SaleSrankAct.class,true);
    }



    @Click
    void textView(){
        setXml(AppConst.ISCHECKED,"false");
        openAct(LoginAct.class,true);
    }

    @Click
    void textView2(){
        openAct(SettingAct.class,true);
    }



    @AfterViews
    protected void main() {
        App.getInstance().addActivity(MainAct.this);
        initTitlebar();
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
                setXml("BACK","back");
                openAct(LoginAct.class,true);
            }
        }, "主界面", null);
//        ImageView imageButton= (ImageView) titlebar.findViewById(R.id.titlebar_left_btn);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }

    private long mkeyTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.ACTION_DOWN:
                if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                    mkeyTime = System.currentTimeMillis();
                    ToastUtils.showLong("再按一次退出");
                } else {
                    try {
                        pressHome();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
//            case KeyEvent.KEYCODE_VOLUME_DOWN:
//                openAct(AddPlanActivity.class, true);
//                overridePendingTransition(R.anim.to_up_anim, R.anim.none_anim);
//                return true;
//            case KeyEvent.KEYCODE_VOLUME_UP:
//                return true;
//            case KeyEvent.KEYCODE_VOLUME_MUTE:
//                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

