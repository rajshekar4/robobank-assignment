package com.robobank.service;

import java.util.List;
import java.util.Map;

import com.robobank.model.Record;

public interface ValidationService {
	
	Map<Integer,String> getDuplicateRecords(List<Record> records);
	Map<Integer,String> getEndBalanceFailureRecords(List<Record> records);

}
