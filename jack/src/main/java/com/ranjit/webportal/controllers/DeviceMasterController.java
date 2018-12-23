package com.einfochips.webportal.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.einfochips.webportal.domain.CustomerMaster;
import com.einfochips.webportal.domain.DeviceMaster;
import com.einfochips.webportal.domain.DeviceProviderCertificate;
import com.einfochips.webportal.dto.DeregisteredDeviceDto;
import com.einfochips.webportal.dto.UnImportDeviceDto;
import com.einfochips.webportal.services.CustomerMasterService;
import com.einfochips.webportal.services.DeviceMasterService;
import com.einfochips.webportal.services.DevicePartnoMasterService;
import com.einfochips.webportal.services.DeviceProviderCertificateService;
import com.einfochips.webportal.utility.AsyncCallForDeRegistration;
import com.einfochips.webportal.utility.Constants;
import com.einfochips.webportal.utility.FieldValidator;

@RestController
public class DeviceMasterController {

	final static Logger logger = Logger.getLogger(DeviceMasterController.class);

	@Autowired
	private DeviceMasterService service;

	@Autowired
	DeviceProviderCertificateService deviceProviderCertificateService;

	@Autowired
	private AsyncCallForDeRegistration asyncCall;

	@Autowired
	private CustomerMasterService customerService;

	@Autowired
	private DevicePartnoMasterService devicePartnoMasterService;

