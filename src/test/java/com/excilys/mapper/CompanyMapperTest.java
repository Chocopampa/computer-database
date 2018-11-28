package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.model.Company;

import junit.framework.Assert;

public class CompanyMapperTest {

	@Mock
	private ResultSet rs;

	@InjectMocks
	private CompanyMapper companyMapper = CompanyMapper.getInstance();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testMapList() throws SQLException {
		List<Company> companiesExpected = new ArrayList<>();
		int numberOfCompanies = 2;

		for (int i = 0; i < numberOfCompanies; i++) {
			Company company = new Company.Builder(i).withName("company"+i).build();
			
			companiesExpected.add(company);
		}

		// To adapt to number of companies created
		Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getLong(Mockito.anyString())).thenReturn(companiesExpected.get(0).getId()).thenReturn(companiesExpected.get(1).getId());
		Mockito.when(rs.getString(Mockito.anyString())).thenReturn(companiesExpected.get(0).getName()).thenReturn(companiesExpected.get(1).getName());

		List<Company> result = companyMapper.mapList(rs);

		for (int i = 0; i < numberOfCompanies; i++) {
			Assert.assertEquals(companiesExpected.get(i).getId(), result.get(i).getId());
			Assert.assertEquals(companiesExpected.get(i).getName(), result.get(i).getName());
		}
	}

}
