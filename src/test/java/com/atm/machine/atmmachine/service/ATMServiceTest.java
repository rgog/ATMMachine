package com.atm.machine.atmmachine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.ATM;
import com.atm.machine.atmmachine.repository.ATMMachineRepository;

public class ATMServiceTest {
	@InjectMocks
	private ATMService atmService;
	@Mock
	private ATMMachineRepository atmMachineRepository;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		List<ATM> atmBillsAndCount = new ArrayList<>();
		ATM atmBillAndCount1 = new ATM(50, 10);
		ATM atmBillAndCount2 = new ATM(20, 30);
		ATM atmBillAndCount3 = new ATM(10, 30);
		ATM atmBillAndCount4 = new ATM(5, 20);
		atmBillsAndCount.add(atmBillAndCount1);
		atmBillsAndCount.add(atmBillAndCount2);
		atmBillsAndCount.add(atmBillAndCount3);
		atmBillsAndCount.add(atmBillAndCount4);
		when(atmMachineRepository.findAll()).thenReturn(atmBillsAndCount);

	}
	
	@Test
	public void testGetBillsCount() {
		List<ATM> retrievedAtmBillsAndCount = atmService.getBillsCount();
        assertEquals(4, retrievedAtmBillsAndCount.size());
        verify(atmMachineRepository, times(1)).findAll();
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(1500, atmService.getBalance());
	}
	@Test
	public void testWithdrawMoneyWhenAmountNotMultipleOfMminDenomination() throws BankAccountGenericException {
		Exception ex = assertThrows(BankAccountGenericException.class, ()->atmService.withdrawMoney(2));
		assertTrue(ex.getMessage().contains("Withdraw in multiples of"));
	}
	
	
	@Test
	public void testWithdrawMoneyWhenAtmShort() throws BankAccountGenericException {
		Exception ex = assertThrows(BankAccountGenericException.class, ()->atmService.withdrawMoney(2000));
		assertTrue(ex.getMessage().contains("ATM is short of cash."));
	}
	
	@Test
	public void testWithdrawMoneyWhenAtmLacksDenominations() throws BankAccountGenericException {
		List<ATM> atmBillsAndCount = new ArrayList<>();
		ATM atmBillAndCount1 = new ATM(50, 1);
		ATM atmBillAndCount2 = new ATM(20, 0);
		ATM atmBillAndCount3 = new ATM(10, 0);
		ATM atmBillAndCount4 = new ATM(5, 0);
		atmBillsAndCount.add(atmBillAndCount1);
		atmBillsAndCount.add(atmBillAndCount2);
		atmBillsAndCount.add(atmBillAndCount3);
		atmBillsAndCount.add(atmBillAndCount4);
		when(atmMachineRepository.findAll()).thenReturn(atmBillsAndCount);
		Exception ex = assertThrows(BankAccountGenericException.class, ()->atmService.withdrawMoney(20));
		assertTrue(ex.getMessage().contains("ATM is unable to dispense this amount"));
	}

	@Test
	public void testWithdrawMoneyWhenAtmNotShort() throws BankAccountGenericException {
		List<ATM> withdrawnBillsAndCount = atmService.withdrawMoney(115);
		Map<Integer, Integer> billsWithdrawnMap = withdrawnBillsAndCount.stream().collect(Collectors.toMap(ATM::getBillDenomination, ATM::getNumberOfBills));
		assertEquals(2, billsWithdrawnMap.get(50));
		assertEquals(1, billsWithdrawnMap.get(10));
		assertEquals(1, billsWithdrawnMap.get(5));
	}
	
}
