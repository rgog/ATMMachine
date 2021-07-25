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
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.data.MoneyWithdrawalResponseObject;
import com.atm.machine.atmmachine.repository.BankAccountRepository;

public class AccountManagementServiceTest {
	@InjectMocks
	private AccountManagementService accountManagementService;
	@Mock
	private BankAccountRepository bankAccountRepository;
	@Mock
	private ATMService atmService;
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAllAccounts() {
		List<BankAccount> bankAccounts = new ArrayList<BankAccount>();
		BankAccount account1 = new BankAccount(111111111, "1111", 1000, 200);
		BankAccount account2 = new BankAccount(222222222, "2222", 900, 150);
		bankAccounts.add(account1);
		bankAccounts.add(account2);
		when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
		List<BankAccount> retrievedAccounts = accountManagementService.getAllAccounts();
        assertEquals(2, retrievedAccounts.size());
        verify(bankAccountRepository, times(1)).findAll();
	}
	
	@Test
	public void testGetAccountByAccountNumber() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		when(bankAccountRepository.findByAccountNumber(111111111)).thenReturn(account);
		BankAccount retrievedAccount = accountManagementService.getAccountByAccountNumber(account);
        assertEquals("1111", retrievedAccount.getPin());
	}
	
	@Test
	public void testWithdrawMoneyWhenAmountMoreThanLimit() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		when(bankAccountRepository.findByAccountNumber(111111111)).thenReturn(account);
		Exception ex = assertThrows(BankAccountGenericException.class, ()-> accountManagementService.withdrawMoney(account, 1250));
		assertTrue(ex.getMessage().contains("Cannot withdraw the entered amount."));
	}
	
	@Test
	public void testWithdrawMoneyWhenAmountWithinLimit() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		List<ATM> atmWithdrawnBills = new ArrayList<ATM>();
		ATM withdrawal1 = new ATM(50, 2);
		ATM withdrawal2 = new ATM(10, 1);
		ATM withdrawal3 = new ATM(5, 1);
		atmWithdrawnBills.add(withdrawal1);
		atmWithdrawnBills.add(withdrawal2);
		atmWithdrawnBills.add(withdrawal3);
		when(bankAccountRepository.findByAccountNumber(111111111)).thenReturn(account);
		when(atmService.withdrawMoney(115)).thenReturn(atmWithdrawnBills);
		MoneyWithdrawalResponseObject withdrawnBillsAndCount = accountManagementService.withdrawMoney(account, 115);
		Map<Integer, Integer> billsWithdrawnMap = withdrawnBillsAndCount.getAtm().stream().collect(Collectors.toMap(ATM::getBillDenomination, ATM::getNumberOfBills));
		assertEquals(2, billsWithdrawnMap.get(50));
		assertEquals(1, billsWithdrawnMap.get(10));
		assertEquals(1, billsWithdrawnMap.get(5));
		
	}
}
