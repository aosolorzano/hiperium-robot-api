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
package com.hiperium.robot.api.test;

import java.util.Properties;

import org.python.util.PythonInterpreter;

/**
 * 
 * @author Andres Solorzano
 *
 */
public class JythonTest2 {

	public static void main(String[] args) throws InterruptedException {
		Properties properties = System.getProperties();
		PythonInterpreter.initialize(properties, properties, new String[0]);
		PythonInterpreter interp = new PythonInterpreter();
		interp.execfile("/home/pi/SunFounder_PiCar-S/example/ultra_sonic_avoid.py");
		Thread.sleep(5000);
		interp.close(); 
	}

}
