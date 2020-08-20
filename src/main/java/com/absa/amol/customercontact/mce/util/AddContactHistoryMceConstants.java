package com.absa.amol.customercontact.mce.util;

/**
 * @author AB011Y8
 * @purpose to hold all the String constants
 */
public final class AddContactHistoryMceConstants {

	private AddContactHistoryMceConstants() {
		
	}

	public static final String EMPTY = "";
	public static final String API_TITLE = "Add Contact History Management System MCE";
	public static final String AMOL = "AMOL";
	public static final String API_EMAIL = "abc@absa.africa";
	public static final String API_VERSION = "1.0.0";
	public static final String TAG_NAME = "Add Contact History MCE - OpenAPI Resources";
	public static final String OPERATION_DESC = "store customer id along with transaction reference number";
	public static final String OPERATION_SUMMARY = "Add Contact History";
	public static final String INTERNAL_ERROR_CODE = "500";
	public static final String BAD_REQUEST_CODE = "400";
	public static final String TIMEOUT_CODE = "408";
	public static final String SUCCESS_CODE = "200";
	public static final String SUCCESS_RESPONSE = "Data Updated Successfully";
	public static final String SOAP_SUCCESS_CODE = "0000";
	public static final String SOAP_FAILURE_CODE = "0001";
	public static final String SOAP_ERROR_CODE = "0009";
	public static final String API_RESPONSE_DESC = "Missing Request Information";
	public static final String REQ_SCHEMA_NAME = "AddContactHistoryRequest";
	public static final String ERR_CONTACT_HISTORY_EMPTY = "contacthistory.notnullempty.error.message";
	public static final String ERR_STAFF_ID_EMPTY = "staffid.notnullempty.error.message";
	public static final String ERR_STAFF_ID_LENGTH = "staffid.minlength.error.message";
	public static final String REQ_SCHEMA_DESC = "Request Schema For Add Contact History System MCE Adapter";
	public static final String RESP_SCHEMA_NAME = "AddContactHistoryResponse";
	public static final String RESP_SCHEMA_DESC = "Response Schema For Add Contact History System MCE Adapter";
	public static final String CONTACT_LIST = "contactHistoryList";
	public static final String UNKNOWN_EXCEPTION = "Unknown Exception";
	public static final String SUCCESS_MSG = "Success";
	public static final String FAIL_MSG = "Failure";
	public static final String DIRECT_SOAP_SERVICE = "direct:ContactHistoryService";
	public static final String YYYY_MM_DD = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final int TIMEOUT_CODE_INT = 408;
	public static final long FALLBACK_TIMEOUT = 4000;
	public static final String FALLBACK_METHOD_FOR_TIMEOUT = "fallbackForTimeout";
	public static final String API_REQ_HEADER_BEAN = "ApiRequestHeaderBean";
	public static final String SERVICE_REQ_BEAN = "serviceReqBean";
}