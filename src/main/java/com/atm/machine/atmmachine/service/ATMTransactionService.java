package com.atm.machine.atmmachine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.repository.BankAccountRepository;

@Service
public class ATMTransactionService {
	
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	public ATMTransactionService(BankAccountRepository bankAccountRepository) {
		super();
		this.bankAccountRepository = bankAccountRepository;
	}
	public List<BankAccount> getAllAccounts(){
    	List<BankAccount> accounts = new ArrayList<BankAccount>();
        this.bankAccountRepository.findAll().forEach(accounts::add);
        return accounts;
    }
	
	public BankAccount getAccountByAccountNumber(BankAccount bankAccount) throws BankAccountGenericException{
        BankAccount account =  this.bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber());
        if(account == null) {
        	throw new BankAccountGenericException("Account Not Found");
        }
        if(account.getPin().equals(bankAccount.getPin())) {
        	return account;
        }
        throw new BankAccountGenericException("Incorrect Account PIN");
	}
}
