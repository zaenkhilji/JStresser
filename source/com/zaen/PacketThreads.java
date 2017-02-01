package com.zaen;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicBoolean;

public class PacketThreads extends Thread {

	/**
	 * @author: Zaen Khilji
	 */

	private AtomicBoolean atomicBoolean = new AtomicBoolean(true);
	private final URL url;

	private String param = null;

	public PacketThreads() throws Exception {
		url = new URL(
				Launcher.SSL.isSelected() ? "https" : "http" + "://" + Launcher.IP.getText() + ":" + Launcher.PORT.getText());
		param = "param1=" + URLEncoder.encode("87845", "UTF-8");
	}

	@Override
	public void run() {
		while (atomicBoolean.get()) {
			try {
				process();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	public void process() throws Exception {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Host", "localhost");
		connection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0) Gecko/20100101 Firefox/8.0");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", param);
		Settings.log(this + " " + connection.getResponseCode());
		connection.getInputStream();
	}
}