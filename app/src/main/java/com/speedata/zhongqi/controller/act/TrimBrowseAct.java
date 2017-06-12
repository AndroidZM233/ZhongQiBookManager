package com.speedata.zhongqi.controller.act;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.speedata.zhongqi.App;
import com.speedata.zhongqi.R;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.application.AppConst;
import com.speedata.zhongqi.bean.webServiceBean.LLBeanClass;
import com.speedata.zhongqi.bean.webServiceBean.PDLLBeanClass;
import com.speedata.zhongqi.bean.webServiceBean.SjcjLLBeanClass;
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
@EActivity(R.layout.act_goodstrim_browse)
public class TrimBrowseAct extends FragActBase implements SearchView.OnQueryTextListener,
        IBaseScanFragment{


    @ViewById
    TextView goodstrimbrowerDepartmentTv;
    @ViewById
    EditText edvSearchGoodstrimbrowse;
    @ViewById
    ImageButton ivSearchGoodstrimbrowse;
    @ViewById
    ListView goodstrimbrowseTrimlistviewLv;
    @ViewById
    TextView goodstrimbrowseZPZTv;
    @ViewById
    TextView goodstrimbrowseZSLTv;
    @ViewById
    TextView goodstrimbrowseZMYTv;
    @ViewById
    Button goodstrimbrowerBackBtn;
    @ViewById
    Button goodstrimbrowerHomepageBtn;
    @ViewById
    SearchView trim_search_view;
    private static final int LLSuccess = 0;

    private int lastVisible;
    private int partNum = 20;
    private TextView loadmoreTV;
    private ProgressBar loadmorePB;
    private View foodView;
    private CommonAdapter commonAdapter;
    private final int FENYE = 8;
    private List<LLBeanClass.HwTzLLBean> hwTz_llPart =
            new ArrayList<LLBeanClass.HwTzLLBean>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LLSuccess:
                    if (partNum < hwTz_ll.size()) {
                        loadmoreTV.setText("上拉加载更多");
                        for (int i = 0; i < partNum; i++) {
                            hwTz_llPart.add(hwTz_ll.get(i));
                        }
                    } else {
                        hwTz_llPart.addAll(hwTz_ll) ;
                        loadmoreTV.setVisibility(View.GONE);
                    }

                    commonAdapter = new CommonAdapter(mContext, hwTz_llPart, R.layout.act_goodstrim_browse_lv_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.goodstrim_lv_tvHWBH,
                                    hwTz_llPart.get(position).getHWBH());
                            helper.setText(R.id.goodstrim_lv_tvOLDHWBH,
                                    hwTz_llPart.get(position).getOLDHWBH());
                            helper.setText(R.id.goodstrim_lv_tvDJ,
                                    hwTz_llPart.get(position).getDJ());
                            helper.setText(R.id.goodstrim_lv_tvPM,
                                    hwTz_llPart.get(position).getPM());
                        }
                    };
                    goodstrimbrowseTrimlistviewLv.setAdapter(commonAdapter);
                    trim_search_view.setOnQueryTextListener(TrimBrowseAct.this);
                    trim_search_view.setSubmitButtonEnabled(false);
                    goodstrimbrowseZPZTv.setText(hwTz_ll.get(0).getZPZ());
                    break;

                //分页加载
                case FENYE:
                    loadMoreDate();
                    commonAdapter.notifyDataSetChanged();
                    if (hwTz_llPart.size() == hwTz_ll.size()) {
                        loadmoreTV.setText("数据已加载完毕");
                        loadmorePB.setVisibility(View.GONE);
                    } else {
                        loadmoreTV.setText("上拉加载更多");
                    }
                    break;
            }
        }
    };
    private List<LLBeanClass.HwTzLLBean> hwTz_ll;

    private void loadMoreDate() {
        int count = commonAdapter.getCount();
        if (count + 20 < hwTz_ll.size()) {
            for (int i = count; i < count + 20; i++) {
                hwTz_llPart.add(hwTz_ll.get(i));
            }
        } else {
            for (int i = count; i < hwTz_ll.size(); i++) {
                hwTz_llPart.add(hwTz_ll.get(i));
            }
        }

    }

    @Click
    void ivSearchGoodstrimbrowse() {
        //搜索
    }

    @Click
    void goodstrimbrowerBackBtn() {
        openAct(TrimAct.class, true);
    }

    @Click
    void goodstrimbrowerHomepageBtn() {
        openAct(MainAct.class, true);
    }

    @AfterViews
    protected void main() {
        App.getInstance().addActivity(TrimBrowseAct.this);
        goodstrimbrowerDepartmentTv.setText(getXml("DEPARTNAME", "1"));
        foodView = LayoutInflater.from(TrimBrowseAct.this).inflate(R.layout.lv_loadmore, null);
        loadmoreTV = (TextView) foodView.findViewById(R.id.loadMoreTV);
        loadmorePB = (ProgressBar) foodView.findViewById(R.id.bar);
        goodstrimbrowseTrimlistviewLv.addFooterView(foodView);
        goodstrimbrowseTrimlistviewLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (lastVisible == commonAdapter.getCount() && scrollState == SCROLL_STATE_IDLE
                        && lastVisible != hwTz_ll.size()) {
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
        params.put("CzyID", getXml(AppConst.USERID, "1"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.HwTz_LL,
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
                                        }
                                    });
                                } else {
                                    LLBeanClass llBeanClass = JSON.parseObject(callWebService, LLBeanClass.class);
                                    hwTz_ll = llBeanClass.getHwTz_LL();
                                    if (hwTz_ll != null) {
                                        Message msg = new Message();
                                        msg.what = LLSuccess;
                                        handler.sendMessage(msg);
                                        hideLoading();
                                    }
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
            hwTz_llPart.clear();
            Message message=new Message();
            message.what=LLSuccess;
            handler.sendMessage(message);
        }else {
            loadmoreTV.setVisibility(View.GONE);
            newText=replaceStringNR(newText);
            List<LLBeanClass.HwTzLLBean> searchList = searchItem(newText);
            updateLayout(searchList);
        }

        return true;
    }

    private void updateLayout(final List<LLBeanClass.HwTzLLBean> searchList) {
        CommonAdapter searchAdapter=new CommonAdapter(mContext,searchList,R.layout.act_goodstrim_browse_lv_content) {
            @Override
            public void convert(ViewHolder helper, Object item, int position) {
                helper.setText(R.id.goodstrim_lv_tvHWBH,
                        searchList.get(position).getHWBH());
                helper.setText(R.id.goodstrim_lv_tvOLDHWBH,
                        searchList.get(position).getOLDHWBH());
                helper.setText(R.id.goodstrim_lv_tvDJ,
                        searchList.get(position).getDJ());
                helper.setText(R.id.goodstrim_lv_tvPM,
                        searchList.get(position).getPM());
            }
        };
        goodstrimbrowseTrimlistviewLv.setAdapter(searchAdapter);
    }


    private List<LLBeanClass.HwTzLLBean> searchItem(String newText) {
        List<LLBeanClass.HwTzLLBean> mSearchList = new ArrayList<LLBeanClass.HwTzLLBean>();
        for (int i = 0; i < hwTz_ll.size(); i++) {
            int indexPM = hwTz_ll.get(i).getPM().indexOf(newText);
            int index=hwTz_ll.get(i).getTXM().indexOf(newText);
            // 存在匹配的数据
            if (index != -1 || indexPM!=-1) {
                mSearchList.add(hwTz_ll.get(i));
            }
        }
        return mSearchList;
    }
    @Override
    public void onGetBarcode(String barcode) {
        barcode=replaceStringNR(barcode);
        trim_search_view.setQuery(barcode,false);
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
