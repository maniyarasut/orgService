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

import com.org.service.dto.AssetRequest;
import com.org.service.model.Asset;
import com.org.service.model.Org;
import com.org.service.repo.AssetRepo;
import com.org.service.repo.EmpRepo;
import com.org.service.repo.OrgRepo;
import com.org.service.service.AssetService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssetServiceTest {

	@InjectMocks
	AssetService service;

	@Mock
	OrgRepo orgRepo;

	@Mock
	EmpRepo empRepo;
	
	@Mock
	AssetRepo repo;
	
	@Test
	public void getAllAssetTest() {
		List<Asset> expected = new ArrayList<Asset>();
		expected.add(new Asset(1L, "test", null,null));
		Mockito.when(repo.findAllAsset()).thenReturn(expected);
		List<Asset> actual = service.getallAsset();
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	public void getAssetTest() throws Exception {
		Asset expected = new Asset(1L, "test", null,null);
		Mockito.when(repo.findAssetById(Mockito.anyLong())).thenReturn(expected);
		Asset actual = service.getAsset(1L);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void getAssetFailedTest() throws Exception {
		Exception ex= new Exception("Asset not found");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		try {
			service.getAsset(1L);
		}
		catch(Exception actual)
		{
		assertEquals(ex.getMessage(), actual.getMessage());
		}
	}

	@Test
	public void addAssetTest() throws Exception {
		Asset expected = new Asset(1L, "test", null,null);
		Mockito.when(repo.save(Mockito.any())).thenReturn(expected);
		Org org = new Org(1L, "test", "test", "test@test.com");
		AssetRequest req = new AssetRequest();
		req.setAssetName("TEST");
		req.setOrgId(1L);
		Mockito.when(orgRepo.findById(Mockito.any())).thenReturn(Optional.of(org));
		Asset actual = service.addAsset(req);
		assertEquals(expected.getId(), actual.getId());
	}

	@Test
	public void updateAssetTest() throws Exception {
		Asset expected = new Asset(1L, "test", null,null);
		Mockito.when(repo.save(Mockito.any())).thenReturn(expected);
		Org org = new Org(1L, "test", "test", "test@test.com");
		AssetRequest req = new AssetRequest();
		req.setAssetName("TEST");
		req.setAssetId(1L);
		req.setOrgId(1L);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
		Mockito.when(orgRepo.findById(Mockito.any())).thenReturn(Optional.of(org));
		Asset actual = service.updateAsset(req);
		assertEquals(expected.getId(), actual.getId());
	}
	
	@Test
	public void updateAssetFailedTest() throws Exception {
		Exception ex= new Exception("Invalid AssetId");
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		try {
			Org org = new Org(1L, "test", "test", "test@test.com");
			AssetRequest req = new AssetRequest();
			req.setAssetName("TEST");
			req.setAssetId(1L);
			req.setOrgId(1L);
			service.updateAsset(req);
		}
		catch(Exception actual)
		{
		assertEquals(ex.getMessage(), actual.getMessage());
		}
	}
	
	@Test
	public void deleteAssetTest() throws Exception {
		Asset asset = new Asset(1L, "test", null,null);
		ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.OK);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(asset));
		doNothing().when(repo).deleteById(Mockito.anyLong());
		ResponseEntity<String> actual = service.deleteAsset(1L);
		assertEquals(expected.getStatusCode(), actual.getStatusCode());
	}

	@Test
	public void deleteOrgFailedTest() throws Exception {
		ResponseEntity<String> expected = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		doNothing().when(repo).deleteById(Mockito.anyLong());
		ResponseEntity<String> actual = service.deleteAsset(1L);
		assertEquals(expected.getStatusCode(), actual.getStatusCode());
	}

}
