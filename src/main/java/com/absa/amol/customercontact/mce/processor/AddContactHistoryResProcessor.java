package com.absa.amol.customercontact.mce.processor;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.absa.amol.common.exception.ApiResponseException;
import com.absa.amol.common.logging.Logger;
import com.absa.amol.common.logging.LoggerFactory;
import com.absa.amol.customercontact.mce.model.AddContactHistoryResWrapper;
import com.absa.amol.customercontact.mce.model.AddContactHistoryResponse;
import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;
import com.barclays.mce.service.contact.entities.addcontacthistoryresults.AddContactHistoryResults;
import com.barclays.mce.service.error.mceerror.MCEError;

/**
 * @author AB011Y8
 * @purpose Response processor class
 */
public class AddContactHistoryResProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddContactHistoryResProcessor.class);

	/**
	 * @purpose to handle response captured from SOAP call
	 */
	@Override
	public void process(Exchange exchange) {

		String methodName = "process";
		AddContactHistoryResWrapper addContactHistoryResWrapper = new AddContactHistoryResWrapper();
		try {
			AddContactHistoryResults addContactHistoryResults = exchange.getIn().getBody(AddContactHistoryResults.class);
			addContactHistoryResWrapper = processHelper(addContactHistoryResults);
		} catch (Exception exception) {
			LOGGER.error(methodName, AddContactHistoryMceConstants.EMPTY, "Exception Occured", exception.getMessage());
			LOGGER.debug(methodName, AddContactHistoryMceConstants.EMPTY, "Exception", exception);
			throw new ApiResponseException(AddContactHistoryMceConstants.INTERNAL_ERROR_CODE, "Error in processing response");
		}
		exchange.getIn().setBody(addContactHistoryResWrapper);
	}

	public AddContactHistoryResWrapper processHelper(AddContactHistoryResults addContactHistoryResults) {

		AddContactHistoryResWrapper addContactHistoryResWrapper = new AddContactHistoryResWrapper();
		String correlationId = AddContactHistoryMceConstants.EMPTY;
		String methodName = "processHelper";
		try {
			String responseCode = addContactHistoryResults.getResponseHeader().getServiceResponseCode();
			LOGGER.debug(methodName, correlationId, "responseCode", responseCode);
			addContactHistoryResWrapper.setSoapResponseCode(responseCode);

			if (AddContactHistoryMceConstants.SOAP_SUCCESS_CODE.equals(responseCode)) {
				AddContactHistoryResponse addContactHistoryResponse = new AddContactHistoryResponse();
				addContactHistoryResponse.setContactHistoryList(addContactHistoryResults.getContactHistoryLists());
				addContactHistoryResWrapper.setAddContactHistoryResponse(addContactHistoryResponse);
				correlationId = addContactHistoryResults.getContactHistoryLists().get(0).getTransactionReferenceNo();

			} else if (AddContactHistoryMceConstants.SOAP_FAILURE_CODE.equals(responseCode)) {
				List<MCEError> mceErrorList = addContactHistoryResults.getResponseHeader().getMCEErrorList().getMCEErrors();
				addContactHistoryResWrapper.setMceErrorList(mceErrorList);
			}

		} catch (Exception exception) {
			LOGGER.error(methodName, correlationId, "Exception Occured", exception.getMessage());
			LOGGER.debug(methodName, correlationId, "Exception", exception);
			throw exception;
		}
		return addContactHistoryResWrapper;
	}
}