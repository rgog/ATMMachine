package com.atm.machine.atmmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.ATMMachineDenominations;
import com.atm.machine.atmmachine.data.ATMTransaction;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.service.ATMTransactionService;
import com.atm.machine.atmmachine.service.AuthService;

@RestController
@RequestMapping("/api")
public class ATMTransactionController {
	private ATMTransactionService atmTransactionService;
	private AuthService authService;
	
	@Autowired
	public ATMTransactionController(ATMTransactionService atmTransactionService, AuthService authService) {
		this.atmTransactionService = atmTransactionService;
		this.authService = authService;
	}
	
	@GetMapping("/accounts")
    public List<BankAccount> getAllAccounts(){
        return this.atmTransactionService.getAllAccounts();
    }
	
	@PostMapping("/account")
    public BankAccount getAccountByAccountNumber(@RequestBody BankAccount bankAccount, @RequestHeader String authToken) throws BankAccountGenericException{
		authService.validateAuthToken(authToken, bankAccount.getAccountNumber());
        return this.atmTransactionService.getAccountByAccountNumber(bankAccount);
    }
	
	@PostMapping("/withdraw")
	public List<ATMMachineDenominations> withdrawMoney(@RequestBody ATMTransaction atmTransaction, @RequestHeader String authToken) throws BankAccountGenericException {
		authService.validateAuthToken(authToken, atmTransaction.getBankAccount().getAccountNumber());
		return this.atmTransactionService.withdrawMoney(atmTransaction.getBankAccount(), atmTransaction.getWithdrawalAmount());
	}
}
