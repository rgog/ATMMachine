package com.atm.machine.atmmachine.data;

public class ATMTransaction {

	private BankAccount bankAccount;
	private int withdrawalAmount;

	public ATMTransaction() {

	}

	public ATMTransaction(BankAccount bankAccount, int withdrawalAmount) {
		super();
		this.bankAccount = bankAccount;
		this.withdrawalAmount = withdrawalAmount;
	}

	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public int getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(int withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}
}
