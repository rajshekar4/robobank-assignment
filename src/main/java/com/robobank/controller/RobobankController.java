package com.robobank.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robobank.exception.ServiceException;
import com.robobank.service.ExtractService;


@RestController
public class RobobankController 
{
	private static final Logger log = LoggerFactory.getLogger(RobobankController.class);
@Autowired
public ExtractService extractService;
	
	@PostMapping(value="/api/v1/process",consumes = { "multipart/form-data" })
	public ResponseEntity<?> process(@RequestParam("file") MultipartFile multipart)
	{
		log.debug("inside rest Api process method ");
		
		Map<Integer,String> records=null;
		if (multipart.isEmpty()) 
		{
			log.error("Empty file in the request ");
			return new ResponseEntity<String>(ObjectToString("Empty file input is provided"),HttpStatus.BAD_REQUEST);
		}
		else 
		{
			try
			{
				records = extractService.processStatement(multipart);
			} catch (ServiceException e) 
			{
				return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
				
		return new ResponseEntity<String>(ObjectToString(records),HttpStatus.OK);
		
	}
	
	public String ObjectToString(Object obj) {
		String result = null;
		try 
		{
			result = new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}

}
