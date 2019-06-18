/**
 * Product  : Hiperium Project
 * Architect: Andres Solorzano.
 * Created  : 08-05-2009 - 23:30:00
 * 
 * The contents of this file are copyrighted by Andres Solorzano 
 * and it is protected by the license: "GPL V3." You can find a copy of this 
 * license at: http://www.hiperium.com/about/licence.html
 * 
 * Copyright 2014 Andres Solorzano. All rights reserved.
 * 
 */
package com.hiperium.robot.api.main;

import org.apache.log4j.Logger;

/**
 * @author Andres Solorzano
 *
 */
public class MainClass {

	/** The LOGGER property for logger messages. */
	private static final Logger LOGGER = Logger.getLogger(MainClass.class);

	/**
	 * Main class method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LOGGER.info("Main class - START");
		DeviceMessageListener deviceMessageListener = new DeviceMessageListener();
		try {
			deviceMessageListener.startXbeePackageListener();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("Main class - END");
	}
}
