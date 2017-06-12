package com.speedata.zhongqi.controller.act;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.application.AppConst;
import com.speedata.zhongqi.bean.webServiceBean.SjcjCXBeanClass;
import com.speedata.zhongqi.controller.adapter.CommonAdapter;
import com.speedata.zhongqi.controller.adapter.ViewHolder;
import com.speedata.zhongqi.view.NumberInput;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
@EActivity(R.layout.act_customercollect)
public class CustomerCollectAct extends FragActBase implements IBaseScanFragment {


    @ViewById
    TextView customercollectCustomerTv;
    @ViewById
    EditText edvSearchCustomercollect;
    @ViewById
    Button customer_search_btn;
    @ViewById
    ListView customercollectCollectlistviewLv;
    @ViewById
    TextView customercollectZPZTv;
    @ViewById
    TextView customercollectZSLTv;
    @ViewById
    TextView customercollectZMYTv;
    @ViewById
    Button customercollectCollectBtn;
    @ViewById
    Button customercollectBrowseBtn;
    @ViewById
    Button customercollectHomepageBtn;
    @ViewById
    TextView customercollect_tvPM;
    @ViewById
    TextView customercollect_tvSPXXID;
    @ViewById
    TextView customercollect_tvQMKC;
    @ViewById
    TextView customercollect_tvDJ;
    @ViewById
    ImageView tel_et_clearbtn;
    private List<SjcjCXBeanClass.SjcjCXBean> sjcj_cx;
    private static final int SjcjCXSuccess = 0;
    private static final int SjcjLRSuccess = 1;
    private static final int SjcjCheck = 2;
    List<SjcjCXBeanClass.SjcjCXBean> sjcjCXBeanList = new ArrayList<SjcjCXBeanClass.SjcjCXBean>();


    private ListView selectLV;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //一号多品选择
                case SjcjCXSuccess:
                    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    final View view = layoutInflater.inflate(R.layout.dia_title
                            , (ViewGroup) findViewById(R.id.dia_ll_main));

