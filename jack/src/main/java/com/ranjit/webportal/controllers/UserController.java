package com.einfochips.webportal.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.einfochips.webportal.domain.Users;
import com.einfochips.webportal.dto.UserDTO;
import com.einfochips.webportal.services.UserService;
import com.einfochips.webportal.utils.PasswordUtility;
import com.einfochips.webportal.utils.UserTokenParser;

/**
 * @author akash.shinde
 *
 */
@RestController
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class);

	/**
	 * 
	 */
	@Autowired
	private UserService userService;

	/**
	 * @param request
	 * @return
	 * 
	 * This method used to get all users from database
	 */
	@RequestMapping(value = "/get-all-user", method = RequestMethod.POST)
	public ResponseEntity<List<UserDTO>> getAllUser(
			@RequestParam("isActive") Boolean users) {
		LOGGER.info("get all user");
		LOGGER.info(users);
		List<UserDTO> list = new ArrayList<>();
		if (userService.getAllUsers(users).spliterator().getExactSizeIfKnown() > 0) {

			for (Users u : userService.getAllUsers(users)) {
				UserDTO object = new UserDTO();
				object.setUserId(u.getUserId());
				object.setAddress(u.getAddress());
				if (u.getCreatedBy() != 0) {
					Users uObject = userService.getUser(u.getCreatedBy());
					if (uObject != null) {
						object.setCreatedBy(
								uObject.getFirstName() + " " + uObject.getLastName());
					}

				}

				if ((u.getMiddleName() != null) && (!"".equals(u.getMiddleName()))) {
					object.setUsername(u.getFirstName() + " " + u.getMiddleName() + " "
							+ u.getLastName());
				}
				else {
					object.setUsername(u.getFirstName() + " " + u.getLastName());
				}

				object.setEmail(u.getEmail());
				object.setPhone(u.getPhone());
				object.setFirstName(u.getFirstName());
				object.setLastName(u.getLastName());
				object.setMiddleName(u.getMiddleName());
				object.setUpdationTime(u.getUpdationTime());
				list.add(object);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
		}

	}

	/**
	 * @param user This method used to add users to database
	 */

	@RequestMapping(value = "/add-user", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" })
	public ResponseEntity<Users> addUser(@RequestBody Users user,
			HttpServletRequest request) {
		LOGGER.info("Add user " + request);
		ResponseEntity<Users> response;
		Integer userId = UserTokenParser.getUserIdFromToken(request);
		LOGGER.info("User  id from token:  " + userId);
		user.setPassword(PasswordUtility.passwordEncoder(user.getPassword()));
		user.setActive(true);
		user.setCreatedBy(userId);
		user.setUpdationTime(new Date());
		user.setCreationTime(new Date());
		user.setSuperUser(false);
		user.setNewUser(true);
		Users u = userService.findUserByEmailId(user.getEmail());
		if (u == null) {
			LOGGER.info("user saved");
			response = new ResponseEntity<>(userService.save(user), HttpStatus.OK);
		}
		else {
			if (u.isActive()) {
				response = new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
			}
			else {
				response = new ResponseEntity<>(HttpStatus.IM_USED);
			}

		}
		return response;
	}

	/**
	 * @param user This method used to update users to database
	 */
	@RequestMapping(value = "/update-user", method = RequestMethod.POST)
	public ResponseEntity<Users> editUser(@RequestBody Users userRecord,
			HttpServletRequest httpServletRequest) {
		LOGGER.info("------IN UPDATE  USER START-----");
		LOGGER.info("user :" + userRecord);

		Integer userId = UserTokenParser.getUserIdFromToken(httpServletRequest);

		userRecord.setActive(true);
		Users u = userService.getUser(userRecord.getEmail());
		u.setFirstName(userRecord.getFirstName());
		u.setMiddleName(userRecord.getMiddleName());
		u.setLastName(userRecord.getLastName());
		u.setAddress(userRecord.getAddress());
		u.setPhone(userRecord.getPhone());
		u.setUpdationTime(new Date());
		u.setCreatedBy(userId);
		u = userService.editUser(u);

		LOGGER.info("------IN UPDATE  USER END -----");

		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	/**
	 * @param user
	 * 
	 * This class used to soft delete user from database
	 */
	@RequestMapping(value = "/deactivate-user", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> deActivateUser(@RequestBody UserDTO user,
			HttpServletRequest request) {
		LOGGER.info("------in de-activate  user-----");

		Integer userId = UserTokenParser.getUserIdFromToken(request);

		LOGGER.info("------GETTING user from token START-----");
		Users users = userService.getUser(UserTokenParser.getUserIdFromToken(request));
		LOGGER.info("------GETTING user from token END-----");

		Users u = userService.getUser(user.getEmail());
		if (users.getEmail().equals(user.getEmail())) {
			LOGGER.info("------de-activated user is current logged in user-----");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		else if (u.isSuperUser()) {
			return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
		}
		else {
			u.setActive(false);
			u.setCreatedBy(userId);
			u.setUpdationTime(new Date());
			userService.save(u);
			LOGGER.info("deleted user");
			LOGGER.info("------ user de-activated  successfully----");
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	/**
	 * @param user
	 * 
	 * This class used to soft delete user from database
	 */
	@RequestMapping(value = "/activate-user", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> activateUser(@RequestBody UserDTO user,
			HttpServletRequest request) {
		LOGGER.info("------IN re-activate user-----");

		Users u = userService.getUser(user.getEmail());
		Integer userId = UserTokenParser.getUserIdFromToken(request);

		u.setActive(true);
		u.setCreatedBy(userId);
		u.setUpdationTime(new Date());

		userService.save(u);

		LOGGER.info("------ USER re-activated successfully----");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * @param user
	 * @return This method used to change the password as requested by user.
	 */
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public ResponseEntity<UserController> changePassword(
			@RequestParam("password") String password, HttpServletRequest request,
			@RequestParam("new_password") String newPassword) {
		LOGGER.info("In change password");
		LOGGER.info("----- IN change password -----");

		ResponseEntity<UserController> response = null;
		LOGGER.info("----- getting user id from token change password -----");
		Users users = userService.getUser(UserTokenParser.getUserIdFromToken(request));
		if (users != null) {

			if (PasswordUtility.matchPassword(password, users.getPassword())) {
				users.setPassword(PasswordUtility.passwordEncoder(newPassword));
				userService.save(users);
				LOGGER.info("----- password updated successfully -----");
				response = new ResponseEntity<>(HttpStatus.OK);

			}
			else {
				response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

		}
		return response;
	}

	/**
	 * @param user
	 * @return This method used to change the password as requested by user.
	 */
	@RequestMapping(value = "/get-loggedin-user", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getCurrentLoggedInUser(HttpServletRequest request) {
		ResponseEntity<UserDTO> response;
		LOGGER.info("----- IN get logged in user details  -----");
		Users users = userService.getUser(UserTokenParser.getUserIdFromToken(request));
		UserDTO userdto = new UserDTO();
		if (users != null) {
			LOGGER.info(users);
			userdto.setFirstName(users.getFirstName());
			userdto.setLastName(users.getLastName());
			LOGGER.info("-----IN GET logged in  user details -----");
			response = new ResponseEntity<>(userdto, HttpStatus.OK);
		}
		else {
			LOGGER.info("-----user not logged in-----");
			response = new ResponseEntity<>(userdto, HttpStatus.FORBIDDEN);
		}

		return response;
	}
}