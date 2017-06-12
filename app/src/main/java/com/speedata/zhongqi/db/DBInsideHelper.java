package com.speedata.zhongqi.db;

import android.content.Context;

import com.elsw.base.db.orm.AbDBHelper;

import com.speedata.zhongqi.bean.LoginBean;

/**
 * Copyright (c) 2012 All rights reserved 名称：DBInsideHelper.java
 * 描述：手机data/data下面的数据库
 *
 * @author zhaoqp
 * @version v1.0
 */
public class DBInsideHelper extends AbDBHelper {
    private static final String DBNAME = "ZhongQiBook.db";

    // 当前数据库的版本
    private static final int DBVERSION = 1;
    // 要初始化的表
    private static final Class<?>[] clazz = {LoginBean.class};//,
    // InitAccountBean.class};

    public DBInsideHelper(Context context) {
        super(context, DBNAME, null, DBVERSION, clazz);
    }
}
