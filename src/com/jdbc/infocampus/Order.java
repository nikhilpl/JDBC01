package com.jdbc.infocampus;

import java.awt.DisplayMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.jdbc.dbconn.ConnectMysql;

public class Order {
	
	private long id;
	private long orderid;
	
	Order(){
		id=setId();
		orderid=orderID();
	}
	
	
	private long setId() {
		long id=Table.getUserid(new Login().getUsername());
		
		return id;
	}


	long getId() {
		return id;
	}


	long getOrderid() {
		return orderid;
	}


	public void newOrder() {
		System.out.println("Following Items are Available:");
		Item.displayItems();
		
		purchaseItems();
		
		System.out.println("Do you want to any change in your order(y/n):");
		Scanner scan=new Scanner(System.in);
		if (scan.nextLine().equals("y")) {
			displayOrder();
			changeOrder();
		} 
		
		System.out.println("Total Amount is:Rs. "+totalAmount());
		System.out.println("Thanks for Purchase");
			
	}

	private double totalAmount() {
		double amount=0.0;
		try(Connection connection=new ConnectMysql().getConnection()) {
			
			PreparedStatement st=connection.prepareStatement("Select SUM(total_price) From "+Table.getOrder()+" WHERE id="+getId()+" AND order_id="+getOrderid()+"");			
			ResultSet rs=st.executeQuery();
			rs.next();
			amount=rs.getDouble(1);
			rs.close();
			st.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}


	private void displayOrder() {
		try(Connection connection=new ConnectMysql().getConnection()) {
			System.out.println("item id item quantity  item price");
			PreparedStatement st=connection.prepareStatement("Select item_id,quantity,total_price From "+Table.getOrder()+" WHERE id="+getId()+" AND order_id="+getOrderid()+"");			
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getLong(1)+"\t"+rs.getString(2)+"\t"+rs.getLong(3)+"\t");
			}
			rs.close();
			st.close();
		}catch (SQLException e) {
			// TODO: handle exception
		}
		
	}

	private void changeOrder() {
		try(Connection connection=new ConnectMysql().getConnection()) {
		long itemid = 0;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		PreparedStatement pstmt = connection.prepareStatement("UPDATE "+Table.getOrder()+" SET quantity=?,total_price=?  WHERE item_id=? AND id="+getId()+" AND order_id="+getOrderid()+"");
		PreparedStatement pstm = connection.prepareStatement("UPDATE "+Table.getItem()+" SET item_quantity=?  WHERE item_id=?");
		while (true) {
			System.out.println("Item ID: ");
			 itemid=Long.parseLong(br.readLine());
			System.out.println("How many quantity are reduced:");
			long reduce_qnty=Long.parseLong(br.readLine());
			long actualqnt=currentQnt(itemid)-reduce_qnty;
			pstmt.setLong(1, actualqnt);
			long reduceQuntity=(Table.getItemQuantity(itemid))+(reduce_qnty);
			pstm.setLong(1, reduceQuntity);
			double totalprice=actualqnt*Table.getItemPrice(itemid);
			pstmt.setDouble(2,totalprice );
			pstmt.setLong(3, itemid);
			pstm.setLong(2, itemid);
			pstm.executeUpdate();
			pstmt.executeUpdate();
			System.out.println("Do you want to continue to reduce another quantities? (y/n) ");
			if (br.readLine().equals("n"))
				break;
		}
		}catch (SQLException | NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private long currentQnt(long item_id) {
		long qnt = 0;
		try(Connection connection=new ConnectMysql().getConnection()){
			
			Statement st=connection.createStatement();
			String sql="Select quantity From "+Table.getOrder()+" WHERE item_id="+item_id+" AND id="+getId()+" AND order_id="+getOrderid()+"";
			ResultSet rs=st.executeQuery(sql);
			rs.next();
			qnt= rs.getLong(1);
			rs.close();
			st.close();
		}catch (SQLException e) {
			
		}
		return qnt;
	}


	private void purchaseItems() {
		long itemid;
		try(Connection connection=new ConnectMysql().getConnection()){BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		if(!Table.checkTable(Table.getOrder())) {
			Table obj = new Table();
			obj.createOrderTable();
		
		}
			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO "+Table.getOrder()+" VALUES(?, ?, ?, ?, ?)");
			PreparedStatement pstm = connection.prepareStatement("UPDATE "+Table.getItem()+" SET item_quantity=?  WHERE item_id=?");
			pstmt.setLong(1, getOrderid());
			
			pstmt.setLong(3,getId() );
			while (true) {
				System.out.println("Item ID: ");
				 itemid=Long.parseLong(br.readLine());
				
				pstmt.setLong(2, itemid);
				
				System.out.println("Item Quanity: ");
				long qnt=Long.parseLong(br.readLine());
				 pstm.setLong(1, (Table.getItemQuantity(itemid))-qnt);
				 pstm.setLong(2, itemid);
				pstmt.setLong(4, qnt);
				pstmt.setLong(5, total(itemid,qnt));
				pstmt.addBatch();
				pstm.addBatch();

				System.out.println("Do you want to continue? (y/n) ");
				if (br.readLine().equals("n"))
					break;
			}

			int[] i = pstmt.executeBatch();
			pstm.executeBatch();
			
			
		
		} catch (SQLException | NumberFormatException | IOException e) {
			// TODO: handle exception
		}
		
	}

	private long total(long itemid, long qnt) {
		long total=0;
		try(Connection connection=new ConnectMysql().getConnection()){
			
			PreparedStatement pst=connection.prepareStatement("Select item_price From "+Table.getItem()+" WHERE item_id="+itemid+"");
			ResultSet rs=pst.executeQuery();
			rs.next();
			long price= rs.getLong(1);
			total=price*qnt;
			rs.close();
			pst.close();
		}catch (SQLException e) {
			
		}
		return total;
		
	}

	private long orderID() {
		 Date dNow = new Date();
	        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
	        long datetime =Long.parseLong(ft.format(dNow));
	        return datetime;	
	}

}
