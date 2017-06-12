package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.application.AppConst;
import com.speedata.zhongqi.bean.webServiceBean.PDLLBeanClass;
import com.speedata.zhongqi.controller.adapter.CommonAdapter;
import com.speedata.zhongqi.controller.adapter.ViewHolder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
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

@EActivity(R.layout.act_goodscheck_browse)
public class CheckBrowseAct extends FragActBase implements SearchView.OnQueryTextListener ,
        IBaseScanFragment{
    @ViewById
    TextView goodscheckbrowerLocationTv;
    @ViewById
    EditText edvSearchGoodscheckbrowse;
    @ViewById
    ImageButton ivSearchGoodscheckbrowse;
    @ViewById
    ListView goodscheckbrowseChecklistviewLv;
    @ViewById
    TextView goodscheckbrowseZPZTv;
    @ViewById
    TextView goodscheckbrowseZSLTv;
    @ViewById
    TextView goodscheckbrowseZMYTv;
    @ViewById
    Button goodscheckbrowerBackBtn;
    @ViewById
    Button goodscheckbrowerHomepageBtn;
    @ViewById
    SearchView check_search_view;
    @ViewById
    Button goodscheckbrower_printbtn;
    private List<PDLLBeanClass.PDLLBean> pdll;
    private static final int PDLLSuccess = 0;

    private int lastVisible;
    private int partNum = 20;
    private TextView loadmoreTV;
    private ProgressBar loadmorePB;
    private View foodView;
    private CommonAdapter commonAdapter;
    private final int FENYE = 8;
    private List<PDLLBeanClass.PDLLBean> pdllPart = new ArrayList<PDLLBeanClass.PDLLBean>();


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PDLLSuccess:
                    if (partNum < pdll.size()) {
                        loadmoreTV.setText("上拉加载更多");
                        for (int i = 0; i < partNum; i++) {
                            pdllPart.add(pdll.get(i));
                        }
                    } else {
                        pdllPart .addAll(pdll);
                        loadmoreTV.setVisibility(View.GONE);
                    }
                    commonAdapter = new CommonAdapter(mContext, pdllPart, R.layout.act_goodscheck_browse_lv_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.browse_lv_tvPM,
                                    pdll.get(position).getPM());
                            helper.setText(R.id.browse_lv_tvDJ,
                                    pdll.get(position).getDJ());
                            helper.setText(R.id.browse_lv_tvBB,
                                    pdll.get(position).getBB());
                            helper.setText(R.id.browse_lv_tvKC,
                                    pdll.get(position).getQMKC());
                            helper.setText(R.id.browse_lv_tvYPSL,
                                    pdll.get(position).getPCSL());
                        }
                    };
                    goodscheckbrowseChecklistviewLv.setAdapter(commonAdapter);
