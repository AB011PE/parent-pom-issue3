package com.absa.amol.customercontact.mce.processor;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.absa.amol.common.exception.ApiRequestException;
import com.absa.amol.common.logging.Logger;
import com.absa.amol.common.logging.LoggerFactory;
import com.absa.amol.common.model.ApiRequestHeader;
import com.absa.amol.common.util.StringUtil;
import com.absa.amol.customercontact.mce.model.AddContactHistoryRequest;
import com.absa.amol.customercontact.mce.model.ContactHistoryDetails;
import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;
import com.barclays.mce.service.contact.entities.addcontacthistoryreq.AddContactHistoryReq;
import com.barclays.mce.service.contact.entities.contacthistory.ContactHistory;
import com.barclays.mce.service.header.mceheader.MCERequestHeader;

/**
 * @author AB011Y8
 * @purpose Request processor class
 */
public class AddContactHistoryReqProcessor implements Processor {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddContactHistoryReqProcessor.class);

	/**
	 * @purpose to make request payload
	 */
	@Override
	public void process(Exchange exchange) {

		String methodName = "process";
		String correlationId = null;
		AddContactHistoryReq addContactHistoryReq = new AddContactHistoryReq();
		try {
			AddContactHistoryRequest addContactHistoryRequest = exchange.getIn().getBody(AddContactHistoryRequest.class);
			correlationId = addContactHistoryRequest.getApiRequestHeader().getCorrelationId();
			addContactHistoryReq = processHelper(addContactHistoryRequest);
		} catch (Exception exception) {
			LOGGER.error(methodName, correlationId, "Exception Occured", exception.getMessage());
			LOGGER.debug(methodName, correlationId, "Exception", exception);
			throw new ApiRequestException(AddContactHistoryMceConstants.BAD_REQUEST_CODE, "Error in processing request");
		}
		exchange.getIn().setBody(addContactHistoryReq);
	}

	public AddContactHistoryReq processHelper (AddContactHistoryRequest addContactHistoryRequest) {

		AddContactHistoryReq addContactHistoryReq = new AddContactHistoryReq();
		String correlationId = AddContactHistoryMceConstants.EMPTY;
		try {
			ApiRequestHeader apiRequestHeader = addContactHistoryRequest.getApiRequestHeader();
			correlationId = apiRequestHeader.getCorrelationId();
			ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneOffset.UTC);
			String serviceDateTime = zonedDateTime.format(DateTimeFormatter.ofPattern(AddContactHistoryMceConstants.YYYY_MM_DD));

			MCERequestHeader mceRequestHeader = new MCERequestHeader();
			mceRequestHeader.setBusinessID(apiRequestHeader.getBusinessId());
			mceRequestHeader.setOrigniatingChannel(apiRequestHeader.getSystemId());
			mceRequestHeader.setTransactionReferenceNo(correlationId);
			mceRequestHeader.setServiceDateTime(serviceDateTime);
			mceRequestHeader.setUserID(addContactHistoryRequest.getStaffId());

			String authorizerId = addContactHistoryRequest.getAuthorizerId();
			if (StringUtil.isStringNotNullAndNotEmpty(authorizerId)) {
				mceRequestHeader.setAuthorizerId(authorizerId);
			}
			addContactHistoryReq.setRequestHeader(mceRequestHeader);

			List<ContactHistoryDetails> contactHistoryDetailsList = addContactHistoryRequest.getContactHistoryDetails();
			for (ContactHistoryDetails contactHistoryDetails : contactHistoryDetailsList) {

				ContactHistory contactHistory = new ContactHistory();
				contactHistory.setBusinessID(contactHistoryDetails.getBusinessId());
				contactHistory.setTransactionReferenceNo(contactHistoryDetails.getTransactionReferenceNo());
				contactHistory.setTransactionDtm(serviceDateTime);
				contactHistory.setActivityID(contactHistoryDetails.getActivityId());
				contactHistory.setActivityName(contactHistoryDetails.getActivityName());
				contactHistory.setChannelID(contactHistoryDetails.getChannelId());
				contactHistory.setCustomerID(contactHistoryDetails.getCustomerId());
				contactHistory.setUserID(contactHistoryDetails.getUserId());
				contactHistory.setData1(contactHistoryDetails.getData1());
				contactHistory.setData2(contactHistoryDetails.getData2());
				addContactHistoryReq.getContactHistoryLists().add(contactHistory);
			}
		} catch (Exception exception) {
			String methodName = "processHelper";
			LOGGER.error(methodName, correlationId, "Exception Occured", exception.getMessage());
			LOGGER.debug(methodName, correlationId, "Exception", exception);
			throw exception;
		}
		return addContactHistoryReq;
	}
}