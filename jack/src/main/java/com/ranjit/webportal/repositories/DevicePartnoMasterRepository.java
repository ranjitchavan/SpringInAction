package com.einfochips.webportal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.einfochips.webportal.domain.DevicePartnoMaster;

/**
 * 
 * @author asra.shaikh
 *
 */
@Repository
public interface DevicePartnoMasterRepository
		extends CrudRepository<DevicePartnoMaster, Integer> {

	public DevicePartnoMaster findByPartNo(String partNum);

	public DevicePartnoMaster findByDevicePartnoMasterId(int devicePartnoMasterId);
}