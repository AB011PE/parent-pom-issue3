package com.absa.amol.customercontact.mce.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.absa.amol.common.exception.ApiException;
import com.absa.amol.common.exception.ApiRequestException;
import com.absa.amol.common.exception.ApiResponseException;
import com.absa.amol.common.logging.Logger;
import com.absa.amol.common.logging.LoggerFactory;
import com.absa.amol.common.model.ApiRequestHeader;
import com.absa.amol.common.model.ResponseEntity;
import com.absa.amol.customercontact.mce.model.AddContactHistoryRequest;
import com.absa.amol.customercontact.mce.model.AddContactHistoryResponse;
import com.absa.amol.customercontact.mce.service.AddContactHistoryMceService;
import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;

/**
 * @author AB011Y8
 * @purpose Controller class
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = AddContactHistoryMceConstants.TAG_NAME)
@ApplicationScoped
public class AddContactHistoryMceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddContactHistoryMceController.class);

	@Inject
	AddContactHistoryMceService addContactHistoryMceService;

	@POST
    @Path("/add-contact-history")
    @Timeout(AddContactHistoryMceConstants.FALLBACK_TIMEOUT)
    @Fallback(fallbackMethod = AddContactHistoryMceConstants.FALLBACK_METHOD_FOR_TIMEOUT, applyOn = {TimeoutException.class})
    @Operation(summary = AddContactHistoryMceConstants.OPERATION_SUMMARY, description = AddContactHistoryMceConstants.OPERATION_DESC)
    @APIResponses(
    	value = {
			@APIResponse(responseCode = AddContactHistoryMceConstants.BAD_REQUEST_CODE,
						description = AddContactHistoryMceConstants.API_RESPONSE_DESC, content = @Content(mediaType = MediaType.TEXT_PLAIN)),
			@APIResponse(responseCode = AddContactHistoryMceConstants.SUCCESS_CODE, description = AddContactHistoryMceConstants.OPERATION_DESC,
    					content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AddContactHistoryResponse.class)))
			})
	public Response addContactHistory(@BeanParam ApiRequestHeader apiRequestHeader, @RequestBody AddContactHistoryRequest addContactHistoryRequest) {

		String methodName = "addContactHistory";
		String correlationId = null;
    	ResponseEntity<AddContactHistoryResponse> responseEntity = null;

		try {
			addContactHistoryRequest.setApiRequestHeader(apiRequestHeader);
			correlationId = addContactHistoryRequest.getApiRequestHeader().getCorrelationId();
			LOGGER.debug(methodName, correlationId, "message", "method starts");
			responseEntity = addContactHistoryMceService.addContactDetails(addContactHistoryRequest);

		} catch (ApiRequestException apiRequestException) {
			LOGGER.error(methodName, correlationId, "ApiRequestException Occured", apiRequestException.getErrorMessage());
			LOGGER.debug(methodName, correlationId, "ApiRequestException", apiRequestException);
			throw new ApiRequestException(apiRequestException.getErrorCode(), apiRequestException.getErrorMessage());

		} catch (ApiResponseException apiResponseException) {
			LOGGER.error(methodName, correlationId, "ApiResponseException Occured", apiResponseException.getErrorMessage());
			LOGGER.debug(methodName, correlationId, "ApiResponseException", apiResponseException);
			throw new ApiResponseException(apiResponseException.getErrorCode(), apiResponseException.getErrorMessage());

		} catch (ApiException apiException) {
			LOGGER.error(methodName, correlationId, "ApiException Occured", apiException.getMessage());
			LOGGER.debug(methodName, correlationId, "ApiException", apiException);
			throw new ApiException(apiException.getErrorCode(), apiException.getErrorMessage());

		} catch (Exception exception) {
			LOGGER.error(methodName, correlationId, "ApiException Occured", exception.getMessage());
			LOGGER.debug(methodName, correlationId, "ApiException", exception);
			throw new ApiException(AddContactHistoryMceConstants.INTERNAL_ERROR_CODE, AddContactHistoryMceConstants.UNKNOWN_EXCEPTION);
		}
    	return Response.ok(responseEntity).build();
	}

    public Response fallbackForTimeout (@BeanParam ApiRequestHeader apiRequestHeader, @RequestBody AddContactHistoryRequest addContactHistoryRequest)throws TimeoutException {

    	LOGGER.info(AddContactHistoryMceConstants.FALLBACK_METHOD_FOR_TIMEOUT, apiRequestHeader.getCorrelationId(), "Fallback method after waiting 4 second!", "");
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(AddContactHistoryMceConstants.TIMEOUT_CODE, "Time out Exception", AddContactHistoryMceConstants.FAIL_MSG, null);
		return Response.status(AddContactHistoryMceConstants.TIMEOUT_CODE_INT).entity(responseEntity).build();
	}
}