package com.speedata.zhongqi.controller.act;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.application.AppConst;
import com.speedata.zhongqi.bean.webServiceBean.GetSpxxForPdBeanClass;
import com.speedata.zhongqi.controller.adapter.CommonAdapter;
import com.speedata.zhongqi.controller.adapter.ViewHolder;
import com.speedata.zhongqi.view.NumberInput;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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

@EActivity(R.layout.act_goodscheck)
public class CheckDetailAct extends FragActBase implements IBaseScanFragment {
    @ViewById
    TextView goodscheckLocationTv;
    @ViewById
    EditText goodscheckInputEt;
    @ViewById
    Button goodscheckSearchBtn;
    @ViewById
    TextView goodscheckGoodsnameTv;

    @ViewById
    TextView goodscheckPriceTv;
    @ViewById
    TextView goodscheckEditionTv;

    @ViewById
    Button goodscheckSureBtn;
    @ViewById
    ListView goodscheckChecklistviewLv;
    @ViewById
    Button goodscheckBrowseBtn;
    @ViewById
    Button goodscheckPrintBtn;
    @ViewById
    Button goodscheckHomepageBtn;
    @ViewById
    NumberInput check_numberinput;
    @ViewById
    TextView goodscheckCurrentinventoryTv;
    @ViewById
    TextView goodscheckPublicationdateTv;
    @ViewById
    TextView goodscheckCheckednumberTv;
    @ViewById
    EditText goodscheckInventorycountTv;
    @ViewById
    ImageView tel_et_clearbtn;
    private ListView selectLV;
    private static final int GetSpxxForPdMany = 0;
    private static final int PDLRSuccess = 1;
    List<GetSpxxForPdBeanClass.GetSpxxForPdBean> pdlrSuccessList =
            new ArrayList<GetSpxxForPdBeanClass.GetSpxxForPdBean>();
    private List<GetSpxxForPdBeanClass.GetSpxxForPdBean> getSpxxForPd;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //一号多品选择
                case GetSpxxForPdMany:
                    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    final View view = layoutInflater.inflate(R.layout.dia_title
                            , (ViewGroup) findViewById(R.id.dia_ll_main));

                    builder.setView(view);
//                    builder.setCancelable(false);
                    if (alertDialog==null){
                        alertDialog = builder.create();
                    }
                    alertDialog.setTitle(String.valueOf(msg.obj) + "(一号多书)");
                    alertDialog.show();
                    selectLV = (ListView) alertDialog.findViewById(R.id.dia_lv);
                    CommonAdapter commonAdapter = new CommonAdapter(mContext, getSpxxForPd, R.layout.dia_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.tv_PM, getSpxxForPd.get(position).getPM());
                            helper.setText(R.id.tv_DJ, getSpxxForPd.get(position).getDJ());
                            helper.setText(R.id.tv_CBNY, getSpxxForPd.get(position).getQMKC());
                        }
                    };
                    selectLV.setAdapter(commonAdapter);
                    selectLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            setXml("SPXXID", getSpxxForPd.get(position).getSPXXID());
                            goodscheckCurrentinventoryTv.setText(getSpxxForPd.get(position).getQMKC());
                            goodscheckPriceTv.setText(getSpxxForPd.get(position).getDJ());
                            goodscheckEditionTv.setText(getSpxxForPd.get(position).getBB());
                            goodscheckPublicationdateTv.setText(getSpxxForPd.get(position).getCBNY());
                            goodscheckCheckednumberTv.setText(getSpxxForPd.get(position).getYPSL());
                            goodscheckGoodsnameTv.setText("品名：" + getSpxxForPd.get(position).getPM());
                            check_numberinput.etFocusable();
                            alertDialog.cancel();

                        }
                    });
                    break;

                //录入成功更新界面
                case PDLRSuccess:
                    showToast("录入成功！");
                    GetSpxxForPdBeanClass.GetSpxxForPdBean getSpxxForPdBean =
                            new GetSpxxForPdBeanClass.GetSpxxForPdBean();
                    getSpxxForPdBean.setPM(String.valueOf(goodscheckGoodsnameTv.getText()));
                    getSpxxForPdBean.setDJ(String.valueOf(goodscheckPriceTv.getText()));
                    getSpxxForPdBean.setBB(String.valueOf(goodscheckEditionTv.getText()));
                    String oldNumStr = String.valueOf(goodscheckCheckednumberTv.getText());
                    int oldNum = 0;
                    if (!TextUtils.isEmpty(oldNumStr)) {
                        oldNum = Integer.parseInt(oldNumStr);
                    }
                    int newNum = Integer.parseInt(String.valueOf(check_numberinput.etGetText()));
                    getSpxxForPdBean.setPDS(String.valueOf(newNum));
                    goodscheckCheckednumberTv.setText(String.valueOf(oldNum + newNum));
                    getSpxxForPdBean.setQMKC(String.valueOf(goodscheckCurrentinventoryTv.getText()));
                    pdlrSuccessList.add(0, getSpxxForPdBean);
                    CommonAdapter commonAdapter1 = new CommonAdapter(mContext, pdlrSuccessList, R.layout.act_goodscheck_lv_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.goodscheck_lv_tvPM, pdlrSuccessList
                                    .get(position).getPM());
                            helper.setText(R.id.goodscheck_lv_tvDJ, pdlrSuccessList
                                    .get(position).getDJ());
                            helper.setText(R.id.goodscheck_lv_tvBB, pdlrSuccessList
                                    .get(position).getBB());
                            helper.setText(R.id.goodscheck_lv_tvKC, pdlrSuccessList
                                    .get(position).getQMKC());
                            helper.setText(R.id.goodscheck_lv_tvYPSL, pdlrSuccessList
                                    .get(position).getPDS());
                        }
                    };
                    goodscheckChecklistviewLv.setAdapter(commonAdapter1);
