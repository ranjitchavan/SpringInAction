package com.einfochips.webportal.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.einfochips.webportal.domain.DevicePartnoMaster;
import com.einfochips.webportal.services.DevicePartnoMasterService;

@RestController
public class DevicePartNoController {

	private static final Logger LOGGER = Logger.getLogger(DevicePartNoController.class);

	@Autowired
	private DevicePartnoMasterService devicePartnoMasterService;

	@RequestMapping(value = "/get-all-part-no", method = RequestMethod.GET)
	public ResponseEntity<List<DevicePartnoMaster>> deRegisterDevice() {
		LOGGER.info("------------Get All Device Part No Start------------");
		try {
			List<DevicePartnoMaster> devicePartnoMasters = devicePartnoMasterService
					.getAllDevicePartNumbers();
			return new ResponseEntity<>(devicePartnoMasters, HttpStatus.OK);

		}
		catch (Exception e) {
			LOGGER.error("Exception in de-register device. Error : " + e.getMessage());
			LOGGER.info("------------Get All Device Part No End------------");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/add-device-part-no", method = RequestMethod.POST)
	public ResponseEntity<?> addDevicePartNo(@RequestBody DevicePartnoMaster master) {
		LOGGER.info("------------Add Device Part No Start------------");
		try {
			if (devicePartnoMasterService
					.getDevicePartnoMasterByPartNo(master.getPartNo()) == null) {
				devicePartnoMasterService.addDevicePartNo(master);
				return new ResponseEntity<>(null, HttpStatus.CREATED);
			}
			else {
				LOGGER.info("------------ Device Part No Already exit------------");
				LOGGER.info("------------Add Device Part No End------------");
				LOGGER.info("master: getDevicePartnoMasterId:  "
						+ master.getDevicePartnoMasterId());
				if (master.getDevicePartnoMasterId() != 0) {

					LOGGER.info("in device master 0");
					DevicePartnoMaster devicePartNumber = devicePartnoMasterService
							.getDevicePartNumberById(master.getDevicePartnoMasterId());
					if (devicePartNumber != null) {
						if (devicePartNumber.getPartNo().equals(master.getPartNo())) {
							devicePartnoMasterService.addDevicePartNo(master);
							return new ResponseEntity<>(null, HttpStatus.OK);
						}
						else {

							if (devicePartnoMasterService.getDevicePartnoMasterByPartNo(
									master.getPartNo()) == null) {
								devicePartnoMasterService.addDevicePartNo(master);
								return new ResponseEntity<>(null, HttpStatus.OK);
							}
							else {
								// devicePartnoMasterService.addDevicePartNo(master);
								return new ResponseEntity<>(null,
										HttpStatus.ALREADY_REPORTED);
							}
						}
					}
				}
				else {

					if (devicePartnoMasterService
							.getDevicePartnoMasterByPartNo(master.getPartNo()) == null) {
						devicePartnoMasterService.addDevicePartNo(master);
					}

					return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
				}
			}
		}
		catch (Exception e) {
			LOGGER.error("Exception in adding device part no. Error : " + e.getMessage());
			LOGGER.info("------------Add Device Part No End------------");
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		return null;
	}

	@RequestMapping(value = "/delete-device-part-no", method = RequestMethod.POST)
	public ResponseEntity<?> deleteDevicePartNo(@RequestBody DevicePartnoMaster master) {
		LOGGER.info("------------Delete Device Part No Start------------");
		try {
			devicePartnoMasterService.deletePartNo(master);
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error(
					"Exception in deleting device part no. Error : " + e.getMessage());
			LOGGER.info("------------Delete Device Part No End------------");
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
}