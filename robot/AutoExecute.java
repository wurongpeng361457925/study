package com.cmbc.robot;

import java.awt.AWTException;
import java.awt.Robot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AutoExecute {
	private static final Log log = LogFactory.getLog(AutoExecute.class);
	private static AutoExecute ae;
	private Robot r;

	
	public Robot getR() {
		return r;
	}

	private AutoExecute() {
		try {
			if (r == null) {
				r = new Robot();
			}
		} catch (AWTException e) {
			e.printStackTrace();
		}
	};

	public synchronized static AutoExecute getInstance() {
		if (ae == null) {
			ae = new AutoExecute();
		}
		return ae;
	}

	public void pollingInterval(Robot r, int... key) {
		if (r != null) {
			for (int i : key) {
				r.keyPress(i);
				r.keyRelease(i);
				r.delay(1200);
				log.info("press key of " + i + "...");
			}
		}
	}

	public static void main(String[] args) {

	}
}
