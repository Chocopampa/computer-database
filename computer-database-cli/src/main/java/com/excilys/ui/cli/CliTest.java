package com.excilys.ui.cli;

import com.excilys.ui.config.JaxRsService;

public class CliTest {

	public static void main(String []args) {
		
		JaxRsService entryPoint = new JaxRsService();
		System.out.println(entryPoint.getJsonComputers());
}
	
}
