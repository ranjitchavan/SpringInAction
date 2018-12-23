package com.einfochips.webportal.repositories;

import org.springframework.data.repository.CrudRepository;

import com.einfochips.webportal.domain.TokenName;

/**
 * @author akash.shinde
 *
 */
public interface TokenMasterRepository extends CrudRepository<TokenName, Integer> {
}