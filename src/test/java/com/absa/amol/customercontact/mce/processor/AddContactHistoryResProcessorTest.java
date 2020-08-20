package com.absa.amol.customercontact.mce.processor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.camel.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.absa.amol.customercontact.mce.model.AddContactHistoryResWrapper;
import com.barclays.mce.service.contact.entities.addcontacthistoryresults.AddContactHistoryResults;
import com.barclays.mce.service.contact.entities.contacthistory.ContactHistory;
import com.barclays.mce.service.error.mceerror.MCEError;
import com.barclays.mce.service.error.mceerrorlist.MCEErrorList;
import com.barclays.mce.service.header.mceheader.MCEResponseHeader;

/**
 * @author AB011Y8
 * @purpose unit test cases for AddContactHistoryResProcessor
 *
 */
class AddContactHistoryResProcessorTest {

	@InjectMocks
	AddContactHistoryResProcessor addContactHistoryResProcessor;

	@Mock
	Exchange exchange;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void processTest() {
		assertThrows(Exception.class, () -> addContactHistoryResProcessor.process(exchange));
	}

	@Test
	public void processHelperTest() {
		AddContactHistoryResWrapper addContactHistoryResWrapper1 = addContactHistoryResProcessor.processHelper(responsePayload());
		AddContactHistoryResWrapper addContactHistoryResWrapper2 = addContactHistoryResProcessor.processHelper(errorPayload());
		assertAll(
			() -> assertEquals("0000", addContactHistoryResWrapper1.getSoapResponseCode()),
			() -> assertEquals("0001", addContactHistoryResWrapper2.getSoapResponseCode())
		);
	}

	@Test
	public void processHelperErrorTest() {
		assertThrows(Exception.class, () -> addContactHistoryResProcessor.processHelper(new AddContactHistoryResults()));
	}

	public AddContactHistoryResults responsePayload() {
		
		ContactHistory contactHistory = new ContactHistory();
		contactHistory.setTransactionReferenceNo("12345");
		AddContactHistoryResults addContactHistoryResults = new AddContactHistoryResults();
		MCEResponseHeader header = new MCEResponseHeader();
		header.setServiceResponseCode("0000");
		addContactHistoryResults.setResponseHeader(header);
		addContactHistoryResults.getContactHistoryLists().add(contactHistory);
		return addContactHistoryResults;
	}
	public AddContactHistoryResults errorPayload() {
		
		AddContactHistoryResults addContactHistoryResults = new AddContactHistoryResults();
		MCEResponseHeader header = new MCEResponseHeader();
		header.setServiceResponseCode("0001");
		MCEError mceError = new MCEError();
		mceError.setErrorCode("0009");
		mceError.setErrorDesc("Error");
		MCEErrorList errorList = new MCEErrorList();
		errorList.getMCEErrors().add(mceError);
		header.setMCEErrorList(errorList);
		addContactHistoryResults.setResponseHeader(header);	
		return addContactHistoryResults;
	}
}