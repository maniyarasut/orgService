package com.org.service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.org.service.model.Asset;

public interface AssetRepo extends JpaRepository<Asset, Long> {
	
	@Query("select asset from Asset asset left outer join fetch asset.org org left outer join fetch asset.emp emp")
	List<Asset> findAllAsset();
	
	@Query("select asset from Asset asset left outer join fetch asset.org org left outer join fetch asset.emp emp where asset.id=:id")
	Asset findAssetById(@Param("id")Long id);

	@Modifying
	@Query("update Asset asset set asset.emp=null where asset.emp.id=:id")
	void updateEmpAsset(@Param("id")Long id);
	
	@Modifying
	@Query("delete Asset asset where asset.org.id=:id")
	void deleteByOrgId(@Param("id")Long id);
	

}
