package com.therandomlabs.randompatches.integration.config;

import com.therandomlabs.randomlib.config.ConfigManager;
import com.therandomlabs.randompatches.integration.RPIntegration;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

public class RPIGuiConfig extends GuiConfig {
	public RPIGuiConfig(GuiScreen parentScreen) {
		super(
				parentScreen,
				ConfigManager.getConfigElements(RPIConfig.class),
				RPIntegration.MOD_ID,
				RPIntegration.MOD_ID,
				false,
				false,
				ConfigManager.getPathString(RPIConfig.class)
		);
	}
}