	@RequestMapping(value = "/add-device-list", method = RequestMethod.POST, consumes = {
			"multipart/mixed", "multipart/form-data" })
	public ResponseEntity<ArrayList<String>> addDeviceList(
			@RequestParam("file") MultipartFile file,
			@RequestParam("SelectCustomer") String customerId) {
		logger.info("------------Add Device List Start------------");
		Integer successCount = 0;
		Integer errorCount = 0;
		logger.info("file get name:" + file.getOriginalFilename());

		// used to store unimportdevice devices
		List<UnImportDeviceDto> unimportdevice = new ArrayList<>(1000000);

		CustomerMaster customerMaster = customerService.isCustomerPresent(customerId);

		List<String> listPartNumbers = devicePartnoMasterService
				.getOnlyDevicePartNumbers();

		List<DeviceProviderCertificate> dps = deviceProviderCertificateService
				.findCustomerByCustomerId(customerMaster.getCustomerMasterId());

		if (dps.size() == 0) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE);
		}
		else {

			if (new Date().before(dps.get(0).getExpiryDate())) {
				logger.info("validate certificate");

				try {

					List<DeviceMaster> deviceMasterList = new ArrayList<>(1000000);
					OPCPackage pkg = OPCPackage.open(file.getInputStream());
					XSSFWorkbook wb = new XSSFWorkbook(pkg);

					XSSFSheet ws = wb.getSheetAt(0);
					ws.setForceFormulaRecalculation(true);

					int rowNum = ws.getLastRowNum() + 1;
					int colNum = ws.getRow(0).getLastCellNum();
					int productNameHeaderIndex = -1, oemPNHeaderIndex = -1,
							oemSNHeaderIndex = -1, desktopPNHeaderIndex = -1,
							desktopSNHeaderIndex = -1, firmwareHeaderIndex = -1,
							deviceTypeHeaderIndex = -1;

					// Read the headers first. Locate the ones you need
					XSSFRow rowHeader = ws.getRow(0);
					for (int j = 0; j < colNum; j++) {
						XSSFCell cell = rowHeader.getCell(j);
						String cellValue = cellToString(cell);
						if ("Product Name".equalsIgnoreCase(cellValue)) {
							productNameHeaderIndex = j;
						}
						else if ("OEM P/N".equalsIgnoreCase(cellValue)) {
							oemPNHeaderIndex = j;
						}
						else if ("OEM S/N".equalsIgnoreCase(cellValue)) {
							oemSNHeaderIndex = j;
						}
						else if ("Desktop P/N".equalsIgnoreCase(cellValue)) {
							desktopPNHeaderIndex = j;
						}
						else if ("Desktop S/N".equalsIgnoreCase(cellValue)) {
							desktopSNHeaderIndex = j;
						}
						else if ("Firmware Version".equalsIgnoreCase(cellValue)) {
							firmwareHeaderIndex = j;
						}
						else if ("Device Type".equalsIgnoreCase(cellValue)) {
							deviceTypeHeaderIndex = j;
						}
					}

					if (productNameHeaderIndex == -1 || oemPNHeaderIndex == -1
							|| oemSNHeaderIndex == -1 || desktopPNHeaderIndex == -1
							|| desktopSNHeaderIndex == -1 || firmwareHeaderIndex == -1
							|| deviceTypeHeaderIndex == -1) {
						throw new Exception("Could not find all header indexes");
					}

					DeviceMaster master = new DeviceMaster();
					UnImportDeviceDto unImportDeviceDto = new UnImportDeviceDto();

					unImportDeviceDto.setDeviceId("Device Id");
					unImportDeviceDto.setPartNo("Part No");
					unImportDeviceDto.setSerialNo("Serial No");
					unImportDeviceDto.setReasonOfFailure("Import Status");
				//	unimportdevice.add(unImportDeviceDto);

					unImportDeviceDto = new UnImportDeviceDto();

					for (int i = 1; i < rowNum; i++) {

						XSSFRow row = ws.getRow(i);

						if (!checkIfRowIsEmpty(row)) {

							/// new added me
							if (FieldValidator.isNull(
									cellToString(row.getCell(productNameHeaderIndex)))) {

								logger.info("product name is not present");

								unImportDeviceDto.setDeviceId("");
								unImportDeviceDto.setPartNo(
										cellToString(row.getCell(oemPNHeaderIndex)));
								unImportDeviceDto.setSerialNo(
										cellToString(row.getCell(oemSNHeaderIndex)));
								unImportDeviceDto
										.setReasonOfFailure("Product name field empty");
								unimportdevice.add(unImportDeviceDto);
								unImportDeviceDto = new UnImportDeviceDto();
								errorCount++;
								continue;

							} // end new added

							if (FieldValidator
									.isNull(cellToString(row.getCell(oemPNHeaderIndex)))
									|| FieldValidator.isNull(cellToString(
											row.getCell(oemSNHeaderIndex)))) {

							//	logger.info("check part_no and serial_no epmty");

								if (cellToString(row.getCell(oemPNHeaderIndex)) != null) {
									unImportDeviceDto.setPartNo(
											cellToString(row.getCell(oemPNHeaderIndex)));
								}
								else {
									unImportDeviceDto.setPartNo("");
								}

								if (cellToString(row.getCell(oemSNHeaderIndex)) != null) {
									unImportDeviceDto.setSerialNo(
											cellToString(row.getCell(oemSNHeaderIndex)));
								}
								else {
									unImportDeviceDto.setSerialNo("");
								}

								unImportDeviceDto.setDeviceId("");

								unImportDeviceDto.setReasonOfFailure(
										"Invalid data for S/N and P/N");
								unimportdevice.add(unImportDeviceDto);
								logger.info(
										"serialno::" + unImportDeviceDto.getSerialNo());
								logger.info("part no::" + unImportDeviceDto.getPartNo());
								unImportDeviceDto = new UnImportDeviceDto();

								errorCount++;

								continue;
							}
							// }
							//logger.info("i am here");

							String part_no = cellToString(row.getCell(oemPNHeaderIndex));
							String serial_no = cellToString(
									row.getCell(oemSNHeaderIndex));

							String device_id = part_no.trim() + '-' + serial_no.trim();

							int zeroCount = 24 - device_id.length();

						//	logger.info("zeroCount:  " + zeroCount);
							if (FieldValidator.isNull(serial_no)) {
								continue;
							}

							while (zeroCount > 0) {

								device_id = "0" + device_id;
								part_no = "0" + part_no;
								zeroCount--;
							}

							logger.info("device_id:  " + device_id);
							DeviceMaster exists;

							try {
								exists = service.ifExists(device_id);
								if (exists == null) {
									master.setProductName(cellToString(
											row.getCell(productNameHeaderIndex)));
									master.setPartNo(part_no);
									master.setSerialNo(serial_no);

									if (!listPartNumbers.contains(part_no)) {
										logger.info(
												"part number is not found in part number table");

										errorCount++;
										unImportDeviceDto
												.setDeviceId(master.getDeviceId());
										unImportDeviceDto.setPartNo(master.getPartNo());
										unImportDeviceDto
												.setSerialNo(master.getSerialNo());
										unImportDeviceDto.setReasonOfFailure(
												"Part number not found");
										unimportdevice.add(unImportDeviceDto);
										unImportDeviceDto = new UnImportDeviceDto();

										continue;
									}

									if (row.getCell(desktopPNHeaderIndex) != null) {
										if (cellToString(
												row.getCell(desktopPNHeaderIndex)) != ""
												&& cellToString(row.getCell(
														desktopPNHeaderIndex)) != null) {
											master.setDesktopPN(cellToString(
													row.getCell(desktopPNHeaderIndex)));
										}
										else {
											master.setDesktopPN("");
										}
									}
									else {
										master.setDesktopPN("");
									}

									if (row.getCell(desktopSNHeaderIndex) != null) {
										if (cellToString(
												row.getCell(desktopSNHeaderIndex)) != ""
												&& cellToString(row.getCell(
														desktopSNHeaderIndex)) != null) {
											master.setDesktopSN(cellToString(
													row.getCell(desktopSNHeaderIndex)));
										}
										else {
											master.setDesktopSN("");
										}
									}
									else {
										master.setDesktopSN("");
									}

									if (row.getCell(firmwareHeaderIndex) != null) {
										if (cellToString(
												row.getCell(firmwareHeaderIndex)) != null
												&& cellToString(row.getCell(
														firmwareHeaderIndex)) != null) {
											master.setFirmwareVersion(cellToString(
													row.getCell(firmwareHeaderIndex)));
										}
										else {
											master.setFirmwareVersion("");
										}
									}
									else {
										master.setFirmwareVersion("");
									}
									master.setCreationTime(new Date());
									master.setUpdationTime(new Date());

									master.setCustomerMaster(customerMaster);
									master.setStatus("Not Registered");
									master.setDeviceId(device_id);

									// ADDED changes for L0 to L1 migration
									// START
									if (row.getCell(deviceTypeHeaderIndex) != null) {
										if (cellToString(row
												.getCell(deviceTypeHeaderIndex)) != null
												&& cellToString(row.getCell(
														deviceTypeHeaderIndex)) != null) {
											master.setDeviceType(cellToString(
													row.getCell(deviceTypeHeaderIndex)));
										}
										else {
											master.setDeviceType("");
											//errorCount++;
											logger.info("invalid data");
										}
									}
									else {
										master.setDeviceType("");
										//errorCount++;
										logger.info("invalid data last");
									}

									master.setEnableMigration("No");

									// ADDED changes for L0 to L1 migration END
									if (master.getDeviceType().equals("L1")
											|| master.getDeviceType().equals("L0")) {
										if (deviceMasterList.contains(master)) {
											errorCount++;
											// logger.info("Duplicate device found in
											// list");
											unImportDeviceDto
													.setDeviceId(master.getDeviceId());
											unImportDeviceDto
													.setPartNo(master.getPartNo());
											unImportDeviceDto
													.setSerialNo(master.getSerialNo());
											unImportDeviceDto.setReasonOfFailure(
													"Duplicate Record");
											unimportdevice.add(unImportDeviceDto);
											unImportDeviceDto = new UnImportDeviceDto();

										}
										else {
											deviceMasterList.add(master);

											unImportDeviceDto
													.setDeviceId(master.getDeviceId());
											unImportDeviceDto
													.setPartNo(master.getPartNo());
											unImportDeviceDto
													.setSerialNo(master.getSerialNo());
											unImportDeviceDto
													.setReasonOfFailure("Success");
											unimportdevice.add(unImportDeviceDto);
											unImportDeviceDto = new UnImportDeviceDto();

											successCount++;
										}

									}
									else {

										logger.info(master.getDeviceType()
												+ " - Invalid Device Type for "
												+ master.getDeviceId());

										unImportDeviceDto
												.setDeviceId(master.getDeviceId());
										unImportDeviceDto.setPartNo(master.getPartNo());
										unImportDeviceDto
												.setSerialNo(master.getSerialNo());
										unImportDeviceDto.setReasonOfFailure(
												"Invalid Device Type: "
														+ master.getDeviceType());
										unimportdevice.add(unImportDeviceDto);
										unImportDeviceDto = new UnImportDeviceDto();
										errorCount++;
									}

									// logger.info("increase count");
									master = new DeviceMaster();

								}
								else {
									// logger.info("Duplicate entry for device id : " +
									// device_id);
									errorCount++;

									unImportDeviceDto.setDeviceId(exists.getDeviceId());
									unImportDeviceDto.setPartNo(exists.getPartNo());
									unImportDeviceDto.setSerialNo(exists.getSerialNo());
									unImportDeviceDto
											.setReasonOfFailure("Duplicate Record");
									unimportdevice.add(unImportDeviceDto);
									unImportDeviceDto = new UnImportDeviceDto();

								}
							}
							catch (Exception e) {
								logger.error("Error in getting deviceId data : "
										+ device_id + ". Error : " + e.getMessage());

								unImportDeviceDto.setDeviceId(master.getDeviceId());
								unImportDeviceDto.setPartNo(master.getPartNo());
								unImportDeviceDto.setSerialNo(master.getSerialNo());
								unImportDeviceDto.setReasonOfFailure("Unknown Error");

								unimportdevice.add(unImportDeviceDto);
								unImportDeviceDto = new UnImportDeviceDto();

							}

							if (deviceMasterList.size() > 2000) {
								service.addDevice(deviceMasterList);
								deviceMasterList = new ArrayList<>();
							}

						}
						else {
							logger.info("EXCEL cell is emptys");
						}
					}

					if (deviceMasterList.size() > 0) {
						service.addDevice(deviceMasterList);
					}

					String fileName = "";

					if (file != null) {
						fileName = file.getOriginalFilename();
						logger.info("file namae: " + fileName);
					}

					String fname = unImportedDevice(unimportdevice, fileName);

					ArrayList<String> results = new ArrayList<>();
					results.add(successCount + "");
					results.add(errorCount + "");
					results.add((successCount + errorCount) + "");
					logger.info("returning file: " + fname);
					results.add(fname);

					return new ResponseEntity<>(results, HttpStatus.OK);

				}
				catch (Exception e) {
					e.printStackTrace();
					logger.error(
							"Exception in add device list.  Error : " + e.getMessage());
					logger.info("------------Add Device List End------------");
					return new ResponseEntity<>(new ArrayList<>(),
							HttpStatus.FAILED_DEPENDENCY);
				}

			}
			else {
				logger.info("Invalid certificate" + dps.get(0).getExpiryDate());
				errorCount++;
				logger.info(
						"-------------ErrorCount in Invalid Certificate::" + errorCount);
				return new ResponseEntity<>(new ArrayList<>(),
						HttpStatus.PRECONDITION_FAILED);
			}
		}
	}

	public static String cellToString(XSSFCell cell) {

		int type;
		Object result = null;

		if (cell != null) {
			type = cell.getCellType();

			switch (type) {

			case XSSFCell.CELL_TYPE_NUMERIC:
				result = BigDecimal.valueOf(cell.getNumericCellValue()).toPlainString();
				break;

			case XSSFCell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;

			case XSSFCell.CELL_TYPE_BLANK:
				result = "";
				break;

			case XSSFCell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
			}

			return result.toString();

		}
		else {
			return null;
		}

	}

	@RequestMapping(value = "/de-register-device", method = RequestMethod.POST, consumes = {
			"multipart/mixed", "multipart/form-data" })
	public ResponseEntity<List<Integer>> deRegisterDevice(
			@RequestParam("file") MultipartFile file) throws InterruptedException {
		logger.info("------------De-Register device start------------");
		List<Integer> result = asyncCall.deRegistration(file);
		logger.info("------------De-Register device end------------");
		if (!result.isEmpty()) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
	}

	/**
	 * API to get list of de-registered devices.
	 * 
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deregistered-devices", method = RequestMethod.GET)
	public ResponseEntity<List<DeregisteredDeviceDto>> getDeregisteredDevices(
			@RequestParam("customerId") int customerId) {

		ResponseEntity<List<DeregisteredDeviceDto>> response = null;

		try {
			logger.info("Getting device(s) with status :-> "
					+ Constants.DEVICE_STATUS_UNREGISTERED_FROM_CLIENT);
			List<String> status = new ArrayList<>();
			status.add(Constants.DEVICE_STATUS_UNREGISTERED_FROM_CLIENT);
			status.add(Constants.DEVICE_STATUS_DE_REGISTERED);

			List<DeregisteredDeviceDto> deregisteredDeviceDtoList = service
					.getDeregisteredDevices(customerId, status);

			logger.info("list size of devices with status["
					+ Constants.DEVICE_STATUS_UNREGISTERED_FROM_CLIENT + "] is : -> "
					+ deregisteredDeviceDtoList.size());
			if (deregisteredDeviceDtoList.isEmpty()) {
				response = new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			else {
				response = new ResponseEntity<>(deregisteredDeviceDtoList, HttpStatus.OK);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return response;
	}

	/**
	 * API to convert device status from DE-REGISTERED to NOT-REGISTERED.
	 * 
	 * @param deviceMasterIds
	 * @return
	 */
	@RequestMapping(value = "/notregistered-device", method = RequestMethod.POST)
	public ResponseEntity<String> updateDeviceStatus(
			@RequestBody List<Long> deviceMasterIds) {
		ResponseEntity<String> response = null;
		try {
			logger.info("Setting device status to - ["
					+ Constants.DEVICE_STATUS_NOT_REGISTERED + "] for devices :->"
					+ deviceMasterIds);
			int updateCount = service.updateDeviceStatus(deviceMasterIds,
					Constants.DEVICE_STATUS_NOT_REGISTERED);
			logger.info("No. of devices with status updated to["
					+ Constants.DEVICE_STATUS_NOT_REGISTERED + "] :->" + updateCount);
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return response;
	}

	private boolean checkIfRowIsEmpty(Row row) {

		if (row == null) {
			return true;
		}

		if (row.getLastCellNum() <= 0) {
			return true;
		}

		for (int cellNum = row.getFirstCellNum(); cellNum < row
				.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK
					&& StringUtils.isNotBlank(cell.toString())) {
				return false;
			}
		}

		return true;
	}

	public static String unImportedDevice(List<UnImportDeviceDto> unimportdevice,
			String fileName) throws IOException {

		logger.info("Writing into file start: " + fileName);

		// fileName = System.getProperty("catalina.base") + "//UnImportDevices" +
		// ".csv".trim();
		String dateToEnd = new Date().toString().replaceAll(" ", "_").replaceAll(":",
				"_");
		;
		String fName = fileName;

		fileName = System.getProperty("catalina.base") + "//" + fName.replaceAll(" ", "_")
				+ "_" + dateToEnd + ".csv".trim();

		logger.info(fileName.trim());

		FileWriter fileWriter = new FileWriter(fileName.trim());

		fileWriter.append("Device id");
		fileWriter.append(',');
		fileWriter.append("Part no");

		fileWriter.append(',');
		fileWriter.append("Serial no");

		fileWriter.append(',');
		fileWriter.append("Import Status");
		fileWriter.append('\n');

		try {
			Iterator reportDataItr = unimportdevice.listIterator();
			while (reportDataItr.hasNext()) {
				UnImportDeviceDto element = (UnImportDeviceDto) reportDataItr.next();

				fileWriter.append(element.getPartNo() + "-" + element.getSerialNo());
				fileWriter.append(",");
				fileWriter.append(element.getPartNo());
				fileWriter.append(",");
				fileWriter.append(element.getSerialNo());
				fileWriter.append(",");
				fileWriter.append(element.getReasonOfFailure());
				fileWriter.append("\n");

			}
			// pw.write(sb.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		fileWriter.close();
		logger.info("UnImportedDevices.csv written successfully: " + fileName);
		return fileName;

	}

	private static void writeBook(DeviceMaster dm, Row row) {
		Cell cell = row.createCell(1);
		cell.setCellValue(dm.getDeviceId());

		cell = row.createCell(2);
		cell.setCellValue(dm.getDeviceType());
		cell = row.createCell(3);

		cell.setCellValue(dm.getProductName());

	}

	@RequestMapping(value = "/get-unimport-Device", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> downloadUnImportDeviceList(
			HttpServletResponse response, @RequestParam("filename") String fileName) {
		logger.info("get-unimport-Device method file received" + fileName);

		try {
			String filePathToBeServed = fileName;// complete file name with path;
			File fileToDownload = new File(filePathToBeServed);
			InputStream inputStream = new FileInputStream(fileToDownload);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + fileName + ".csv");
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		}
		catch (Exception e) {
			logger.debug(
					"Request could not be completed at this moment. Please try again.");
			e.printStackTrace();
		}
		return null;
	}

// Add enable migration as per device-id
	@RequestMapping(value = "/enable-L1HL0-Devices", method = RequestMethod.POST, consumes = {
			"multipart/mixed", "multipart/form-data" })
	public ResponseEntity<ArrayList<String>> addL1HL0Devices(
			@RequestParam("file") MultipartFile file) {
	
		logger.info("------------Add Device List Start------------");
		Integer successCount = 0;
		Integer errorCount = 0;

		// used to store unimportdevice devices
		List<UnImportDeviceDto> unimportdevice = new ArrayList<>();

		try {

			List<String> deviceMasterList = new ArrayList<>();
			OPCPackage pkg = OPCPackage.open(file.getInputStream());
			XSSFWorkbook wb = new XSSFWorkbook(pkg);

			XSSFSheet ws = wb.getSheetAt(0);
			ws.setForceFormulaRecalculation(true);

			int rowNum = ws.getLastRowNum() + 1;
			int colNum = ws.getRow(0).getLastCellNum();
			int deviceIdIndex = -1;
		
			// Read the headers first. Locate the ones you need
			XSSFRow rowHeader = ws.getRow(0);
			for (int j = 0; j < colNum; j++) {
				XSSFCell cell = rowHeader.getCell(j);
				String cellValue = cellToString(cell);
				if ("Device Id".equalsIgnoreCase(cellValue)) {
					deviceIdIndex = j;
				}
			}

			if (deviceIdIndex == -1 ) {
					throw new Exception("Could not find header indexe");
			}

			DeviceMaster master = new DeviceMaster();
			UnImportDeviceDto unImportDeviceDto = new UnImportDeviceDto();

			unImportDeviceDto.setDeviceId("Device Id");
			unImportDeviceDto.setPartNo("Part No");
			unImportDeviceDto.setSerialNo("Serial No");
			unImportDeviceDto.setReasonOfFailure("Import Status");
			//unimportdevice.add(unImportDeviceDto);

			unImportDeviceDto = new UnImportDeviceDto();

			for (int i = 1; i < rowNum; i++) {

				XSSFRow row = ws.getRow(i);

				if (!checkIfRowIsEmpty(row)) {


					String device_id = cellToString(row.getCell(deviceIdIndex));

					DeviceMaster exists;

					try {
						exists = service.ifExists(device_id);
						if (exists != null) {
							deviceMasterList.add(device_id);

							unImportDeviceDto.setDeviceId(device_id);
							unImportDeviceDto.setReasonOfFailure("Success");
							unimportdevice.add(unImportDeviceDto);
							unImportDeviceDto = new UnImportDeviceDto();
							
							successCount++;
						} else {
							unImportDeviceDto.setDeviceId(device_id);
							unImportDeviceDto.setReasonOfFailure("Entry not found in database");
							unimportdevice.add(unImportDeviceDto);
							unImportDeviceDto = new UnImportDeviceDto();

							errorCount++;
						}
					} catch (Exception e) {
		
						unImportDeviceDto.setDeviceId(device_id);
						unImportDeviceDto.setReasonOfFailure("Unknown Error");

						unimportdevice.add(unImportDeviceDto);
						unImportDeviceDto = new UnImportDeviceDto();

					}
				} else {
					logger.info("EXCEL cell is emptys");
				}
			}

			if (deviceMasterList.size() > 0) {
				service.updateEnableDevice(deviceMasterList, "yes");
			}
			
			String fileName = "";

			if (file != null) {
				fileName = file.getOriginalFilename();
				logger.info("File Name :" + fileName);
			}

			ArrayList<String> results = new ArrayList<>();
			results.add(successCount + "");
			results.add(errorCount + "");
			results.add((successCount + errorCount) + "");
			
			String fName = unImportedL0HL1Device(unimportdevice,fileName);

			results.add(fName);
			
			return new ResponseEntity<>(results, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in add device list.  Error : " + e.getMessage());
			logger.info("------------Add Device List End------------");
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	public static String unImportedL0HL1Device(List<UnImportDeviceDto> unimportdevice,
			String fileName) throws IOException {

		logger.info("Writing into file start: " + fileName);

		String dateToEnd = new Date().toString().replaceAll(" ", "_").replaceAll(":","_");;
	
		String fName = fileName;
		fileName = System.getProperty("catalina.base") +"//"+fName.replaceAll(" ", "_")+"_"+ dateToEnd + ".csv".trim();

		logger.info(fileName.trim());

		FileWriter fileWriter = new FileWriter(fileName.trim());

		fileWriter.append("Device id");
		fileWriter.append(',');
		fileWriter.append("Reason of failure");
		fileWriter.append('\n');

		try {
			Iterator reportDataItr = unimportdevice.listIterator();
			while (reportDataItr.hasNext()) {
				UnImportDeviceDto element = (UnImportDeviceDto) reportDataItr.next();
				fileWriter.append(element.getDeviceId());
				fileWriter.append(",");
				fileWriter.append(element.getReasonOfFailure());
				fileWriter.append("\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		fileWriter.close();
		
		logger.info("UnImportedDevices.csv written successfully: " + fileName);
		
		return fileName;

	}

}