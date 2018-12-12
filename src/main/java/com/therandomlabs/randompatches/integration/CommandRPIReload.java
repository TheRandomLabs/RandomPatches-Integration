package com.therandomlabs.randompatches.integration;

import com.therandomlabs.randompatches.config.RPConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;

public class CommandRPIReload extends CommandBase {
	private final boolean isClient;

	public CommandRPIReload(Side side) {
		isClient = side.isClient();
	}

	@Override
	public String getName() {
		return isClient ? "rpireloadclient" : "rpireload";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return isClient ? "commands.rpireloadclient.usage" : "/rpireload";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args)
			throws CommandException {
		if(server != null && server.isDedicatedServer()) {
			RPIStaticConfig.reload();
			notifyCommandListener(
					sender, this, "RandomPatches Integration configuration reloaded!"
			);
		} else {
			RPConfig.removeConfigForReloadFromDisk(RPIntegration.MOD_ID);
			RPIConfig.reload();
			sender.sendMessage(new TextComponentTranslation("commands.rpireloadclient.success"));
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return isClient ? 0 : 4;
	}
}
