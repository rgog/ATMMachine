package com.atm.machine.atmmachine.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AUTHENTICATION_LOG")
public class BankAccountAuthentication {
	
	@Id
	@Column(name="ID")
	@GeneratedValue
	private int id;
	@Column(name="ACCOUNT_NUMBER")
	private int accountNumber;
	@Column(name="TOKEN")
	private String token;
	@Column(name="TOKEN_ISSUE_TIME")
	private LocalDateTime timestamp;
	@Column(name="VALID")
	private boolean valid;
	
	public BankAccountAuthentication() {
		
	}
	
	public BankAccountAuthentication(int id, int accountNumber, String token, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.token = token;
		this.timestamp = timestamp;
		this.valid = true;
	}
	
	public BankAccountAuthentication(int accountNumber, String token, LocalDateTime localDateTime) {
		super();
		this.accountNumber = accountNumber;
		this.token = token;
		this.timestamp = localDateTime;
		this.valid = true;
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
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
