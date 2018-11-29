package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1250893512382988704L;
	private ComputerService computerService = ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();
	private ComputerMapper computerMapper = ComputerMapper.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = companyService.getCompanies();
		List<Long> companiesIds = new ArrayList<>();
		companies.stream().forEach(company -> companiesIds.add(company.getId()));
		request.setAttribute("companiesIds", companiesIds);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long id = Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyNumber = request.getParameter("companyId");
		

		Computer computer = computerMapper.mapUnique(name, introduced, discontinued, companyNumber);
		computer.setId(id);
		computerService.updateComputer(computer);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}
	
}
