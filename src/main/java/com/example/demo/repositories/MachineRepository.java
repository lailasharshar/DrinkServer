package com.example.demo.repositories;

import com.example.demo.beans.Machine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MachineRepository extends CrudRepository<Machine, Long> {
	List<Machine> findByName(String name);
}
