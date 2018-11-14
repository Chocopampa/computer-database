package services;


public class CompanyServices {
	
	
	private CompanyServices() {};

	private static CompanyServices INSTANCE = new CompanyServices();

	public static CompanyServices getInstance() {
		return INSTANCE;
	}

}
