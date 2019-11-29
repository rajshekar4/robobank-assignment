package com.robobank.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.robobank.exception.ServiceException;
import com.robobank.model.Record;

public interface ExtractService {
	
	public List<Record> extractStatmentFromCSV(File file) throws ServiceException;
	
	public List<Record> extractStatmentFromXML(File file) throws ServiceException;
	
	public Map<Integer,String> processStatement(MultipartFile multipart) throws ServiceException;

}
