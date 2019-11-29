package com.robobank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import com.robobank.constants.Constants;
import com.robobank.exception.ServiceException;
import com.robobank.model.Record;

import net.bytebuddy.asm.Advice.Thrown;

class ExtractServiceImplTest 
{
	@Mock
	public ValidationServiceImpl validationService;
	
	@InjectMocks
	private ExtractServiceImpl extractServiceImpl;

	File file;
	private InputStream is;
	
	List<Record> records ;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		 MockitoAnnotations.initMocks(this);				
		 file= new File(Constants.uploadingDir+"\\src\\test\\resources\\"+"records1.csv");

	}

	@Test
	void testExtractStatmentFromCSV() throws ServiceException 
	{
		records = new ArrayList<Record>();
		Record record = new Record();
		record.setTransactionReference(112806);
		record.setAccountNumber("NL91RABO0315273637");
		record.setDescription("Tickets for Erik Dekker");
		record.setStartBalance(41.63);
		record.setMutation(12.41);
		record.setEndBalance(54.04);
		records.add(record);
		List<Record> acutalRecords = extractServiceImpl.extractStatmentFromCSV(file);
		assertEquals(records, acutalRecords);
		
	}
	
	@Test
	void testExtractStatmentFromCSVExcpetion() throws ServiceException 
	{
		
		records = new ArrayList<Record>();
		Record record = new Record();
		record.setTransactionReference(112806);
		record.setAccountNumber("NL91RABO0315273637");
		record.setDescription("Tickets for Erik Dekker");
		record.setStartBalance(41.63);
		record.setMutation(12.41);
		record.setEndBalance(54.04);
		records.add(record);
		Assertions.assertThrows(ServiceException.class,()->{extractServiceImpl.extractStatmentFromCSV(new File(""));});
		
		
	}
	
        
	/*@Test
	void testExtractStatmentFromXMLException() throws ServiceException 
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
		 file= new File(Constants.uploadingDir+"\\src\\test\\resources\\"+"records.xml");
		 Assertions.assertThrows(ServiceException.class,()->{extractServiceImpl.extractStatmentFromXML(new File(""));});
			
	}*/

	@Test
	void testExtractStatmentFromXML() throws ServiceException 
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
		 file= new File(Constants.uploadingDir+"\\src\\test\\resources\\"+"records.xml");
		 List<Record> acutalRecords = extractServiceImpl.extractStatmentFromXML(file);
		 assertEquals(records, acutalRecords);		
	}
//-negative test case
	@Test
	void testProcessStatement_invalid() throws IOException, ServiceException 
	{
		Map<Integer,String> records= new HashMap<Integer, String>();
		records.put(0,"given input file type is not supported");
		is = extractServiceImpl.getClass().getClassLoader().getResourceAsStream("records.csv");
		 MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.csv", "multipart1/form-data", is);		 
		 Map<Integer,String> actualRecord= extractServiceImpl.processStatement(mockMultipartFile);
		 assertEquals(records, actualRecord);
	}
	
	
	@Test
	void testProcessStatement() throws IOException, ServiceException 
	{
		Map<Integer,String> records= new HashMap<Integer, String>();
		//records.put(112806,"duplicate record found in the statement");
		is = extractServiceImpl.getClass().getClassLoader().getResourceAsStream("records.xml");
		 MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records.xml", "application/xml", is);		 
		 Map<Integer,String> actualRecord= extractServiceImpl.processStatement(mockMultipartFile);
		 assertEquals(records, actualRecord);
	}
	
	@Test
	void testProcessStatementErrorBalance() throws IOException, ServiceException 
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
		
		Map<Integer,String> records= new HashMap<Integer, String>();
		records.put(112806,"duplicate record found in the statement");
		is = extractServiceImpl.getClass().getClassLoader().getResourceAsStream("records2.xml");
		 MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "records2.xml", "application/xml", is);
		 
		 when(validationService.getDuplicateRecords(this.records)).thenReturn(records);
		 
		 Map<Integer,String> actualRecord= extractServiceImpl.processStatement(mockMultipartFile);
		 assertEquals(records, actualRecord);
	}

}
