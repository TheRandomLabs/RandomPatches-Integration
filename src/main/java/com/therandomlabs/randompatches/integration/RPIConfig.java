package com.therandomlabs.randompatches.integration;

import java.lang.reflect.Field;
import java.util.Map;
import com.therandomlabs.randompatches.RPConfig;
import net.minecraftforge.common.config.Config;

@Config(modid = RPIntegration.MODID, name = RPIntegration.MODID, category = "")
public class RPIConfig {
	public static class Misc {
		@Config.LangKey("rpintegration.config.misc.morpheusSetSpawnMessagePatch")
		@Config.Comment(RPIStaticConfig.Comments.MORPHEUS_SET_SPAWN_MESSAGE_PATCH)
		public boolean morpheusSetSpawnMessagePatch =
				RPIStaticConfig.Defaults.MORPHEUS_SET_SPAWN_MESSAGE_PATCH;

		@Config.RequiresWorldRestart
		@Config.LangKey("rpintegration.config.misc.rpireload")
		@Config.Comment(RPIStaticConfig.Comments.RPIRELOAD)
		public boolean rpireload = RPIStaticConfig.Defaults.RPIRELOAD;

		@Config.RequiresWorldRestart
		@Config.LangKey("rpintegration.config.misc.rpireloadclient")
		@Config.Comment(RPIStaticConfig.Comments.RPIRELOADCLIENT)
		public boolean rpireloadclient = RPIStaticConfig.Defaults.RPIRELOADCLIENT;
	}

	@Config.LangKey("rpintegration.config.misc")
	@Config.Comment(RPIStaticConfig.MISC_COMMENT)
	public static Misc misc = new Misc();

	private static Map<Object, Field> properties;

	public static void reload() {
		if(properties == null) {
			properties = RPConfig.getProperties(RPIConfig.class);
		}

		RPConfig.reload(properties, RPIntegration.MODID, RPIConfig.class, RPIStaticConfig.class,
				RPIStaticConfig::onReload);
	}
}
