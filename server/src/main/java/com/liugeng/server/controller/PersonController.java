package com.liugeng.server.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liugeng.pojo.CommonResponse;
import com.liugeng.pojo.Person;

@RestController
public class PersonController {

	private final List<Person> personList = new CopyOnWriteArrayList<>();
	private final Logger logger = LoggerFactory.getLogger(PersonController.class);
	private final AtomicInteger id = new AtomicInteger();


	@PostMapping("/persons")
	public CommonResponse addPersons(@RequestBody List<Person> personList){
		logger.info(personList.toString());
		personList.forEach(person -> person.setId(id.getAndIncrement()));
		this.personList.addAll(personList);
		CommonResponse<String> response = new CommonResponse<>();
		response.setCode("0");
		response.setMsg("success");
		return response;
	}

	@GetMapping("/persons")
	public CommonResponse getPersonList(){
		logger.debug("http calling");
		CommonResponse<List<Person>> response = new CommonResponse<>();
		response.setCode("0");
		response.setMsg("success");
		response.setBody(personList);
		return response;
	}

	@GetMapping("/persons/{id}")
	public CommonResponse getPersonById(@PathVariable("id")int id){
		logger.debug("http calling");
		CommonResponse<Person> response = new CommonResponse<>();
		if(personList.isEmpty()){
			response.setCode("999");
			response.setMsg("no person yet!");
			return response;
		}
		for(Person person : personList){
			if(person.getId() == id){
				response.setCode("0");
				response.setMsg("success");
				response.setBody(person);
				return response;
			}
		}
		response.setCode("999");
		response.setMsg("no such person!");
		return response;
	}

	@GetMapping("/persons/getByName")
	public CommonResponse getPersonByName(@RequestParam("name") String name){
		CommonResponse<Person> response = new CommonResponse<>();
		if(personList.isEmpty()){
			response.setCode("999");
			response.setMsg("no person yet!");
			return response;
		}
		for(Person person : personList){
			if(person.getName().equals(name)){
				response.setCode("0");
				response.setMsg("success");
				response.setBody(person);
				return response;
			}
		}
		response.setCode("999");
		response.setMsg("no such person!");
		return response;
	}

	@PutMapping("/persons")
	public CommonResponse addOnePerson(@RequestBody Person person){
		logger.debug("http calling");
		person.setId(id.getAndIncrement());
		this.personList.add(person);
		CommonResponse<Person> response = new CommonResponse<>();
		response.setCode("0");
		response.setMsg("success");
		return response;
	}

	@DeleteMapping("/persons/{id}")
	public CommonResponse deletePerson(@PathVariable("id") int id){
		logger.debug("http calling");
		CommonResponse<Person> response = new CommonResponse<>();
		if(personList.isEmpty()){
			response.setCode("999");
			response.setMsg("no person yet!");
			return response;
		}
		for(Person person : personList){
			if(person.getId() == id){
				personList.remove(person);
				response.setCode("0");
				response.setMsg("success");
				return response;
			}
		}
		response.setCode("999");
		response.setMsg("no such person!");
		return response;
	}


}
