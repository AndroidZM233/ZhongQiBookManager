package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.bean.webServiceBean.SjcjLoadBeanClass;
import com.speedata.zhongqi.view.CustomTitlebar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import common.base.IBaseScanFragment;
import common.base.act.FragActBase;
import common.event.ViewMessage;
import common.http.MResponse;
import common.http.MResponseListener;
import common.utils.ScanUtil;
import common.webserice.WebService;

/**
 * Created by TER on 2016/6/22.
 */

@EActivity(R.layout.act_customercollect_first)
public class CustomerFirstAct extends FragActBase implements IBaseScanFragment{


    @ViewById
    EditText customercollectfirstInputEt;

    @ViewById
    Button customercollectfirstSureBtn;

    @ViewById
    Button customercollectfirstExitBtn;
    @ViewById
    CustomTitlebar titlebar;
    @ViewById
    ImageView tel_et_clearbtn;
    @Click
    void customercollectfirstSureBtn() {
        //确认
        String customerId = customercollectfirstInputEt.getText().toString();
        if (TextUtils.isEmpty(customerId)) {
            showToast("请输入客户编号！");
        } else {
            showLoading("校验中...");
            params.clear();
            params.put("KHBH", customerId);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WebService.getInstance().callWebService(params, APIMethod.Sjcj_Load
                            ,ipUrl, new MResponseListener() {
                                @Override
                                public void onSuccess(MResponse response) {
                                    String callWebService = String.valueOf(response.data);
                                    callWebService=replaceStringNR(callWebService);
                                    if (callWebService.equals("数据获取失败！")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showToast("数据获取失败！");
                                            }
                                        });
                                    } else {
                                        SjcjLoadBeanClass sjcjLoadBeanClass = JSON.parseObject(callWebService
                                                , SjcjLoadBeanClass.class);
                                        List<SjcjLoadBeanClass.SjcjLoadBean> sjcj_load = sjcjLoadBeanClass.getSjcj_Load();
                                        if (sjcj_load != null) {
                                            setXml("CUS_KHBMID", sjcj_load.get(0).getKHBMID());
                                            setXml("CUS_KHBH", sjcj_load.get(0).getKHBH());
                                            setXml("CUS_KHMC", sjcj_load.get(0).getKHMC());
                                            openAct(CustomerCollectAct.class, true);

                                        }
                                    }
                                    hideLoading();
                                }

                                @Override
                                public void onError(final MResponse response) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (response.msg != null) {
                                                showToast(response.msg.toString());
                                            } else {
                                                showToast("出错了！");
                                            }
                                            hideLoading();
                                        }
                                    });
                                }
                            });
                }
            }).start();
        }

    }

    @Click
    void customercollectfirstExitBtn() {
        openAct(MainAct.class, true);
    }


    @AfterViews
    protected void main() {
        App.getInstance().addActivity(CustomerFirstAct.this);
        initTitlebar();
        customercollectfirstInputEt.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        setClearBtnListener(customercollectfirstInputEt, tel_et_clearbtn);
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
        }, "客户采集", null);
    }

    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }

    @Override
    public void onGetBarcode(String barcode) {
        barcode=replaceStringNR(barcode);
        customercollectfirstInputEt.setText(barcode);
    }

    private ScanUtil scanUtil;

    @Override
    protected void onResume() {
        super.onResume();
        ipUrl=getXml("IP","");
        scanUtil = new ScanUtil(this);
        scanUtil.setOnScanListener(new ScanUtil.OnScanListener() {

            @Override
            public void getBarcode(String barcode) {
                onGetBarcode(barcode);
            }
        });
    }

    @Override
    public void onPause() {
        scanUtil.stopScan();
        super.onPause();
    }
}
