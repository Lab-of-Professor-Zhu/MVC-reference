package com.edncp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 用来做数据的基础连接和相关操作的关闭
 * @author Lulu
 *
 */
public class BaseDao {
    public BaseDao() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // ==================== 属性 ====================
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    // ==================== 获得连接 ====================
    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(lsr.Configuration.ip, lsr.Configuration.oracName, lsr.Configuration.oracKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getConnection（）错误");
        }
        return conn;
    }

    // ==================== 执行增、删、改SQL语句 ====================
    /**
     * 
     * @param sql
     * @return int表示影响的行数
     * @throws SQLException
     */
    public int executeUpdate(String sql) throws SQLException {
        if (getConn() == null) {
            System.out.println("与数据库连接失败!");
            return -1;
        }
        stmt = conn.createStatement();
        return stmt.executeUpdate(sql);
    }

    // ==================== 重载执行增、删、改SQL语句 ====================
    public int executeUpdate(String sql, Object[] obj) throws SQLException {

        if (getConn() == null) {
            System.out.println("与数据库连接失败!");
            return -1;
        }
        pstmt = conn.prepareStatement(sql);
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                pstmt.setObject(i + 1, obj[i]);
            }
        }
        return pstmt.executeUpdate();
    }

    // ==================== 执行查询SQL语句 ====================
    public ResultSet executeQuery(String sql) throws SQLException {
        if (getConn() == null) {
            System.out.println("与数据库连接失败!");
            return null;
        }
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        return rs;
    }

    // ==================== 重载执行查询SQL语句 ====================
    public ResultSet executeQuery(String sql, Object[] obj) throws SQLException {
        //第一步获取链接
        if (getConn() == null) {//该步骤完成了与数据库的链接操作
            System.out.println("与数据库连接失败!");
            return null;
        }
        //将数据准备一下
        pstmt = conn.prepareStatement(sql);
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                pstmt.setObject(i + 1, obj[i]);
            }
        }
        //执行完整sql语句
        rs = pstmt.executeQuery();
        return rs;
    }

    // ==================== 关闭ResultSet ====================
    public void closeResultSet() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // ==================== 关闭Connection ====================
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ==================== 关闭Statement和PreparedStatement ====================
    public void closeStatement() {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}