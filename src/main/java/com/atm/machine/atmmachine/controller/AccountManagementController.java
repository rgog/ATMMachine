package com.atm.machine.atmmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.ATM;
import com.atm.machine.atmmachine.data.AccountTransaction;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.data.MoneyWithdrawalResponseObject;
import com.atm.machine.atmmachine.service.AccountManagementService;
import com.atm.machine.atmmachine.service.AuthService;

@RestController
@RequestMapping("/accountmgmt")
public class AccountManagementController {
	
	@Autowired
	private AccountManagementService accountManagementService;
	@Autowired
	private AuthService authService;
	
	@PostMapping("/account")
    public BankAccount getAccountByAccountNumber(@RequestBody BankAccount bankAccount, @RequestHeader String authToken) throws BankAccountGenericException{
		authService.validateAuthToken(authToken, bankAccount);
        return this.accountManagementService.getAccountByAccountNumber(bankAccount);
    }
	
	@PostMapping("/withdraw")
	public MoneyWithdrawalResponseObject withdrawMoney(@RequestBody AccountTransaction atmTransaction, @RequestHeader String authToken) throws BankAccountGenericException {
		authService.validateAuthToken(authToken, atmTransaction.getBankAccount());
		return this.accountManagementService.withdrawMoney(atmTransaction.getBankAccount(), atmTransaction.getWithdrawalAmount());
	}
}
