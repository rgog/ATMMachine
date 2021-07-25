package com.atm.machine.atmmachine.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.atm.machine.atmmachine.data.Auth;

@Repository
public interface AuthRepository extends CrudRepository<Auth, Integer> {
	Auth findByAccountNumberAndValid(int accountNumber, boolean valid);
}
