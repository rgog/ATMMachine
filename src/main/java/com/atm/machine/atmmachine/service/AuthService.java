package com.atm.machine.atmmachine.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.Auth;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.repository.AuthRepository;

@Service
public class AuthService {

	@Autowired
	private AuthRepository authRepository;
	@Autowired
	private AccountManagementService accountManagementService;
	
	public Auth authenticateUser(BankAccount bankAccount) throws BankAccountGenericException {
		BankAccount account = this.accountManagementService.getAccountByAccountNumber(bankAccount);
		if(account == null) {
        	throw new BankAccountGenericException("Account Not Found. Please enter a valid account number.");
        }
        if(account.getPin().equals(bankAccount.getPin())) {
        	Auth oldBankAccountAuthentication = this.authRepository.findByAccountNumberAndValid(bankAccount.getAccountNumber(), true);
        	if(oldBankAccountAuthentication != null) {
        		oldBankAccountAuthentication.setValid(false);
        		this.authRepository.save(oldBankAccountAuthentication);
        	}
        	Auth bankAccountAuthentication = new Auth(account.getAccountNumber(), UUID.randomUUID().toString(), LocalDateTime.now());
        	authRepository.save(bankAccountAuthentication);
        	return bankAccountAuthentication;
        }
        throw new BankAccountGenericException("Incorrect Account PIN");
	}


	public List<Auth> getAuthLogs() {
		List<Auth> authLogs = new ArrayList<Auth>();
        this.authRepository.findAll().forEach(authLogs::add);
        return authLogs;
	}


	public void validateAuthToken(String authToken, BankAccount bankAccount) throws BankAccountGenericException {
		if(bankAccount.getAccountNumber() == 0) {
			throw new BankAccountGenericException("Please add account number to the request body");
		}
		Auth bankAccountAuthentication = this.authRepository.findByAccountNumberAndValid(bankAccount.getAccountNumber(), true);
		if(bankAccountAuthentication == null || !bankAccountAuthentication.getToken().equals(authToken) || ChronoUnit.MINUTES.between(bankAccountAuthentication.getTimestamp(), LocalDateTime.now())>30){
			throw new BankAccountGenericException("Auth token invalid. Please login first.");
		}
	}

}
