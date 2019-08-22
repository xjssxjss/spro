package com.spro.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @Author yahui, xie
 * @create 2018-10-09
 */
public class DBConn {

    private static Logger logger = LoggerFactory.getLogger(DBConn.class);
    private static String dbDriver = "com.mysql.cj.jdbc.Driver";
    private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/spro?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
    private static String dbUserName = "root";
    private static String dbPassWord = "root1234";
    private static final Properties prop;
    static {
        prop = new Properties();
        try{
            prop.load(ClassLoader.getSystemResourceAsStream("spro.properties"));
            dbDriver = prop.getProperty("dbDriver");
            dbUrl = prop.getProperty("dbUrl");
            dbUserName = prop.getProperty("dbUserName");
            dbPassWord = prop.getProperty("dbPassWord");
            logger.info("dbDriver>>>>>>>>>"+dbDriver);
            logger.info("dbUrl>>>>>>>>>"+dbUrl);
            logger.info("dbUserName>>>>>>>>>"+dbUserName);
            logger.info("dbPassWord>>>>>>>>>"+dbPassWord);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void conn(){
        try{
            //加载数据库驱动包
            Class.forName(dbDriver);
            logger.info("数据库连接before...");
            Connection conn = DriverManager.getConnection(dbUrl,dbUserName,dbPassWord);
            logger.info("数据库连接成功...");
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error("数据库连接失败。");
        }
    }

    @Test
    public void testConn(){
        conn();
    }
}
