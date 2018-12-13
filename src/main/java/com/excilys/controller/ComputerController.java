package com.excilys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/")
public class ComputerController {

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;

	private static String orderDirection = "DESC";

	private static final Logger LOG4J = LogManager.getLogger(ComputerController.class.getName());

	@GetMapping
	public String listComputers(@RequestParam(required = false) String nbItem,
			@RequestParam(required = false) String numPage, 
			@RequestParam(required = false) String search,
			@RequestParam(required = false) String order, 
			@RequestParam(required = false) String change, 
			ModelMap model)
			throws ServletException, IOException {
		long firstId = 0;
		if (order == null) {
			order = "";
		}
		Page page = new Page(0, 10);
		List<Computer> computers = new ArrayList<>();
		if (nbItem != null) {
			int offset = Integer.parseInt(nbItem);
			if (numPage != null) {
				firstId = (offset - 1) * Long.parseLong(numPage);
			}
			page = new Page(firstId, offset);
			if ("DESC".equals(orderDirection) && Boolean.parseBoolean(change)) {
				orderDirection = "ASC";
			} else if ("ASC".equals(orderDirection) && Boolean.parseBoolean(change)) {
				orderDirection = "DESC";
			}
			computers = computerService.getPagedComputersOrdered(page, order, orderDirection);
		} else if (search != null) {
			LOG4J.info("Searching for '" + search + "' in computers and companies names...");
			computers = computerService.getComputersWithSearch(search);
		} else {
			computers = computerService.getPagedComputersOrdered(page, order, orderDirection);
		}

		List<ComputerDTO> computersDTO = new ArrayList<>();

		computers.stream().forEach(currentComputer -> {
			computersDTO.add(new ComputerDTO(currentComputer));
		});
		model.addAttribute("result_size", computersDTO.size());
		model.addAttribute("computers", computersDTO);
		return "getComputers";
	}

	@PostMapping
	public String deleteComputers(@RequestParam("computerChecked") List<String> idComputersToDelete)
			throws ServletException, IOException {
		idComputersToDelete.stream().forEach(idComputer -> computerService.deleteComputer(Long.parseLong(idComputer)));
		return "redirect:";
	}

	@GetMapping(value = "/editComputer/{id}")
	public String editComputerView(@PathVariable("id") long id, ModelMap model) throws ServletException, IOException {
		LOG4J.info("Entering get method");
		List<Company> companies = companyService.getCompanies();
		List<Long> companiesIds = new ArrayList<>();
		companies.stream().forEach(company -> companiesIds.add(company.getId()));
		Computer computer = computerService.getComputerById(id).get();
		String introduced = (computer.getIntroduced() != null
				? computer.getIntroduced().toString().subSequence(0, 10).toString()
				: null);
		String discontinued = (computer.getDiscontinued() != null
				? computer.getDiscontinued().toString().subSequence(0, 10).toString()
				: null);
		model.addAttribute("computer", computer);
		model.addAttribute("introduced", introduced);
		model.addAttribute("discontinued", discontinued);
		model.addAttribute("company", computer.getCompany());
		model.addAttribute("companiesIds", companiesIds);
		return "editComputer";
	}

	@PostMapping(value = "/editComputer/{id}")
	public String editComputer(@PathVariable("id") long id, 
			@RequestParam("computerName") String name,
			@RequestParam("introduced") String introduced, 
			@RequestParam("discontinued") String discontinued,
			@RequestParam("companyId") String companyNumber) throws ServletException, IOException {
		Computer computer = computerMapper.mapUnique(name, introduced, discontinued, companyNumber);
		computer.setId(id);
		computerService.updateComputer(computer);
		return "redirect:/editComputer/" + id;

	}

	@GetMapping(value = "/addComputer")
	public String addComputerView(ModelMap model) throws ServletException, IOException {
		List<Company> companies = companyService.getCompanies();
		List<Long> companiesIds = new ArrayList<>();
		companies.stream().forEach(company -> companiesIds.add(company.getId()));
		model.addAttribute("companiesIds", companiesIds);
		return "addComputer";
	}

	@PostMapping(value = "/addComputer")
	public String addComputer( 
			@RequestParam("computerName") String name,
			@RequestParam("introduced") String introduced, 
			@RequestParam("discontinued") String discontinued,
			@RequestParam("companyId") String companyNumber) throws ServletException, IOException {

		Computer computer = computerMapper.mapUnique(name, introduced, discontinued, companyNumber);
		computerService.createComputer(computer);
		return "redirect:/addComputer/";
	}
}
