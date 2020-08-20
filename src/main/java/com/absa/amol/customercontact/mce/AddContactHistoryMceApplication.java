package com.absa.amol.customercontact.mce;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;

/**
 * @author AB011Y8
 * @purpose Main application class
 */
@OpenAPIDefinition(info = @Info(
		title = AddContactHistoryMceConstants.API_TITLE,
		contact = @Contact(name = AddContactHistoryMceConstants.AMOL, email = AddContactHistoryMceConstants.API_EMAIL),
		license = @License(name = AddContactHistoryMceConstants.AMOL), version = AddContactHistoryMceConstants.API_VERSION))
@ApplicationPath("/")
public class AddContactHistoryMceApplication extends Application {

}