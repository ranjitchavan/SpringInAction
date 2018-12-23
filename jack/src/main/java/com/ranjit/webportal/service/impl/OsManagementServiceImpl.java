package com.einfochips.webportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.repositories.OsManagementRepository;
import com.einfochips.webportal.services.OsManagementService;

@Service
public class OsManagementServiceImpl implements OsManagementService {

	@Autowired
	private OsManagementRepository repository;

	public List<String> getOsType() {
		return repository.getOsType();
	}

	@Override
	public List<String> getOsSubType(String os) {
		return repository.getOsSubType(os);
	}

}
