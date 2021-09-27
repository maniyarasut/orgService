package com.org.service.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.service.dto.EmpRequest;
import com.org.service.model.Emp;
import com.org.service.model.Org;
import com.org.service.repo.AssetRepo;
import com.org.service.repo.EmpRepo;
import com.org.service.repo.OrgRepo;

@Service
@Transactional
public class EmpService {

	@Autowired
	EmpRepo empRepo;
	
	@Autowired
	OrgRepo orgRepo;
	
	@Autowired
	AssetRepo assetRepo;
	

	public List<Emp> getallEmp() {
		return empRepo.findAllEmp();
	}

	public Emp getEmp(Long id) throws Exception {
		Optional<Emp> empOpt = empRepo.findById(id);
		if (empOpt.isPresent()) {
			return empOpt.get();
		}
		throw new Exception("Employee not found");
	}
	
	public Emp addEmp(EmpRequest request) throws Exception {
		Emp emp= empDtoEntityMapping(request, new Emp());
		empRepo.save(emp);
		return emp;
	}
	
	public Emp updateEmp(EmpRequest request) throws Exception {
		if(request.getId()!=null && request.getId()!=0)
		{
			Optional<Emp> empOpt = empRepo.findById(request.getId());
			if(empOpt.isPresent()) {
				Emp emp = empDtoEntityMapping(request,empOpt.get());
				empRepo.save(emp);
				return emp;
			}
		}
		throw new Exception("Invalid employee Id");
	}
	
	private Emp empDtoEntityMapping(EmpRequest request, Emp emp) throws Exception {
		Optional<Org> orgOpt=orgRepo.findById(request.getOrgId());
		if(orgOpt.isPresent()) {
			Org org = orgOpt.get();
			emp.setEmail(request.getEmailId());
			emp.setFirstName(request.getFirstName());
			emp.setLastName(request.getLastName());
			emp.setOrg(org);
			return emp;
		}
		throw new Exception("Invalid Organisation");
	}
	
	@Transactional
	public ResponseEntity<String> deleteEmp(Long id) {
		Optional<Emp> empOpt = empRepo.findById(id);
		if (empOpt.isPresent()) {
			Emp emp= empOpt.get();
			assetRepo.updateEmpAsset(id);
			empRepo.deleteById(id);
			return new ResponseEntity<String>("Employee "+emp.getFirstName()+" "+emp.getLastName()+" deleted successfully ",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Employee not found",HttpStatus.BAD_REQUEST);
	}
}
