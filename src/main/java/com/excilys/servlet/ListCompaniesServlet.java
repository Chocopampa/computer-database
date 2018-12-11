package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.model.Company;
import com.excilys.service.CompanyService;

@WebServlet("/getCompanies")
public class ListCompaniesServlet extends HttpServlet {

	private static final long serialVersionUID = 3092660329888954541L;
	
	@Autowired
	private CompanyService companyService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<Company> companies = companyService.getCompanies();
		request.setAttribute("companies", companies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/getCompanies.jsp").forward(request, response);
    }

}
