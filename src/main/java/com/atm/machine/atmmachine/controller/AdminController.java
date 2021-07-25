package com.atm.machine.atmmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.machine.atmmachine.data.ATM;
import com.atm.machine.atmmachine.data.Auth;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.service.ATMService;
import com.atm.machine.atmmachine.service.AccountManagementService;
import com.atm.machine.atmmachine.service.AuthService;

@RestController
@Profile("!prod")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ATMService atmAdminService;
	@Autowired
	private AuthService authService;
	@Autowired
	private AccountManagementService accountManagementService;
	
	@GetMapping("/bills")
    public List<ATM> getBillsCount(){
        return this.atmAdminService.getBillsCount();
    }
	
	@GetMapping("/balance")
    public int getBalance(){
        return this.atmAdminService.getBalance();
    }
	
	@GetMapping("authlogs")
	public List<Auth> getAuthLogs(){
		return this.authService.getAuthLogs();
	}
	
	@GetMapping("/accounts")
    public List<BankAccount> getAllAccounts(){
        return this.accountManagementService.getAllAccounts();
    }
}
