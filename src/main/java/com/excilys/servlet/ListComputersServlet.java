package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;

public class ListComputersServlet extends HttpServlet {

	private static final long serialVersionUID = -3938443724704425725L;
	private ComputerService computerService = ComputerService.getInstance();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nbComputer = request.getParameter("nbItem");
		List<Computer> computers = new ArrayList<>();
		if (nbComputer != null) {
			Page page = new Page(0, Integer.parseInt(nbComputer));
			computers = computerService.getPagedComputers(page);
		} else {
			computers = computerService.getComputers();
		}
		request.setAttribute("result_size", computers.size());
		request.setAttribute("computers", computers);
		this.getServletContext().getRequestDispatcher("/views/getComputers.jsp").forward(request, response);
	}
}
