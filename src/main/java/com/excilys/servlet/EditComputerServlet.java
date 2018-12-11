package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/editComputer/{id}")
public class EditComputerServlet  {
	
	private static final Logger LOG4J = LogManager.getLogger(EditComputerServlet.class.getName());
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@PathVariable("id") long id, ModelMap model) throws ServletException, IOException {
		LOG4J.info("Entering get method");
		List<Company> companies = companyService.getCompanies();
		List<Long> companiesIds = new ArrayList<>();
		companies.stream().forEach(company -> companiesIds.add(company.getId()));
		Computer computer = computerService.getComputerById(id).get();
		String introduced = (computer.getIntroduced() != null ? computer.getIntroduced().toString().subSequence(0, 10).toString() : null);
		String discontinued = (computer.getDiscontinued() != null ? computer.getDiscontinued().toString().subSequence(0, 10).toString() : null);
		model.addAttribute("computer", computer);
		model.addAttribute("introduced", introduced);
		model.addAttribute("discontinued", discontinued);
		model.addAttribute("company", computer.getCompany());
		model.addAttribute("companiesIds", companiesIds);
		return "editComputer";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@PathVariable("id") long id, HttpServletRequest request) throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyNumber = request.getParameter("companyId");
		
		Computer computer = computerMapper.mapUnique(name, introduced, discontinued, companyNumber);
		computer.setId(id);
		computerService.updateComputer(computer);
		return "redirect:/editComputer/" + id;

	}
}
