package com.therandomlabs.randompatches.integration;

import java.lang.reflect.Field;
import java.util.Map;
import com.therandomlabs.randompatches.config.RPConfig;
import net.minecraftforge.common.config.Config;

@Config(modid = RPIntegration.MOD_ID, name = RPIntegration.MOD_ID, category = "")
public final class RPIConfig {
	public static final class Client {
		@Config.RequiresMcRestart
		@Config.LangKey("rpintegration.config.client.rpireloadclient")
		@Config.Comment(RPIStaticConfig.Comments.RPIRELOADCLIENT)
		public boolean rpireloadclient = RPIStaticConfig.Defaults.RPIRELOADCLIENT;
	}

	public static final class Misc {
		@Config.LangKey("rpintegration.config.misc.morpheusSetSpawnMessage")
		@Config.Comment(RPIStaticConfig.Comments.MORPHEUS_SET_SPAWN_MESSAGE)
		public String morpheusSetSpawnMessage =
				RPIStaticConfig.Defaults.MORPHEUS_SET_SPAWN_MESSAGE;

		@Config.RequiresWorldRestart
		@Config.LangKey("rpintegration.config.misc.rpireload")
		@Config.Comment(RPIStaticConfig.Comments.RPIRELOAD)
		public boolean rpireload = RPIStaticConfig.Defaults.RPIRELOAD;
	}

	@Config.LangKey("rpintegration.config.client")
	@Config.Comment(RPIStaticConfig.CLIENT_COMMENT)
	public static final Client client = new Client();

	@Config.LangKey("rpintegration.config.misc")
	@Config.Comment(RPIStaticConfig.MISC_COMMENT)
	public static final Misc misc = new Misc();

	private static Map<Object, Field[]> propertyCache;

	public static void reload() {
		if(propertyCache == null) {
			propertyCache = RPConfig.getProperties(RPIConfig.class);
		}

		RPConfig.reload(
				propertyCache,
				RPIntegration.MOD_ID,
				RPIConfig.class,
				RPIStaticConfig.class,
				RPIStaticConfig::onReload
		);
	}
}
