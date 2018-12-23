package com.einfochips.webportal.utility;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

/**
 * 
 *
 *
 */
public class ConversionUtility {

	/**
	 * This method will help to decode, encoded string.
	 * 
	 * @param encodedString
	 * @return
	 */
	public static byte[] decodeBase64Certificate(String encodedString) {
		byte[] decoded = Base64.getDecoder().decode(encodedString);
		return decoded;
	}

	/**
	 * This method will help to encode, decoded string.
	 * 
	 * @param decodedbytes
	 * @return
	 */
	public static String encodeBase64Certificate(byte[] decodedbytes) {
		String encoded = Base64.getEncoder().encodeToString(decodedbytes);
		return encoded;
	}

	/**
	 * This method will help to Read certificate.
	 * 
	 * @param encodedString
	 * @return
	 * @throws CertificateException
	 */
	public static X509Certificate readCertificate(String certificateData)
			throws CertificateException {
		X509Certificate x509Certificate = null;
		// decode certificate and store into stream
		ByteArrayInputStream certificateStream = new ByteArrayInputStream(
				decodeBase64Certificate(certificateData));
		// read certificate and store into certificate object
		Certificate certificate = CertificateFactory.getInstance("X.509")
				.generateCertificate(certificateStream);
		// read x509Certificate from certificate
		x509Certificate = (X509Certificate) certificate;

		return x509Certificate;
	}

	public static Date getExpiryDate(byte[] decodedbytes) {
		X509Certificate x509Certificate = null;
		try {
			x509Certificate = readCertificate(new String(decodedbytes));
		}
		catch (CertificateException e) {
			return null;
		}
		return x509Certificate.getNotAfter();

	}

	public static Date getFromDate(byte[] decodedbytes) {
		X509Certificate x509Certificate = null;
		try {
			x509Certificate = readCertificate(new String(decodedbytes));
		}
		catch (CertificateException e) {
			return null;
		}
		return x509Certificate.getNotBefore();

	}

	public static String validCertificate(String cert) {
		boolean result = (cert.contains(Constants.CERTIFICATE_VALIDATE_START_STRING)
				&& cert.contains(Constants.CERTIFICATE_VALIDATE_END_STRING));
System.out.println(result);
		if (result) {
			cert = new String(cert.getBytes())
					.replace(Constants.CERTIFICATE_VALIDATE_START_STRING, "")
					.replace(Constants.CERTIFICATE_VALIDATE_END_STRING, "")
					.replaceAll("[\n\r]", "");
			return cert;
		}
		else {
			// Change
			cert = new String(cert.getBytes())
					.replace(Constants.CERTIFICATE_VALIDATE_START_STRING, "")
					.replace(Constants.CERTIFICATE_VALIDATE_END_STRING, "")
					.replaceAll("[\n\r]", "");
			return cert;
		}
	}

}
