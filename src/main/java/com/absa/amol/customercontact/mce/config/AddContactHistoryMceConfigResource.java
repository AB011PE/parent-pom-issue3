package com.absa.amol.customercontact.mce.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.microprofile.config.spi.ConfigSource;

import com.absa.amol.common.logging.Logger;
import com.absa.amol.common.logging.LoggerFactory;
import com.absa.amol.customercontact.mce.util.AddContactHistoryMceConstants;

/**
 * @author AB011Y8
 * @purpose Resource class to read properties file
 */
public class AddContactHistoryMceConfigResource implements ConfigSource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddContactHistoryMceConfigResource.class);
	private final String CONFIG_SOURCE_NAME = "AddContactHistoryMceConfigResource";
	private Map<String, String> custmap = new HashMap<>();

	public AddContactHistoryMceConfigResource() {

		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		try {
			String path = System.getProperty("properties.path");
			LOGGER.info(CONFIG_SOURCE_NAME, AddContactHistoryMceConstants.EMPTY, "properties.path", path);
			fileInputStream = new FileInputStream(new File(path));
			properties.load(fileInputStream);
			properties.forEach((k, v) -> custmap.put(String.valueOf(k), String.valueOf(v)));
			fileInputStream.close();
		} catch (Exception e) {
			LOGGER.error(CONFIG_SOURCE_NAME, AddContactHistoryMceConstants.EMPTY, "Exception : ", e);
			LOGGER.debug(CONFIG_SOURCE_NAME, AddContactHistoryMceConstants.EMPTY, "Detailed Exception : ", e.getMessage());
		} finally {
			if (null != fileInputStream) {
				try {
					fileInputStream.close();
				} catch (Exception e) {
					LOGGER.error(CONFIG_SOURCE_NAME, AddContactHistoryMceConstants.EMPTY, "Exception in file close : ", e);
					LOGGER.debug(CONFIG_SOURCE_NAME, AddContactHistoryMceConstants.EMPTY, "Detailed Exception in file close : ", e.getMessage());
				}
			}
		}
	}

	@Override
	public Map<String, String> getProperties() {
		return custmap;
	}

	@Override
	public String getValue(String s) {
		return custmap.get(s);
	}

	@Override
	public String getName() {
		return CONFIG_SOURCE_NAME;
	}
}