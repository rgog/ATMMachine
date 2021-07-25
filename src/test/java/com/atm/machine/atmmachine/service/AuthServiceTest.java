package com.atm.machine.atmmachine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.atm.machine.atmmachine.customExceptionsAndErrors.BankAccountGenericException;
import com.atm.machine.atmmachine.data.Auth;
import com.atm.machine.atmmachine.data.BankAccount;
import com.atm.machine.atmmachine.repository.AuthRepository;

public class AuthServiceTest{
	@InjectMocks
	private AuthService authService;
	@Mock
	private AuthRepository authRepository;
	@Mock
	private AccountManagementService accountManagementService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetAuthLogs() {
		
		List<Auth> authLogs = new ArrayList<>(); 
		Auth authLog1 = new Auth(111111111, "token1", LocalDateTime.now());
		authLog1.setValid(false);
		Auth authLog2 = new Auth(111111111, "token2", LocalDateTime.now().minusMinutes(40));
		authLog2.setValid(false);
		Auth authLog3 = new Auth(111111111, "token3", LocalDateTime.now());
		Auth authLog4 = new Auth(222222222, "token4", LocalDateTime.now());
		authLogs.add(authLog1);
		authLogs.add(authLog2);
		authLogs.add(authLog3);
		authLogs.add(authLog4);
		
		when(authRepository.findAll()).thenReturn(authLogs);
		//test
        List<Auth> retrievedAuthLogs = authService.getAuthLogs();
        assertEquals(4, retrievedAuthLogs.size());
        verify(authRepository, times(1)).findAll();
	}
	
	@Test 
	public void testAuthenticateUserWhenAccountNotFound() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		when(accountManagementService.getAccountByAccountNumber(account)).thenReturn(null);
		when(authRepository.findByAccountNumberAndValid(account.getAccountNumber(), true)).thenReturn(null);
		Exception ex = assertThrows(BankAccountGenericException.class, ()->authService.authenticateUser(account)) ;
		assertTrue(ex.getMessage().contains("Account Not Found"));
	}
	
	@Test 
	public void testAuthenticateUserWhenNoOldTokenAvailable() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		when(accountManagementService.getAccountByAccountNumber(account)).thenReturn(account);
		when(authRepository.findByAccountNumberAndValid(account.getAccountNumber(), true)).thenReturn(null);
		Auth authenticationObject = authService.authenticateUser(account);
		assertEquals(authenticationObject.getAccountNumber(), account.getAccountNumber());
		assertTrue(authenticationObject.isValid());
	}
	
	@Test 
	public void testAuthenticateUserWhenOldTokenAvailable() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		Auth oldAuthToken = new Auth(111111111, "token3", LocalDateTime.now());
		assertTrue(oldAuthToken.isValid());
		when(accountManagementService.getAccountByAccountNumber(account)).thenReturn(account);
		when(authRepository.findByAccountNumberAndValid(account.getAccountNumber(), true)).thenReturn(oldAuthToken);
		Auth authenticationObject = authService.authenticateUser(account);
		assertTrue(!oldAuthToken.isValid());
		assertEquals(authenticationObject.getAccountNumber(), account.getAccountNumber());
		assertTrue(authenticationObject.isValid());
	}
	
	@Test 
	public void testAuthenticateUserWhenPinIncorrect() throws BankAccountGenericException {
		BankAccount account1 = new BankAccount(111111111, "1111", 1000, 200);
		BankAccount account2 = new BankAccount(111111111, "1234", 1000, 200);
		when(accountManagementService.getAccountByAccountNumber(account1)).thenReturn(account2);
		when(authRepository.findByAccountNumberAndValid(account1.getAccountNumber(), true)).thenReturn(null);
		Exception ex = assertThrows(BankAccountGenericException.class, ()-> authService.authenticateUser(account1));
		assertTrue(ex.getMessage().contains("Incorrect Account PIN"));
	}
	
	@Test
	public void testValidateAuthTokenWhenNoBankAccountNumber() throws BankAccountGenericException {
		BankAccount account = new BankAccount(0, "", 1000, 200);
		String token = "token1";
		Exception ex = assertThrows(BankAccountGenericException.class, ()-> authService.validateAuthToken(token, account));
		assertTrue(ex.getMessage().contains("Please add account number to the request body"));
	}
	
	@Test
	public void testValidateAuthTokenWhenNoAuthLogExists() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		String token = "token1";
		when(authRepository.findByAccountNumberAndValid(account.getAccountNumber(), true)).thenReturn(null);
		Exception ex = assertThrows(BankAccountGenericException.class, ()-> authService.validateAuthToken(token, account));
		assertTrue(ex.getMessage().contains("Auth token invalid. Please login first"));
	}
	
	@Test
	public void testValidateAuthTokenWhenInputTokenNoMatch() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		Auth auth = new Auth(111111111, "token1", LocalDateTime.now());
		String token = "tokenNoMatch";
		when(authRepository.findByAccountNumberAndValid(account.getAccountNumber(), true)).thenReturn(auth);
		Exception ex = assertThrows(BankAccountGenericException.class, ()-> authService.validateAuthToken(token, account));
		assertTrue(ex.getMessage().contains("Auth token invalid. Please login first"));
	}
	
	@Test
	public void testValidateAuthTokenWhenTokenTimeExpired() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		Auth auth = new Auth(111111111, "token1", LocalDateTime.now().minusMinutes(31));
		String token = "token1";
		when(authRepository.findByAccountNumberAndValid(account.getAccountNumber(), true)).thenReturn(auth);
		Exception ex = assertThrows(BankAccountGenericException.class, ()-> authService.validateAuthToken(token, account));
		assertTrue(ex.getMessage().contains("Auth token invalid. Please login first"));
	}
	
	@Test
	public void testValidateAuthTokenWhenValidTokenExists() throws BankAccountGenericException {
		BankAccount account = new BankAccount(111111111, "1111", 1000, 200);
		Auth auth = new Auth(111111111, "token1", LocalDateTime.now());
		String token = "token1";
		when(authRepository.findByAccountNumberAndValid(account.getAccountNumber(), true)).thenReturn(auth);
		authService.validateAuthToken(token, account);
		verify(authRepository, times(1)).findByAccountNumberAndValid(account.getAccountNumber(), true);
	}
	
}
