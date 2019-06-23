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

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hiperium.robot.api.util.enums.EnumRobotFunction;
import com.hiperium.robot.api.util.enums.EnumRobotModel;
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

	private static final String PORT_NAME = "/dev/ttyUSB0";
	private static final String PROPERTIES_FILE_NAME = "scripts.properties";
	private static final String PICAR_S_PROPERTY_NAME = "picar.s.";
	private static final String YAHBOOM_4WD_PROPERTY_NAME = "yahbomm.4wd.";

	/** The property PROPERTIES. */
	private static final Properties PROPERTIES = new Properties();

	/** The property processExec. */
	private ProcessExec processExec;

	/** The property xbee. */
	private XBee xbee;

	/** The property data. */
	private int[] data;

	/**
	 * Class initialization.
	 */
	static {
		try {
			PROPERTIES.load(DeviceMessageListener.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * Class constructor.
	 */
	public DeviceMessageListener() {
		this.processExec = null;
		this.xbee = new XBee();
		this.data = new int[3];
	}

	/**
	 * Start XBEE Radio package listening.
	 * 
	 * @throws XBeeException
	 */
	public void startXbeePackageListener() throws XBeeException {
		LOGGER.info("startXbeePackageListener() - START");
		this.xbee.open(PORT_NAME, 9600);
		this.xbee.addPacketListener(this);
		LOGGER.info("startXbeePackageListener() - END");
	}

	/**
	 * Receives data from IoT Gateway to control the Robot.
	 */
	@Override
	public void processResponse(XBeeResponse response) {
		if (response.getApiId() == ApiId.RX_16_RESPONSE) {
			RxResponse16 response16 = (RxResponse16) response;
			this.data = response16.getData();
			LOGGER.info("**** RECEIVED DATA *****");
			for (int i = 0; i < this.data.length; i++) {
				LOGGER.info("Data[" + i + "]: " + this.data[i]);
			}
			if (this.data.length == 3 && this.data[0] == 0) { // DEACTIVATE ROBOT
				this.stopPythonProcess();
			} else if (this.data.length == 3 && this.data[1] != 0 && this.data[2] != 0) { // ACTIVATE ROBOT MODULE
				EnumRobotModel enumRobotModel = EnumRobotModel.getModelByValue(this.data[1]);
				EnumRobotFunction enumRobotFunction = EnumRobotFunction.getFunctionByValue(this.data[2]);
				try {
					this.callRobotFunctionByModel(enumRobotModel, enumRobotFunction);
				} catch (IOException | InterruptedException e) {
					LOGGER.error(e.getMessage(), e);
				}

			}
		}
	}

	/**
	 * Verifies the robot model before call the correct function.
	 * 
	 * @param enumRobotModel
	 * @param enumRobotFunction
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void callRobotFunctionByModel(EnumRobotModel enumRobotModel, EnumRobotFunction enumRobotFunction)
			throws IOException, InterruptedException {
		LOGGER.info("ROBOT MODEL: " + enumRobotModel.name());
		LOGGER.info("ROBOT FUNCTION: " + enumRobotFunction.name());
		switch (enumRobotModel) {
		case PICAR_S:
			this.callRobotFunction(PICAR_S_PROPERTY_NAME, enumRobotFunction);
			break;
		case YAHBOOM_4WD_PI:
			this.callRobotFunction(YAHBOOM_4WD_PROPERTY_NAME, enumRobotFunction);
			break;
		default:
			break;
		}
	}

	/**
	 * Calls the robot's python script.
	 * 
	 * @param robotModelKey
	 * @param enumRobotFunction
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void callRobotFunction(String robotModelKey, EnumRobotFunction enumRobotFunction)
			throws IOException, InterruptedException {
		String script = PROPERTIES.getProperty(robotModelKey.concat(enumRobotFunction.getPropertyName()));

		if (StringUtils.isBlank(script)) {
			LOGGER.info("NO SCRIPT FOUND TO EXECUTE...");
		} else {
			this.processExec = new ProcessExec(script);
			this.processExec.start();
		}
	}

	/**
	 * Stops the execution of the python process.
	 */
	private void stopPythonProcess() {
		if (null != this.processExec) {
			this.processExec.stopPythonProcess();
		}
	}
}