                    builder.setView(view);
//                    builder.setCancelable(false);
                    if (alertDialog2==null){
                        alertDialog2 = builder.create();
                    }
                    alertDialog2.setTitle(String.valueOf(msg.obj) + "(一号多书)");
                    alertDialog2.show();
                    selectLV = (ListView) alertDialog2.findViewById(R.id.dia_lv);
                    CommonAdapter commonAdapter = new CommonAdapter(mContext, sjcj_cx, R.layout.cus_dia_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.tv_PM, sjcj_cx.get(position).getPM());
                            helper.setText(R.id.tv_DJ, sjcj_cx.get(position).getDJ());
                            helper.setText(R.id.tv_QMKC, sjcj_cx.get(position).getQMKC());
                        }
                    };
                    selectLV.setAdapter(commonAdapter);
                    selectLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            setXml("CUS_SPXXID", sjcj_cx.get(position).getSPXXID());
                            customercollect_tvPM.setText("品名：" + sjcj_cx.get(position).getPM());
                            customercollect_tvSPXXID.setText(sjcj_cx.get(position).getTXM());
                            customercollect_tvQMKC.setText(sjcj_cx.get(position).getQMKC());
                            customercollect_tvDJ.setText(sjcj_cx.get(position).getDJ());
                            alertDialog2.cancel();
                        }
                    });
                    break;

                //采集成功更新界面
                case SjcjLRSuccess:
                    showToast("采集成功！");
                    SjcjCXBeanClass.SjcjCXBean sjcjCXBean = new SjcjCXBeanClass.SjcjCXBean();
                    sjcjCXBean.setCJSL(String.valueOf(msg.obj));
                    sjcjCXBean.setPM(String.valueOf(customercollect_tvPM.getText()));
                    sjcjCXBean.setDJ(String.valueOf(customercollect_tvDJ.getText()));
                    sjcjCXBean.setQMKC(String.valueOf(customercollect_tvQMKC.getText()));
                    sjcjCXBean.setSPXXID(String.valueOf(customercollect_tvSPXXID.getText()));
                    sjcjCXBean.setTXM(String.valueOf(edvSearchCustomercollect.getText()));
                    sjcjCXBeanList.add(0, sjcjCXBean);
                    CommonAdapter commonAdapter1 = new CommonAdapter(mContext, sjcjCXBeanList, R.layout.act_customercollect_lv_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.customercollect_lv_tvPM,
                                    sjcjCXBeanList.get(position).getPM());
                            helper.setText(R.id.customercollect_lv_tvDJ,
                                    sjcjCXBeanList.get(position).getDJ());
                            helper.setText(R.id.customercollect_lv_tvSPXXID,
                                    sjcjCXBeanList.get(position).getTXM());
                            helper.setText(R.id.customercollect_lv_tvCJSL,
                                    sjcjCXBeanList.get(position).getCJSL());
                            helper.setText(R.id.customercollect_lv_tvQMKC,
                                    sjcjCXBeanList.get(position).getQMKC());
                        }
                    };
                    customercollectCollectlistviewLv.setAdapter(commonAdapter1);
                    customercollect_tvSPXXID.setText("");
                    customercollect_tvQMKC.setText("");
                    customercollect_tvDJ.setText("");
                    customercollect_tvPM.setText("");
                    break;

                //检验
                case SjcjCheck:
                    collectNumber = String.valueOf(msg.obj);
                    AlertDialog.Builder builderCheck = new AlertDialog.Builder(mContext);
                    builderCheck.setTitle("检验结果");
                    builderCheck.setMessage("该客户已经进行过该条码商品的数据采集，是否继续？");
                    builderCheck.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GetSjcjLR(collectNumber);
                        }
                    });
                    builderCheck.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edvSearchCustomercollect.setText("");
                        }
                    });
                    alertDialog1 = builderCheck.create();
                    alertDialog1.setOnKeyListener(onKeyListenerSjcjCheck);
                    alertDialog1.show();

                    break;
            }
        }
    };
    private AlertDialog alertDialog;
    private NumberInput numberInput;
    private AlertDialog alertDialog1;
    private String collectNumber;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog2;


    @Click
    void customer_search_btn() {
        //搜索
        if (TextUtils.isEmpty(edvSearchCustomercollect.getText())) {
            showToast("请输入商品条形码");
        } else {
            GetSjcj_CX(String.valueOf(edvSearchCustomercollect.getText()));
        }

    }

    private void GetSjcj_CX(final String barcode) {
        showLoading("查询中...");
        params.clear();
        params.put("TXM", barcode);
        params.put("KHBH", getXml("CUS_KHBMID", "1"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.Sjcj_CX,
                        ipUrl,new MResponseListener() {
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
                                    SjcjCXBeanClass sjcjCXBeanClass = JSON.parseObject(callWebService,
                                            SjcjCXBeanClass.class);
                                    sjcj_cx = sjcjCXBeanClass.getSjcj_CX();
                                    if (sjcj_cx != null) {
                                        if (sjcj_cx.size() > 1) {
                                            Message message = new Message();
                                            message.what = SjcjCXSuccess;
                                            message.obj = barcode;
                                            handler.sendMessage(message);
                                            hideLoading();
                                        } else {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (alertDialog2!=null){
                                                        alertDialog2.cancel();
                                                    }
                                                    customercollect_tvPM.setText("品名：" + sjcj_cx.get(0).getPM());
                                                    customercollect_tvSPXXID.setText(sjcj_cx.get(0).getTXM());
                                                    customercollect_tvQMKC.setText(sjcj_cx.get(0).getQMKC());
                                                    customercollect_tvDJ.setText(sjcj_cx.get(0).getDJ());
                                                    setXml("CUS_SPXXID", sjcj_cx.get(0).getSPXXID());

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
        edvSearchCustomercollect.setFocusable(true);
        edvSearchCustomercollect.setFocusableInTouchMode(true);
        edvSearchCustomercollect.requestFocus();
        customercollect_tvSPXXID.setText("");
        customercollect_tvQMKC.setText("");
        customercollect_tvDJ.setText("");
        customercollect_tvPM.setText("");
    }

    @Click
    void customercollectCollectBtn() {
        //采集
        String customercollect_tvPMText = String.valueOf(customercollect_tvPM.getText());
        if (customercollect_tvPMText.equals("品名:")||customercollect_tvPMText.equals("")) {
            showToast("请先进行商品查询！");
            return;
        }
//        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
//        final View view=layoutInflater.inflate(R.layout.number_input,null);
//        final View numberInput = view.findViewById(R.id.customer_numberinput);
        numberInput = new NumberInput(mContext);
        numberInput.backEditText().setOnKeyListener(onKeyListenerNumberInput);
        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerCollectAct.this);
        builder.setTitle("请输入采集数量：");
        numberInput.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                numberInput.etSelectAll();
                return false;
            }
        });
        builder.setView(numberInput);
        builder.setCancelable(false);
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                edvSearchCustomercollect.setText("");
                dismissDialog();
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Positive(numberInput);
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setOnKeyListener(onKeyListenerAl);
    }

    private void Positive(NumberInput numberInput) {
        String etGetText = numberInput.etGetText();
        final String collectNum = String.valueOf(etGetText);


        if (TextUtils.isEmpty(collectNum)) {
            showToast("请填写采集数量！");
            numberInput.etSelectAllHideInput();
            preventDismissDialog();
            return;
        }
        int number= Integer.parseInt(collectNum);
        if (number==0){
            showToast("采集数不能为0");
            numberInput.etSelectAllHideInput();
            preventDismissDialog();
            return;
        }else {
            showLoading("校验中...");
            params.clear();
            params.put("Spxxid", getXml("CUS_SPXXID", "1"));
            params.put("KHBMID", getXml("CUS_KHBMID", "1"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WebService.getInstance().callWebService(params, APIMethod.Sjcj_Check
                            , ipUrl,new MResponseListener() {
                                @Override
                                public void onSuccess(MResponse response) {
                                    String callWebService = String
                                            .valueOf(response.data);
                                    if (callWebService.equals("该客户已经进行过该条码商品的数据采集，是否继续？")) {
                                        Message message = new Message();
                                        message.what = SjcjCheck;
                                        message.obj = collectNum;
                                        handler.sendMessage(message);
                                    } else {
                                        GetSjcjLR(collectNum);
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
            dismissDialog();
        }
    }

    private void GetSjcjLR(final String collectNum) {
        showLoading("采集中...");
        params.clear();
        params.put("Khid", getXml("CUS_KHBMID", "1"));
        params.put("Spxxid", getXml("CUS_SPXXID", "1"));
        params.put("CJsl", collectNum);
        params.put("Czyid", getXml(AppConst.USERID, "1"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params,
                        APIMethod.Sjcj_Lr, ipUrl,new MResponseListener() {
                            @Override
                            public void onSuccess(MResponse response) {
                                String callWebService = String.valueOf(response.data);
                                if (callWebService.equals("采集成功")) {
                                    Message message = new Message();
                                    message.what = SjcjLRSuccess;
                                    message.obj = collectNum;
                                    handler.sendMessage(message);
                                    hideLoading();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast("采集失败！");
                                            hideLoading();
                                        }
                                    });
                                }
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
    void customercollectBrowseBtn() {
        openAct(CustomerBrowseAct.class, true);
    }

    @Click
    void customercollectHomepageBtn() {
        openAct(MainAct.class, true);
    }


    @AfterViews
    protected void main() {
        App.getInstance().addActivity(CustomerCollectAct.this);
        customercollectCustomerTv.setText(getXml("CUS_KHMC", "1"));
        edvSearchCustomercollect.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        setClearBtnListener(edvSearchCustomercollect, tel_et_clearbtn);
        edvSearchCustomercollect.setOnKeyListener(onKeyListener);
        builder = new AlertDialog.Builder(CustomerCollectAct.this);
        edvSearchCustomercollect.addTextChangedListener(watcher);
        customercollectHomepageBtn.setFocusable(false);
        customercollectBrowseBtn.setFocusable(false);
        customercollectCollectBtn.setFocusable(false);
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
            clearn();
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
        barcode = replaceStringNR(barcode);
        edvSearchCustomercollect.setText(barcode);
        GetSjcj_CX(barcode);
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
                    GetSjcj_CX(String.valueOf(edvSearchCustomercollect.getText()));
                    return true;
                }else if (keyCode==KeyEvent.KEYCODE_POUND){
                    customercollectCollectBtn();
                    return true;
                }
            }
            return false;
        }
    };

    private View.OnKeyListener onKeyListenerNumberInput = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_ENTER){
                    Positive(numberInput);
                    numberInput.etSetOne();
                    alertDialog.cancel();
                    return true;
                }else if (keyCode==KeyEvent.KEYCODE_BACK){
                    dismissDialog();
                    return true;
                }else if (keyCode==KeyEvent.KEYCODE_F4){
                    dismissDialog();
                    return true;
                }
            }
            return false;
        }
    };
    private DialogInterface.OnKeyListener onKeyListenerAl = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_ENTER){
                    Positive(numberInput);

                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                            .setFocusable(false);
                    alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                            .setFocusable(false);
                    numberInput.etSelectAllHideInput();
                    alertDialog.cancel();
                    return true;
                }else if (keyCode==KeyEvent.KEYCODE_BACK){
                    dismissDialog();
                    return true;
                }else if (keyCode==KeyEvent.KEYCODE_F4){
                    dismissDialog();
                    return true;
                }
            }
            return false;
        }

    };
    private DialogInterface.OnKeyListener onKeyListenerSjcjCheck = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_ENTER){
                    GetSjcjLR(collectNumber);
                    alertDialog1.cancel();
                    return true;
                }else if (keyCode==KeyEvent.KEYCODE_BACK){
                    alertDialog1.cancel();
                    return true;
                }
            }
            return false;
        }

    };

    /**
     * 关闭对话框
     */
    private void dismissDialog() {
        try {
            Field field = alertDialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(alertDialog, true);
        } catch (Exception e) {
        }
        alertDialog.dismiss();
    }

    /**
     * 通过反射 阻止关闭对话框
     */
    private void preventDismissDialog() {
        try {
            Field field = alertDialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            //设置mShowing值，欺骗android系统
            field.set(alertDialog, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_POUND){
            customercollectCollectBtn();
        }
        return super.onKeyDown(keyCode, event);
    }
}
