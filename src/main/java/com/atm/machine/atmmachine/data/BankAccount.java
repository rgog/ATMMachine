package com.atm.machine.atmmachine.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BANK_ACCOUNT")
public class BankAccount {
	@Id
	@Column(name="ACCOUNT_ID")
	@GeneratedValue
	private int accountId;
	@Column(name="ACCOUNT_NUMBER")
	private int accountNumber;
	@Column(name="PIN")
	private String pin;
	@Column(name="OPENING_BALANCE")
	private double openingBalance;
	@Column(name="OVERDRAFT")
	private double overdraft;
	
	public BankAccount() {
		
	}
	
	public BankAccount(int accountId, int accountNumber, String pin, double openingBalance, double overdraft) {
		super();
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.openingBalance = openingBalance;
		this.overdraft = overdraft;
	}
	
	public BankAccount(int accountNumber, String pin, double openingBalance, double overdraft) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.openingBalance = openingBalance;
		this.overdraft = overdraft;
	}
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public double getOpeningBalance() {
		return openingBalance;
	}
	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}
	public double getOverdraft() {
		return overdraft;
	}
	public void setOverdraft(double overdraft) {
		this.overdraft = overdraft;
	}
	
	public double getWithdrawalLimit() {
		return this.openingBalance + this.overdraft;
	}
	
}
