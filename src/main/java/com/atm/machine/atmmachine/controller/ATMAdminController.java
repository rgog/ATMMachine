package com.atm.machine.atmmachine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atm.machine.atmmachine.data.ATMMachineDenominations;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.service.ATMService;

@RestController
@RequestMapping("/atmadmin")
public class ATMAdminController {
	
	private ATMService atmAdminService;
	
	@Autowired
	public ATMAdminController(ATMService atmAdminService) {
		this.atmAdminService = atmAdminService;
	}
	
	@GetMapping("/bills")
    public List<ATMMachineDenominations> getBillsCount(){
        return this.atmAdminService.getBillsCount();
    }
	
	@GetMapping("/balance")
    public int getBalance(){
        return this.atmAdminService.getBalance();
    }
}
