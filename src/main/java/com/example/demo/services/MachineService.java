package com.example.demo.services;

import com.example.demo.beans.Machine;
import com.example.demo.exceptions.MachineException;
import com.example.demo.repositories.MachineRepository;
import com.example.demo.utils.NotifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {
	@Autowired
	private MachineRepository machineRepository;
	@Value("${reservePercentage}")
	private Double machineReserve;

	public Double decrementVolume(Long id, Double amount) throws MachineException {
		Machine machine = machineRepository.findById(id).orElseThrow(() -> new MachineException("Cannot find machine"));
		if (machine == null) {
			throw new MachineException("Cannot find machine");
		}
		machine.setRemaining(machine.getRemaining() - amount);
		machineRepository.save(machine);
		return machine.getRemaining();
	}

	public Machine createNewMachine(String name, String location, Double volume) throws MachineException  {
		List<Machine> sameName = machineRepository.findByName(name);
		if (sameName != null && !sameName.isEmpty()) {
			throw new MachineException("Name " + name + " already exists");
		}
		Machine m = new Machine().setName(name).setLocation(location).setVolume(volume).setRemaining(volume);
		return machineRepository.save(m);
	}

	public Machine reduceRemaining(Long id, Double volume) throws MachineException {
		Machine machine = machineRepository.findById(id).orElseThrow(() -> new MachineException("Cannot find machine"));
		machine.setRemaining(machine.getRemaining() - volume);
		Double percentRemaining = machine.getRemaining()/machine.getVolume();
		Machine savedMachine = machineRepository.save(machine);
		if (percentRemaining < machineReserve) {
			NotifyUtils.notify("Home Base",
					"Reserved for Machine" + machine.getName() + "(" + machine.getId() + ") is at " + percentRemaining);
		}
		return savedMachine;
	}

	public Machine getMachine(Long id) throws MachineException {
		return machineRepository.findById(id).orElseThrow(() -> new MachineException("Cannot find machine"));
	}

	public Machine getMachine(String name) {
		List<Machine> machines =  machineRepository.findByName(name);
		if (machines != null && !machines.isEmpty()) {
			return machines.get(0);
		}
		return null;
	}
}
