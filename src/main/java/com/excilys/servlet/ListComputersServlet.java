package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Computer;
import com.excilys.service.ComputerService;


public class ListComputersServlet extends HttpServlet{

	private static final long serialVersionUID = -3938443724704425725L;
	private ComputerService computerService = ComputerService.getInstance();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Computer> computers = computerService.getComputers();
		request.setAttribute("computers", computers);
		this.getServletContext().getRequestDispatcher("/views/getComputers.jsp").forward(request, response);
    }
}
