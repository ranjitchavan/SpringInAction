package com.einfochips.webportal.services;

import org.springframework.stereotype.Service;

import com.einfochips.webportal.domain.Users;

@Service
public interface UserService {

	/**
	 * Returns the list of users
	 * 
	 * @return
	 */
	public Iterable<Users> getAllUsers(Boolean isActive);

	/**
	 * Used to get user details by id
	 * 
	 * @param id
	 * @return
	 */
	public Users getUser(String id);

	/**
	 * 
	 * This method used to update user details
	 * 
	 * @param userRecord
	 * @return
	 */
	public Users editUser(Users userRecord);

	/**
	 * This method user to delete user by id
	 * 
	 * @param user
	 */
	public void deleteUser(Users user);

	/**
	 * This method used save user
	 * 
	 * @param id
	 * @return
	 */
	public Users save(Users user);

	/**
	 * 
	 * This method used to get user details by id
	 * 
	 * @param user
	 * @return
	 */
	public Users getUser(long id);

	/**
	 * This method used to find user by email Id
	 * 
	 * @param email
	 * @return
	 */
	public Users findUserByEmailId(String email);

	/**
	 * @param id
	 * @return
	 */
	public Users getUserForCreatedby(long id);

	/**
	 * @return
	 */
	public Iterable<Users> getAllActiveAndInactiveUsers();
}