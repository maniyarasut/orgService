package com.org.service.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.org.service.dto.AssetRequest;
import com.org.service.model.Asset;
import com.org.service.model.Emp;
import com.org.service.model.Org;
import com.org.service.repo.AssetRepo;
import com.org.service.repo.EmpRepo;
import com.org.service.repo.OrgRepo;

@Service
public class AssetService {

	@Autowired
	AssetRepo assetRepo;

	@Autowired
	OrgRepo orgRepo;

	@Autowired
	EmpRepo empRepo;

	public List<Asset> getallAsset() {
		return assetRepo.findAllAsset();
	}

	public Asset getAsset(Long id) throws Exception {
		Asset asset = assetRepo.findAssetById(id);//Using Join fetch to ignore n+1 problem
		if (asset!=null) {
			return asset;
		}
		throw new Exception("Asset not found");
	}

	public Asset addAsset(AssetRequest request) throws Exception {
		if (request.getOrgId() != null && request.getOrgId() != 0) {
			Optional<Org> orgOpt = orgRepo.findById(request.getOrgId());
			if (orgOpt.isPresent()) {
				Asset asset = new Asset();
				asset.setName(request.getAssetName());
				asset.setOrg(orgOpt.get());
				if (request.getEmpId() != null) {
					Emp emp= empRepo.getOne(request.getEmpId()); //Get the Emp Details
					if(emp!=null && emp.getOrg().getId().equals(request.getOrgId()))
						asset.setEmp(emp);
					else if(emp!=null && !emp.getOrg().getId().equals(request.getOrgId()))
						throw new Exception("Organization Id & Employee ID mismatch"); //Checking whether the emp belongs to the given Organisation
				}
				return assetRepo.save(asset);

			}
		}
		throw new Exception("Invalid Organization Id");
	}

	public Asset updateAsset(AssetRequest request) throws Exception {
		if (request.getAssetId() != null && request.getAssetId() != 0) {
			Optional<Asset> assetOpt = assetRepo.findById(request.getAssetId());
			if (assetOpt.isPresent()) {
				Asset asset=dtoEntityMapping(request,assetOpt.get());
				assetRepo.save(asset);
				return asset;
			}
		}
		throw new Exception("Invalid AssetId");
	}

	private Asset dtoEntityMapping(AssetRequest request, Asset asset) throws Exception {

		if (request.getOrgId() != null && request.getOrgId() != 0) {
			Optional<Org> orgOpt = orgRepo.findById(request.getOrgId());
			if (orgOpt.isPresent()) {//Checking whether Organization is valid
				asset.setName(request.getAssetName());
				asset.setOrg(orgOpt.get());
				if (request.getEmpId() != null) {
					Emp emp= empRepo.getOne(request.getEmpId());
					if(emp!=null && emp.getOrg().getId().equals(request.getOrgId()))
						asset.setEmp(emp);
					else if(emp!=null && !emp.getOrg().getId().equals(request.getOrgId()))
						throw new Exception("Organization & Employee mismatch");//Checking whether the emp belongs to the given Organisation
				}
				return asset;

			}
		}
		throw new Exception("Invalid OrganizationId");

	}

	@Transactional
	public ResponseEntity<String> deleteAsset(Long id) {
		Optional<Asset> assetOpt = assetRepo.findById(id);
		if (assetOpt.isPresent()) {
			Asset asset = assetOpt.get();
			assetRepo.deleteById(id);
			return new ResponseEntity<String>("Asset -" + asset.getName() + " deleted successfully ",
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("Asset not found", HttpStatus.BAD_REQUEST);
	}

}
