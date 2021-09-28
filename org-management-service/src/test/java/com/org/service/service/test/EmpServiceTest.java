package com.org.service.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.org.service.dto.EmpRequest;
import com.org.service.model.Emp;
import com.org.service.model.Org;
import com.org.service.repo.AssetRepo;
import com.org.service.repo.EmpRepo;
import com.org.service.repo.OrgRepo;
import com.org.service.service.EmpService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmpServiceTest {

	@InjectMocks
	EmpService service;

	@Mock
	EmpRepo repo;
	
	@Mock
	OrgRepo orgRepo;
	
	@Mock
	AssetRepo assetRepo;
	
	@Test
	public void getAllEmpTest() {
		List<Emp> expected = new ArrayList<Emp>();
		Org org = new Org();
		expected.add(new Emp(1L, "test", "test", "test@test.com", org));
		Mockito.when(repo.findAllEmp()).thenReturn(expected);
		List<Emp> actual = service.getallEmp();
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void getEmpTest() throws Exception {
		Emp expected = new Emp(1L, "test", "test", "test@test.com",null);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
		Emp actual = service.getEmp(1L);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void getEmpFailedTest() throws Exception {
		Exception ex= new Exception("Employee not found");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		try {
			service.getEmp(1L);
		}
		catch(Exception actual)
		{
		assertEquals(ex.getMessage(), actual.getMessage());
		}
	}

	@Test
	public void addEmpTest() throws Exception {
		Org org = new Org();
		Emp expected = new Emp(1L, "test", "test", "test@test.com",org);
		Mockito.when(repo.save(Mockito.any())).thenReturn(expected);
		Mockito.when(orgRepo.findById(Mockito.any())).thenReturn(Optional.of(org));
		Emp actual = service.addEmp(new EmpRequest());
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void updateEmpTest() throws Exception {
		Org org = new Org();
		org.setId(1L);
		Emp expected = new Emp(1L, "test", "test", "test@test.com",org);
		Mockito.when(repo.save(Mockito.any())).thenReturn(expected);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
		Mockito.when(orgRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(org));
		EmpRequest req=new EmpRequest();
		req.setId(1L);
		req.setOrgId(1L);
		Emp actual = service.updateEmp(req);
		assertEquals(expected.getId(), actual.getId());
	}
	
	@Test
	public void updateEmpFailedTest() throws Exception {
		Exception ex= new Exception("Invalid employee Id");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		try {
			EmpRequest req=new EmpRequest();
			req.setId(1L);
			service.updateEmp(req);
		}
		catch(Exception actual)
		{
		assertEquals(ex.getMessage(), actual.getMessage());
		}
	}
	
	@Test
	public void deleteEmpTest() throws Exception {
		Emp emp = new Emp(1L, "test", "test", "test@test.com",null);
		ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.OK);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(emp));
		doNothing().when(assetRepo).updateEmpAsset(Mockito.anyLong());
		doNothing().when(repo).deleteById(Mockito.anyLong());
		ResponseEntity<String> actual = service.deleteEmp(1L);
		assertEquals(expected.getStatusCode(), actual.getStatusCode());
	}

	@Test
	public void deleteEmpFailedTest() throws Exception {
		ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		doNothing().when(assetRepo).updateEmpAsset(Mockito.anyLong());
		doNothing().when(repo).deleteById(Mockito.anyLong());
		ResponseEntity<String> actual = service.deleteEmp(1L);
		assertEquals(expected.getStatusCode(), actual.getStatusCode());
	}

}
