/**
 * 
 */
package com.jdbc.infocampus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.jdbc.dbconn.ConnectMysql;

/**
 * @author Nikhil
 *
 */
public class Login {
	private String username=null;
	private String password=null;
	private Scanner scanner;
	
	
	String getUsername() {
		return username;
	}


	void setUsername(String username) {
		this.username = username;
	}


	String getPassword() {
		return password;
	}


	void setPassword(String password) {
		this.password = password;
	}

	//check login
	private boolean checkLoin() {
		boolean flag=false;
		
		try(Connection connection=new ConnectMysql().getConnection()){
		
			Statement st=connection.createStatement();
			String sql="Select * From "+Table.getLogin()+" WHERE email='"+getUsername()+"' AND password='"+getPassword()+"'";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next()) {
				flag=true; 
			}
			else {
				flag=false;
			}
		}catch (SQLException e) {
			
		}
		return flag;
	}

	public void loginCustomer() {
		System.out.println("Welcom to SONY Mobile World ");
		System.out.println("Please Enter the Login details.....");
		System.out.println("");
		scanner = new Scanner(System.in);
		System.out.print("username/email ID:");
		setUsername(scanner.nextLine());
		System.out.print("Password:");
		setPassword(scanner.nextLine());
		
		//checking login
		if(checkLoin()&& getUsername().equals("admin")) {
			String yn="n";
			do {
			System.out.println("Welcome to Admin");
			new Item().newItemList();
			System.out.println("Do you want to continue(y/n)");
			 yn=scanner.nextLine();
			}while(yn.equals("y"));
			System.out.println("Logout... :-)");
		}else if(checkLoin()) {
			String yn="n";
			do {
			System.out.println("Welcome to "+Table.getUserName(getUsername()));
			new Order().newOrder();
			System.out.println("Do you want to continue(y/n)");
			 yn=scanner.nextLine();
			}while(yn.equals("y"));
			System.out.println("Logout... :-)");
		}else {
			System.out.println("ERROR!. Your username and password are Wrong");
		}
		
		
		
	}

}
