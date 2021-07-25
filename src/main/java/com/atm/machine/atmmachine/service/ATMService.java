package com.atm.machine.atmmachine.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.ATM;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.repository.ATMMachineRepository;

@Service
public class ATMService {
	
	@Autowired
	private ATMMachineRepository atmMachineRepository;

	public List<ATM> getBillsCount() {
		List<ATM> denominationsCount = new ArrayList<ATM>();
        this.atmMachineRepository.findAll().forEach(denominationsCount::add);
        return denominationsCount;
	}

	public int getBalance() {
		int balance = 0;
		List<ATM> denominationsCount = new ArrayList<ATM>();
		this.atmMachineRepository.findAll().forEach(denominationsCount::add);
		for (ATM atmMachineDenomination : denominationsCount) {
			balance +=atmMachineDenomination.getBillDenomination()*atmMachineDenomination.getNumberOfBills();
		}
		return balance;
	}

	public List<ATM> withdrawMoney(int amount) throws BankAccountGenericException {
		List<ATM> withdrawalDenominations = new ArrayList<ATM>();
		List<ATM> billsInAtm = getBillsCount();
		Map<Integer, Integer> denominationsCountMap = billsInAtm.stream().collect(Collectors.toMap(ATM::getBillDenomination, ATM::getNumberOfBills));
		int minDenomination = getMinimumDenomination(denominationsCountMap);
		if(amount%minDenomination!=0) {
			throw new BankAccountGenericException("Withdraw in multiples of " + minDenomination);
		}
		if(amount > getBalance()) {
			throw new BankAccountGenericException("ATM is short of cash. Max available = " + getBalance());
		}
		SortedSet<Integer> denominationsInAtm = new TreeSet<>(denominationsCountMap.keySet()).descendingSet();
		for (Integer denomination : denominationsInAtm) {
			ATM withdrawalDenomination = new ATM();
			if(denomination<=amount) {
				withdrawalDenomination.setBillDenomination(denomination);
				int billCountWithdrawable = getWithdrawableBillCount(amount/denomination, denominationsCountMap.get(denomination));
				withdrawalDenomination.setNumberOfBills(billCountWithdrawable);
				withdrawalDenominations.add(withdrawalDenomination);
				amount = amount - (denomination * billCountWithdrawable);
				if(amount == 0) {
					break;
				}
			}			
		}
		if(amount != 0) {
			throw new BankAccountGenericException("ATM is unable to dispense this amount due to lack of denominations. Please try some other amount.");
		} else {
			updateATMBillsCount(billsInAtm, withdrawalDenominations);
			return withdrawalDenominations;
		}
	}
	
	private void updateATMBillsCount(List<ATM> billsInAtm, List<ATM> withdrawalDenominations) {
		Map<Integer, Integer> billsWithdrawnMap = withdrawalDenominations.stream().collect(Collectors.toMap(ATM::getBillDenomination, ATM::getNumberOfBills));
		for (ATM billInAtm : billsInAtm) {
			if(billsWithdrawnMap.containsKey(billInAtm.getBillDenomination())) {
				billInAtm.setNumberOfBills(billInAtm.getNumberOfBills() - billsWithdrawnMap.get(billInAtm.getBillDenomination()));
			}
		}
		atmMachineRepository.saveAll(billsInAtm);
	}

	private int getWithdrawableBillCount(int countRequired, Integer countAvailable) {
		if(countRequired <= countAvailable) {
			return countRequired;
		} else {
			return countAvailable;
		}
	}

	public Integer getMinimumDenomination(Map<Integer, Integer> denominationsCount) {
		return Collections.min(denominationsCount.keySet());
	}

}
