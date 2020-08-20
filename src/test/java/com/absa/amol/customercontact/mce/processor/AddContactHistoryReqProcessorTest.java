package com.absa.amol.customercontact.mce.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.absa.amol.common.model.ApiRequestHeader;
import com.absa.amol.customercontact.mce.model.AddContactHistoryRequest;
import com.absa.amol.customercontact.mce.model.ContactHistoryDetails;
import com.barclays.mce.service.contact.entities.addcontacthistoryreq.AddContactHistoryReq;

/**
 * @author AB011Y8
 * @purpose unit test cases for AddContactHistoryReqProcessor
 *
 */
class AddContactHistoryReqProcessorTest {

	@InjectMocks
	AddContactHistoryReqProcessor addContactHistoryReqProcessor;

	@Mock
	Exchange exchange;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processTest() {
		assertThrows(Exception.class, () -> addContactHistoryReqProcessor.process(exchange));
	}

	@Test
	public void processHelperTest() {
		AddContactHistoryReq addContactHistoryReq = addContactHistoryReqProcessor.processHelper(payload());
		assertEquals("ZMBRB", addContactHistoryReq.getRequestHeader().getBusinessID());
	}

	@Test
	public void processHelperErrorTest() {
		assertThrows(Exception.class, () -> addContactHistoryReqProcessor.processHelper(new AddContactHistoryRequest()));
	}

	public AddContactHistoryRequest payload() {
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
		ApiRequestHeader apiRequestHeader = new ApiRequestHeader();
		apiRequestHeader.setBusinessId("ZMBRB");
		apiRequestHeader.setCorrelationId("123456789123456789123456789123456789");
		apiRequestHeader.setSystemId("UB");
		apiRequestHeader.setCountryCode("KE");
		addContactHistoryRequest.setApiRequestHeader(apiRequestHeader);
		return addContactHistoryRequest;
	}
	 
}
