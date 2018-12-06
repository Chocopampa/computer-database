package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.dto.ComputerDTO;
import com.excilys.main.SpringConfiguration;
import com.excilys.mapper.ComputerDTOMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/getComputers")
public class ListComputersServlet extends HttpServlet {

	private static final long serialVersionUID = -3938443724704425725L;

	private CompanyService companyService;
	private ComputerService computerService;

	private ComputerDTOMapper computerDTOMapper = ComputerDTOMapper.getInstance();
	private static final Logger LOG4J = LogManager.getLogger(CompanyDAO.class.getName());

	@RequestMapping(method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String offsetString = request.getParameter("nbItem");
		String numPage = request.getParameter("numPage");
		long firstId = 0;
		String search = request.getParameter("search");

		List<Computer> computers = new ArrayList<>();
		if (offsetString != null) {
			int offset = Integer.parseInt(offsetString);
			if (numPage != null) {
				firstId = (offset - 1) * Long.parseLong(numPage);
			}
			Page page = new Page(firstId, offset);
			computers = computerService.getPagedComputers(page);
		} else if (search != null) {
			LOG4J.info("Searching for '" + search + "' in computers and companies names...");
			computers = computerService.getComputersWithSearch(search);
		} else {
			computers = computerService.getComputers();
		}

		List<ComputerDTO> computersDTO = new ArrayList<>();

		computers.stream().forEach(currentComputer -> {
			Company company = currentComputer.getCompany();
			if (company.getId() != 0) {
				currentComputer.getCompany().setName(companyService.getCompanyById(company.getId()).get().getName());
			}
			computersDTO.add(computerDTOMapper.map(currentComputer));
		});
		request.setAttribute("result_size", computersDTO.size());
		request.setAttribute("computers", computersDTO);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/getComputers.jsp").forward(request, response);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> idComputersToDelete = Arrays.asList(request.getParameterValues("computerChecked"));
		idComputersToDelete.stream().forEach(idComputer -> computerService.deleteComputer(Long.parseLong(idComputer)));
		this.doGet(request, response);
	}

	@Override
	public void init() throws ServletException {

		ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

		this.companyService = (CompanyService) context.getBean(CompanyService.class);
		this.computerService = (ComputerService) context.getBean(ComputerService.class);
	}
}
