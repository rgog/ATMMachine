package com.atm.machine.atmmachine.data;

import java.util.List;

public class MoneyWithdrawalResponseObject {

	private List<ATM> atm;
	private double balance;
	
	public MoneyWithdrawalResponseObject(List<ATM> atm, double balance) {
		this.atm = atm;
		this.balance = balance;
	}
	
	public List<ATM> getAtm() {
		return atm;
	}
	public double getBalance() {
		return balance;
	}
}
