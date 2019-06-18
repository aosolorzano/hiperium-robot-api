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
package com.hiperium.robot.api.util;

/**
 * 
 * @author Andres Solorzano
 *
 */
public enum EnumRobotFunction {

	OBSTACLE_AVOIDANCE(1, "obstacle.avoidance"),
	LIGHT_FOLLOWING(2, "light.following"),
	LINE_FOLLOWING(3, "line.following");

	private int value;
	private String propertyName;

	/**
	 * Enumerator constructor.
	 * 
	 * @param value
	 * @param propertyName
	 */
	private EnumRobotFunction(int value, String propertyName) {
		this.value = value;
		this.propertyName = propertyName;
	}

	public static EnumRobotFunction getFunctionByValue(int value) {
		for (EnumRobotFunction e : EnumRobotFunction.values()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return EnumRobotFunction.OBSTACLE_AVOIDANCE;
	}

	public int getValue() {
		return value;
	}

	public String getPropertyName() {
		return propertyName;
	}

}
