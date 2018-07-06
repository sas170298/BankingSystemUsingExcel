package com.verdantis.bank.bo;

public class custPojo {
	int accountNo;
	String name;
	String address;
	int balance;
	int branchId;
	public custPojo(int accountNo,String name,String address,int balance,int branchId)
	{
		this.accountNo=accountNo;
		this.name=name;
		this.address=address;
		this.balance=balance;
		this.branchId=branchId;
	}
	public int getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
}
