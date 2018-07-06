package com.verdantis.bank.controller;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.verdantis.bank.bo.custPojo;
import com.verdantis.bank.service.serviceImpl;
import java.io.File;
public class controller {	
public static File file=new File("workbook.xls");
public static Logger log=Logger.getLogger(controller.class);	
private static Scanner sc;
public static int flag=0;	
public static int flag2=0;
static serviceImpl s=new serviceImpl();	
public static void main(String args[]) { 
	 
		int op=0;
		sc = new Scanner(System.in);
		do{
			try{
			System.out.println("press\n 1.ADD CUSTOMER\n 2.DEPOSIT CASH\n 3.WITHDRAW CASH\n 4.TRANSACT\n 5.LIST CUSTOMER DETAILS\n 6.SHOW TRANSACTIONS");
			op=sc.nextInt();
			}catch(Exception e)
			{
				System.out.println("INPUT MISMATCH");
				log.error(e);
				break;
			}
			switch(op)
			{			
			case 1:
			{
				addCustomer();	
				System.out.println("SUCCESSFULLY ADDED");
				break;				
			}
			case 2:
			{
				flag=0;
				deposit();
				if(flag==1)
					System.out.println("SUCCESSFULLY DEPOSITED");
					else
						System.out.println("UNSUCCESSFUL");
				break;
			}		
			case 3:
			{	
				flag=0;
				withdraw();
				if(flag==1)
				System.out.println("SUCCESSFULLY WITHDREW");
				else
					System.out.println("UNSUCCESSFUL");
				break;
			}
			case 4:
			{	
					flag=0;
					transact();	
					if(flag==1)
					System.out.println("SUCCESSFUL");
					else
						System.out.println("UNSUCCESSFUL");
				break;
			}
			case 5:
			{				
				printCustDetails();
				break;
			}
			case 6:
			{
				showCustTransactions();
				break;
			}
			}
						
			}while(op!=10);
		
			
	}

private static void showCustTransactions() {
	int accNo=0;
	System.out.println("ENTER ACCOUNT NO");
	try
	{
	accNo=sc.nextInt();	
	s.showCustDetails(accNo);
	if(flag2==1)
	{		
	System.out.println("TRANSACTIONS ARE STORED AT LOCATION");
	System.out.println("D:/sadia/BankExcel/tran"+accNo+".pdf");
	}
	else
	{
		System.out.println("INVALID");
	}
	}catch(Exception e)
	{
		System.out.println("INPUT MISMATCH");
		log.error(e);		
	}
	
}

private static void withdraw() {

	System.out.println("ENTER ACCOUNT NO");
	int accNo=sc.nextInt();
	try{		
	System.out.println("ENTER AMOUNT TO WITHDRAW");
	int money=sc.nextInt();
	try{
		serviceImpl s=new serviceImpl();
		s.withdraw(accNo,money);
	}catch(Exception e)
	{
		System.out.println("INVALID BALANCE");
		log.error(e);
	}
	}catch(Exception e)
	{
		System.out.println("NO SUCH SUCH ACCOUNT");
		log.error(e);
	}
}

private static void printCustDetails() {
	System.out.println("ENTER YOUR ACCOUNT NO");
	int accNo=sc.nextInt();
	try{	
		
		custPojo customer=s.getDetail(accNo);		
		String name=customer.getName();
		String address=customer.getAddress();
		int balance=customer.getBalance();

		System.out.println("NAME IS "+name);		
		System.out.println("ADDRESS IS "+address);		
		System.out.println("BALANCE IS "+balance);
		
		
	}catch(Exception e)
	{
		log.error(e);		
	}
		
}

private static void transact() {
		try{
			System.out.println("ENTER ACCOUNT NO");
			int accNo1=sc.nextInt();
			System.out.println("ENTER OTHER ACCOUNT NO");
			int accNo2=sc.nextInt();
			System.out.println("ENTER AMOUNT TO DEPOSIT");
			int money=sc.nextInt();
			
			s.transact(accNo1,accNo2,money);
		}		
		catch(Exception e)
		{
			log.error(e);
		}
	}

private static void deposit() {
		try{
			
			System.out.println("ENTER ACCOUNT NO");
			int accNo1=sc.nextInt();
		
			System.out.println("ENTER AMOUNT TO DEPOSIT");
			int money=sc.nextInt();
			
			s.deposit(accNo1,money);
		}		
		catch(Exception e)
		{
			System.out.println("NO SUCH SUCH ACCOUNT");
			log.error(e);
		}
		
	}

private static void addCustomer() {
		try{			
			System.out.println("ENTER ACCOUNT NO, NAME, ADDRESS, BALANCE, BRANCH ID");
			int accNo=sc.nextInt();
			String custName=sc.next();
			String address=sc.next();
			int balance=sc.nextInt();
			int branchId=sc.nextInt();
			custPojo custObj=new custPojo(accNo,custName,address,balance,branchId);
			s.addCust(custObj);
		}		
		catch(Exception e)
		{
			log.error(e);
		}		
	}


}
