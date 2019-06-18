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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 * 
 * @author Andres Solorzano
 *
 */
public class JythonTest {

	public static void main(String[] args) throws FileNotFoundException, ScriptException {
		StringWriter writer = new StringWriter();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptContext context = new SimpleScriptContext();
		context.setWriter(writer);
		ScriptEngine engine = manager.getEngineByName("python");
		engine.eval(new FileReader("/home/pi/SunFounder_PiCar-S/example/ultra_sonic_avoid.py"), context);
		System.out.println(writer.toString());
	}

}
