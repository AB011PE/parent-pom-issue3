package com.absa.amol.customercontact.mce.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author AB011Y8
 * @purpose unit test cases for AddContactHistoryExceptionProcessorTest
 *
 */
public class AddContactHistoryExceptionProcessorTest {

	@InjectMocks
	AddContactHistoryExceptionProcessor addContactHistoryExceptionProcessor;

	@Mock
	Exchange exchange;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void soapRespCodeTest() {
		assertEquals("0009", addContactHistoryExceptionProcessor.soapRespCode().getSoapResponseCode());
	}
}