package com.org.service.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.service.model.Org;
import com.org.service.repo.AssetRepo;
import com.org.service.repo.EmpRepo;
import com.org.service.repo.OrgRepo;

@Service
@Transactional
public class OrgService {

	@Autowired
	OrgRepo orgRepo;
	
	@Autowired
	EmpRepo empRepo;
	
	@Autowired
	AssetRepo assetRepo;
	
	public List<Org> getallOrg() {
		return orgRepo.findAll();
	}
	
	
	public Org getOrg(Long id) throws Exception {
		Optional<Org> orgOpt = orgRepo.findById(id);
		if (orgOpt.isPresent()) {
			return orgOpt.get();
		}
		throw new Exception("Organisation not found");
	}
	
	public Org addOrg(Org request) throws Exception {
		Org org= new Org();
		org.setEmail(request.getEmail());
		org.setName(request.getName());
		org.setLocation(request.getLocation());
		orgRepo.save(org);
		return org;
	}
	
	public Org updateOrg(Org request) throws Exception {
		if(request.getId()!=null && request.getId()!=0)
		{
			Optional<Org> orgOpt = orgRepo.findById(request.getId());
			if(orgOpt.isPresent()) {
				Org org = orgOpt.get();
				org.setEmail(request.getEmail());
				org.setName(request.getName());
				org.setLocation(request.getLocation());
				orgRepo.save(org);
				return org;
			}
		}
		throw new Exception("Invalid orgloyee Id");
	}
	
	
	
	@Transactional
	public ResponseEntity<String> deleteOrg(Long id) {
		Optional<Org> orgOpt = orgRepo.findById(id);
		if (orgOpt.isPresent()) {
			Org org= orgOpt.get();
			empRepo.deleteByOrgId(id);
			assetRepo.deleteByOrgId(id);
			orgRepo.deleteById(id);
			return new ResponseEntity<String>("Organization -"+org.getName()+" deleted successfully ",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Organization not found",HttpStatus.BAD_REQUEST);
	}
}
