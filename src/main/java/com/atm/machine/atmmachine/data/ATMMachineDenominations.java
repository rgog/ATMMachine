package com.atm.machine.atmmachine.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ATM_MACHINE")
public class ATMMachineDenominations {
	
	@Id
	@Column(name="ID")
	@GeneratedValue
	private int id;
	@Column(name="BILL_DENOMINATION")
	private int billDenomination;
	@Column(name="NUMBER_OF_BILLS")
	private int numberOfBills;
	
	public ATMMachineDenominations() {
		
	}
	
	public ATMMachineDenominations(int id, int billDenomination, int numberOfBills) {
		super();
		this.id = id;
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
