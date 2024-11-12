package com.learning.restful_web_services.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.learning.restful_web_services.entities.SomeBean;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public SomeBean staticFilter() {
		return new SomeBean("val1", "val2", "val3");
	}

	@GetMapping("/filtering/list")
	public List<SomeBean> filterList() {
		return Arrays.asList(new SomeBean("val1", "val2", "val3"), new SomeBean("val4", "val5", "val6"));
	}

	@GetMapping("/filtering/dynamic") // field2 excluded
	public MappingJacksonValue dynamicFilter() {
		SomeBean someBean = new SomeBean("val1", "val2", "val3");
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

		mappingJacksonValue.setFilters(filters);

		return mappingJacksonValue;
	}

	@GetMapping("/filtering/list/dynamic") // field3 excluded
	public MappingJacksonValue dynamicFilterList() {
		List<SomeBean> li = Arrays.asList(new SomeBean("val1", "val2", "val3"), new SomeBean("val4", "val5", "val6"));
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(li);

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

		mappingJacksonValue.setFilters(filters);

		return mappingJacksonValue;
	}
}
