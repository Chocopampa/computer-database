package com.excilys.persistence;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.model.Page;

public class CompanyDAOTest {

	@Mock
	private DatabaseConnection dbConnection;
	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement statement;
	@Mock
	private ResultSet rs;
	@Mock
	private CompanyMapper companyMapper;

	@InjectMocks
	private CompanyDAO companyDAO = CompanyDAO.getInstance();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(dbConnection.connect()).thenReturn(connection);
		Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
		Mockito.when(statement.executeQuery()).thenReturn(rs);
	}
	
	
	@Test
	public void testGetCompanies() throws SQLException {
		List<Company> companies = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			companies.add(new Company.Builder(i).withName("company"+i).build());
		}
		Mockito.when(companyMapper.mapList(rs)).thenReturn(companies);
		
		List<Company> result = companyDAO.getCompanies();
		
		Assert.assertEquals("The returned list is different",companies, result);
	}

	@Test
	public void testGetListCompanies() {
		List<Company> companies = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			companies.add(new Company.Builder(i).withName("company"+i).build());
		}
		Page page = new Page(0,2);
		List<Company> result = companyDAO.getListCompanies(page);
		if(companies.get(0) == companies.get(4))
			System.out.println("");
		else
			System.out.println(0);

		//TODO
	}

}
