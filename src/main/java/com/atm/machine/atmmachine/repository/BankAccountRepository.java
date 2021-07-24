package com.atm.machine.atmmachine.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.atm.machine.atmmachine.data.BankAccount;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long>{
	BankAccount findByAccountNumber(int accountNumber);
}
