package com.therandomlabs.randompatches.integration;

import com.therandomlabs.randompatches.RPEventHandler;
import com.therandomlabs.randompatches.RPUtils;
import com.therandomlabs.randompatches.RandomPatches;

public final class RPIEventHandler {
	public static void containerInit() {
		if(!RPUtils.hasFingerprint(RPEventHandler.class, RPIntegration.CERTIFICATE_FINGERPRINT)) {
			RandomPatches.LOGGER.error("Invalid fingerprint detected!");
		}
	}
}
