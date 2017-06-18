package com.jdbc.infocampus;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jdbc.dbconn.ConnectMysql;

public class Table {
	private final static String login = "JDBC1_LOGIN";
	private final static String user = "JDBC1_USER";
	private final static String item = "JDBC1_ITEM";
	private final static String order = "JDBC1_ORDER";

	static String getLogin() {
		return login;
	}

	static String getUser() {
		return user;
	}

	static String getItem() {
		return item;
	}
	
	static String getOrder() {
		return order;
	}
	
	/**
	 * @param emailid
	 * @return username
	 * 
	 */
	static String getUserName(String email) {
		String name=null;
		try(Connection connection=new ConnectMysql().getConnection()){
			
			Statement st=connection.createStatement();
			String sql="Select name From "+Table.getUser()+" WHERE email='"+email+"'";
			ResultSet rs=st.executeQuery(sql);
			rs.next();
			name= rs.getString(1);
			rs.close();
			st.close();
		}catch (SQLException e) {
			
		}
		return name;
	}
	
	/**
	 * @param emailid
	 * @return id
	 * 
	 */
	static long getUserid(String email) {
	long id = 0;
		try(Connection connection=new ConnectMysql().getConnection()){
			
			Statement st=connection.createStatement();
			String sql="Select id From "+Table.getUser()+" WHERE email='"+email+"'";
			ResultSet rs=st.executeQuery(sql);
			rs.next();
			id= rs.getLong(1);
			rs.close();
			st.close();
		}catch (SQLException e) {
			
		}
		return id;
	}
	
	static long getItemQuantity(long itemid) {
		long qnt = 0;
			try(Connection connection=new ConnectMysql().getConnection()){
				
				Statement st=connection.createStatement();
				String sql="Select item_quantity From "+Table.getItem()+" WHERE item_id='"+itemid+"'";
				ResultSet rs=st.executeQuery(sql);
				rs.next();
				qnt= rs.getLong(1);
				rs.close();
				st.close();
			}catch (SQLException e) {
				
			}
			return qnt;
		}
	
	static long getItemPrice(long itemid) {
		long price = 0;
			try(Connection connection=new ConnectMysql().getConnection()){
				
				Statement st=connection.createStatement();
				String sql="Select item_price From "+Table.getItem()+" WHERE item_id='"+itemid+"'";
				ResultSet rs=st.executeQuery(sql);
				rs.next();
				price= rs.getLong(1);
				rs.close();
				st.close();
			}catch (SQLException e) {
				
			}
			return price;
		}
	// Login Table admin insert
		void adminLoginTable() {
			try (Connection con = new ConnectMysql().getConnection()) {
				Statement st = con.createStatement();
				String sql = "INSERT INTO " + getLogin() +" Values('admin','admin');";
				 st.executeUpdate(sql);
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	// Login Table Creation
	void createLoginTable() {
		try (Connection con = new ConnectMysql().getConnection()) {
			String str = "CREATE TABLE " + getLogin() + "(email VARCHAR(30),password VARCHAR(30))";
			Statement st = con.createStatement();
			st.execute(str);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// registration table creation

	void createRegisterTable() {
		try (Connection con = new ConnectMysql().getConnection()) {
			String str = "CREATE TABLE " + getUser()
					+ "(id DECIMAL(25),name VARCHAR(30),age DECIMAL(3),mobile DECIMAL(12),email VARCHAR(30))";
			Statement st = con.createStatement();
			st.execute(str);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// item table creation
	void createItemTable() {
		try (Connection con = new ConnectMysql().getConnection()) {
			String str = "CREATE TABLE " + getItem()
					+ "(item_id DECIMAL(25),item_name VARCHAR(30),item_quantity DECIMAL(15),item_price DECIMAL(8,2))";
			Statement st = con.createStatement();
			st.execute(str);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// order table creation
	void createOrderTable() {
		try (Connection con = new ConnectMysql().getConnection()) {
			String str = "CREATE TABLE " + getOrder()+ "(order_id DECIMAL(25),item_id DECIMAL(25),id DECIMAL(25),quantity DECIMAL(5),total_price DECIMAL(8,2))";
			Statement st = con.createStatement();
			st.execute(str);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	// check table is available or not
	public static boolean checkTable(String table_name) {
		boolean flag = false;
		try (Connection con = new ConnectMysql().getConnection()) {
			DatabaseMetaData metadata = con.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, table_name, null);

			if (resultSet.next())
				flag = true;
			else
				flag = false;

			resultSet.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return flag;

	}

}
