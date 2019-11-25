package com.robobank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.robobank.model.Record;

@Service
public class ValidationServiceImpl implements ValidationService
{
	private static final Logger log = LoggerFactory.getLogger(ValidationServiceImpl.class);

	@Override
	public Map<Integer,String> getDuplicateRecords(List<Record> records) 
	{
		Map<Record,Integer> map = new HashMap<Record,Integer>();
		Map<Integer,String> errorRecords = new HashMap<Integer,String>();
		for(Record record : records) {
			if(map.get(record) == null) 
			{
				map.put(record,1);
			}
			else 
			{
				map.put(record,map.get(record)+1);
				errorRecords.put(record.getTransactionReference(),"duplicate record found in the statement");
				log.error("duplicate record found in the statement :"+record.getTransactionReference());
			}
		}
		return errorRecords;
	}

	@Override
	public Map<Integer,String> getEndBalanceFailureRecords(List<Record> records) 
	{
		Map<Integer,String> errorRecords = new HashMap<Integer,String>();
		for (Record record : records) {
		
				if(Math.round(record.getStartBalance() + record.getMutation())!= Math.round(record.getEndBalance()))
				{
					errorRecords.put(record.getTransactionReference(),"discrepancy in total amount(closing amount) , startBalance + mutation : "+Math.round(record.getStartBalance() + record.getMutation())+" closing balance : "+Math.round(record.getEndBalance()));
					log.error("discrepancy in total amount(closing amount) Reference :"+record.getTransactionReference()+"startBalance + mutation : " +Math.round(record.getStartBalance() + record.getMutation())+" closing balance : "+Math.round(record.getEndBalance()));
				}
			
		}
		return errorRecords;
	}

}
