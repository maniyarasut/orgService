package com.org.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	public List<Org> getallOrg(int pageNumber, int count) {
		Pageable paging = PageRequest.of(pageNumber, count);
		Page<Org> pagedResult = orgRepo.findAll(paging);
		if (pagedResult.hasContent()) {
			List<Org> orgList = pagedResult.getContent();
			return orgList.stream().map(org -> {
				org.setLocation(org.getLocation() != null ? org.getLocation().toUpperCase() : null);
				return org;
			}).collect(Collectors.toList());

		} else {
			return new ArrayList<Org>();
		}
	}

	public Org getOrg(Long id) throws Exception {
		Optional<Org> orgOpt = orgRepo.findById(id);
		if (orgOpt.isPresent()) {
			return orgOpt.get();
		}
		throw new Exception("Organization not found");
	}

	public Org addOrg(Org request) throws Exception {
		Org org = new Org();
		org.setEmail(request.getEmail());
		org.setName(request.getName());
		org.setLocation(request.getLocation());
		return orgRepo.save(org);
	}

	public Org updateOrg(Org request) throws Exception {
		if (request.getId() != null && request.getId() != 0) {
			Optional<Org> orgOpt = orgRepo.findById(request.getId());
			if (orgOpt.isPresent()) {
				Org org = orgOpt.get();
				org.setEmail(request.getEmail());
				org.setName(request.getName());
				org.setLocation(request.getLocation());
				orgRepo.save(org);
				return org;
			}
		}
		throw new Exception("Invalid org Id");
	}

	@Transactional
	public ResponseEntity<String> deleteOrg(Long id) {
		Optional<Org> orgOpt = orgRepo.findById(id);
		if (orgOpt.isPresent()) {
			Org org = orgOpt.get();
			empRepo.deleteByOrgId(id);
			assetRepo.deleteByOrgId(id);
			orgRepo.deleteById(id);
			return new ResponseEntity<String>("Organization -" + org.getName() + " deleted successfully ",
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("Organization not found", HttpStatus.BAD_REQUEST);
	}
}
