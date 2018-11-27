package com.excilys.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Computer;
import com.excilys.service.ComputerService;

public class ComputerServlet extends HttpServlet{

//	private ComputerService computerService = ComputerService.getInstance();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		List<Computer> computers = computerService.getComputers();
//		
//		request.setAttribute("computers", computers);
		PrintWriter out = response.getWriter();
		Computer computer = new Computer.Builder("Billy").build();
		out.println(computer.getName());
		request.setAttribute("computer", computer);
//		request.getRequestDispatcher("/getComputers.jsp").forward(request, response);
    }
}
