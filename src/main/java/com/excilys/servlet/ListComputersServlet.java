package com.excilys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.ComputerService;

@Controller
@RequestMapping("/")
public class ListComputersServlet {

	@Autowired
	private ComputerService computerService;

	private static String orderDirection = "DESC";
	
	private static final Logger LOG4J = LogManager.getLogger(ListComputersServlet.class.getName());

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(@RequestParam(required = false) String nbItem, @RequestParam(required = false) String numPage,
			@RequestParam(required = false) String search, @RequestParam(required = false) String order, @RequestParam(required = false) String change, ModelMap model)
			throws ServletException, IOException {
		long firstId = 0;
		if (order == null) {
			order = "";
		}
		Page page = new Page(0, 10);
		List<Computer> computers = new ArrayList<>();
		if (nbItem != null) {
			int offset = Integer.parseInt(nbItem);
			if (numPage != null) {
				firstId = (offset - 1) * Long.parseLong(numPage);
			}
			page = new Page(firstId, offset);
			if ("DESC".equals(orderDirection) && Boolean.parseBoolean(change)) {
				orderDirection = "ASC";
			} else if ("ASC".equals(orderDirection) && Boolean.parseBoolean(change)) {
				orderDirection = "DESC";
			}
			computers = computerService.getPagedComputersOrdered(page, order, orderDirection);
		} else if (search != null) {
			LOG4J.info("Searching for '" + search + "' in computers and companies names...");
			computers = computerService.getComputersWithSearch(search);
		} else {
			computers = computerService.getPagedComputersOrdered(page, order, orderDirection);
		}

		List<ComputerDTO> computersDTO = new ArrayList<>();

		computers.stream().forEach(currentComputer -> {
			computersDTO.add(new ComputerDTO(currentComputer));
		});
		model.addAttribute("result_size", computersDTO.size());
		model.addAttribute("computers", computersDTO);
		return "getComputers";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request)
			throws ServletException, IOException {
		List<String> idComputersToDelete = Arrays.asList(request.getParameterValues("computerChecked"));
		idComputersToDelete.stream().forEach(idComputer -> computerService.deleteComputer(Long.parseLong(idComputer)));
		return "redirect:";
	}
}
