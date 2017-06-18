package com.jdbc.infocampus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.jdbc.dbconn.ConnectMysql;

public class Item {
	
	private Scanner scan;

	//display choice
	private int displayChoice() {
		System.out.println("1. Add Items");
		System.out.println("2. Display Items");
		System.out.println("3.  Logout");
		scan = new Scanner(System.in);
		System.out.println("Enter the choice:");
		int choice=scan.nextInt();
		return choice;
		}

	public void newItemList() {
		switch(displayChoice()) {
		case 1:
			addItem();
			//displayItems();
			break;
		case 2:
			displayItems();
			break;
		case 3:
			System.exit(0);
		default:
			System.out.println("you are enter the Wrong choice....");
			System.out.println("please enter the correct choice");
			newItemList();
		}
	}

	public static void displayItems() {
		try(Connection connection=new ConnectMysql().getConnection()) {
			System.out.println("item ID  item Name  item quantity  item price");
			PreparedStatement st=connection.prepareStatement("Select * From "+Table.getItem()+"");			
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getLong(1)+"\t"+rs.getString(2)+"\t"+rs.getLong(3)+"\t"+rs.getDouble(4));
			}
			rs.close();
			st.close();
		}catch (SQLException e) {
			// TODO: handle exception
		}
		
	}

	private void addItem() {
		try(Connection connection=new ConnectMysql().getConnection()) {
			BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
			if(!Table.checkTable(Table.getItem())) {
				Table obj = new Table();
				obj.createItemTable();
			
			}else {
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO "+Table.getItem()+" VALUES(?, ?, ?, ?)");
			do {
				System.out.println("--Enter the Item Details--");
				System.out.print("Item ID:");
				stmt.setLong(1, Integer.parseInt(br.readLine()));
				
				System.out.print("Item Name:");
				stmt.setString(2, br.readLine());
				
				System.out.print("Item quantity:");
				stmt.setLong(3, Integer.parseInt(br.readLine()));
				
				System.out.print("Item price:");
				stmt.setDouble(4, Double.parseDouble(br.readLine()));

				int numberOfRecords = stmt.executeUpdate();
				System.out.println(numberOfRecords
						+ " record(s) has been updated!");

				System.out.println("Do you want to add more items: y/n");
				String s = br.readLine();
				if (s.startsWith("n")) {
					break;
				}
			} while (true);
			}
		} catch (IOException | SQLException e) {
			// TODO: handle exception
		
		}
	
	}
}
