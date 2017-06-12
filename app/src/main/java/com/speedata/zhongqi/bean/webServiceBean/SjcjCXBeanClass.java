package com.speedata.zhongqi.bean.webServiceBean;

import java.util.List;

/**
 * Created by 74118 on 2016/7/12.
 */
public class SjcjCXBeanClass {
    /**
     * SPXXID : 7481
     * PM : 欧莱雅多重防护BB隔离露SPF30+/PA+++（30ml）
     * DJ : 150
     * QMKC : 5
     * TXM : 6946537014421
     */

    private List<SjcjCXBean> Sjcj_CX;

    public List<SjcjCXBean> getSjcj_CX() {
        return Sjcj_CX;
    }

    public void setSjcj_CX(List<SjcjCXBean> Sjcj_CX) {
        this.Sjcj_CX = Sjcj_CX;
    }

    public static class SjcjCXBean {
        private String SPXXID;
        private String PM;
        private String DJ;
        private String QMKC;
        private String TXM;
        private String CJSL;


        public String getCJSL() {
            return CJSL;
        }

        public void setCJSL(String CJSL) {
            this.CJSL = CJSL;
        }

        public String getSPXXID() {
            return SPXXID;
        }

        public void setSPXXID(String SPXXID) {
            this.SPXXID = SPXXID;
        }

        public String getPM() {
            return PM;
        }

        public void setPM(String PM) {
            this.PM = PM;
        }

        public String getDJ() {
            return DJ;
        }

        public void setDJ(String DJ) {
            this.DJ = DJ;
        }

        public String getQMKC() {
            return QMKC;
        }

        public void setQMKC(String QMKC) {
            this.QMKC = QMKC;
        }

        public String getTXM() {
            return TXM;
        }

        public void setTXM(String TXM) {
            this.TXM = TXM;
        }
    }
}
