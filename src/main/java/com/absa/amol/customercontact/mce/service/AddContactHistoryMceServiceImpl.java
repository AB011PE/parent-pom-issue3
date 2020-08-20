package com.absa.amol.customercontact.mce.service;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.config.Config;

import com.absa.amol.common.exception.ApiException;
import com.absa.amol.common.exception.ApiRequestException;
import com.absa.amol.common.exception.ApiResponseException;
import com.absa.amol.common.logging.Logger;
import com.absa.amol.common.logging.LoggerFactory;
import com.absa.amol.common.model.ResponseEntity;
import com.absa.amol.common.util.StringUtil;
import com.absa.amol.customercontact.mce.model.AddContactHistoryRequest;
import com.absa.amol.customercontact.mce.model.AddContactHistoryResWrapper;
import com.absa.amol.customercontact.mce.model.AddContactHistoryResponse;
import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;

/**
 * @author AB011Y8
 * @purpose Service implementation class
 */
@ApplicationScoped
public class AddContactHistoryMceServiceImpl implements AddContactHistoryMceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddContactHistoryMceServiceImpl.class);

	@Inject
	ProducerTemplate producerTemplate;

	@Inject
	private Validator validator;

	@Inject
	Config config;

	/**
	 * @purpose make request object, do the SOAP call, compose the response
	 */
	@Override
	public ResponseEntity<AddContactHistoryResponse> addContactDetails(AddContactHistoryRequest addContactHistoryRequest) {

		String methodName = "addContactDetails";
		String correlationId = null;
		String responseCode = AddContactHistoryMceConstants.INTERNAL_ERROR_CODE;
		String responseMessage = AddContactHistoryMceConstants.UNKNOWN_EXCEPTION;
		String status = AddContactHistoryMceConstants.FAIL_MSG;
		AddContactHistoryResponse addContactHistoryResponse = null;

		try {
			correlationId = addContactHistoryRequest.getApiRequestHeader().getCorrelationId();
			String errorMsg = validateInputRequest(addContactHistoryRequest);
			LOGGER.debug(methodName, correlationId, "errorMsg", errorMsg);

			if (StringUtil.isStringNullOrEmpty(errorMsg)) {

				AddContactHistoryResWrapper addContactHistoryResWrapper = producerTemplate.requestBody(AddContactHistoryMceConstants.DIRECT_SOAP_SERVICE, addContactHistoryRequest, AddContactHistoryResWrapper.class);
				String soapResponseCode = addContactHistoryResWrapper.getSoapResponseCode();
				LOGGER.info(methodName, correlationId, "SoapResponseCode", soapResponseCode);

				if (AddContactHistoryMceConstants.SOAP_SUCCESS_CODE.equals(addContactHistoryResWrapper.getSoapResponseCode())) {
					responseCode = AddContactHistoryMceConstants.SUCCESS_CODE;
					status = AddContactHistoryMceConstants.SUCCESS_MSG;
					responseMessage = AddContactHistoryMceConstants.SUCCESS_RESPONSE;
					addContactHistoryResponse = new AddContactHistoryResponse();
					addContactHistoryResponse.setContactHistoryList(addContactHistoryResWrapper.getAddContactHistoryResponse().getContactHistoryList());
					
				} else if (AddContactHistoryMceConstants.SOAP_FAILURE_CODE.equals(addContactHistoryResWrapper.getSoapResponseCode())) {
					throw new ApiRequestException(AddContactHistoryMceConstants.BAD_REQUEST_CODE, addContactHistoryResWrapper.getMceErrorList().get(0).getErrorDesc());
				} else {
					throw new ApiResponseException(AddContactHistoryMceConstants.INTERNAL_ERROR_CODE, AddContactHistoryMceConstants.UNKNOWN_EXCEPTION);
				}

			} else {
				throw new ApiRequestException(AddContactHistoryMceConstants.BAD_REQUEST_CODE, errorMsg);
			}
		} catch (ApiRequestException apiRequestException) {
			LOGGER.error(methodName, correlationId, "ApiRequestException Occured", apiRequestException.getErrorMessage());
			LOGGER.debug(methodName, correlationId, "ApiRequestException", apiRequestException);
			throw new ApiRequestException(apiRequestException.getErrorCode(), apiRequestException.getErrorMessage());

		} catch (ApiResponseException apiResponseException) {
			LOGGER.error(methodName, correlationId, "ApiResponseException Occured", apiResponseException.getErrorMessage());
			LOGGER.debug(methodName, correlationId, "ApiResponseException", apiResponseException);
			throw new ApiResponseException(apiResponseException.getErrorCode(), apiResponseException.getErrorMessage());

		} catch (CamelExecutionException camelExecutionException) {
			LOGGER.error(methodName, correlationId, "Exception Occured", camelExecutionException.getMessage());
			LOGGER.debug(methodName, correlationId, "Exception", camelExecutionException);
			throw new ApiResponseException(AddContactHistoryMceConstants.INTERNAL_ERROR_CODE, AddContactHistoryMceConstants.UNKNOWN_EXCEPTION);

		} catch (Exception exception) {
			LOGGER.error(methodName, correlationId, "Exception Occured", exception.getMessage());
			LOGGER.debug(methodName, correlationId, "Exception", exception);
			throw new ApiException(AddContactHistoryMceConstants.INTERNAL_ERROR_CODE, AddContactHistoryMceConstants.UNKNOWN_EXCEPTION);
		}
		return new ResponseEntity<AddContactHistoryResponse>(responseCode, responseMessage, status, addContactHistoryResponse);
	}

	/**
	 * @purpose to validate input request
	 */
	public String validateInputRequest(final AddContactHistoryRequest addContactHistoryRequest) {

		String errorMsg = null;
		String correlationId = AddContactHistoryMceConstants.EMPTY;
		try {
			correlationId = addContactHistoryRequest.getApiRequestHeader().getCorrelationId();
			Set<String> errorSet = new HashSet<>();
			Set<ConstraintViolation<Object>> violations = validator.validate(addContactHistoryRequest);
			for (ConstraintViolation<Object> error : violations) {
				errorSet.add(0 == error.getLeafBean().toString().indexOf("ApiRequestHeader") ? error.getMessage() : config.getValue(error.getMessageTemplate(), String.class));
			}
			if (!errorSet.isEmpty()) {
				errorMsg = String.join(", ", errorSet);
			}
		} catch (Exception exception) {
			String methodName = "validateInputRequest";
			errorMsg = AddContactHistoryMceConstants.UNKNOWN_EXCEPTION;
			LOGGER.error(methodName, correlationId, "Exception Occured", exception.getMessage());
			LOGGER.debug(methodName, correlationId, "Exception", exception);
		}
		return errorMsg;
	} 
}