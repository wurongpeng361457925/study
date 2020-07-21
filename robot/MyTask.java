package com.cmbc.robot;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.TimerTask;

public class MyTask extends TimerTask {

	private long interval;
	private String[] keys;

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	@Override
	public void run() {
		AutoExecute instance = AutoExecute.getInstance();
		instance.pollingInterval(instance.getR());
	}

	public String[] pressKeys_buff() {
		Properties p = new Properties();
		keys = new String[] {};
		String filepath = "../../resource/timeInterval.properties";
		try {
			FileInputStream fis = new FileInputStream(filepath);
			p.load(fis);
			Iterator<String> it = p.stringPropertyNames().iterator();
			int n = 0;
			while (it.hasNext()) {
				String key = it.next();
				if (key.contains("buff")) {
					String v = p.getProperty(key);
					insertKeys(v, n);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keys;
	}

	public void insertKeys(String n, int index) {
		switch (n) {
		case "1":
			keys[index] = "VK_NUMPAD1";
			break;
		case "2":
			keys[index] = "VK_NUMPAD2";
			break;
		case "3":
			keys[index] = "VK_NUMPAD3";
			break;
		case "4":
			keys[index] = "VK_NUMPAD4";
			break;
		case "5":
			keys[index] = "VK_NUMPAD5";
			break;

		default:
			break;
		}
	}
}
