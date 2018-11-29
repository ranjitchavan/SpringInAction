package com.einfochips.webportal.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.einfochips.webportal.domain.DeviceMaster;

public interface DeviceMasterRepository extends CrudRepository<DeviceMaster, Integer> {
	DeviceMaster findBySerialNo(String serialNo);

	DeviceMaster findByDeviceId(String deviceId);

	@Query("SELECT DM from DeviceMaster DM where DM.status in :status  and DM.customerMaster.customerMasterId = :customerId")
	List<DeviceMaster> findUnregisteredDevices(@Param("customerId") int customerId,
			@Param("status") List<String> status);

	@Transactional
	@Modifying
	@Query("UPDATE DeviceMaster d SET d.status = :status WHERE d.deviceMasterId in :deviceMasterIds")
	int updateDeviceStatus(@Param("deviceMasterIds") List<Long> deviceMasterIds,
			@Param("status") String status);

	@Transactional
	@Modifying
	@Query("UPDATE DeviceMaster d SET d.enableMigration = :yes WHERE d.deviceId in :deviceIds")
	void updateEnableL1HL0Devices(@Param("deviceIds") List<String> deviceIds,
			@Param("yes") String yes);
}