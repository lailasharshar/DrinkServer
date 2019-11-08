package com.example.demo.controllers;

import com.example.demo.beans.Machine;
import com.example.demo.exceptions.MachineException;
import com.example.demo.services.MachineService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class MachineController {
	@Autowired
	private MachineService machineService;

	@PostMapping("/machines/init")
	public ResponseEntity<Machine> init(@RequestParam String name, @RequestParam String location, @RequestParam Double volume) {
		try {
			return new ResponseEntity<>(machineService.createNewMachine(name, location, volume), HttpStatus.CREATED);
		} catch (MachineException ex) {
			log.error(ex.getMessage(), ex);
			return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
		}
	}

	@GetMapping("/machines/whoami")
	public ResponseEntity<Machine> findMe(String name) {
		return new ResponseEntity<>(machineService.getMachine(name), HttpStatus.OK);
	}
}
