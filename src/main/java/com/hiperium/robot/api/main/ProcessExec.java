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

import org.apache.log4j.Logger;

import com.hiperium.robot.api.util.StreamGobbler;

/**
 * This class represents a Bean that gets a message from the IoT Gateway to
 * operate the robot.
 * 
 * @author Andres Solorzano
 * 
 */
public class ProcessExec extends Thread {

	/** The LOGGER property for logger messages. */
	private static final Logger LOGGER = Logger.getLogger(ProcessExec.class);

	/** The property pythonProcess. */
	private Process pythonProcess;

	/** The property pythonScript. */
	private String pythonScript;

	/**
	 * Class constructor.
	 */
	public ProcessExec(String pythonScript) {
		this.pythonProcess = null;
		this.pythonScript = pythonScript;
	}

	@Override
	public void run() {
		LOGGER.info("SCRIPT TO EXECUTE: " + this.pythonScript);
		try {
			this.pythonProcess = Runtime.getRuntime().exec(this.pythonScript);
			// for error messages
			StreamGobbler errorGobbler = new StreamGobbler(this.pythonProcess.getErrorStream(), "ERROR");

			// for any output message
			StreamGobbler outputGobbler = new StreamGobbler(this.pythonProcess.getInputStream(), "OUTPUT");

			// Start to log the messages
			errorGobbler.start(); 
			outputGobbler.start(); 

			// Wait until process ends
			int exitVal = this.pythonProcess.waitFor();
			LOGGER.info("ExitValue: " + exitVal);

			Process process = this.pythonProcess.destroyForcibly();
			if (null != process && process.isAlive()) {
				LOGGER.info("TERMINATING ALIVE PYTHON PROCESS...");
				process.destroy();
			}
		} catch (IOException | InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	/**
	 * Stops the execution of the python process.
	 * 
	 */
	public void stopPythonProcess() {
		if (null != this.pythonProcess) {
			LOGGER.info("DESTROY CURRENT PYTHON PROCESS...");
			this.pythonProcess.destroy();
		}
	}
}
