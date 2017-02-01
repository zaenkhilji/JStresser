package com.zaen;

public class Settings {

	/**
	 * @author: Zaen Khilji
	 */

	static final String NAME = "JStresser";

	public static void log(String message) {
		System.out.println(message);
		Launcher.CONSOLE.append(message + "\n");
	}
}