package com.therandomlabs.randompatches.integration;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RPIntegration {
	public static final String MODID = "rpintegration";
	public static final String NAME = "RandomPatches Integration";
	public static final String VERSION = "@VERSION@";
	public static final String AUTHOR = "TheRandomLabs";
	public static final String DESCRIPTION = "An addon for RandomPatches that patches other mods.";
	public static final String LOGO_FILE = "assets/" + MODID + "/logo.png";
	public static final String PROJECT_URL =
			"https://minecraft.curseforge.com/projects/randompatches-integration";
	public static final String UPDATE_JSON = "https://raw.githubusercontent.com/TheRandomLabs/" +
			"RandomPatches-Integration/misc/versions.json";
	public static final URL UPDATE_URL;

	public static final String MC_VERSION = "1.12.2";
	public static final String RANDOMPATCHES_MINIMUM_VERSION = "1.12.2-1.4.0.0";
	public static final String RANDOMPATCHES_VERSION_RANGE = "[" +
			RANDOMPATCHES_MINIMUM_VERSION + ",)";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	static {
		URL url = null;

		try {
			url = new URL(UPDATE_JSON);
		} catch(MalformedURLException ignored) {}

		UPDATE_URL = url;
	}

	private RPIntegration() {}
}
