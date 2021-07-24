package com.atm.machine.atmmachine.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.atm.machine.atmmachine.data.ATMMachineDenominations;

@Repository
public interface ATMMachineRepository extends CrudRepository<ATMMachineDenominations, Integer>{
	
}
