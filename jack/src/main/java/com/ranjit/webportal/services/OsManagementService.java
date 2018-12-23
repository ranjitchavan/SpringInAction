package com.einfochips.webportal.services;

import java.util.List;

public interface OsManagementService {
	List<String> getOsType();

	List<String> getOsSubType(String os);
}