//                    goodscheckbrowseChecklistviewLv.setTextFilterEnabled(true);
                    check_search_view.setOnQueryTextListener(CheckBrowseAct.this);
                    check_search_view.setSubmitButtonEnabled(false);
                    goodscheckbrowseZPZTv.setText(pdll.get(0).getZPZ());
                    goodscheckbrowseZMYTv.setText(pdll.get(0).getZMY());
                    goodscheckbrowseZSLTv.setText(pdll.get(0).getZSL());
                    break;

                //分页加载
                case FENYE:
                    loadMoreDate();
                    commonAdapter.notifyDataSetChanged();
                    if (pdllPart.size() == pdll.size()) {
                        loadmoreTV.setText("数据已加载完毕");
                        loadmorePB.setVisibility(View.GONE);
                    } else {
                        loadmoreTV.setText("上拉加载更多");
                    }
                    break;
            }
        }
    };

    private void loadMoreDate() {
        int count = commonAdapter.getCount();
        if (count + 20 < pdll.size()) {
            for (int i = count; i < count + 20; i++) {
                pdllPart.add(pdll.get(i));
            }
        } else {
            for (int i = count; i < pdll.size(); i++) {
                pdllPart.add(pdll.get(i));
            }
        }

    }

    @Click
    void goodscheckbrower_printbtn(){
        //打印
        showLoading("打印中...");
        params.clear();
        params.put("HWID", getXml("HWBMID", "1"));
        params.put("CzyId", getXml(AppConst.USERID, "1"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.PdPrint
                        ,ipUrl, new MResponseListener() {
                            @Override
                            public void onSuccess(MResponse response) {
                                String callWebService = String.valueOf(response.data);
                                if (callWebService.equals("anyType{}")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast("打印成功！");
                                        }
                                    });
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast("打印失败！");
                                        }
                                    });
                                }
                                hideLoading();
                                edvSearchGoodscheckbrowse.setFocusable(true);
                                edvSearchGoodscheckbrowse.setFocusableInTouchMode(true);
                                edvSearchGoodscheckbrowse.requestFocus();
                                hideInputMethod();
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
                                        hideInputMethod();
                                    }
                                });
                            }
                        });
            }
        }).start();
        hideInputMethod();
    }
    @Click
    void ivSearchGoodscheckbrowse() {
        //搜索
    }

    @Click
    void goodscheckbrowerBackBtn() {
        openAct(CheckDetailAct.class, true);
    }

    @Click
    void goodscheckbrowerHomepageBtn() {
        openAct(MainAct.class, true);
    }


    @AfterViews
    protected void main() {
        App.getInstance().addActivity(CheckBrowseAct.this);
        ipUrl=getXml("IP","");
        goodscheckbrowerLocationTv.setText(getXml("HWBH", "001"));
        foodView = LayoutInflater.from(CheckBrowseAct.this).inflate(R.layout.lv_loadmore, null);
        loadmoreTV = (TextView) foodView.findViewById(R.id.loadMoreTV);
        loadmorePB = (ProgressBar) foodView.findViewById(R.id.bar);
        goodscheckbrowseChecklistviewLv.addFooterView(foodView);
        goodscheckbrowseChecklistviewLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (lastVisible == commonAdapter.getCount() && scrollState == SCROLL_STATE_IDLE
                        && lastVisible != pdll.size()) {
                    loadmorePB.setVisibility(View.VISIBLE);
                    loadmoreTV.setText("正在加载中...");
                    Message message = new Message();
                    message.what = FENYE;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 计算最后可见条目的索引
                lastVisible = firstVisibleItem + visibleItemCount - 1;
            }
        });


    }

    private void selectAll() {
        showLoading("加载中...");
        params.clear();
        params.put("HWID", getXml("HWBMID", "1"));
        params.put("Czyid", getXml(AppConst.USERID, "1"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.PDLL
                        ,ipUrl, new MResponseListener() {
                            @Override
                            public void onSuccess(MResponse response) {
                                String callWebService = String.valueOf(response.data);
                                callWebService=replaceStringNR(callWebService);
                                if (callWebService.equals("数据获取失败")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showToast("数据获取失败!");
                                        }
                                    });
                                } else {
                                    PDLLBeanClass pdllBeanClass = JSON
                                            .parseObject(callWebService, PDLLBeanClass.class);
                                    pdll = pdllBeanClass.getPDLL();
                                    if (pdll != null) {
                                        Message msg = new Message();
                                        msg.what = PDLLSuccess;
                                        handler.sendMessage(msg);
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
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)){
            pdllPart.clear();
            Message message=new Message();
            message.what=PDLLSuccess;
            handler.sendMessage(message);
        }else {
            loadmoreTV.setVisibility(View.GONE);
            newText=replaceStringNR(newText);
            List<PDLLBeanClass.PDLLBean> searchList = searchItem(newText);
            updateLayout(searchList);
        }

        return true;
    }

    private void updateLayout(final List<PDLLBeanClass.PDLLBean> searchList) {
        CommonAdapter searchAdapter=new CommonAdapter(mContext,searchList,R.layout.act_goodscheck_browse_lv_content) {
            @Override
            public void convert(ViewHolder helper, Object item, int position) {
                helper.setText(R.id.browse_lv_tvPM,
                        searchList.get(position).getPM());
                helper.setText(R.id.browse_lv_tvDJ,
                        searchList.get(position).getDJ());
                helper.setText(R.id.browse_lv_tvBB,
                        searchList.get(position).getBB());
                helper.setText(R.id.browse_lv_tvKC,
                        searchList.get(position).getQMKC());
                helper.setText(R.id.browse_lv_tvYPSL,
                        searchList.get(position).getPCSL());
            }
        };
        goodscheckbrowseChecklistviewLv.setAdapter(searchAdapter);
    }


    private List<PDLLBeanClass.PDLLBean> searchItem(String newText) {
        List<PDLLBeanClass.PDLLBean> mSearchList = new ArrayList<PDLLBeanClass.PDLLBean>();
        for (int i = 0; i < pdll.size(); i++) {
            int indexPM = pdll.get(i).getPM().indexOf(newText);
            int index=pdll.get(i).getTXM().indexOf(newText);
            // 存在匹配的数据
            if (index != -1 || indexPM!=-1) {
                mSearchList.add(pdll.get(i));
            }
        }
        return mSearchList;
    }

    @Override
    public void onGetBarcode(String barcode) {
        barcode=replaceStringNR(barcode);
        check_search_view.setQuery(barcode,false);
        hideInputMethod();
    }

    private ScanUtil scanUtil;

    @Override
    protected void onResume() {
        super.onResume();
        ipUrl=getXml("IP","");
        selectAll();
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