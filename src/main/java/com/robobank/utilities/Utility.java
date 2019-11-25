package com.robobank.utilities;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.robobank.model.Records;



public final class Utility 

{
	public static JAXBContext jaxbContext;
	

	public static Records convertXMlToObject(File file) throws JAXBException
	{
		jaxbContext=JAXBContext.newInstance(Records.class);
		Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
		Records records = (Records) jaxbUnMarshaller.unmarshal(file);
		return records;
	}	

}
