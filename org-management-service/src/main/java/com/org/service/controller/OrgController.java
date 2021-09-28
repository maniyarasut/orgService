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

import com.org.service.model.Org;
import com.org.service.service.OrgService;

@RestController
@RequestMapping("/org")
public class OrgController {

	//Org Management Controller
	
	@Autowired
	OrgService orgService;

	@GetMapping(produces = { "application/xml", "text/xml" ,"application/json"})
	public List<Org> getallOrg() {
		return orgService.getallOrg();
	}
	
	@GetMapping(value="/{id}", produces = { "application/xml", "text/xml","application/json" })
	public Org getOrgById(@PathVariable("id") Long id) throws Exception {
		return orgService.getOrg(id);
	}

	@PostMapping(produces = { "application/xml", "text/xml","application/json" })
	public Org createOrg(@Valid @RequestBody Org org) throws Exception {
		return orgService.addOrg(org);
	}

	@PutMapping(produces = { "application/xml", "text/xml","application/json" })
	public Org updateOrg(@Valid @RequestBody Org org) throws Exception {
		return orgService.updateOrg(org);
	}

	@DeleteMapping(value="/{id}", produces = { "application/xml", "text/xml" ,"application/json"})
	public ResponseEntity<String> deleteOrgById(@PathVariable("id") Long id) throws Exception {
		return orgService.deleteOrg(id);
	}

}
