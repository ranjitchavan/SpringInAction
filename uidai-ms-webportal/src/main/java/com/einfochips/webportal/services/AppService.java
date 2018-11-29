package com.einfochips.webportal.services;

import java.util.List;

import com.einfochips.webportal.domain.AppStatus;

public interface AppService {

	public AppStatus saveAppData(AppStatus appStatus);

	public void deleteApp(AppStatus appStatus);

	public List<AppStatus> findAllApp();

	public AppStatus findByIpAdress(String ipAddress);

}
