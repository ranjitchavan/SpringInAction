package com.einfochips.webportal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.AppStatus;
import com.einfochips.webportal.repositories.AppRepository;
import com.einfochips.webportal.services.AppService;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private AppRepository appRepository;

	@Override
	public void deleteApp(AppStatus appStatus) {
		appRepository.delete(appStatus);

	}

	@Override
	public List<AppStatus> findAllApp() {
		List<AppStatus> list = new ArrayList<>();

		for (AppStatus as : appRepository.findAll()) {
			list.add(as);
		}

		return list;
	}

	@Override
	public AppStatus saveAppData(AppStatus appStatus) {
		return appRepository.save(appStatus);
	}

	@Override
	public AppStatus findByIpAdress(String ipAddress) {

		return appRepository.findByIpAddress(ipAddress);
	}

}
