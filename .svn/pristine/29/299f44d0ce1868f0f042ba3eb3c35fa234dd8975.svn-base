package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.bean.webServiceBean.GetHWMCBeanClass;
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

import static common.utils.LogUtil.DEGUG_MODE;

/**
 * Created by TER on 2016/6/20.
 */
@EActivity(R.layout.goodscheckfirst_activity)
public class CheckAct extends FragActBase implements IBaseScanFragment{
    private static final String TAG = DEGUG_MODE ? "CheckAct" : CheckAct.class.getSimpleName();
    private static final boolean debug = true;
    @ViewById
    EditText goodscheckfirstInputEt;
    @ViewById
    Button goodscheckfirstSureBtn;
    @ViewById
    Button goodscheckfirstExitBtn;
    @ViewById
    CustomTitlebar titlebar;
    @ViewById
    ImageView tel_et_clearbtn;
    private String checkLocation;

    @Override
    protected void initTitlebar() {
        titlebar.setTitlebarStyle(CustomTitlebar.TITLEBAR_STYLE_NORMAL);
        titlebar.setAttrs(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }, "日常盘点", null);
    }


    @Click
    void goodscheckfirstExitBtn() {
        openAct(MainAct.class, true);
    }

    ;


    @AfterViews
    protected void main() {
        App.getInstance().addActivity(CheckAct.this);
        initTitlebar();
        setSwipeEnable(false);
        goodscheckfirstInputEt.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        setClearBtnListener(goodscheckfirstInputEt, tel_et_clearbtn);
        ipUrl=getXml("IP","");
    }

    @Click
    void goodscheckfirstSureBtn() {
        checkLocation = goodscheckfirstInputEt.getText().toString();
        if (checkLocations(checkLocation)) {
            showLoading("校验中");

            location();
        }

    }

    private void location() {
        params.clear();
        params.put("HWBH", checkLocation);
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.GetHWMC
                        , ipUrl,new MResponseListener() {
                            @Override
                            public void onSuccess(MResponse response) {
                                String callWebService = String.valueOf(response.data);
                                callWebService=replaceStringNR(callWebService);
                                if (callWebService.equals("输入的货位不存在！")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast("输入的货位不存在！");
                                        }
                                    });
                                    goodscheckfirstInputEt.setFocusable(true);
                                    goodscheckfirstInputEt.setFocusableInTouchMode(true);
                                    goodscheckfirstInputEt.requestFocus();
                                } else {
                                    GetHWMCBeanClass getHWMCBeanClass = JSON.parseObject(callWebService, GetHWMCBeanClass.class);
                                    List<GetHWMCBeanClass.GetHWMCBean> getHWMC = getHWMCBeanClass.getGetHWMC();
                                    if (getHWMC != null) {
                                        setXml("HWBH", getHWMC.get(0).getHWBH());
                                        setXml("HWBMID", getHWMC.get(0).getHWBMID());
                                        setXml("WLBMID", getHWMC.get(0).getWLBMID());
                                    }
                                    openAct(CheckDetailAct.class, true);
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
                                goodscheckfirstInputEt.setFocusable(true);
                                goodscheckfirstInputEt.setFocusableInTouchMode(true);
                                goodscheckfirstInputEt.requestFocus();
                            }
                        });
            }
        }).start();
    }


    private boolean checkLocations(String checkLocation) {
        if (checkLocation != null ) {
            return true;
        } else {

            return false;
        }
    }


    @Override
    protected Context regieterBaiduBaseCount() {
        return null;
    }


    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }
    @Override
    public void onGetBarcode(String barcode) {
        barcode=replaceStringNR(barcode);
        goodscheckfirstInputEt.setText(barcode);
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
