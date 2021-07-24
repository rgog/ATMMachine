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
import com.atm.machine.atmmachine.data.ATMMachineDenominations;
import com.atm.machine.atmmachine.repository.ATMMachineRepository;

@Service
public class ATMService {
	
	private ATMMachineRepository atmMachineRepository;

	@Autowired
	public ATMService(ATMMachineRepository atmMachineRepository) {
		super();
		this.atmMachineRepository = atmMachineRepository;
	}

	public List<ATMMachineDenominations> getBillsCount() {
		List<ATMMachineDenominations> denominationsCount = new ArrayList<ATMMachineDenominations>();
        this.atmMachineRepository.findAll().forEach(denominationsCount::add);
        return denominationsCount;
	}

	public int getBalance() {
		int balance = 0;
		List<ATMMachineDenominations> denominationsCount = new ArrayList<ATMMachineDenominations>();
		this.atmMachineRepository.findAll().forEach(denominationsCount::add);
		for (ATMMachineDenominations atmMachineDenomination : denominationsCount) {
			balance +=atmMachineDenomination.getBillDenomination()*atmMachineDenomination.getNumberOfBills();
		}
		return balance;
	}

	public List<ATMMachineDenominations> withDrawMoney(int amount) throws BankAccountGenericException {
		List<ATMMachineDenominations> withdrawalDenominations = new ArrayList<ATMMachineDenominations>();
		List<ATMMachineDenominations> billsInAtm = getBillsCount();
		Map<Integer, Integer> denominationsCountMap = billsInAtm.stream().collect(Collectors.toMap(ATMMachineDenominations::getBillDenomination, ATMMachineDenominations::getNumberOfBills));
		int minDenomination = getMinimumDenomination(denominationsCountMap);
		if(amount%minDenomination!=0) {
			throw new BankAccountGenericException("Withdraw in multiples of " + minDenomination);
		}
		SortedSet<Integer> denominationsInAtm = new TreeSet<>(denominationsCountMap.keySet()).descendingSet();
		for (Integer denomination : denominationsInAtm) {
			ATMMachineDenominations withdrawalDenomination = new ATMMachineDenominations();
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
			throw new BankAccountGenericException("ATM is short of cash. Max withdrawable amount = " + getBalance());
		} else {
			updateATMBillsCount(billsInAtm, withdrawalDenominations);
			return withdrawalDenominations;
		}
	}
	
	private void updateATMBillsCount(List<ATMMachineDenominations> billsInAtm, List<ATMMachineDenominations> withdrawalDenominations) {
		Map<Integer, Integer> billsWithdrawnMap = withdrawalDenominations.stream().collect(Collectors.toMap(ATMMachineDenominations::getBillDenomination, ATMMachineDenominations::getNumberOfBills));
		for (ATMMachineDenominations billInAtm : billsInAtm) {
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
