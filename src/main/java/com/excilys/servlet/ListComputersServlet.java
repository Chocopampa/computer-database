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
		String offsetString = request.getParameter("nbItem");
		String numPage = request.getParameter("numPage");
		long firstId = 0;
		
		List<Computer> computers = new ArrayList<>();
		if (offsetString != null) {
			int offset = Integer.parseInt(offsetString);
			if (numPage != null) {
				firstId = (offset-1) * Long.parseLong(numPage);
			}
			Page page = new Page(firstId, offset);
			computers = computerService.getPagedComputers(page);
		} else {
			computers = computerService.getComputers();
		}
		request.setAttribute("result_size", computers.size());
		request.setAttribute("computers", computers);
		this.getServletContext().getRequestDispatcher("/views/getComputers.jsp").forward(request, response);
	}
}
