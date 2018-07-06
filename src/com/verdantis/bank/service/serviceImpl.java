package com.verdantis.bank.service;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.verdantis.bank.bo.custPojo;
import com.verdantis.bank.controller.controller;
import com.verdantis.bank.dao.daoImpl;
import com.verdantis.bank.exceptionB.InvalidBalanceException;
import com.verdantis.bank.exceptionB.ResourseNotFoundException;

public class serviceImpl {
	daoImpl d=new daoImpl();	
public void deposit(int accNo,int money) 
{
	if(d.checkAccountNo(accNo))
	{
	try{
			 
	int balance=d.getBalance(accNo);
	System.out.println("balance is "+balance);
	balance+=money;
	d.updateBalance(accNo,balance);
	int bal=d.getBalance(accNo);
	System.out.println("after deposit "+bal);
	//d.putIntTransactFile(accNo,balance);
	controller.flag=1;
	}catch(Exception e)
	{
		controller.log.error(e);
	}
	}
	else
	{
		try {
			throw new ResourseNotFoundException("NO SUCH ACCOUNT");
		} catch (ResourseNotFoundException e) {
			controller.log.error(e);
		}
	}
}

public void withdraw(int accNo,int money) 
{
	if(d.checkAccountNo(accNo))
	{
{
	try{	
		
	
	int balance=d.getBalance(accNo);
	if(balance<money)
	{		
			throw new InvalidBalanceException("INVALID BALANCE");		
	}
	else
	{
	balance-=money;
	d.updateBalance(accNo,balance);
	//d.putIntTransactFile(accNo,balance);
	controller.flag=1;
	}
	}catch(Exception e)
	{
		controller.log.error(e);
	}
}
	} else
		try {
			throw new ResourseNotFoundException("NO SUCH ACCOUNT");
		} catch (ResourseNotFoundException e) {
			controller.log.error(e);
		}
		
}

public void transact(int accNo1,int accNo2,int money) 
{
	try{
	
	if(d.checkAccountNo(accNo1)==false)
	{
		//System.out.println("falseeeee");
		throw new ResourseNotFoundException("NO SUCH ACCOUNT");
		
	}
	if(d.checkAccountNo(accNo2)==false)
		throw new ResourseNotFoundException("NO SUCH ACCOUNT");
	int balance1=d.getBalance(accNo1);
	int balance2=d.getBalance(accNo2);
	if(balance1<money)
	{
			throw new InvalidBalanceException("INVALID BALANCE");
	}
	else{
		balance1-=money;
		balance2+=money;
		d.updateBalance(accNo1,balance1);
		d.updateBalance(accNo2,balance2);
	}
	
	
	d.putIntTransactFile(accNo1, balance1,accNo2,balance2);
	controller.flag=1;
	//d.putIntTransactFile(accNo2, balance2);
	}catch(Exception e)
	{
		
		controller.log.error(e);
	}
}


public void addCust(custPojo customer)
{
	d.addCustomer(customer);
}

public custPojo getDetail(int accNo) {
	
	d.checkAccountNo(accNo);
	custPojo cust=d.getDetail(accNo);
	return cust;
}

public void putInSeparateTranFile(Document document, PdfPTable table,int accNo) {
	d.putInSeparateTranFile(document, table,accNo);	
}

public void showCustDetails(int accNo) {
	controller.flag2=0;
	try{
	if(d.checkAccountNo(accNo)==false)
	{
		//controller.flag=0;
		throw new ResourseNotFoundException("NO SUCH ACCOUNT");		
	}
	else
	{
		controller.flag2=1;
	}
	}catch(Exception e)
	{
		controller.log.error(e);
	}
	
	
	if(controller.flag2==1)
	{
	d.showCustTransactions(accNo);
	}
}

}