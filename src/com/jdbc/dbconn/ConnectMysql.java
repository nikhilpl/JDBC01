/**
 * 
 */
package com.jdbc.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author nikhil
 *
 */
public class ConnectMysql implements ConnectionFactory{
	
	private static ConnectMysql instance = new ConnectMysql();
    public static final String URL = "jdbc:mysql://localhost:3306/jdbc1";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "Avittom123#";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    
    
	
	 public ConnectMysql() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//private connection class
	private Connection createConnection() {
		Connection connection=null;
		try {
			connection=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
		
	}
	
	@Override
	public  Connection getConnection() {
		
		return instance.createConnection();
	}

}
