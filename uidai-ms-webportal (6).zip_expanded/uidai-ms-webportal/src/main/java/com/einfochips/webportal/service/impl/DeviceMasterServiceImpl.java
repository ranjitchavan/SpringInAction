package com.einfochips.webportal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.DeviceMaster;
import com.einfochips.webportal.dto.DeregisteredDeviceDto;
import com.einfochips.webportal.repositories.DeviceMasterRepository;
import com.einfochips.webportal.services.DeviceMasterService;
import com.einfochips.webportal.utility.EntityDTOBeanUtils;

@Service
public class DeviceMasterServiceImpl implements DeviceMasterService {
	@Autowired
	private DeviceMasterRepository repository;

	@Override
	public void addDevice(List<DeviceMaster> deviceMaster) {
		repository.save(deviceMaster);
	}

	@Override
	public DeviceMaster ifExists(String device_id) {
		DeviceMaster master = repository.findByDeviceId(device_id);
		return master;
	}

	@Override
	public void deregister(List<DeviceMaster> deviceMaster) {
		List<DeviceMaster> deviceMasterList = new ArrayList<>();
		for (DeviceMaster master : deviceMaster) {
			DeviceMaster master2 = repository.findBySerialNo(master.getSerialNo());
			if (master2 != null) {
				master2.setStatus("De-register");
				deviceMasterList.add(master2);
			}
		}

		repository.save(deviceMasterList);
	}

	@Override
	public DeviceMaster getDevice(String device_id) {
		return repository.findByDeviceId(device_id);
	}

	@Override
	public void addSingleDevice(DeviceMaster deviceMaster) {
		repository.save(deviceMaster);
	}

	@Override
	public List<DeregisteredDeviceDto> getDeregisteredDevices(int customerId,
			List<String> status) {
		List<DeviceMaster> deviceMasterList = repository
				.findUnregisteredDevices(customerId, status);

		List<DeregisteredDeviceDto> deregisteredDeviceDtoList = new ArrayList<>();

		for (DeviceMaster deviceMaster : deviceMasterList) {
			DeregisteredDeviceDto deregisteredDeviceDto = new DeregisteredDeviceDto();
			EntityDTOBeanUtils.copyNotNullProperties(deregisteredDeviceDto, deviceMaster);
			deregisteredDeviceDtoList.add(deregisteredDeviceDto);
		}
		return deregisteredDeviceDtoList;
	}

	@Override
	public int updateDeviceStatus(List<Long> deviceMasterIds, String status) {
		return repository.updateDeviceStatus(deviceMasterIds, status);
	}
	
	// Update L1HL0 devices as a enable
	@Override
	public void updateEnableDevice(List<String> deviceId, String yes) {
		repository.updateEnableL1HL0Devices(deviceId, yes);
	}

}