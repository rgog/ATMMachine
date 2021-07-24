package com.atm.machine.atmmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.service.ATMTransactionService;

@RestController
@RequestMapping("/api")
public class ATMTransactionController {
	private ATMTransactionService atmTransactionService;
	
	@Autowired
	public ATMTransactionController(ATMTransactionService atmTransactionService) {
		this.atmTransactionService = atmTransactionService;
	}
	
	@GetMapping("/accounts")
    public List<BankAccount> getAllAccounts(){
        return this.atmTransactionService.getAllAccounts();
    }
	
	@PostMapping("/account")
    public BankAccount getAccountByAccountNumber(@RequestBody BankAccount bankAccount) throws BankAccountGenericException{
        return this.atmTransactionService.getAccountByAccountNumber(bankAccount);
    }
}
