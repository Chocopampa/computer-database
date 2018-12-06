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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1250893512382988704L;
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = companyService.getCompanies();
		List<Long> companiesIds = new ArrayList<>();
		companies.stream().forEach(company -> companiesIds.add(company.getId()));
		Computer computer = computerService.getComputerById(Long.parseLong(request.getParameter("id"))).get();
		String introduced = (computer.getIntroduced() != null ? computer.getIntroduced().toString().subSequence(0, 10).toString() : null);
		String discontinued = (computer.getDiscontinued() != null ? computer.getDiscontinued().toString().subSequence(0, 10).toString() : null);
		request.setAttribute("computerName", computer.getName());
		request.setAttribute("introduced", introduced);
		request.setAttribute("discontinued", discontinued);
		request.setAttribute("company", computer.getCompany());
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


	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
}
