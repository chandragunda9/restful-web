package com.learning.restful_web_services.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learning.restful_web_services.entities.PersonV1;
import com.learning.restful_web_services.entities.PersonV2;

@RestController
public class VersioningController {

	@RequestMapping(value = "/message/v1", method = RequestMethod.GET)
	public String helloV1() {
		return "v1";
	}

	@RequestMapping(value = "/message/v2", method = RequestMethod.GET)
	public String helloV2() {
		return "v2";
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET, params = "version=1")
	public String helloReqParamv1() {
		return "version 1";
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET, params = "version=2")
	public String helloReqParamv2() {
		return "version 2";
	}

	// headers versioning
	@RequestMapping(value = "/message", method = RequestMethod.GET, headers = "x-api-version=1")
	public String helloReqheaderv1() {
		return "header version 1";
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET, headers = "x-api-version=2")
	public String helloReqheaderv2() {
		return "header version 2";
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET, produces = "application/vnd.company.app-v1+json")
	public PersonV1 helloReqAcceptv1() {
		return new PersonV1("chandra gunda");
	}

	@RequestMapping(value = "/message", method = RequestMethod.GET, produces = "application/vnd.company.app-v2+json")
	public PersonV2 helloReqAcceptv2() {
		return new PersonV2("chandra", "gunda");
	}

}
