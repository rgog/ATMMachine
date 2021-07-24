package com.atm.machine.atmmachine.data;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUTHENTICATION_LOG")
public class Authentication {
	
	@Id
	@Column(name="ACCOUNT_ID")
	@GeneratedValue
	private int id;
	@Column(name="ACCOUNT_NUMBER")
	private int accountNumber;
	@Column(name="TOKEN")
	private String token;
	@Column(name="TOKEN_ISSUE_TIME")
	private Timestamp timestamp;
	
	public Authentication() {
		
	}
	
	public Authentication(int id, int accountNumber, String token, Timestamp timestamp) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.token = token;
		this.timestamp = timestamp;
	}
	
	public Authentication(int accountNumber, String token, Timestamp timestamp) {
		super();
		this.accountNumber = accountNumber;
		this.token = token;
		this.timestamp = timestamp;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
