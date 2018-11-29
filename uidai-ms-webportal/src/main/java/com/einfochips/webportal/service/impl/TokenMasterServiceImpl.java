package com.einfochips.webportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.TokenName;
import com.einfochips.webportal.repositories.TokenMasterRepository;
import com.einfochips.webportal.services.TokenMasterService;

@Service
public class TokenMasterServiceImpl implements TokenMasterService {

	@Autowired
	private TokenMasterRepository tokenMasterRepository;

	@Override
	public List<TokenName> getAllTokens() {
		return (List<TokenName>) tokenMasterRepository.findAll();
	}

}
