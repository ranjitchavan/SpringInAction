package com.einfochips.webportal.services;

import java.util.List;
import java.util.Set;

import com.einfochips.webportal.domain.DeviceMaster;
import com.einfochips.webportal.dto.DeregisteredDeviceDto;

public interface DeviceMasterService {

	public void addDevice(List<DeviceMaster> deviceMaster);

	public void deregister(List<DeviceMaster> deviceMaster);

	public DeviceMaster ifExists(String device_id);

	public DeviceMaster getDevice(String device_id);

	public void addSingleDevice(DeviceMaster deviceMaster);

	public List<DeregisteredDeviceDto> getDeregisteredDevices(int customerId,
			List<String> status);

	public int updateDeviceStatus(List<Long> deviceMasterIds, String status);
	
	// Update L1HL0 devices as a enable
	public void updateEnableDevice(List<String> device_id, String yes);
}