package com.excilys.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.excilys.exception.ComputerCreationException;
import com.excilys.exception.ComputerIdException;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;
import com.excilys.validator.ComputerValidator;

@RestController
public class MyRestController {
	

	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerValidator computerValidator;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Computer> getComputers() {
		return computerService.getComputers();
	}

	@GetMapping("/editComputer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Computer getComputerById(@PathVariable("id") long id) throws ComputerIdException {
		Optional<Computer> computer = computerService.getComputerById(id);
		if (computer.isPresent()) {
			return computer.get();
		}
		throw new ComputerIdException();
	}
	
	@PostMapping("/addComputer")
	@ResponseStatus(HttpStatus.CREATED)
	public int addComputer(@RequestBody Computer computer) throws ComputerCreationException {
		if (computerValidator.correctComputer(computer)) {
			return computerService.createComputer(computer);
		}
		throw new ComputerCreationException();
	}
	

	

}
