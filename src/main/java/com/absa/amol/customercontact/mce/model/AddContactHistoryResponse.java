package com.absa.amol.customercontact.mce.model;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;
import com.barclays.mce.service.contact.entities.contacthistory.ContactHistory;

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
@Schema(name = AddContactHistoryMceConstants.RESP_SCHEMA_NAME, description = AddContactHistoryMceConstants.RESP_SCHEMA_DESC)
public class AddContactHistoryResponse {

	private List<ContactHistory> contactHistoryList;
}