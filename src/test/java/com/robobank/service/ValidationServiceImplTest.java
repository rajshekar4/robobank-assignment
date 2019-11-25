package com.robobank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.robobank.model.Record;

class ValidationServiceImplTest {

	@InjectMocks
	private ValidationServiceImpl validationServiceImpl;
	
	List<Record> records;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		 MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetDuplicateRecords() 
	{
		records = new ArrayList<Record>();
		Record record = new Record();
		record.setTransactionReference(187997);
		record.setAccountNumber("NL91RABO0315273637");
		record.setDescription("Clothes for Rik King");
		record.setStartBalance(57.6);
		record.setMutation(-32.98);
		record.setEndBalance(24.62);
		records.add(record);
		record = new Record();
		record.setTransactionReference(187997);
		record.setAccountNumber("NL91RABO0315273637");
		record.setDescription("Clothes for Rik King");
		record.setStartBalance(57.6);
		record.setMutation(-32.98);
		record.setEndBalance(24.62);
		records.add(record);
		
		Map<Integer,String> recordsDup= new HashMap<Integer, String>();
		recordsDup.put(187997,"duplicate record found in the statement");
		
		Map<Integer,String>  actual = validationServiceImpl.getDuplicateRecords(records);
		
		assertEquals(recordsDup, actual);
	}

	@Test
	void testGetEndBalanceFailureRecords() {
		records = new ArrayList<Record>();
		Record record = new Record();
		record.setTransactionReference(187997);
		record.setAccountNumber("NL91RABO0315273637");
		record.setDescription("Clothes for Rik King");
		record.setStartBalance(57.6);
		record.setMutation(-32.98);
		record.setEndBalance(24.62);
		records.add(record);
		
		Map<Integer,String> recordsDup= new HashMap<Integer, String>();
		//recordsDup.put(187997,"duplicate record found in the statement");
		
		Map<Integer,String>  actual = validationServiceImpl.getEndBalanceFailureRecords(records);
		assertEquals(recordsDup, actual);
	
	}

}
