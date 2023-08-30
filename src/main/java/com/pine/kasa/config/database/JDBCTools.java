package com.pine.kasa.config.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * jdbc工具类
 *
 * @author pine
 * @date 2020-08-13 21:20.
 */
public class JDBCTools {
    static String className = null ;
    static String url = null;
    static String user = null;
    static String password = null ;

    static{
        /**
         * className=com.mysql.jdbc.Driver
         * url=jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=UTF-8
         * username=root
         * password=123
         */
        Properties pro = new Properties();
        InputStream is = JDBCTools.class.getClassLoader().getResourceAsStream("config/db.properties");
        try {
            pro.load(is);
            className = pro.getProperty("className");
            url = pro.getProperty("url");
            user = pro.getProperty("username");
            password = pro.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * main方法
     * @param args
     */
    public static void main(String[] args) {
        try {
            getCon();
            System.out.println("连接成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 得到数据连接
     * @return 数据库连接
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getCon() throws ClassNotFoundException, SQLException{
        Class.forName(className);

        Connection con = DriverManager.getConnection(url, user, password);
        return con;
    }

    /**
     * 关闭数据库连接
     * @param con
     * @param pstm
     * @param rs
     */
    public static void close(Connection con, PreparedStatement pstm, ResultSet rs){
        try {
            if(con!=null){
                con.close();
            }
            if(pstm!=null){
                pstm.close();
            }
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param con
     * @param pstm
     */
    public static void close(Connection con,PreparedStatement pstm){
        try {
            if(con!=null){
                con.close();
            }
            if(pstm!=null){
                pstm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
