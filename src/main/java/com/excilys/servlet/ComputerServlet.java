package com.excilys.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.model.Computer;
import com.excilys.service.ComputerService;


public class ComputerServlet extends HttpServlet{

	private static final long serialVersionUID = -3938443724704425725L;
	private ComputerService computerService = ComputerService.getInstance();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Computer> computers = computerService.getComputers();
		
//		request.setAttribute("computers", computers);
//		Computer computer1 = new Computer.Builder("Billy1").build();
//		Computer computer2 = new Computer.Builder("Billy2").build();
//		Computer computer3 = new Computer.Builder("Billy3").build();
//		List<Computer> computers = new ArrayList<>();
//		computers.add(computer1);
//		computers.add(computer2);
//		computers.add(computer3);
		request.setAttribute("computers", computers);
		this.getServletContext().getRequestDispatcher("/getComputers.jsp").forward(request, response);
    }
}
