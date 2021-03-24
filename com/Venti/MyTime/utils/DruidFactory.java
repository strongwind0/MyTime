package com.Venti.MyTime.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidFactory {

    private static DruidDataSource dataSource = null;

    public static void init() throws Exception {
        Properties properties = new Properties();
        InputStream in = DruidFactory.class.getClassLoader().
                getResourceAsStream("conf/Druid.properties");
        properties.load(in);
        dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        in.close();
    }

    public static Connection getConnection() throws Exception {
        if(null==dataSource)
            init();
        return dataSource.getConnection();
    }
}
