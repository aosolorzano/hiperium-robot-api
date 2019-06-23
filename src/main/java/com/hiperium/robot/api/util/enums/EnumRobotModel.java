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
package com.hiperium.robot.api.util.enums;

/**
 * 
 * @author Andres Solorzano
 *
 */
public enum EnumRobotModel {

	PICAR_S(1), 
	YAHBOOM_4WD_PI(2);

	private int value;

	/**
	 * Enumerator constructor.
	 * 
	 * @param value
	 */
	private EnumRobotModel(int value) {
		this.value = value;
	}

	public static EnumRobotModel getModelByValue(int value) {
		for (EnumRobotModel e : EnumRobotModel.values()) {
			if (e.getValue() == value) {
				return e;
			}
		}
		return EnumRobotModel.PICAR_S;
	}

	public int getValue() {
		return value;
	}
}
