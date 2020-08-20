package com.absa.amol.customercontact.mce.service;

import com.absa.amol.customercontact.mce.model.AddContactHistoryRequest;
import com.absa.amol.customercontact.mce.model.AddContactHistoryResponse;
import com.absa.amol.common.model.ResponseEntity;

/**
 * @author AB011Y8
 * @purpose Service interface
 */
public interface AddContactHistoryMceService {

	public ResponseEntity<AddContactHistoryResponse> addContactDetails(AddContactHistoryRequest addContactHistoryRequest);
}