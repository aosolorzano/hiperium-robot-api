/**
 * This class contains the methods needed to communicate to a remote radio
 * device (XBee), and maintains a continuously listening for events with the
 * coordinator radio device.
 * 
 * @author Andres Solorzano
 * 
 */
package com.hiperium.robot.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * 
 * @author Andres Solorzano
 *
 */
public class StreamGobbler extends Thread {

	/** The LOGGER property for logger messages. */
	private static final Logger LOGGER = Logger.getLogger(StreamGobbler.class);

	private InputStream is;
	private String type;

	public StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	@Override
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				LOGGER.info(this.type + " > " + line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
