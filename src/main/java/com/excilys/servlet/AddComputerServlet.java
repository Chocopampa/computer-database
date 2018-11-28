package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.service.ComputerService;

public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 4450306511039154175L;
	private ComputerService computerService = ComputerService.getInstance();
	private ComputerMapper computerMapper = ComputerMapper.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyNumber = request.getParameter("companyId");

		Computer computer = null;
		computer = computerMapper.mapUnique(name, introduced, discontinued, companyNumber);
		computerService.createComputer(computer);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}
}
