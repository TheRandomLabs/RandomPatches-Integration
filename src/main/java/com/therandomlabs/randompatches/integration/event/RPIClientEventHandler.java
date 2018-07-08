package com.therandomlabs.randompatches.integration.event;

import com.therandomlabs.randompatches.integration.RPIConfig;
import com.therandomlabs.randompatches.integration.RPIntegration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RPIClientEventHandler {
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(RPIntegration.MODID)) {
			RPIConfig.reload();
		}
	}
}
