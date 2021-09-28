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

import com.org.service.model.Org;
import com.org.service.repo.AssetRepo;
import com.org.service.repo.EmpRepo;
import com.org.service.repo.OrgRepo;
import com.org.service.service.OrgService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrgServiceTest {

	@InjectMocks
	OrgService service;

	@Mock
	OrgRepo repo;
	
	@Mock
	EmpRepo empRepo;
	
	@Mock
	AssetRepo assetRepo;

	@Test
	public void getAllOrgTest() {
		List<Org> expected = new ArrayList<Org>();
		expected.add(new Org(1L, "test", "test", "test@test.com"));
		Mockito.when(repo.findAll()).thenReturn(expected);
		List<Org> actual = service.getallOrg();
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void getOrgTest() throws Exception {
		Org expected = new Org(1L, "test", "test", "test@test.com");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
		Org actual = service.getOrg(1L);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void getOrgFailedTest() throws Exception {
		Exception ex= new Exception("Organization not found");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		try {
			service.getOrg(1L);
		}
		catch(Exception actual)
		{
		assertEquals(ex.getMessage(), actual.getMessage());
		}
	}

	@Test
	public void addOrgTest() throws Exception {
		Org expected = new Org(1L, "test", "test", "test@test.com");
		Mockito.when(repo.save(Mockito.any())).thenReturn(expected);
		Org actual = service.addOrg(expected);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void updateOrgTest() throws Exception {
		Org expected = new Org(1L, "test", "test", "test@test.com");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
		Mockito.when(repo.save(Mockito.any())).thenReturn(expected);
		Org actual = service.updateOrg(expected);
		assertEquals(expected.getId(), actual.getId());
	}
	
	@Test
	public void updateOrgFailedTest() throws Exception {
		Exception ex= new Exception("Invalid org Id");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		try {
			service.updateOrg(new Org(1L, "test", "test", "test@test.com"));
		}
		catch(Exception actual)
		{
		assertEquals(ex.getMessage(), actual.getMessage());
		}
	}
	
	@Test
	public void deleteOrgTest() throws Exception {
		Org org = new Org(1L, "test", "test", "test@test.com");
		ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.OK);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(org));
		doNothing().when(empRepo).deleteByOrgId(Mockito.anyLong());
		doNothing().when(assetRepo).deleteByOrgId(Mockito.anyLong());
		doNothing().when(repo).deleteById(Mockito.anyLong());
		ResponseEntity<String> actual = service.deleteOrg(1L);
		assertEquals(expected.getStatusCode(), actual.getStatusCode());
	}

	@Test
	public void deleteOrgFailedTest() throws Exception {
		ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		doNothing().when(empRepo).deleteByOrgId(Mockito.anyLong());
		doNothing().when(assetRepo).deleteByOrgId(Mockito.anyLong());
		doNothing().when(repo).deleteById(Mockito.anyLong());
		ResponseEntity<String> actual = service.deleteOrg(1L);
		assertEquals(expected.getStatusCode(), actual.getStatusCode());
	}

}
