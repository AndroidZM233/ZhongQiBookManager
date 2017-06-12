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

public class DBModel {
    public static byte[] lock = new byte[0];
    private static DBModel mDBModel;
    private static Context mContext;

    private DBModel(Context context) {
        mContext = context;
    }


    public static DBModel getInstance(Context context) {
        synchronized (lock) {
            if (mDBModel == null) {
                mDBModel = new DBModel(context);
            }
            return mDBModel;
        }
    }





}