package com.atm.machine.atmmachine.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.data.BankAccountAuthentication;
import com.atm.machine.atmmachine.service.AuthService;

@RestController
@RequestMapping("/login")
public class AuthController {

	private AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping
	public BankAccountAuthentication authenticateUser(@RequestBody BankAccount bankAccount) throws BankAccountGenericException {
		return this.authService.authenticateUser(bankAccount);
	}
	
	@GetMapping("authlogs")
	public List<BankAccountAuthentication> getAuthLogs(){
		return this.authService.getAuthLogs();
	}
	
	
}
