package com.therandomlabs.randompatches.integration;

import java.io.File;
import com.therandomlabs.randompatches.RPStaticConfig;
import net.minecraftforge.common.config.Configuration;
import static com.therandomlabs.randompatches.RPStaticConfig.getBoolean;

public class RPIStaticConfig {
	public static class Comments {
		public static final String MORPHEUS_SET_SPAWN_MESSAGE_PATCH =
				"Patches Morpheus so the spawn set message shows as a status message.";
		public static final String RPIRELOAD = "Enables the /rpireload command. " +
				"This option only takes effect after a world restart.";
		public static final String RPIRELOADCLIENT = "Enables the /rpireloadclient command. " +
				"This option only takes effect after a Minecraft restart.";
	}

	public static class Defaults {
		public static final boolean MORPHEUS_SET_SPAWN_MESSAGE_PATCH = true;
		public static final boolean RPIRELOAD = true;
		public static final boolean RPIRELOADCLIENT = true;
	}

	public static final String MISC_COMMENT = "Options that don't fit into any other categories.";

	public static boolean morpheusSetSpawnMessagePatch;
	public static boolean rpireload;
	public static boolean rpireloadclient;

	private static Configuration config;

	public static void reload() {
		if(config == null) {
			config = new Configuration(new File("config", RPIntegration.MODID + ".cfg"));
		} else {
			config.load();
		}

		RPStaticConfig.setCurrentConfig(config);

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

		rpireloadclient = getBoolean(
				"rpireloadclient",
				"misc",
				Defaults.RPIRELOADCLIENT,
				Comments.RPIRELOADCLIENT,
				false,
				true
		);

		RPStaticConfig.removeOldProperties(config);
		onReload();
		config.save();
	}

	public static void onReload() {

	}
}
