package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/addComputer")
public class AddComputerServlet {

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(HttpServletRequest request) throws ServletException, IOException {
		List<Company> companies = companyService.getCompanies();
		List<Long> companiesIds = new ArrayList<>();
		companies.stream().forEach(company -> companiesIds.add(company.getId()));
		request.setAttribute("companiesIds", companiesIds);
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request) throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyNumber = request.getParameter("companyId");

		Computer computer = computerMapper.mapUnique(name, introduced, discontinued, companyNumber);
		computerService.createComputer(computer);
		return "redirect:/addComputer/";
	}
}
