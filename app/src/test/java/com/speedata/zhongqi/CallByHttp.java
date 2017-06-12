/*
 *
 * @author Echo
 * @created 2016.6.6
 * @email bairu.xu@speedatagroup.com
 * @version v1.0
 *
 */

package com.speedata.zhongqi;

import android.text.TextUtils;

import com.speedata.zhongqi.crash.utils.FileUtil;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import utils.LogUtil;

public class CallByHttp {

    private String actionBySOAP() {
        String property = System.getProperty("user.dir");
        String fileName = property + "/src/main/assets/webparms.templet";
        String templetstr = FileUtil.read(fileName);
        String replace = templetstr.replace("${username}", "28641").replace("${password}", "1234561");
        LogUtil.i(debug, TAG, "【CallByHttp.actionBySOAP()】【replace=" + replace + "】");
        return replace;
    }

    private static final String TAG = LogUtil.DEGUG_MODE ? "CallByHttp" : CallByHttp.class.getSimpleName();
    private static final boolean debug = true;
    private  String callWebService(String wsdl)
            throws Exception {
        System.setProperty("sun.net.client.defaultConnectTimeout", "20000");
        System.setProperty("sun.net.client.defaultReadTimeout", "20000");

        // URL连接
        URL url = new URL(wsdl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // conn.setRequestMethod("GET");
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Length", String.valueOf(actionBySOAP().getBytes().length));
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setConnectTimeout(20000);
        // 请求输入内容
        OutputStream output = conn.getOutputStream();
        output.write(actionBySOAP().getBytes());
        output.flush();
        output.close();
        // 请求返回内容
        InputStreamReader isr = new InputStreamReader(conn.getInputStream(),"GB2312");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str + "\n");
        }
        br.close();
        isr.close();
        return sb.toString();
    }
    private String NAMESPACE = "http://tempuri.org/";
    private String METHOD = "Login";
    private String WEB_SERVICE_URL = "http://140.206.155.102:8681/BosInterface.asmx";


    @Test
    public void main() throws Exception {
        String result = callWebService(WEB_SERVICE_URL);
        System.out.println(result);
//        String add = URLEncoder.encode("上海z", "GB2312");
//        LogUtil.i(debug, TAG, "【CallByHttp.main()】【add=" + add + "】");
    }



    /**
     * JsonStr字符串BOM头处理
     * @param data
     * @return
     */
    public static final String removeBOM(String data) {

        if (TextUtils.isEmpty(data)) {
            return data;
        }
        if (data.startsWith("ufeff")) {
            return data.substring(1);
        } else {
            return data;
        }
    }
}