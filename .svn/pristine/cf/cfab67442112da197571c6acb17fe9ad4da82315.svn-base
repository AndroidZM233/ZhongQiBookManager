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

@EActivity(R.layout.act_goodscollect_browse)
public class GoodsBrowseAct extends FragActBase implements SearchView.OnQueryTextListener
,IBaseScanFragment{
    @ViewById
    EditText edvSearchGoodscollectbrowse;
    @ViewById
    ImageButton ivSearchGoodscollectbrowse;
    @ViewById
    ListView goodscollectbrowseCollectlistviewLv;
    @ViewById
    TextView goodscollectbrowseZPZTv;
    @ViewById
    TextView goodscollectbrowseZSLTv;
    @ViewById
    TextView goodscollectbrowseZMYTv;
    @ViewById
    Button goodscollectbrowseBackBtn;
    @ViewById
    Button goodscollectbrowseHomepageBtn;
    @ViewById
    SearchView goods_search_view;

    private List<SjcjLLBeanClass.SjcjLLBean> sjcj_ll;
    private static final int SjcjLLSuccess = 0;

    private int lastVisible;
    private int partNum = 20;
    private TextView loadmoreTV;
    private ProgressBar loadmorePB;
    private View foodView;
    private CommonAdapter commonAdapter;
    private final int FENYE = 8;
    private List<SjcjLLBeanClass.SjcjLLBean> sjcj_llPart = new ArrayList<SjcjLLBeanClass.SjcjLLBean>();


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SjcjLLSuccess:
                    if (partNum < sjcj_ll.size()) {
                        loadmoreTV.setText("上拉加载更多");
                        for (int i = 0; i < partNum; i++) {
                            sjcj_llPart.add(sjcj_ll.get(i));
                        }
                    } else {
                        sjcj_llPart.addAll(sjcj_ll);
                        loadmoreTV.setVisibility(View.GONE);
                    }
                    commonAdapter = new CommonAdapter(mContext, sjcj_llPart, R.layout.act_customercollect_lv_content) {
                        @Override
                        public void convert(ViewHolder helper, Object item, int position) {
                            helper.setText(R.id.customercollect_lv_tvPM,
                                    sjcj_llPart.get(position).getPM());
                            helper.setText(R.id.customercollect_lv_tvDJ,
                                    sjcj_llPart.get(position).getDJ());
                            helper.setText(R.id.customercollect_lv_tvSPXXID,
                                    sjcj_llPart.get(position).getTXM());
                            helper.setText(R.id.customercollect_lv_tvCJSL,
                                    sjcj_llPart.get(position).getCJSL());
                            helper.setText(R.id.customercollect_lv_tvQMKC,
                                    sjcj_llPart.get(position).getQMKC());
                        }
                    };
                    goodscollectbrowseCollectlistviewLv.setAdapter(commonAdapter);
                    goods_search_view.setOnQueryTextListener(GoodsBrowseAct.this);
                    goods_search_view.setSubmitButtonEnabled(false);
                    goodscollectbrowseZPZTv.setText(sjcj_ll.get(0).getZPZ());
                    goodscollectbrowseZMYTv.setText(sjcj_ll.get(0).getZMY());
                    goodscollectbrowseZSLTv.setText(sjcj_ll.get(0).getZSL());
                    break;

                //分页加载
                case FENYE:
                    loadMoreDate();
                    commonAdapter.notifyDataSetChanged();
                    if (sjcj_llPart.size() == sjcj_ll.size()) {
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
        if (count + 20 < sjcj_ll.size()) {
            for (int i = count; i < count + 20; i++) {
                sjcj_llPart.add(sjcj_ll.get(i));
            }
        } else {
            for (int i = count; i < sjcj_ll.size(); i++) {
                sjcj_llPart.add(sjcj_ll.get(i));
            }
        }

    }

    @Click
    void ivSearchGoodscollectbrowse() {
        //搜索
    }

    @Click
    void goodscollectbrowseBackBtn() {
        openAct(GoodsCollectAct.class, true);
    }

    @Click
    void goodscollectbrowseHomepageBtn() {
        openAct(MainAct.class, true);
    }

    @AfterViews
    protected void main() {
        App.getInstance().addActivity(GoodsBrowseAct.this);
        foodView = LayoutInflater.from(GoodsBrowseAct.this).inflate(R.layout.lv_loadmore, null);
        loadmoreTV = (TextView) foodView.findViewById(R.id.loadMoreTV);
        loadmorePB = (ProgressBar) foodView.findViewById(R.id.bar);
        goodscollectbrowseCollectlistviewLv.addFooterView(foodView);
        goodscollectbrowseCollectlistviewLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (lastVisible == commonAdapter.getCount() && scrollState == SCROLL_STATE_IDLE
                        && lastVisible != sjcj_ll.size()) {
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
        params.put("Czyid", getXml(AppConst.USERID, "1"));
        params.put("KHID", "");
        showLoading("查询中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                WebService.getInstance().callWebService(params, APIMethod.Sjcj_LL,
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
                                            hideLoading();
                                        }
                                    });
                                } else {
                                    if (callWebService != null) {
                                        SjcjLLBeanClass sjcjLLBeanClass = JSON.parseObject(callWebService,
                                                SjcjLLBeanClass.class);
                                        sjcj_ll = sjcjLLBeanClass.getSjcj_LL();
                                        Message message = new Message();
                                        message.what = SjcjLLSuccess;
                                        handler.sendMessage(message);
                                        hideLoading();
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
            sjcj_llPart.clear();
            Message message=new Message();
            message.what=SjcjLLSuccess;
            handler.sendMessage(message);
        }else {
            loadmoreTV.setVisibility(View.GONE);
            newText=replaceStringNR(newText);
            List<SjcjLLBeanClass.SjcjLLBean> searchList = searchItem(newText);
            updateLayout(searchList);
        }

        return true;
    }

    private void updateLayout(final List<SjcjLLBeanClass.SjcjLLBean> searchList) {
        CommonAdapter searchAdapter = new CommonAdapter(mContext, searchList, R.layout.act_customercollect_lv_content) {
            @Override
            public void convert(ViewHolder helper, Object item, int position) {
                helper.setText(R.id.customercollect_lv_tvPM,
                        searchList.get(position).getPM());
                helper.setText(R.id.customercollect_lv_tvDJ,
                        searchList.get(position).getDJ());
                helper.setText(R.id.customercollect_lv_tvSPXXID,
                        searchList.get(position).getTXM());
                helper.setText(R.id.customercollect_lv_tvCJSL,
                        searchList.get(position).getCJSL());
                helper.setText(R.id.customercollect_lv_tvQMKC,
                        searchList.get(position).getQMKC());
            }
        };
        goodscollectbrowseCollectlistviewLv.setAdapter(searchAdapter);
    }


    private List<SjcjLLBeanClass.SjcjLLBean> searchItem(String newText) {
        List<SjcjLLBeanClass.SjcjLLBean> mSearchList = new ArrayList<SjcjLLBeanClass.SjcjLLBean>();
        for (int i = 0; i < sjcj_ll.size(); i++) {
            int indexPM = sjcj_ll.get(i).getPM().indexOf(newText);
            int index = sjcj_ll.get(i).getTXM().indexOf(newText);
            // 存在匹配的数据
            if (index != -1 || indexPM != -1) {
                mSearchList.add(sjcj_ll.get(i));
            }
        }
        return mSearchList;
    }

    @Override
    public void onGetBarcode(String barcode) {
        barcode=replaceStringNR(barcode);
        goods_search_view.setQuery(barcode,false);
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
