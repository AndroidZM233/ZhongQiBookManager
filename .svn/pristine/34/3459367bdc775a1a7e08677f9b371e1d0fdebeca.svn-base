package com.speedata.zhongqi;

import com.speedata.zhongqi.crash.utils.FileUtil;

import org.junit.Test;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import utils.LogUtil;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private static final String TAG = LogUtil.DEGUG_MODE ? "ExampleUnitTest" : ExampleUnitTest.class.getSimpleName();
    private static final boolean debug = true;


    public static final String TEMPLET_PROPERTIES = "IndicateProtocol.properties";

    @Test
    public void startGenerateJavaByAssets() throws Exception {
        String property = System.getProperty("user.dir");
        String dir = property + "/src/main/assets/";
//        1.常量文件生成
        String apiMethod = "APIMethod";
        String des = property + "/src/main/java/com.speedata.zhongqi/application/";
        generateFile(dir, apiMethod, des);
        //2.bean文件生成
        String Name1 = "Bean";
        String des2 = property + "/src/main/java/com.speedata.zhongqi/bean/";
        generateBeanFiles(dir, Name1, des2);
    }

    private void generateFile(String dir, String javaFileName, String des) throws IOException {
        String fileName = dir + TEMPLET_PROPERTIES;
        String templetstr = FileUtil.read(fileName);
        Properties prop = new Properties();
        InputStream in = new FileInputStream(fileName);
        prop.load(in);
        Set<Object> objects = prop.keySet();
        List<Object> list = new ArrayList<>();
        list.addAll(objects);
        String templetres = FileUtil.read(dir + javaFileName + ".templet");
//        LogUtil.i(debug, TAG, "【ExampleUnitTest.startGenerateJavaByAssets()】【templetres=" + templetres + "】");
        System.out.println(templetstr);// user.dir指定了当前的路径
        String[] split = templetres.split("\\|");
        int size1 = list.size();
        for (int i = 0; i < size1; i++) {
//            String o = split[i];
//            System.out.println("==>" + o);// user.dir指定了当前的路径
        }
        String each = split[1];
        StringBuffer stringBuffer = new StringBuffer(split[0]);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String o = (String) list.get(i);
            String method = o.replace(".do", "");
            String replace = each.replace("${methodName}", method.toUpperCase())
                    .replace("${method}", method);
            stringBuffer.append(replace);
            System.out.println(replace);// user.dir指定了当前的路径
        }
        stringBuffer.append(split[2]);
        FileUtil.write(des + javaFileName + ".java", stringBuffer.toString(), true);
    }

    private void generateBeanFiles(String dir, String javaFileName, String des) throws IOException {
        String fileName = dir + TEMPLET_PROPERTIES;
        String templetstr = FileUtil.read(fileName);
        Properties prop = new Properties();
        InputStream in = new FileInputStream(fileName);
        prop.load(in);
        Set<Object> objects = prop.keySet();
        List<Object> list = new ArrayList<>();
        list.addAll(objects);
        String templetres = FileUtil.read(dir + javaFileName + ".templet");
//        LogUtil.i(debug, TAG, "【ExampleUnitTest.startGenerateJavaByAssets()】【templetres=" + templetres + "】");
        System.out.println(templetstr);// user.dir指定了当前的路径
        int size = list.size();
        for (int i = 0; i < size; i++) {
            String o = (String) list.get(i);
            StringBuffer stringBuffer = new StringBuffer();
            String value = (String) prop.get(o);
            String className = value.replace("com.speedata.zhongqi.bean.", "");
            String replace = templetres.replace("${method}", className);
            stringBuffer.append(replace);
            System.out.println(stringBuffer);// user.dir指定了当前的路径
            FileUtil.write(des + className + ".java", stringBuffer.toString());
        }
    }

    private String NAMESPACE = "http://tempuri.org/";
    private String METHOD = "Login";
    private String WEB_SERVICE_URL = "http://140.206.155.102:8681/BosInterface.asmx";

    @Test
    public void testWeb() {
        SoapObject soapObject = new SoapObject(NAMESPACE,
                METHOD);
        soapObject.addProperty("grid", "28641");
        soapObject.addProperty("pass", "123456");
        // 通过SOAP1.1协议得到envelop对象
        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // 将soapObject对象设置为envelop对象，传出消息
        envelop.dotNet = true;
        envelop.encodingStyle = "utf-8";
        envelop.setOutputSoapObject(soapObject);
        HttpTransportSE httpSE = new HttpTransportSE(WEB_SERVICE_URL);
        try {
            String soapAction = NAMESPACE + METHOD;
            httpSE.call(soapAction, envelop);
            // 得到远程方法返回的SOAP对象
            Object resultObj = envelop.getResponse();
//            SoapObject object = (SoapObject) envelop.bodyIn;
            System.out.println("========" + resultObj);
//                // 得到服务器传回的数据
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }


}