package com.hiperium.robot.api.test;

import java.io.File;
import java.io.IOException;

public class PythonTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("python", "ultra_sonic_avoid.py");
		pb.directory(new File("/home/pi/SunFounder_PiCar-S/example"));
		Process p = pb.start();
		Thread.sleep(5000L);
		System.out.println("Done.");
		p.destroy();
	}
}
