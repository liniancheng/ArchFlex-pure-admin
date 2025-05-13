package com.adtec.rdc.base.crossvalidation.util;

import com.adtec.rdc.base.crossvalidation.model.bo.JdbcSource;
import com.alibaba.druid.pool.DruidDataSource;
import java.sql.*;


public class JDBCUtils {
    //数据库连接池
    private static DruidDataSource pool = new DruidDataSource();

    /*static {
        Properties properties = new Properties();
        //通过本类加载器获取输入流
        ClassPathResource classPathResource = new ClassPathResource("jdbc/jdbc.properties");
        try {
            InputStream input = classPathResource.getInputStream();
            properties.load(input);
            //告诉对象连接的四要素
            pool.setDriverClassName(properties.getProperty("driverClass"));
            pool.setUrl(properties.getProperty("url"));
            pool.setUsername(properties.getProperty("username"));
            pool.setPassword(properties.getProperty("password"));
            //其他配置参数
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static DruidDataSource getPool(JdbcSource jdbcSource) {
        pool.setDriverClassName(jdbcSource.getDriverClass());
        pool.setUrl(jdbcSource.getUrl());
        pool.setUsername(jdbcSource.getUsername());
        pool.setPassword(jdbcSource.getPassword());
        return pool;
    }

    public static Connection getConnection(JdbcSource jdbcSource) {
        try {
            return getPool(jdbcSource).getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection, Statement statement) {
        close(connection, statement, null);
    }

    public static void close(Connection connection) {
        close(connection, null, null);
    }
}
