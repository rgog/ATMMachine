package com.atm.machine.atmmachine.data;

public class AccountTransaction {

	private BankAccount bankAccount;
	private int withdrawalAmount;

	public AccountTransaction() {
	}

	public AccountTransaction(BankAccount bankAccount, int withdrawalAmount) {
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
