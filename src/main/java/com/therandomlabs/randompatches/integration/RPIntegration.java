package com.therandomlabs.randompatches.integration;

import com.google.common.eventbus.Subscribe;
import com.therandomlabs.randomlib.TRLUtils;
import com.therandomlabs.randomlib.config.CommandConfigReload;
import com.therandomlabs.randomlib.config.ConfigManager;
import com.therandomlabs.randompatches.RandomPatches;
import com.therandomlabs.randompatches.integration.config.RPIConfig;
import com.therandomlabs.randompatches.integration.patch.MorpheusEventHandlerPatch;
import com.therandomlabs.randompatches.util.RPUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.therandomlabs.randompatches.core.RPTransformer.register;

public final class RPIntegration {
	public static final String MOD_ID = "rpintegration";
	public static final String NAME = "RandomPatches Integration";
	public static final String VERSION = "@VERSION@";
	public static final String CERTIFICATE_FINGERPRINT = "@FINGERPRINT@";

	public static final String MC_VERSION = "1.12.2";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Subscribe
	public void preInit(FMLPreInitializationEvent event) {
		if(RPIConfig.Client.rpireloadclient && TRLUtils.IS_CLIENT) {
			ClientCommandHandler.instance.registerCommand(new CommandConfigReload(
					"rpireloadclient", RPIConfig.class, Side.CLIENT
			));
		}
	}

	@Subscribe
	public void init(FMLInitializationEvent event) {
		ConfigManager.registerEventHandler();
	}

	@Subscribe
	public void serverStarting(FMLServerStartingEvent event) {
		if(RPIConfig.Misc.rpireload) {
			event.registerServerCommand(new CommandConfigReload(
					"rpireload", RPIConfig.class, Side.SERVER,
					"RandomPatches Integration configuration reloaded!"
			));
		}
	}

	public static void init() {
		ConfigManager.register(RPIConfig.class);
		registerPatches();
	}

	public static void containerInit() {
		if(!RPUtils.hasFingerprint(RPIntegration.class, CERTIFICATE_FINGERPRINT)) {
			if(RandomPatches.IS_DEOBFUSCATED) {
				LOGGER.debug("Invalid fingerprint detected!");
			} else {
				LOGGER.error("Invalid fingerprint detected!");
			}
		}
	}

	private static void registerPatches() {
		if(!RPIConfig.Misc.morpheusSetSpawnMessage.isEmpty()) {
			register(
					"net.quetzi.morpheus.helpers.MorpheusEventHandler",
					new MorpheusEventHandlerPatch()
			);
		}
	}
}
