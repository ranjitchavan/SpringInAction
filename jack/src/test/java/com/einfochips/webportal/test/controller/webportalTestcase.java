package com.einfochips.webportal.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Assert;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.einfochips.webportal.controllers.UserController;
import com.einfochips.webportal.domain.Users;
import com.einfochips.webportal.services.UserService;
import com.einfochips.webportal.test.base.AbstractControllerTest;
import com.einfochips.webportal.utils.PasswordUtility;

@Transactional
public class webportalTestcase extends AbstractControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		setUp(userController);
	}

	// @Test
	public void testEditUser() throws Exception {
		Users entity = getEntityStubDatauser();
		String uri = "/update-user";
		String inputJson = super.mapToJson(entity);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).content(inputJson))
				.andDo(print()).andReturn();
		int status = result.getResponse().getStatus();

		verify(userService, times(1)).editUser(any(Users.class));
		Assert.assertEquals("failure - expected HTTP status 200", 200, status);
	}

	// Mock data stub creation for editing the existing user ......
	private Users getEntityStubDatauser() {
		Users entity = new Users();
		entity.setUserId(8);
		entity.setEmail("aklash.shine@gmail.com");
		entity.setFirstName("akash");
		entity.setMiddleName("G");
		entity.setLastName("Shinde");
		entity.setPhone("9096596496");
		entity.setAddress(" Pune");
		entity.setPassword(PasswordUtility.passwordEncoder("sachin"));
		return entity;
	}

}
