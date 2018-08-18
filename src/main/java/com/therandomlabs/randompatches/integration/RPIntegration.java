package com.therandomlabs.randompatches.integration;

import com.google.common.eventbus.Subscribe;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RPIntegration {
	public static final String MODID = "rpintegration";
	public static final String NAME = "RandomPatches Integration";
	public static final String VERSION = "@VERSION@";

	public static final String MC_VERSION = "1.12.2";
	public static final String RANDOMPATCHES_MINIMUM_VERSION = "1.12.2-1.4.1.1";
	public static final String RANDOMPATCHES_VERSION_RANGE =
			"[" + RANDOMPATCHES_MINIMUM_VERSION + ",)";

	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@Subscribe
	public void preInit(FMLPreInitializationEvent event) {
		if(RPIStaticConfig.rpireloadclient && event.getSide().equals(Side.CLIENT)) {
			RPIConfig.reload();
			ClientCommandHandler.instance.registerCommand(new CommandRPIReload(Side.CLIENT));
		}
	}

	@Subscribe
	public void init(FMLInitializationEvent event) {
		if(event.getSide().equals(Side.CLIENT)) {
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
		if(event.getModID().equals(MODID)) {
			RPIConfig.reload();
		}
	}
}