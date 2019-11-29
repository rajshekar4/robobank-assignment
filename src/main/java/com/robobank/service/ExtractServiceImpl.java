package com.robobank.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.robobank.constants.Constants;
import com.robobank.exception.ServiceException;
import com.robobank.model.Record;
import com.robobank.model.Records;
import com.robobank.utilities.Utility;

@Service
public class ExtractServiceImpl implements ExtractService{

	private static final Logger log = LoggerFactory.getLogger(ExtractServiceImpl.class);
	@Autowired
	public ValidationService validationService;
	
	@Override
	public List<Record> extractStatmentFromCSV(File file) throws ServiceException 
	{
		log.debug("ExtractServiceImpl - extractStatmentFromCSV method : extracting from CSV records into List of records objects");
		HeaderColumnNameTranslateMappingStrategy<Record> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Record>();
		beanStrategy.setType(Record.class);
		Map<String, String> columnHeader = new HashMap<String, String>();
		
		columnHeader.put("Reference", "transactionReference");
		columnHeader.put("Account Number", "accountNumber");
		columnHeader.put("Start Balance", "startBalance");		
		columnHeader.put("Mutation", "mutation");
		columnHeader.put("Description", "description");
		columnHeader.put("End Balance", "endBalance");

		beanStrategy.setColumnMapping(columnHeader);
		
		CsvToBean<Record> csvToBean = new CsvToBean<Record>();
		CSVReader reader;
		try 
		{
			reader = new CSVReader(new FileReader(file));
		} catch (FileNotFoundException e) 
		{
			log.error("error in loading CSV file "+e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		List<Record> records = csvToBean.parse(beanStrategy, reader);
		return records;
	}

	@Override
	public List<Record> extractStatmentFromXML(File file) throws ServiceException {
		log.debug("ExtractServiceImpl - extractStatmentFromCSV method : extracting from XML records into List of records objects");
		Records records;
		try 
		{
			records = Utility.convertXMlToObject(file);
		} catch (JAXBException e) 
		{
			log.error("Error in converting xml to Object "+e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		return records.getRecordList();
	}
	
	@Override
	public Map<Integer,String> processStatement(MultipartFile multipart) throws ServiceException 
	{
		log.debug("ExtractServiceImpl - processStatement method ");
		Map<Integer,String> errorMap =new HashMap<Integer, String>();
		try 
		{			
		
		if (!multipart.isEmpty()) 
		{			
			if (multipart.getContentType().equalsIgnoreCase(Constants.FILE_TYPE_CSV)) 
			{
				log.debug("ExtractServiceImpl - processStatement method : input file type is CSV ");
				File csvFile = transferFileToTemp(multipart);
				List<Record> extractedRecords = extractStatmentFromCSV(csvFile);
				errorMap.putAll(validationService.getDuplicateRecords(extractedRecords));				
				errorMap.putAll(validationService.getEndBalanceFailureRecords(extractedRecords));
				
			}else if (multipart.getContentType().equalsIgnoreCase(Constants.FILE_TYPE_XML)) 
			{
				log.debug("ExtractServiceImpl - processStatement method : input file type is XML ");	
				File xmlFile = new File(Constants.uploadingDir+"/"+multipart.getOriginalFilename());				
				multipart.transferTo(xmlFile);
				System.out.println(xmlFile.getAbsolutePath());
				List<Record> extractedRecords = extractStatmentFromXML(xmlFile);
				errorMap.putAll(validationService.getDuplicateRecords(extractedRecords));				
				errorMap.putAll(validationService.getEndBalanceFailureRecords(extractedRecords));
				
			}
			else 
			{
				log.error("ExtractServiceImpl - processStatement method : given input file type is not supported ");
				errorMap.put(0, "given input file type is not supported");
			}
		}
		}
		catch(IllegalStateException e) 
		{
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		catch(IOException e) 
		{
			log.error("File Exception "+e.getMessage());
			throw new ServiceException(e.getMessage());
		}
		catch(Exception e) 
		{	log.error(e.getMessage());
			throw new ServiceException(e.getMessage());			
		}	
		return errorMap;
	}

	private File transferFileToTemp(MultipartFile multipart) throws IOException 
	{
		long sec = System.currentTimeMillis();
		File csvFile = new File(Constants.uploadingDir+"/"+sec+"/"+multipart.getOriginalFilename());
		if(!csvFile.isDirectory()) 
		{
			csvFile.mkdirs();
		}
		multipart.transferTo(csvFile);
		return csvFile;
	}

}
