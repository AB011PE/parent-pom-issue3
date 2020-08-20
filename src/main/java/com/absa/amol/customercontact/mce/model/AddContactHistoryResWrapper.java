package com.absa.amol.customercontact.mce.model;

import java.util.List;

import com.barclays.mce.service.error.mceerror.MCEError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddContactHistoryResWrapper {

	private AddContactHistoryResponse addContactHistoryResponse;
	private String soapResponseCode;
	private List<MCEError> mceErrorList;
}