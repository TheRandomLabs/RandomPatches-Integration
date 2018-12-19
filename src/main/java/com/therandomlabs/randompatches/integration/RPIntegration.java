package com.therandomlabs.randompatches.integration;

import com.google.common.eventbus.Subscribe;
import com.therandomlabs.randompatches.RandomPatches;
import com.therandomlabs.randompatches.integration.patch.GuiIngamePatch;
import com.therandomlabs.randompatches.integration.patch.MorpheusEventHandlerPatch;
import com.therandomlabs.randompatches.integration.patch.WorldServerPatch;
import com.therandomlabs.randompatches.util.RPUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
	public void construct(FMLConstructionEvent event) {
		if(RandomPatches.IS_CLIENT) {
			RPIConfig.reload();
		}
	}

	@Subscribe
	public void preInit(FMLPreInitializationEvent event) {
		if(RPIStaticConfig.rpireloadclient && RandomPatches.IS_CLIENT) {
			ClientCommandHandler.instance.registerCommand(new CommandRPIReload(Side.CLIENT));
		}
	}

	@Subscribe
	public void init(FMLInitializationEvent event) {
		if(RandomPatches.IS_CLIENT) {
			MinecraftForge.EVENT_BUS.register(this);
		}
	}

	@Subscribe
	public void serverStarting(FMLServerStartingEvent event) {
		if(RPIStaticConfig.rpireload) {
			event.registerServerCommand(new CommandRPIReload(Side.SERVER));
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(MOD_ID)) {
			RPIConfig.reload();
		}
	}

	public static void init() {
		RPIStaticConfig.reload();
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
		if(RPIStaticConfig.replacePortalRenderer) {
			register("net.minecraft.client.gui.GuiIngame", new GuiIngamePatch());
		}

		if(!RPIStaticConfig.morpheusSetSpawnMessage.isEmpty()) {
			register(
					"net.quetzi.morpheus.helpers.MorpheusEventHandler",
					new MorpheusEventHandlerPatch()
			);
		}

		if(RPIStaticConfig.replaceTeleporter) {
			register("net.minecraft.world.WorldServer", new WorldServerPatch());
		}
	}
}
