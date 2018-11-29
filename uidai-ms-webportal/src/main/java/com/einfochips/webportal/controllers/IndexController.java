package com.einfochips.webportal.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author akash.shinde
 *
 */
@Controller
public class IndexController {
	private static final Logger LOGGER = Logger.getLogger(IndexController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model) {
		LOGGER.info("called index page");
		return "login";
	}
}
