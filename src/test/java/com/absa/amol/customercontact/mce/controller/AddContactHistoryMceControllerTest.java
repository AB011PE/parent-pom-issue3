package com.absa.amol.customercontact.mce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.absa.amol.common.exception.ApiException;
import com.absa.amol.common.model.ApiRequestHeader;
import com.absa.amol.customercontact.mce.model.AddContactHistoryRequest;
import com.absa.amol.customercontact.mce.model.ContactHistoryDetails;
import com.absa.amol.customercontact.mce.service.AddContactHistoryMceService;

/**
 * @author AB011Y8
 * @purpose unit test cases for AddContactHistoryMceController
 *
 */
class AddContactHistoryMceControllerTest {

	@InjectMocks
	AddContactHistoryMceController addContactHistoryMceController;

	@Mock
	AddContactHistoryMceService addContactHistoryMceService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * @purpose success scenario test
	 */
	@Test
	public void addContactHistoryTest() {
		Response response = addContactHistoryMceController.addContactHistory(getHeader(), getContactHistory());
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void addContactHistoryError() {
		assertThrows(ApiException.class, () -> addContactHistoryMceController.addContactHistory(null, getContactHistory()));
	}	
	
	/**
	 * @purpose fallback scenario test
	 */
	@Test
	public void fallbackForTimeoutTest() {
		Response response = addContactHistoryMceController.fallbackForTimeout(getHeader(), getContactHistory());
		assertEquals(408, response.getStatus());
	}
	
	public ApiRequestHeader getHeader() {
		ApiRequestHeader apiRequestHeader = new ApiRequestHeader();
		apiRequestHeader.setBusinessId("ZMBRB");
		apiRequestHeader.setCorrelationId("123456789123456789123456789123456789");
		apiRequestHeader.setSystemId("UB");
		apiRequestHeader.setCountryCode("KE");
		return apiRequestHeader;
	}
	
	public AddContactHistoryRequest getContactHistory() {
		ContactHistoryDetails contactHistoryDetails = new ContactHistoryDetails();
		contactHistoryDetails.setBusinessId("ZMBRB");
		contactHistoryDetails.setTransactionReferenceNo("15906550412529");
		contactHistoryDetails.setActivityId("FTIT_P1_VW");
		contactHistoryDetails.setChannelId("MB");
		contactHistoryDetails.setCustomerId("400021174763");
		contactHistoryDetails.setUserId("IFE");
		List<ContactHistoryDetails> contactHistoryList = new ArrayList<>();
		contactHistoryList.add(contactHistoryDetails);
		AddContactHistoryRequest addContactHistoryRequest = new AddContactHistoryRequest();
		addContactHistoryRequest.setStaffId("IFE");
		addContactHistoryRequest.setContactHistoryDetails(contactHistoryList);
		return addContactHistoryRequest;
	}

}