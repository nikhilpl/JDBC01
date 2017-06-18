package com.jdbc.infocampus;

import java.util.Scanner;

public class Home {
	private Scanner scanner;

	public void main() {
		System.out.println("------SONY Mobiles--------");
		System.out.println("1. Registration");
		System.out.println("2. Login");
		System.out.println("3. Exit");
		System.out.print("Please enter the choice:");
		scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		switch (choice) {
		case 1:
			new Registration().newRegistration();
			new Login().loginCustomer();
			break;
		case 2:
		
			new Login().loginCustomer();
			break;
		case 3:
			System.exit(0);
		default:
			System.out.println("Sorry! You're enter WRONG choice!!!");
			break;
		}

	}

}
