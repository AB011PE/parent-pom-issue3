package com.absa.amol.customercontact.mce.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.config.Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.absa.amol.common.exception.ApiException;
import com.absa.amol.common.model.ApiRequestHeader;
import com.absa.amol.customercontact.mce.model.AddContactHistoryRequest;
import com.absa.amol.customercontact.mce.model.AddContactHistoryResWrapper;
import com.absa.amol.customercontact.mce.model.ContactHistoryDetails;

/**
 * @author AB011Y8
 * @purpose unit test cases for AddContactHistoryMceServiceImpl
 *
 */
class AddContactHistoryMceServiceImplTest {

	@InjectMocks
	AddContactHistoryMceServiceImpl addContactHistoryMceServiceImpl;

	@InjectMocks
	private HashSet<ConstraintViolation<Object>> violations;

	@Mock
	private Validator validator;

	@Mock
	private ConstraintViolation<Object> object;

	@Mock
	private Config config;
	
	@Mock
	AddContactHistoryRequest addContactHistoryRequest;

	@Mock
	ProducerTemplate producerTemplate;
	
	@Mock
	AddContactHistoryResWrapper addContactHistoryResWrapper;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addContactDetailsErrorTest() {
		assertThrows(ApiException.class, () -> addContactHistoryMceServiceImpl.addContactDetails(new AddContactHistoryRequest()));
	}
	
	@Test
	public void addContactDetailsTest() {

		Mockito.when(producerTemplate.requestBody("direct:ContactHistoryService", addContactHistoryRequest, AddContactHistoryResWrapper.class)).thenReturn(addContactHistoryResWrapper);
		assertThrows(Exception.class, () -> addContactHistoryMceServiceImpl.addContactDetails(testPayload()));
	}

	@Test
	public void validateInputRequestTest() {
		assertAll(
				() -> assertEquals(null, addContactHistoryMceServiceImpl.validateInputRequest(testPayload())),
				() -> assertEquals("Unknown Exception", addContactHistoryMceServiceImpl.validateInputRequest(null))
		);
	}

	public AddContactHistoryRequest testPayload() {

		List<ContactHistoryDetails> contactHistoryList = new ArrayList<>();
		AddContactHistoryRequest addContactHistoryRequest = new AddContactHistoryRequest();
		addContactHistoryRequest.setStaffId("IFE");
		addContactHistoryRequest.setContactHistoryDetails(contactHistoryList);
		ApiRequestHeader apiRequestHeader = new ApiRequestHeader();
		apiRequestHeader.setBusinessId("");
		apiRequestHeader.setCorrelationId("");
		apiRequestHeader.setSystemId("");
		apiRequestHeader.setCountryCode("");
		addContactHistoryRequest.setApiRequestHeader(apiRequestHeader);
		return addContactHistoryRequest;
	}

}