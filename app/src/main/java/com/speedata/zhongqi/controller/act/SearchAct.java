package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.bean.webServiceBean.GetSpxxBeanClass;
import com.speedata.zhongqi.controller.adapter.CommonAdapter;
import com.speedata.zhongqi.controller.adapter.ViewHolder;
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
import common.base.dialog.ToastUtils;
import common.event.ViewMessage;
import common.http.MResponse;
import common.http.MResponseListener;
import common.utils.ScanUtil;
import common.utils.StringUtil;
import common.webserice.WebService;

/**
 * Created by TER on 2016/6/22.
 */
@EActivity(R.layout.act_search)
public class SearchAct extends FragActBase implements IBaseScanFragment {
    @ViewById
    Button btn_search;

    @ViewById
    Button btPrint;

    @ViewById
    Button queryReturnBtn;

    @ViewById
    EditText edvSearch;

    @ViewById
    ListView listSearchResult;
    @ViewById
    ImageView tel_et_clearbtn;
    @ViewById
    TextView tv_noSP;
    @ViewById
    CustomTitlebar titlebar;
    private static final int GetSpxxSuccess = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GetSpxxSuccess:
                    tv_noSP.setVisibility(View.GONE);
                    listSearchResult.setVisibility(View.VISIBLE);
                    commonAdapter = new CommonAdapter(mContext, getSpxx, R.layout.act_search_lv_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.search_lv_tvPM,
                                    getSpxx.get(position).getPM());
                            helper.setText(R.id.search_lv_tvDJ,
                                    getSpxx.get(position).getDJ());
                            helper.setText(R.id.search_lv_tvSL,
                                    getSpxx.get(position).getQMKC());
                            helper.setText(R.id.search_lv_tvHWLC,
                                    getSpxx.get(position).getHWMC());
                        }
                    };
                    listSearchResult.setAdapter(commonAdapter);
                    edvSearch.setFocusable(true);
                    edvSearch.setFocusableInTouchMode(true);
                    edvSearch.requestFocus();
                    break;
            }
        }
    };
    private List<GetSpxxBeanClass.GetSpxxBean> getSpxx;
    private CommonAdapter commonAdapter;


    @Click
    void btn_search() {
        //做查询
        String barcode = String.valueOf(edvSearch.getText());
        if (TextUtils.isEmpty(barcode)) {
            showToast("请输入商品条码!");
            edvSearch.setText("");
            edvSearch.setFocusable(true);
            edvSearch.setFocusableInTouchMode(true);
            edvSearch.requestFocus();
            edvSearch.findFocus();
        } else {
            GetSpxx(barcode);
        }

    }

    private void GetSpxx(final String barcode) {
        showLoading("查询中...");
        params.clear();
        params.put("spbh", barcode);
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.GetSpxx
                        , ipUrl,new MResponseListener() {
                            @Override
                            public void onSuccess(MResponse response) {
                                String callWebService = String.valueOf(response.data);
                                callWebService=replaceStringNR(callWebService);
                                if (callWebService.equals("查询的商品不存在！")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast("查询的商品不存在！");
                                            tv_noSP.setVisibility(View.VISIBLE);
                                            listSearchResult.setVisibility(View.GONE);
                                            tv_noSP.setText("无符合该条件的商品");
                                            edvSearch.setFocusable(true);
                                            edvSearch.setFocusableInTouchMode(true);
                                            edvSearch.requestFocus();
                                        }
                                    });
                                } else {
                                    GetSpxxBeanClass getSpxxBeanClass = JSON.parseObject(callWebService,
                                            GetSpxxBeanClass.class);
                                    getSpxx = getSpxxBeanClass.getGetSpxx();
                                    if (getSpxx != null) {
                                        Message message = new Message();
                                        message.what = GetSpxxSuccess;
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
                                        }else {
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
    void btPrint() {
        //做打印

    }

    @Click
    void queryReturnBtn() {
        openAct(MainAct.class, true);

    }

    @AfterViews
    protected void main() {
        App.getInstance().addActivity(SearchAct.this);
        initTitlebar();
        edvSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        edvSearch.setFocusable(true);
        edvSearch.setFocusableInTouchMode(true);
        edvSearch.requestFocus();
        setClearBtnListener(edvSearch, tel_et_clearbtn);
        edvSearch.setOnKeyListener(onKeyListener);
        edvSearch.addTextChangedListener(watcher);

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
            tv_noSP.setVisibility(View.VISIBLE);
            listSearchResult.setVisibility(View.GONE);
            tv_noSP.setText("");
        }
    };

    @Override
    public void onGetBarcode(String barcode) {
        barcode = replaceStringNR(barcode);
        edvSearch.setText(barcode);
        GetSpxx(barcode);
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
        }, "商品查询", null);
    }

    @Override
    public void onEventMainThread(ViewMessage viewMessage) {

    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_ENTER){
                    btn_search();
                    btn_search.setFocusable(false);
                    queryReturnBtn.setFocusable(false);
                    edvSearch.setFocusable(true);
                    edvSearch.setFocusableInTouchMode(true);
                    edvSearch.requestFocus();
                    return true;
                }
            }
            return false;
        }
    };
}
