package com.atm.machine.atmmachine.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.atm.machine.atmmachine.data.BankAccountAuthentication;

@Repository
public interface AuthRepository extends CrudRepository<BankAccountAuthentication, Integer> {
	BankAccountAuthentication findByAccountNumberAndValid(int accountNumber, boolean valid);
}
