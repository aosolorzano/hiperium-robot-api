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

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.RxResponse16;

/**
 * This class represents a Bean that gets a message from the IoT Gateway to
 * operate the robot.
 * 
 * @author Andres Solorzano
 * 
 */
public class DeviceMessageListener implements PacketListener {

	/** The LOGGER property for logger messages. */
	private static final Logger LOGGER = Logger.getLogger(DeviceMessageListener.class);

	/** The property xbee. */
	private XBee xbee;

	/** The property data. */
	private int[] data;

	/**
	 * Class constructor.
	 */
	public DeviceMessageListener() {
		this.xbee = new XBee();
		this.data = new int[3];
	}

	/**
	 * Start XBEE device listening.
	 * 
	 * @throws XBeeException
	 */
	public void startXbeePackageListener() throws XBeeException {
		LOGGER.info("startXbeePackageListener() - START");
		this.xbee.open("/dev/ttyUSB0", 9600);
		this.xbee.addPacketListener(this);
		LOGGER.info("startXbeePackageListener() - END");
	}

	/**
	 * Receives data from devices and send it to the Hiperium Cloud Platform.
	 */
	@Override
	public void processResponse(XBeeResponse response) {
		LOGGER.info("processResponse - START");
		if (response.getApiId() == ApiId.RX_16_RESPONSE) {
			RxResponse16 response16 = (RxResponse16) response;
			this.data = response16.getData();
			for (int i = 0; i < this.data.length; i++) {
				LOGGER.info("Data[" + i + "]: " + this.data[i]);
			}
		}
		LOGGER.info("processResponse - END");
	}
}
