package com.absa.amol.customercontact.mce.model;

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
public class ContactHistoryDetails {

	private String businessId;
	private String transactionReferenceNo;
	private String activityId;
	private String activityName;
	private String channelId;
	private String customerId;
	private String UserId;
	private String data1;
	private String data2;
}