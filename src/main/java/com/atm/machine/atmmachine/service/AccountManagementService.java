package com.atm.machine.atmmachine.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.ATM;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.data.MoneyWithdrawalResponseObject;
import com.atm.machine.atmmachine.repository.BankAccountRepository;

@Service
public class AccountManagementService {
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	@Autowired
	private ATMService atmService;
	
	public List<BankAccount> getAllAccounts(){
    	List<BankAccount> accounts = new ArrayList<BankAccount>();
        this.bankAccountRepository.findAll().forEach(accounts::add);
        return accounts;
    }
	
	public BankAccount getAccountByAccountNumber(BankAccount bankAccount) throws BankAccountGenericException{
	    return bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber());
	}
	
	public MoneyWithdrawalResponseObject withdrawMoney(BankAccount bankAccount, int amount) throws BankAccountGenericException {
		List<ATM> atmMachineDenominations = new ArrayList<>();
		BankAccount account = this.getAccountByAccountNumber(bankAccount);
		if(amount <= account.getWithdrawalLimit()) {
			atmMachineDenominations =  atmService.withdrawMoney(amount);
			account.setOpeningBalance(account.getOpeningBalance()-amount);
			bankAccountRepository.save(account);
			return new MoneyWithdrawalResponseObject(atmMachineDenominations, account.getOpeningBalance());
		} else {
			throw new BankAccountGenericException("Cannot withdraw the entered amount. Max Allowed = " + account.getWithdrawalLimit());
		}
		
	}
}
