package com.jdbc.infocampus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.jdbc.dbconn.ConnectMysql;

public class Registration {
	private long id;
	private String name;
	private short age;
	private long mobile;
	private String email;
	private String password;

	long getId() {
		return id;
	}

	void setId(long id) {
		this.id = id;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	short getAge() {
		return age;
	}

	void setAge(short age) {
		this.age = age;
	}

	long getMobile() {
		return mobile;
	}

	void setMobile(long mobile) {
		this.mobile = mobile;
	}

	String getEmail() {
		return email;
	}

	void setEmail(String email) {
		this.email = email;
	}

	String getPassword() {
		return password;
	}

	void setPassword(String password) {
		this.password = password;
	}

	// register the user
	public void newRegistration() {
		try (Connection con = new ConnectMysql().getConnection()) {
			readRegistrationValues();

			if (!Table.checkTable(Table.getLogin())) {

				Table obj = new Table();
				obj.createLoginTable();
				obj.adminLoginTable();
			}

			if (!Table.checkTable(Table.getUser())) {

				Table obj = new Table();
				obj.createRegisterTable();
			}
			
			
			PreparedStatement stmt1 = con.prepareStatement("INSERT INTO "+Table.getUser()+" VALUES(?, ?, ?, ?, ?)");
		
			PreparedStatement stmt2 = con.prepareStatement("INSERT INTO "+Table.getLogin()+" VALUES(?, ?)");
			
			stmt1.setLong(1, getId());
			stmt1.setString(2, getName());
			stmt1.setInt(3, getAge());
			stmt1.setLong(4, getMobile());
			stmt1.setString(5, getEmail());
			stmt2.setString(1, getEmail());
			stmt2.setString(2, getPassword());
			
			stmt1.executeUpdate();
			stmt2.executeUpdate();
			
			System.out.println("Successfully Registerd.... :-)");
			new Login().loginCustomer();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// read the values from user
	private void readRegistrationValues() {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("----Registration:SONY---");
		System.out.println("Please fill the below form");

		try {
			System.out.print("Name: ");
			setName(br.readLine());

			System.out.println("Age:");
			setAge(Short.parseShort(br.readLine()));

			System.out.println("Mobile number:");
			setMobile(Long.parseLong(br.readLine()));

			System.out.println("Email ID:");
			setEmail(br.readLine());

			System.out.println("Password:");
			setPassword(br.readLine());
			long id=getAge() + getMobile();
			setId(id);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
