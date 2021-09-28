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

import com.org.service.dto.EmpRequest;
import com.org.service.model.Emp;
import com.org.service.service.EmpService;

@RestController
@RequestMapping("/emp")
public class EmpController {

	//Employee Management Controller
	
	@Autowired
	EmpService empService;

	@GetMapping(produces = { "application/xml", "text/xml","application/json" })
	public List<Emp> getallEmp() {
		return empService.getallEmp();
	}

	@GetMapping(value="/{id}",produces = { "application/xml", "text/xml","application/json" })
	public Emp getEmpById(@PathVariable("id") Long id) throws Exception {
		return empService.getEmp(id);
	}

	@PostMapping(produces = { "application/xml", "text/xml" ,"application/json"})
	public Emp createEmp(@Valid @RequestBody EmpRequest request) throws Exception {
		return empService.addEmp(request);
	}

	@PutMapping(produces = { "application/xml", "text/xml","application/json" })
	public Emp updateEmp(@Valid @RequestBody EmpRequest request) throws Exception {
		return empService.updateEmp(request);
	}

	@DeleteMapping(value="/{id}",produces = { "application/xml", "text/xml" ,"application/json"})
	public ResponseEntity<String> deleteEmpById(@PathVariable("id") Long id) throws Exception {
		return empService.deleteEmp(id);
	}

}
