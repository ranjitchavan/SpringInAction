package com.einfochips.webportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.DevicePartnoMaster;
import com.einfochips.webportal.repositories.DevicePartnoMasterRepository;
import com.einfochips.webportal.services.DevicePartnoMasterService;

/**
 * 
 * @author asra.shaikh
 *
 */
@Service
public class DevicePartnoMasterServiceImpl implements DevicePartnoMasterService {

	@Autowired
	private DevicePartnoMasterRepository devicePartnoMasterRepository;

	@Override
	public List<DevicePartnoMaster> getAllDevicePartNumbers() {
		List<DevicePartnoMaster> devicePartnoMasterList = new ArrayList<>();
		devicePartnoMasterRepository.findAll().forEach(devicePartnoMasterList::add);
		return devicePartnoMasterList;
	}

	@Override
	public void addDevicePartNo(DevicePartnoMaster devicePartnoMaster) {
		devicePartnoMasterRepository.save(devicePartnoMaster);
	}

	@Override
	public void deletePartNo(DevicePartnoMaster devicePartnoMaster) {
		devicePartnoMasterRepository.delete(devicePartnoMaster);
	}

	@Override
	public DevicePartnoMaster getDevicePartnoMasterByPartNo(String partNo) {
		return devicePartnoMasterRepository.findByPartNo(partNo);
	}

	@Override
	public DevicePartnoMaster getDevicePartNumberById(int devicePartnoMasterId) {
		return devicePartnoMasterRepository
				.findByDevicePartnoMasterId(devicePartnoMasterId);

	}

	@Override
	public List<String> getOnlyDevicePartNumbers() {
		
		List<String> devicePartnoMasterList = new ArrayList<>();
		for(DevicePartnoMaster dePartNumber: devicePartnoMasterRepository.findAll()){
			devicePartnoMasterList.add(dePartNumber.getPartNo());
		}

		return devicePartnoMasterList;
		
		
	}
}
