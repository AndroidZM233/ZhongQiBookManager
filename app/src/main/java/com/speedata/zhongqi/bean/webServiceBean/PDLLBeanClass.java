package com.speedata.zhongqi.bean.webServiceBean;

import java.util.List;

/**
 * Created by 74118 on 2016/7/12.
 */
public class PDLLBeanClass {
    /**
     * PM :
     * DJ :
     * BB :
     * PCSL : 123
     * QMKC :
     * ZSL : 124
     * ZMY :
     * ZPZ : 2
     */

    private List<PDLLBean> PDLL;

    public List<PDLLBean> getPDLL() {
        return PDLL;
    }

    public void setPDLL(List<PDLLBean> PDLL) {
        this.PDLL = PDLL;
    }

    public static class PDLLBean {
        private String PM;
        private String DJ;
        private String BB;
        private String PCSL;
        private String QMKC;
        private String ZSL;
        private String ZMY;
        private String ZPZ;
        private String TXM;

        public String getTXM() {
            return TXM;
        }

        public void setTXM(String TXM) {
            this.TXM = TXM;
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

        public String getBB() {
            return BB;
        }

        public void setBB(String BB) {
            this.BB = BB;
        }

        public String getPCSL() {
            return PCSL;
        }

        public void setPCSL(String PCSL) {
            this.PCSL = PCSL;
        }

        public String getQMKC() {
            return QMKC;
        }

        public void setQMKC(String QMKC) {
            this.QMKC = QMKC;
        }

        public String getZSL() {
            return ZSL;
        }

        public void setZSL(String ZSL) {
            this.ZSL = ZSL;
        }

        public String getZMY() {
            return ZMY;
        }

        public void setZMY(String ZMY) {
            this.ZMY = ZMY;
        }

        public String getZPZ() {
            return ZPZ;
        }

        public void setZPZ(String ZPZ) {
            this.ZPZ = ZPZ;
        }
    }
}
