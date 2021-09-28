package com.org.service.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.service.dto.AssetRequest;
import com.org.service.model.Asset;
import com.org.service.service.AssetService;

@RestController
@RequestMapping("/asset")
public class AssetController {
	
	//Asset Manaagement Controller

	@Autowired
	AssetService service;

	@GetMapping(produces = { "application/xml", "text/xml","application/json" })
	public List<Asset> getallAsset() {
		return service.getallAsset();
	}
	
	@GetMapping(value="/{id}",produces = { "application/xml", "text/xml","application/json" })
	public Asset getAssetById(@PathVariable("id") Long id) throws Exception {
		return service.getAsset(id);
	}

	@PostMapping(produces = { "application/xml", "text/xml","application/json" })
	public Asset createAsset(@Valid @RequestBody AssetRequest asset) throws Exception {
		return service.addAsset(asset);
	}

	@PutMapping(produces = { "application/xml", "text/xml" ,"application/json"})
	public Asset updateAsset(@Valid @RequestBody AssetRequest asset) throws Exception {
		return service.updateAsset(asset);
	}

	@DeleteMapping(value="/{id}",produces = { "application/xml", "text/xml","application/json" })
	public ResponseEntity<String> deleteAssetById(@PathVariable("id") Long id) throws Exception {
		return service.deleteAsset(id);
	}

	
}
