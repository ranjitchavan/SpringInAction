package com.einfochips.webportal.utils;

import java.io.IOException;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.einfochips.webportal.dto.DeviceMasterDTO;

public class CallApi {

	private CallApi() {

	}

	public static ResponseEntity<String> restCall(String restApiUrl, Document doc) {

		RestTemplate restTemplate = new RestTemplate();

		// Create header and set request & response type
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		SSLCertificateValidation.disable();
		// Create HttpEntity for request
		HttpEntity<String> entity = new HttpEntity<String>(documetToXmlString(doc, null),
				headers);

		// Call the rest api base on restApiUrl
		return restTemplate.exchange(restApiUrl, HttpMethod.POST, entity, String.class);

		// ResponseEntity<String> response =
		// restTemplate.postForEntity(restApiUrl, entity, String.class);
		// return response;

	}

	public static Document createDoc(DeviceMasterDTO deviceMasterDTO)
			throws ParserConfigurationException, UnsupportedEncodingException,
			SAXException, IOException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();

		Element rootElement = doc.createElement("MNTCLTParameters");
		doc.appendChild(rootElement);

		Element parameter = doc.createElement("Parameter");
		rootElement.appendChild(parameter);

		// staff elements
		parameter.setAttribute("id", "ReqID");
		parameter.setAttribute("value", "2");

		Element parameter1 = doc.createElement("Parameter");
		rootElement.appendChild(parameter1);

		parameter1.setAttribute("id", "DeviceID");
		parameter1.setAttribute("value", deviceMasterDTO.getDeviceId());

		Element parameter2 = doc.createElement("Parameter");
		rootElement.appendChild(parameter2);

		parameter2.setAttribute("id", "DeviceCode");
		parameter2.setAttribute("value", deviceMasterDTO.getDeviceCode());

		return doc;

	}

	public static String documetToXmlString(Document doc, Element element) {
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		String canonXmlString = "";
		try {
			transformer = tf.newTransformer();
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
			// "no");
			DOMSource domSource = null;
			if (element != null) {
				domSource = new DOMSource(element);
				transformer.transform(domSource, result);
				Canonicalizer canon = Canonicalizer
						.getInstance(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
				canonXmlString = new String(
						canon.canonicalize(writer.toString().getBytes()));
			}
			else {
				domSource = new DOMSource(doc);
				transformer.transform(domSource, result);
				canonXmlString = writer.toString();
			}
		}
		catch (TransformerConfigurationException e) {
		}
		catch (TransformerException e) {
		}
		catch (InvalidCanonicalizerException e) {
		}
		catch (CanonicalizationException e) {
		}
		catch (ParserConfigurationException e) {
		}
		catch (IOException e) {
		}
		catch (SAXException e) {
		}

		return canonXmlString;
	}

	/**
	 * @param restApiUrl
	 * @param logValue
	 * @return
	 */
	public static ResponseEntity<String> restCallForDisableAndEnableLogss(
			String restApiUrl, String logValue) {

		RestTemplate restTemplate = new RestTemplate();

		// Create header and set request & response type
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		SSLCertificateValidation.disable();
		// Create HttpEntity for request
		HttpEntity<String> entity = new HttpEntity<String>(logValue, headers);

		// Call the REST API base on restApiUrl
		return restTemplate.exchange(restApiUrl, HttpMethod.POST, entity, String.class);
	}

	public static ResponseEntity<String> restCallForGetAuditLogStatus(String api) {
		RestTemplate restTemplate = new RestTemplate();
		// Create header and set request & response type
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		SSLCertificateValidation.disable();
		// Create HttpEntity for request
		HttpEntity<String> entity = new HttpEntity<String>(api, headers);
		System.out.println("restApiUrl : " + api);
		// Call the rest api base on restApiUrl
		return restTemplate.exchange(api, HttpMethod.POST, entity, String.class);

	}

}
