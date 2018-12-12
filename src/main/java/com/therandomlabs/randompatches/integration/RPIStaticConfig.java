package com.therandomlabs.randompatches.integration;

import java.io.File;
import com.therandomlabs.randompatches.config.RPStaticConfig;
import net.minecraftforge.common.config.Configuration;
import static com.therandomlabs.randompatches.config.RPStaticConfig.getBoolean;

public class RPIStaticConfig {
	public static class Comments {
		public static final String RPIRELOADCLIENT = "Enables the /rpireloadclient command.";

		public static final String MORPHEUS_SET_SPAWN_MESSAGE_PATCH =
				"Patches Morpheus so the spawn set message shows as a status message.";
		public static final String RPIRELOAD = "Enables the /rpireload command.";
	}

	public static class Defaults {
		public static final boolean RPIRELOADCLIENT = true;

		public static final boolean MORPHEUS_SET_SPAWN_MESSAGE_PATCH = true;
		public static final boolean RPIRELOAD = true;
	}

	public static final String CLIENT_COMMENT = "Options related to client-sided features.";
	public static final String MISC_COMMENT = "Options that don't fit into any other categories.";

	public static boolean rpireloadclient;

	public static boolean morpheusSetSpawnMessagePatch;
	public static boolean rpireload;

	private static Configuration config;

	public static void reload() {
		if(config == null) {
			config = new Configuration(new File("config", RPIntegration.MOD_ID + ".cfg"));
		} else {
			config.load();
		}

		RPStaticConfig.setCurrentConfig(config);

		config.addCustomCategoryComment("client", CLIENT_COMMENT);

		rpireloadclient = getBoolean(
				"rpireloadclient",
				"client",
				Defaults.RPIRELOADCLIENT,
				Comments.RPIRELOADCLIENT,
				false,
				true
		);

		config.addCustomCategoryComment("misc", MISC_COMMENT);

		morpheusSetSpawnMessagePatch = getBoolean(
				"morpheusSetSpawnMessagePatch",
				"misc",
				Defaults.MORPHEUS_SET_SPAWN_MESSAGE_PATCH,
				Comments.MORPHEUS_SET_SPAWN_MESSAGE_PATCH,
				false,
				true
		);

		rpireload = getBoolean(
				"rpireload",
				"misc",
				Defaults.RPIRELOAD,
				Comments.RPIRELOAD,
				true,
				false
		);

		RPStaticConfig.removeOldProperties(config);
		onReload();
		config.save();
	}

	public static void onReload() {}
}
