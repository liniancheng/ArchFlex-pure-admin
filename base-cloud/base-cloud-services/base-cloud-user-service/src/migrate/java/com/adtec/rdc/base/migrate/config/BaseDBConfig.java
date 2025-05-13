package com.adtec.rdc.base.migrate.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 用于jdbc数据源
 * 
 * @author dengchf
 *
 */
@Component
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class BaseDBConfig {

	private String url;
	private String driverClassName;
	private String username;
	private String password;

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(driverClassName);
		return DriverManager.getConnection(url, username, password);
	}
}
