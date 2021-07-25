package com.atm.machine.atmmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.data.Auth;
import com.atm.machine.atmmachine.service.AuthService;

@RestController
@RequestMapping("/login")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping
	public Auth authenticateUser(@RequestBody BankAccount bankAccount) throws BankAccountGenericException {
		return this.authService.authenticateUser(bankAccount);
	}
	
}
