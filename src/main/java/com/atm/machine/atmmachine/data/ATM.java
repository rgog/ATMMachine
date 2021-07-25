package com.atm.machine.atmmachine.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ATM_MACHINE")
public class ATM {
	
	@Id
	@Column(name="ID")
	@GeneratedValue
	private int id;
	@Column(name="BILL_DENOMINATION")
	private int billDenomination;
	@Column(name="NUMBER_OF_BILLS")
	private int numberOfBills;
	
	public ATM() {
	}

	public ATM(int billDenomination, int numberOfBills) {
		this.billDenomination = billDenomination;
		this.numberOfBills = numberOfBills;
	}
	
	public int getBillDenomination() {
		return billDenomination;
	}
	public void setBillDenomination(int billDenomination) {
		this.billDenomination = billDenomination;
	}
	public int getNumberOfBills() {
		return numberOfBills;
	}
	public void setNumberOfBills(int numberOfBills) {
		this.numberOfBills = numberOfBills;
	}
}
