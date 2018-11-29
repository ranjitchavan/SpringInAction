package com.einfochips.webportal.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.DevicePartnoMaster;

/**
 * 
 * @author asra.shaikh
 *
 */
@Service
public interface DevicePartnoMasterService {

	/**
	 * This method is used to get all device part numbers from DB.
	 * 
	 * @return
	 */
	List<DevicePartnoMaster> getAllDevicePartNumbers();

	void addDevicePartNo(DevicePartnoMaster devicePartnoMaster);

	void deletePartNo(DevicePartnoMaster devicePartnoMaster);

	public DevicePartnoMaster getDevicePartnoMasterByPartNo(String partNo);

	public DevicePartnoMaster getDevicePartNumberById(int devicePartnoMasterId);

	public List<String> getOnlyDevicePartNumbers();
}