//                    goodscheckInventorycountTv.setText("");
//                    goodscheckInputEt.setText("");
                    goodscheckGoodsnameTv.setText("");
                    goodscheckCurrentinventoryTv.setText("");
                    goodscheckPriceTv.setText("");
                    goodscheckEditionTv.setText("");
                    goodscheckPublicationdateTv.setText("");
                    goodscheckCheckednumberTv.setText("");
                    check_numberinput.etSetOne();
                    goodscheckInputEt.setFocusable(true);
                    goodscheckInputEt.setFocusableInTouchMode(true);
                    goodscheckInputEt.requestFocus();
                    goodscheckInputEt.findFocus();
                    hideInputMethod();
                    break;
            }
        }
    };
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;


    @Click
    void goodscheckSearchBtn() {
        //搜索
        if (TextUtils.isEmpty(goodscheckInputEt.getText())) {
            showToast("请输入商品条码");
        } else {
            GetSpxxForPdSearch(String.valueOf(goodscheckInputEt.getText()));
        }
    }

    private void GetSpxxForPdSearch(final String barcode) {
        showLoading("查询中...");

        params.clear();
        params.put("SPBH", barcode);
        params.put("HWID", getXml("HWBMID", "001"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.GetSpxxForPd
                        , ipUrl,new MResponseListener() {
                            @Override
                            public void onSuccess(MResponse response) {
                                String callWebService = String.valueOf(response.data);
                                callWebService=replaceStringNR(callWebService);
                                if (callWebService.equals("数据获取失败！")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast("数据获取失败！");
                                            clearn();
                                        }
                                    });
                                } else {
                                    GetSpxxForPdBeanClass getSpxxForPdBeanClass =
                                            JSON.parseObject(callWebService, GetSpxxForPdBeanClass.class);
                                    getSpxxForPd = getSpxxForPdBeanClass.getGetSpxxForPd();
                                    Message message = new Message();
                                    if (getSpxxForPd != null) {
                                        if (getSpxxForPd.size() > 1) {
                                            message.what = GetSpxxForPdMany;
                                            message.obj = barcode;
                                            handler.sendMessage(message);
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (alertDialog!=null){
                                                        alertDialog.cancel();
                                                    }
                                                    setXml("SPXXID", getSpxxForPd.get(0).getSPXXID());
                                                    goodscheckCurrentinventoryTv.setText(getSpxxForPd.get(0).getQMKC());
                                                    goodscheckPriceTv.setText(getSpxxForPd.get(0).getDJ());
                                                    goodscheckEditionTv.setText(getSpxxForPd.get(0).getBB());
                                                    goodscheckPublicationdateTv.setText(getSpxxForPd.get(0).getCBNY());
                                                    goodscheckCheckednumberTv.setText(getSpxxForPd.get(0).getYPSL());
                                                    goodscheckGoodsnameTv.setText("品名：" + getSpxxForPd.get(0).getPM());
                                                    check_numberinput.etFocusable();
                                                }
                                            });
                                        }
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

    private void clearn() {
        goodscheckInputEt.setFocusable(true);
        goodscheckInputEt.setFocusableInTouchMode(true);
        goodscheckInputEt.requestFocus();
        goodscheckGoodsnameTv.setText("");
        goodscheckCurrentinventoryTv.setText("");
        goodscheckPriceTv.setText("");
        goodscheckEditionTv.setText("");
        goodscheckPublicationdateTv.setText("");
        goodscheckCheckednumberTv.setText("");
        check_numberinput.etSetOne();
    }

    @Click
    void goodscheckSureBtn() {
        //确认
        if (TextUtils.isEmpty(String.valueOf(check_numberinput.etGetText()))) {
            showToast("盘点数不能为空");
            return;
        }
        if (String.valueOf(check_numberinput.etGetText()).equals("0")){
            showToast("盘点数不能为0");
            check_numberinput.etSelectAllHideInput();
            return;
        }
        if (goodscheckGoodsnameTv.getText().equals("品名:")||
                goodscheckGoodsnameTv.getText().equals("")){
            showToast("请先查询商品信息");
            return;
        }
        showLoading("录入中...");
        params.clear();
        params.put("HWID", getXml("HWBMID", "1"));
        params.put("Wlbmid", getXml("WLBMID", "1"));
        params.put("Czyid", getXml(AppConst.USERID, "1"));
        params.put("Spxxid", getXml("SPXXID", "1"));
        params.put("Pcsl", String.valueOf(check_numberinput.etGetText()));
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.PDLR,
                        ipUrl,new MResponseListener() {
                    @Override
                    public void onSuccess(MResponse response) {
                        String callWebService = String.valueOf(response.data);
                        if (callWebService.equals("录入成功")) {
                            Message msg = new Message();
                            msg.what = PDLRSuccess;
                            handler.sendMessage(msg);
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("录入失败！");
                                }
                            });
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
    void goodscheckBrowseBtn() {
        //进入浏览页
        openAct(CheckBrowseAct.class, true);
    }

    @Click
    void goodscheckPrintBtn() {
        //打印
//        showLoading("打印中...");
//        params.clear();
//        params.put("HWID", getXml("HWBMID", "1"));
//        params.put("CzyId", getXml(AppConst.USERID, "1"));
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                WebService.getInstance().callWebService(params, APIMethod.PdPrint
//                        , new MResponseListener() {
//                            @Override
//                            public void onSuccess(MResponse response) {
//                                String callWebService = String.valueOf(response.data);
//                                if (callWebService.equals("打印成功！")) {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            showToast("打印成功！");
//                                        }
//                                    });
//                                } else {
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            showToast("打印失败！");
//                                        }
//                                    });
//                                }
//                                hideLoading();
//                            }
//
//                            @Override
//                            public void onError(final MResponse response) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (response.msg != null) {
//                                            showToast(response.msg.toString());
//                                        } else {
//                                            showToast("出错了！");
//                                        }
//                                        hideLoading();
//                                    }
//                                });
//                            }
//                        });
//            }
//        }).start();
    }

    @Click
    void goodscheckHomepageBtn() {
        openAct(MainAct.class, true);
    }


    @AfterViews
    protected void main() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        App.getInstance().addActivity(CheckDetailAct.this);
        goodscheckLocationTv.setText(getXml("HWBH", "001"));
        goodscheckInputEt.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        setClearBtnListener(goodscheckInputEt, tel_et_clearbtn);
        goodscheckInputEt.setOnKeyListener(onKeyListener);
        check_numberinput.backEditText().setOnKeyListener(onKeyListenerSure);
        builder = new AlertDialog.Builder(CheckDetailAct.this);
        goodscheckInputEt.addTextChangedListener(watcher);
    }
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            goodscheckGoodsnameTv.setText("");
            goodscheckCurrentinventoryTv.setText("");
            goodscheckPriceTv.setText("");
            goodscheckEditionTv.setText("");
            goodscheckPublicationdateTv.setText("");
            goodscheckCheckednumberTv.setText("");
            check_numberinput.etSetOne();
        }
    };


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
    public void onGetBarcode(String barcode) {
        clearn();
        barcode=replaceStringNR(barcode);
        goodscheckInputEt.setText(barcode);
        GetSpxxForPdSearch(barcode);
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

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_ENTER){
                    goodscheckSearchBtn();

                    return true;
                }
            }
            return false;
        }
    };

    private View.OnKeyListener onKeyListenerSure = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_ENTER){

                    goodscheckSureBtn();
                    goodscheckSureBtn.setFocusable(false);
                    goodscheckBrowseBtn.setFocusable(false);
                    goodscheckHomepageBtn.setFocusable(false);
                    check_numberinput.etSelectAllHideInput();
                    return true;
                }
            }
            return false;
        }
    };
}