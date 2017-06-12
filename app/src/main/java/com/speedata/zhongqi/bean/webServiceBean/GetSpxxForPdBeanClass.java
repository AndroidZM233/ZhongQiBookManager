package com.speedata.zhongqi.bean.webServiceBean;

import java.util.List;

/**
 * Created by 74118 on 2016/7/11.
 */
public class GetSpxxForPdBeanClass {
    /**
     * SPXXID : 7481
     * PM : 欧莱雅多重防护BB隔离露SPF30+/PA+++（30ml）
     * DJ : 150
     * CBNY :
     * BB :
     * QMKC : 5
     * YPSL :
     */

    private List<GetSpxxForPdBean> GetSpxxForPd;

    public List<GetSpxxForPdBean> getGetSpxxForPd() {
        return GetSpxxForPd;
    }

    public void setGetSpxxForPd(List<GetSpxxForPdBean> GetSpxxForPd) {
        this.GetSpxxForPd = GetSpxxForPd;
    }

    public static class GetSpxxForPdBean {
        private String SPXXID;
        private String PM;
        private String DJ;
        private String CBNY;
        private String BB;
        private String QMKC;
        private String YPSL;
        private String PDS;

        public String getPDS() {
            return PDS;
        }

        public void setPDS(String PDS) {
            this.PDS = PDS;
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

        public String getCBNY() {
            return CBNY;
        }

        public void setCBNY(String CBNY) {
            this.CBNY = CBNY;
        }

        public String getBB() {
            return BB;
        }

        public void setBB(String BB) {
            this.BB = BB;
        }

        public String getQMKC() {
            return QMKC;
        }

        public void setQMKC(String QMKC) {
            this.QMKC = QMKC;
        }

        public String getYPSL() {
            return YPSL;
        }

        public void setYPSL(String YPSL) {
            this.YPSL = YPSL;
        }
    }
}
