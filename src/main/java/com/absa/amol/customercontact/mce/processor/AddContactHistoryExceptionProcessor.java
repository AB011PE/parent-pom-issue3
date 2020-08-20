package com.absa.amol.customercontact.mce.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.absa.amol.customercontact.mce.model.AddContactHistoryResWrapper;
import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;

/**
 * @author AB011Y8
 * @purpose Exception Handler Class
 */
public class AddContactHistoryExceptionProcessor implements Processor {

	/**
	 * @purpose method will be executed if any exception will occur in route builder
	 */
	@Override
	public void process(Exchange exchange) {

		exchange.getIn().setBody(soapRespCode());
	}

	public AddContactHistoryResWrapper soapRespCode() {

		AddContactHistoryResWrapper addContactHistoryResWrapper = new AddContactHistoryResWrapper();
		addContactHistoryResWrapper.setSoapResponseCode(AddContactHistoryMceConstants.SOAP_ERROR_CODE);
		return addContactHistoryResWrapper;
	}
}