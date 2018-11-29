package com.einfochips.webportal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.einfochips.webportal.domain.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {
	public Users findByEmail(String username);

	public Users findByUserIdAndIsActiveTrue(long id);

	public Users findByEmailAndPassword(String username, String passsword);

	public Iterable<Users> findByIsActive(boolean isActive);
}