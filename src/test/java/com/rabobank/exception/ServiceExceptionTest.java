package com.rabobank.exception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.robobank.exception.ServiceException;

class ServiceExceptionTest {

	@InjectMocks
	private ServiceException serviceException;
	
	@BeforeEach
	void setUp() throws Exception {
	}



}
