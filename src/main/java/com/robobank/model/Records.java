package com.robobank.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="records")
@XmlAccessorType(XmlAccessType.FIELD)
public class Records {
	
	
	private List<Record> records;
	
	@XmlElement(name = "record")
	public List<Record> getRecordList() {
		return records;
	}

	public void setRecordList(List<Record> recordList) {
		this.records = recordList;
	}
	

}
