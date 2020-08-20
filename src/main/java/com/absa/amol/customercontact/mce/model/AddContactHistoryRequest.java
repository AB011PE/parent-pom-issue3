package com.absa.amol.customercontact.mce.model;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.BeanParam;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.absa.amol.common.model.ApiRequestHeader;
import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;

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
@Schema(name = AddContactHistoryMceConstants.REQ_SCHEMA_NAME, description = AddContactHistoryMceConstants.REQ_SCHEMA_DESC)
public class AddContactHistoryRequest {

	@BeanParam
	@Schema(hidden = true)
	@Valid
	private ApiRequestHeader apiRequestHeader;

	@JsonbProperty(AddContactHistoryMceConstants.CONTACT_LIST)
	@NotNull(message = AddContactHistoryMceConstants.ERR_CONTACT_HISTORY_EMPTY)
	@NotEmpty(message = AddContactHistoryMceConstants.ERR_CONTACT_HISTORY_EMPTY)
	private List<ContactHistoryDetails> contactHistoryDetails;

	@NotNull(message = AddContactHistoryMceConstants.ERR_STAFF_ID_EMPTY)
	@NotEmpty(message = AddContactHistoryMceConstants.ERR_STAFF_ID_EMPTY)
	@Size(min = 1, max = 30, message = AddContactHistoryMceConstants.ERR_STAFF_ID_LENGTH)
	private String staffId;

	private String authorizerId;
}