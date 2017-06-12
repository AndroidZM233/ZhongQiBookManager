package com.speedata.zhongqi.bean.response;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

/**
 * Created by Administrator on 2016/6/6. 库存实体类
 */
@Table(name="StoreResponse")
public class StoreResponse {

    /**
     * BILL_CODE : 222
     * COME_PIECE : null
     * N_FLAG : null
     * SEND_SITE : null
     * DESTINATION : null
     * PRE_OR_NEXT_STATION : 武汉Z
     * CUBES : null
     * SETTLEMENT_WEIGHT : null
     * PIECE : 222
     */
    @Column(name = "BILL_CODE")
    public String BILL_CODE;

    @Column(name = "COME_PIECE")
    public String COME_PIECE;

    @Column(name = "N_FLAG")
    public String N_FLAG;

    @Column(name = "SEND_SITE")
    public String SEND_SITE;

    @Column(name = "DESTINATION")
    public String DESTINATION;

    @Column(name = "PRE_OR_NEXT_STATION")
    public String PRE_OR_NEXT_STATION;

    @Column(name = "CUBES")
    public String CUBES;

    @Column(name = "SETTLEMENT_WEIGHT")
    public String SETTLEMENT_WEIGHT;

    @Column(name = "PIECE")
    public String PIECE;


    public String getBILL_CODE() {
        return BILL_CODE;
    }

    public void setBILL_CODE(String BILL_CODE) {
        this.BILL_CODE = BILL_CODE;
    }

    public String getCOME_PIECE() {
        if(COME_PIECE==null)
            return "";
        return COME_PIECE;
    }

    public void setCOME_PIECE(String COME_PIECE) {
        this.COME_PIECE = COME_PIECE;
    }

    public String getCUBES() {
        if(CUBES==null)
            return "";
        return CUBES;
    }

    public void setCUBES(String CUBES) {
        this.CUBES = CUBES;
    }

    public String getDESTINATION() {
        if(DESTINATION==null)
            return "";
        return DESTINATION;
    }

    public void setDESTINATION(String DESTINATION) {
        this.DESTINATION = DESTINATION;
    }

    public String getN_FLAG() {
        if(DESTINATION==null)
            return "";
        return N_FLAG;
    }

    public void setN_FLAG(String n_FLAG) {
        N_FLAG = n_FLAG;
    }

    public String getPIECE() {
        return PIECE;
    }

    public void setPIECE(String PIECE) {
        this.PIECE = PIECE;
    }

    public String getPRE_OR_NEXT_STATION() {
        return PRE_OR_NEXT_STATION;
    }

    public void setPRE_OR_NEXT_STATION(String PRE_OR_NEXT_STATION) {
        this.PRE_OR_NEXT_STATION = PRE_OR_NEXT_STATION;
    }

    public String getSEND_SITE() {
        return SEND_SITE;
    }

    public void setSEND_SITE(String SEND_SITE) {
        this.SEND_SITE = SEND_SITE;
    }

    public String getSETTLEMENT_WEIGHT() {
        return SETTLEMENT_WEIGHT;
    }

    public void setSETTLEMENT_WEIGHT(String SETTLEMENT_WEIGHT) {
        this.SETTLEMENT_WEIGHT = SETTLEMENT_WEIGHT;
    }

    @Override
    public String toString() {
        return "StoreResponse{" +
                "BILL_CODE='" + BILL_CODE + '\'' +
                ", COME_PIECE='" + COME_PIECE + '\'' +
                ", N_FLAG='" + N_FLAG + '\'' +
                ", SEND_SITE='" + SEND_SITE + '\'' +
                ", DESTINATION='" + DESTINATION + '\'' +
                ", PRE_OR_NEXT_STATION='" + PRE_OR_NEXT_STATION + '\'' +
                ", CUBES='" + CUBES + '\'' +
                ", SETTLEMENT_WEIGHT='" + SETTLEMENT_WEIGHT + '\'' +
                ", PIECE='" + PIECE + '\'' +
                '}';
    }
}
