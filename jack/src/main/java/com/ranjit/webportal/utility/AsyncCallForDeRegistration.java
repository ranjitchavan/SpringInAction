package com.einfochips.webportal.utility;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.einfochips.webportal.controllers.DeviceMasterController;
import com.einfochips.webportal.domain.DeviceMaster;
import com.einfochips.webportal.dto.DeviceMasterDTO;
import com.einfochips.webportal.services.DeviceMasterService;
import com.einfochips.webportal.utils.CallApi;

@Component
public class AsyncCallForDeRegistration {

	final static Logger logger = Logger.getLogger(DeviceMasterController.class);

	@Autowired
	private DeviceMasterService service;

	@Value("${unregister.api.path}")
	String apiPath;

	// @Async
	public List<Integer> deRegistration(MultipartFile file) throws InterruptedException {
		logger.info("--------------deRegistration method start-------------------");
		List<DeviceMasterDTO> masterDTOs = new ArrayList<>();
		DeviceMasterDTO deviceMasterDTO = new DeviceMasterDTO();
		List<Integer> result = new ArrayList<>();
		int success = 0;
		int fail = 0;

		try {

			OPCPackage pkg = OPCPackage.open(file.getInputStream());
			XSSFWorkbook wb = new XSSFWorkbook(pkg);

			// Get first sheet from the workbook
			XSSFSheet sheet = wb.getSheetAt(0);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			int header = 0;

			while (rowIterator.hasNext()) {
				logger.info("--------------in while loop-------------------");

				if (header == 0) {
					header++;
					continue;
				}

				Row row = rowIterator.next();
				deviceMasterDTO.setDeviceId(row.getCell(0).getStringCellValue());

				DeviceMaster deviceMaster = service
						.getDevice(deviceMasterDTO.getDeviceId());

				if (deviceMaster == null) {
					deviceMasterDTO.setStatus("Fail");
					masterDTOs.add(deviceMasterDTO);
					deviceMasterDTO = new DeviceMasterDTO();
					continue;
				}

				deviceMasterDTO.setDeviceCode(deviceMaster.getDeviceCode());

				Document doc = CallApi.createDoc(deviceMasterDTO);

				logger.info(
						"REST API called for decide : " + deviceMasterDTO.getDeviceId());

				ResponseEntity<String> responseEntity = CallApi.restCall(apiPath, doc);

				logger.info(" RESPONSE received from REST API called for decide : "
						+ responseEntity.getStatusCode());

				if (responseEntity.getStatusCode() == HttpStatus.OK) {
					logger.info("--------------200 response from api-------------------");

					DocumentBuilderFactory docFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

					Document responseData = docBuilder
							.parse(new InputSource(new ByteArrayInputStream(
									responseEntity.getBody().getBytes())));

					Element ele = responseData.getDocumentElement();
					NodeList el = ele.getChildNodes();

					Element element = (Element) el.item(1);

					// String response = element1.getAttribute("id");
					String errorCode = element.getAttribute("value");

					deviceMasterDTO.setErrorCode(errorCode);
					logger.info("--------------Error code : " + errorCode
							+ "-------------------");

					if (("0").equalsIgnoreCase(errorCode)) {

						logger.info(
								"--------------device de-registration successfull-------------------");
						DeviceMaster master = service
								.getDevice(deviceMasterDTO.getDeviceId());

						if (master != null) {
							logger.info(
									"--------------in update device status , updationtime, device code start-------------------");
							master.setStatus(Constants.DEVICE_STATUS_DE_REGISTERED);
							// master.setDeviceCode("");
							master.setUpdationTime(new Date());
							service.addSingleDevice(master);
							logger.info(
									"--------------in update device status , updationtime, device code end-------------------");
						}
						deviceMasterDTO.setStatus("Success");
						success++;
					}
					else {
						logger.info(
								"--------------device de-registration un-successfull-------------------");
						deviceMasterDTO.setStatus("Fail");
						fail++;
					}

					masterDTOs.add(deviceMasterDTO);
					deviceMasterDTO = new DeviceMasterDTO();
				}
				else {
					logger.info(
							"--------------not ok response from api-------------------");
				}
			}

			result.add(success);
			result.add(fail);

			logger.info("--------------deRegistration method end-------------------");
			return result;

		}
		catch (Exception e) {
			logger.error("Error in de-registration process. " + e.getMessage());
			return result;
		}

	}
}
