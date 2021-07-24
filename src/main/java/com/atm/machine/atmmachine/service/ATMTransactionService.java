package com.atm.machine.atmmachine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.ATMMachineDenominations;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.repository.BankAccountRepository;

@Service
public class ATMTransactionService {
	
	private BankAccountRepository bankAccountRepository;
	private ATMService atmService;
	
	@Autowired
	public ATMTransactionService(BankAccountRepository bankAccountRepository, ATMService atmService) {
		super();
		this.bankAccountRepository = bankAccountRepository;
		this.atmService = atmService;
	}
	public List<BankAccount> getAllAccounts(){
    	List<BankAccount> accounts = new ArrayList<BankAccount>();
        this.bankAccountRepository.findAll().forEach(accounts::add);
        return accounts;
    }
	
	public BankAccount getAccountByAccountNumber(BankAccount bankAccount) throws BankAccountGenericException{
        return this.bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber());
	}
	
	public List<ATMMachineDenominations> withdrawMoney(BankAccount bankAccount, int amount) throws BankAccountGenericException {
		List<ATMMachineDenominations> atmMachineDenominations = new ArrayList<>();
		BankAccount account = getAccountByAccountNumber(bankAccount);
		if(amount <= account.getWithdrawalLimit()) {
			atmMachineDenominations =  atmService.withDrawMoney(amount);
			account.setOpeningBalance(account.getOpeningBalance()-amount);
			bankAccountRepository.save(account);
			return atmMachineDenominations;
		} else {
			throw new BankAccountGenericException("Cannot withdraw the entered amount. Max Allowed = " + account.getWithdrawalLimit());
		}
		
	}
}
