package com.demo.good.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadConfig {

	static Properties properties = new Properties();
	static {
		try {
			String path = "/config.properties";
			properties.load(ReadConfig.class.getResourceAsStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static String getProperty(String key) {
		return properties.getProperty(key);
	}

}
