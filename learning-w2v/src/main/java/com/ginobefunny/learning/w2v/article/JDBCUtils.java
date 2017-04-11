package com.ginobefunny.learning.w2v.article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ginozhang on 2017/2/10.
 */
public final class JDBCUtils {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/w2v_article?user=w2v&password=w2v&useUnicode=true&characterEncoding=UTF8";

    public static List<String> getAllContentData() throws Exception{
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sql = "SELECT content_data FROM article_block WHERE template_key='text'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("content_data"));
            }
        } finally {
            closeQuietly(conn, stmt, rs);
        }

        return list;
    }

    public static List<String> getAllBoysContentDetail() throws Exception{
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sql = "SELECT simplified FROM tbl_content_detail";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("simplified"));
            }
        } finally {
            closeQuietly(conn, stmt, rs);
        }

        return list;
    }

    public static List<String> getAllGirlsContentDetail() throws Exception{
        List<String> list = new ArrayList<String>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
            String sql = "SELECT simplified FROM tbl_content_detailgirl";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("simplified"));
            }
        } finally {
            closeQuietly(conn, stmt, rs);
        }

        return list;
    }

    private static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
}
