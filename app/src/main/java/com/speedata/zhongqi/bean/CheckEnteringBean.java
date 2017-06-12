package com.speedata.zhongqi.bean;

/**
 * Created by xu on 2016/6/17.
 */

//日常盘点 -- 盘点录入(请求参数)
public class CheckEnteringBean {
    private String HWID;           //HWID     货位校验返回值HWBMID

    private String Wlbmid;   //Wlbmid   货位校验返回值WLBMID
    private String Czyid;         //Czyid    系统登录返回值USERID
    private String Spxxid;        //Spxxid   商品查询返回值SPXXID
    private String Pcsl;     //Pcsl     界面输入【盘点数】



    public String getHWID() {
        return HWID;
    }

    public void setHWID(String HWID) {
        this.HWID = HWID;
    }

    public String getWlbmid() {
        return Wlbmid;
    }

    public void setWlbmid(String wlbmid) {
        Wlbmid = wlbmid;
    }

    public String getCzyid() {
        return Czyid;
    }

    public void setCzyid(String czyid) {
        Czyid = czyid;
    }

    public String getSpxxid() {
        return Spxxid;
    }

    public void setSpxxid(String spxxid) {
        Spxxid = spxxid;
    }

    public String getPcsl() {
        return Pcsl;
    }

    public void setPcsl(String pcsl) {
        Pcsl = pcsl;
    }
}