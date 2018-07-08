package com.therandomlabs.randompatches.integration.event;

import com.therandomlabs.randompatches.integration.CommandRPIReload;
import com.therandomlabs.randompatches.integration.RPIStaticConfig;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommandManager;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

public class RPIEventHandler {
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load event) {
		final World world = event.getWorld();

		if(world.isRemote || world.provider.getDimensionType() != DimensionType.OVERWORLD) {
			return;
		}

		final ICommandManager manager = world.getMinecraftServer().getCommandManager();
		registerCommands((CommandHandler) manager);
	}

	public static void registerCommands(CommandHandler handler) {
		if(RPIStaticConfig.rpireload) {
			handler.registerCommand(new CommandRPIReload(Side.SERVER));
		}
	}
}
