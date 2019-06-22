package com.therandomlabs.randompatches.integration.config;

import com.therandomlabs.randomlib.config.Config;
import com.therandomlabs.randompatches.integration.RPIntegration;

@Config(RPIntegration.MOD_ID)
public final class RPIConfig {
	public static final class Client {
		@Config.RequiresMCRestart
		@Config.Property("Enables the /rpireloadclient command.")
		public static boolean rpireloadclient = true;
	}

	public static final class Misc {
		@Config.Property({
				"If this is not an empty string, RandomPatches Integration patches Morpheus " +
						"so the spawn set message is replaced with this value and shows as a " +
						"status message.",
				"If this is empty when Minecraft starts, the game must be restarted for this to " +
						"change."
		})
		public static String morpheusSetSpawnMessage = "Your spawn point has been set!";

		@Config.RequiresWorldReload
		@Config.Property("Enables the /rpireload command.")
		public static boolean rpireload = true;
	}

	@Config.Category("Options related to client-sided features.")
	public static final Client client = null;

	@Config.Category("Options that don't fit into any other categories.")
	public static final Misc misc = null;
}
