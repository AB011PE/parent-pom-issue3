package com.absa.amol.customercontact.mce.router;

import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.absa.amol.customercontact.mce.processor.AddContactHistoryExceptionProcessor;
import com.absa.amol.customercontact.mce.processor.AddContactHistoryReqProcessor;
import com.absa.amol.customercontact.mce.processor.AddContactHistoryResProcessor;
import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;
import com.barclays.mce.service.contact.contacthistoryservice.ContactHistoryService;

/**
 * @author AB011Y8
 * @purpose Route Builder class to manage all routing being used
 */
public class AddContactHistoryRouteBuilder extends RouteBuilder {

	@Inject
	@ConfigProperty(name = "mce.contacthistoryservice.operationname")
	private String operationName;

	@Inject
	@ConfigProperty(name = "mce.contacthistoryservice.operationnamespace")
	private String operationNamespace;	

	@Inject
	@ConfigProperty(name = "mce.contacthistoryservice.serviceurl")
	private String serviceUrl;

	/**
	 * @purpose To make the SOAP service call
	 */
	@Override
	public void configure() {

		StringBuilder cxfLink = new StringBuilder("cxf://")
				.append(serviceUrl)
				.append("?serviceClass=")
				.append(ContactHistoryService.class.getName())
				.append("&wsdlURL=")
				.append(serviceUrl)
				.append("/")
				.append(ContactHistoryService.class.getSimpleName())
				.append(".wsdl");

		from(AddContactHistoryMceConstants.DIRECT_SOAP_SERVICE)
			.doTry()
				.process(new AddContactHistoryReqProcessor())
				.setHeader(CxfConstants.OPERATION_NAME, constant(operationName))
				.setHeader(CxfConstants.OPERATION_NAMESPACE, constant(operationNamespace))
				.to(cxfLink.toString())
				.process(new AddContactHistoryResProcessor())
			.doCatch(Exception.class)
				.process(new AddContactHistoryExceptionProcessor());
	}
}