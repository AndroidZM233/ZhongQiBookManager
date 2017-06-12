package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.bean.webServiceBean.XsphBeanClass;
import com.speedata.zhongqi.controller.adapter.CommonAdapter;
import com.speedata.zhongqi.controller.adapter.ViewHolder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

import common.base.act.FragActBase;
import common.event.ViewMessage;
import common.http.MResponse;
import common.http.MResponseListener;
import common.webserice.WebService;

/**
 * Created by TER on 2016/6/22.
 */

@EActivity(R.layout.act_salesrank)
public class SaleSrankAct extends FragActBase {


    @ViewById
    ListView listSearchResult;
    @ViewById
    Button salesrankWeekBtn;
    @ViewById
    Button salesrankMonthBtn;
    @ViewById
    Button salesrankSeasonBtn;
    @ViewById
    Button salesrankHomepageBtn;
    private static final int XsphSuccess = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case XsphSuccess:
                    CommonAdapter commonAdapter = new CommonAdapter(mContext, xsph, R.layout.act_salesrank_lv_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.salesrank_lv_tvPaiM
                                    , xsph.get(position).getXH());
                            helper.setText(R.id.salesrank_lv_tvSPTM
                                    , xsph.get(position).getTXM());
                            helper.setText(R.id.salesrank_lv_tvPinM
                                    , xsph.get(position).getPM());
                            helper.setText(R.id.salesrank_lv_tvSL
                                    , xsph.get(position).getXSSL());
                            helper.setText(R.id.salesrank_lv_tvDJ
                                    , xsph.get(position).getDJ());
                        }
                    };
                    listSearchResult.setAdapter(commonAdapter);
                    break;
            }
        }
    };
    private List<XsphBeanClass.XsphBean> xsph;

    @Click
    void salesrankWeekBtn() {
        //周排行
        GetXsph("周");
        salesrankWeekBtn.setBackgroundColor(Color.parseColor("#EA0505"));
        salesrankMonthBtn.setBackgroundColor(Color.parseColor("#ffffff"));
        salesrankSeasonBtn.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Click
    void salesrankMonthBtn() {
        //月排行#
        GetXsph("月");
        salesrankWeekBtn.setBackgroundColor(Color.parseColor("#ffffff"));
        salesrankMonthBtn.setBackgroundColor(Color.parseColor("#EA0505"));
        salesrankSeasonBtn.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Click
    void salesrankSeasonBtn() {
        //季排行
        GetXsph("季");
        salesrankWeekBtn.setBackgroundColor(Color.parseColor("#ffffff"));
        salesrankMonthBtn.setBackgroundColor(Color.parseColor("#ffffff"));
        salesrankSeasonBtn.setBackgroundColor(Color.parseColor("#EA0505"));
    }

    private void GetXsph(String Type) {
        showLoading("查询中...");
        params.clear();
        params.put("Type", Type);
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.Xsph
                        ,ipUrl, new MResponseListener() {
                            @Override
                            public void onSuccess(MResponse response) {
                                String callWebService = String.valueOf(response.data);
                                callWebService=replaceStringNR(callWebService);
                                if (callWebService.equals("未找到相应的统计数据！")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast("未找到相应的统计数据！");
                                        }
                                    });
                                } else {
                                    XsphBeanClass xsphBeanClass = JSON.parseObject(callWebService
                                            , XsphBeanClass.class);
                                    xsph = xsphBeanClass.getXsph();
                                    if (xsph != null) {
                                        Message message = new Message();
                                        message.what = XsphSuccess;
                                        handler.sendMessage(message);
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

    @Click
    void salesrankHomepageBtn() {
        openAct(MainAct.class, true);
    }

    @AfterViews
    protected void main() {
        App.getInstance().addActivity(SaleSrankAct.this);
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

    @Override
    protected void onResume() {
        super.onResume();
        ipUrl=getXml("IP","");
    }
}
