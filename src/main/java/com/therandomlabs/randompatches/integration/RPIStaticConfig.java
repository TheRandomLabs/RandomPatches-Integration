package com.therandomlabs.randompatches.integration;

import java.io.File;
import com.therandomlabs.randompatches.config.RPStaticConfig;
import net.minecraftforge.common.config.Configuration;
import static com.therandomlabs.randompatches.config.RPStaticConfig.getBoolean;
import static com.therandomlabs.randompatches.config.RPStaticConfig.getString;

public class RPIStaticConfig {
	public static class Comments {
		public static final String REPLACE_PORTAL_RENDERER = "Whether to allow other mods " +
				"(namely RandomPortals) to replace the portal renderer.";
		public static final String RPIRELOADCLIENT = "Enables the /rpireloadclient command.";

		public static final String MORPHEUS_SET_SPAWN_MESSAGE = "If this is not an empty string, " +
				"RandomPatches Integration patches Morpheus so the spawn set message is replaced " +
				"with this value and shows as a status message.";
		public static final String REPLACE_TELEPORTER = "Whether to allow other mods " +
				"(namely RandomPortals) to replace the default Teleporter.";
		public static final String RPIRELOAD = "Enables the /rpireload command.";
	}

	public static class Defaults {
		public static final boolean REPLACE_PORTAL_RENDERER = true;
		public static final boolean RPIRELOADCLIENT = true;

		public static final String MORPHEUS_SET_SPAWN_MESSAGE = "Your spawn point has been set!";
		public static final boolean REPLACE_TELEPORTER = true;
		public static final boolean RPIRELOAD = true;
	}

	public static final String CLIENT_COMMENT = "Options related to client-sided features.";
	public static final String MISC_COMMENT = "Options that don't fit into any other categories.";

	public static boolean replacePortalRenderer;
	public static boolean rpireloadclient;

	public static String morpheusSetSpawnMessage;
	public static boolean replaceTeleporter;
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

		replacePortalRenderer = getBoolean(
				"replacePortalRenderer",
				"client",
				Defaults.REPLACE_PORTAL_RENDERER,
				Comments.REPLACE_PORTAL_RENDERER,
				false,
				true
		);

		rpireloadclient = getBoolean(
				"rpireloadclient",
				"client",
				Defaults.RPIRELOADCLIENT,
				Comments.RPIRELOADCLIENT,
				false,
				true
		);

		config.addCustomCategoryComment("misc", MISC_COMMENT);

		morpheusSetSpawnMessage = getString(
				"morpheusSetSpawnMessage",
				"misc",
				Defaults.MORPHEUS_SET_SPAWN_MESSAGE,
				Comments.MORPHEUS_SET_SPAWN_MESSAGE,
				false,
				false
		);

		replaceTeleporter = getBoolean(
				"replaceTeleporter",
				"misc",
				Defaults.REPLACE_TELEPORTER,
				Comments.REPLACE_TELEPORTER,
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
