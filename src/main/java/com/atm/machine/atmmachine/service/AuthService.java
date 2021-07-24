package com.atm.machine.atmmachine.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.data.BankAccountAuthentication;
import com.atm.machine.atmmachine.repository.AuthRepository;
import com.atm.machine.atmmachine.repository.BankAccountRepository;

@Service
public class AuthService {

	private AuthRepository authRepository;
	private BankAccountRepository bankAccountRepository;
	
	public AuthService(AuthRepository authRepository, BankAccountRepository bankAccountRepository) {
		super();
		this.authRepository = authRepository;
		this.bankAccountRepository = bankAccountRepository;
	}

	
	public BankAccountAuthentication authenticateUser(BankAccount bankAccount) throws BankAccountGenericException {
		BankAccount account = this.bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber());
		if(account == null) {
        	throw new BankAccountGenericException("Account Not Found. Please enter a valid account number.");
        }
        if(account.getPin().equals(bankAccount.getPin())) {
        	BankAccountAuthentication oldBankAccountAuthentication = this.authRepository.findByAccountNumberAndValid(bankAccount.getAccountNumber(), true);
        	if(oldBankAccountAuthentication != null) {
        		oldBankAccountAuthentication.setValid(false);
        		this.authRepository.save(oldBankAccountAuthentication);
        	}
        	BankAccountAuthentication bankAccountAuthentication = new BankAccountAuthentication(account.getAccountNumber(), UUID.randomUUID().toString(), LocalDateTime.now());
        	authRepository.save(bankAccountAuthentication);
        	return bankAccountAuthentication;
        }
        throw new BankAccountGenericException("Incorrect Account PIN");
	}


	public List<BankAccountAuthentication> getAuthLogs() {
		List<BankAccountAuthentication> authLogs = new ArrayList<BankAccountAuthentication>();
        this.authRepository.findAll().forEach(authLogs::add);
        return authLogs;
	}


	public void validateAuthToken(String authToken, int accountNumber) throws BankAccountGenericException {
		BankAccountAuthentication bankAccountAuthentication = this.authRepository.findByAccountNumberAndValid(accountNumber, true);
		if(!bankAccountAuthentication.getToken().equals(authToken) || ChronoUnit.MINUTES.between(bankAccountAuthentication.getTimestamp(), LocalDateTime.now())>30){
			throw new BankAccountGenericException("Auth token invalid. Please login first.");
		}
	}

}
