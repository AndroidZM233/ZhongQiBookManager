/*
 *
 * @author Echo
 * @created 2016.5.29
 * @email bairu.xu@speedatagroup.com
 * @version v1.0
 *
 */

package com.speedata.zhongqi.model;


import android.content.Context;

import com.speedata.zhongqi.App;
import com.speedata.zhongqi.application.APIMethod;
import com.speedata.zhongqi.application.AppConst;

import java.util.HashMap;

import common.http.MHttpManager;
import common.http.MResponseListener;
import common.utils.LogUtil;
import common.utils.SharedXmlUtil;
import common.webserice.WebService;

public class HttpModel {
    private static final String TAG = LogUtil.DEGUG_MODE ? "HttpModel" : HttpModel.class
            .getSimpleName();
    private static final boolean debug = true;
    public static byte[] lock = new byte[0];
    private static HttpModel mHttpModel;

    public void init(Context context) {
        WebService.init(context);
        MHttpManager.init(context);
    }

    private HttpModel() {
    }

    public static HttpModel getInstance() {
        synchronized (lock) {
            if (mHttpModel == null) {
                mHttpModel = new HttpModel();
            }
            return mHttpModel;
        }
    }

    /**
     * 接口的公共参数
     *
     * @param hashMap
     * @return
     */
    public void addCommonParms(HashMap<String, Object> hashMap) {
        if (hashMap != null) {
            String grid = SharedXmlUtil.getInstance(App.getInstance()).read(AppConst.USERNAME,
                    null);
            String sid = SharedXmlUtil.getInstance(App.getInstance()).read(AppConst.SID, null);
            LogUtil.i(debug, TAG, "【HttpModel.setCommonParms()】【sid=" + sid + ",grid=" + grid +
                    "】");
            hashMap.put("grid", grid);
            hashMap.put("sid", sid);
        }
    }



   //登录
    public void CheckUser(MResponseListener l, String userName,String pwd) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("UserName",userName);
        hashMap.put("Password",pwd);
        MHttpManager.postJson(APIMethod.CheckUser, hashMap, l);
    }



    //书目查询
    public void GetSpxx(MResponseListener l, String spbh) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("spbh",spbh);
        MHttpManager.postJson(APIMethod.GetSpxx, hashMap, l);
    }



    //盘点
    // 货位号校验
    public void GetHWMC(MResponseListener l, String HWBH) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("HWBH",HWBH);
        MHttpManager.postJson(APIMethod.GetHWMC, hashMap, l);
    }

    //盘点商品查询
    public void GetSpxxForPd(MResponseListener l, String SPBH,String HWID) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("SPBH",SPBH);
        hashMap.put("HWID",HWID);
        MHttpManager.postJson(APIMethod.GetSpxxForPd, hashMap, l);
    }

    //盘点录入
    public void PDLR(MResponseListener l, String HWID, String Wlbmid, String Czyid, String Spxxid, String Pcsl) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("HWID",HWID);
        hashMap.put("Wlbmid",Wlbmid);
        hashMap.put("Czyid",Czyid);
        hashMap.put("Spxxid",Spxxid);
        hashMap.put("Pcsl",Pcsl);

        MHttpManager.postJson(APIMethod.PDLR, hashMap, l);
    }

    //盘点浏览
    public void PDLL(MResponseListener l, String HWID,String Czyid) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("HWID",HWID);
        hashMap.put("Czyid",Czyid);
        MHttpManager.postJson(APIMethod.PDLL, hashMap, l);
    }

    //盘点打印
    public void PdPrint(MResponseListener l, String HWID,String Czyid) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("HWID",HWID);
        hashMap.put("Czyid",Czyid);
        MHttpManager.postJson(APIMethod.PDLL, hashMap, l);
    }




    //货位调整
    //货位校验HwTz_MbHw
    public void HwTz_MbHw(MResponseListener l, String Hwbh) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Hwbh",Hwbh);
        MHttpManager.postJson(APIMethod.HwTz_MbHw, hashMap, l);
    }

    //商品查询  public string HwTz_Cx(string Txm, string Bmbh)
    public void HwTz_Cx(MResponseListener l, String Txm,String Bmbh) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Txm",Txm);
        hashMap.put("Bmbh",Bmbh);
        MHttpManager.postJson(APIMethod.HwTz_Cx, hashMap, l);
    }


    //货位调整录入

    public void HwTz_Lr(MResponseListener l, String HWBMID, String Bmspkftzid, String Czyid, String Spxxid, String Oldhwbmid) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("HWBMID",HWBMID);
        hashMap.put("Bmspkftzid",Bmspkftzid);
        hashMap.put("Czyid",Czyid);
        hashMap.put("Spxxid",Spxxid);
        hashMap.put("Oldhwbmid",Oldhwbmid);

        MHttpManager.postJson(APIMethod.HwTz_Lr, hashMap, l);
    }

    //货位调整浏览
    public void HwTz_LL(MResponseListener l, String Czyid) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Czyid",Czyid);
        MHttpManager.postJson(APIMethod.HwTz_LL, hashMap, l);
    }



    //商品、客户采集
    //客户效验
    public void Sjcj_Load(MResponseListener l, String KHBH) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("KHBH",KHBH);
        MHttpManager.postJson(APIMethod.Sjcj_Load, hashMap, l);
    }

    //商品查询
    public void Sjcj_CX(MResponseListener l, String TXM) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("TXM",TXM);
        MHttpManager.postJson(APIMethod.Sjcj_CX, hashMap, l);
    }

    //商品效验
    public void Sjcj_Check(MResponseListener l, String Spxxid,String KHBMID) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Spxxid",Spxxid);
        hashMap.put("KHBMID",KHBMID);
        MHttpManager.postJson(APIMethod.Sjcj_Check, hashMap, l);
    }

    //采集录入

    public void Sjcj_Lr(MResponseListener l, String Khid, String Spxxid,String CJsl, String Czyid) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Khid",Khid);
        hashMap.put("Spxxid",Spxxid);
        hashMap.put("CJsl",CJsl);
        hashMap.put("Czyid",Czyid);

        MHttpManager.postJson(APIMethod.Sjcj_Lr, hashMap, l);
    }

    //采集浏览public string Sjcj_LL(string Czyid, string KHID)

    public void Sjcj_LL(MResponseListener l, String Czyid,String KHID) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Czyid",Czyid);
        hashMap.put("KHID",KHID);
        MHttpManager.postJson(APIMethod.Sjcj_LL, hashMap, l);
    }




//销售排行
    public void Xsph(MResponseListener l, String Type) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Type",Type);
        MHttpManager.postJson(APIMethod.Xsph, hashMap, l);
    }



}