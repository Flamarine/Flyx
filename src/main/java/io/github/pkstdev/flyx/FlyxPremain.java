package io.github.pkstdev.flyx;

import nilloader.api.ModRemapper;
import nilloader.api.NilLogger;

public class FlyxPremain implements Runnable {
	public static final NilLogger log = NilLogger.get("Flyx");
	
	@Override
	public void run() {
		log.info("Initialized.");
		ModRemapper.setTargetMapping("default");
	}
}
