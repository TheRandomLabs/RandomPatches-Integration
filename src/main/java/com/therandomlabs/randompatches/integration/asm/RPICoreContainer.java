package com.therandomlabs.randompatches.integration.asm;

import java.io.File;
import java.net.URL;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.therandomlabs.randompatches.asm.RPCoreContainer;
import com.therandomlabs.randompatches.integration.CommandRPIReload;
import com.therandomlabs.randompatches.integration.RPIConfig;
import com.therandomlabs.randompatches.integration.RPIStaticConfig;
import com.therandomlabs.randompatches.integration.RPIntegration;
import com.therandomlabs.randompatches.integration.event.RPIClientEventHandler;
import com.therandomlabs.randompatches.integration.event.RPIEventHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.relauncher.Side;

public class RPICoreContainer extends DummyModContainer {
	private static final ModMetadata METADATA = new ModMetadata();

	static {
		METADATA.modId = RPIntegration.MODID;
		METADATA.name = RPIntegration.NAME;
		METADATA.version = RPIntegration.VERSION;
		METADATA.authorList.add(RPIntegration.AUTHOR);
		METADATA.description = RPIntegration.DESCRIPTION;
		METADATA.url = RPIntegration.PROJECT_URL;
		METADATA.logoFile = RPIntegration.LOGO_FILE;
	}

	public RPICoreContainer() {
		super(METADATA);
	}

	@Override
	public File getSource() {
		return RPICore.getModFile();
	}

	@Override
	public Class<?> getCustomResourcePackClass() {
		return RPCoreContainer.getResourcePackClass(this);
	}

	@Override
	public URL getUpdateUrl() {
		return RPIntegration.UPDATE_URL;
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		Loader.instance().setActiveModContainer(this);

		MinecraftForge.EVENT_BUS.register(new RPIEventHandler());

		if(FMLCommonHandler.instance().getSide().isClient()) {
			if(RPIStaticConfig.rpireloadclient) {
				ClientCommandHandler.instance.registerCommand(new CommandRPIReload(Side.CLIENT));
			}

			RPIConfig.reload();
			MinecraftForge.EVENT_BUS.register(new RPIClientEventHandler());
		}

		return true;
	}

	@Override
	public List<String> getOwnedPackages() {
		return ImmutableList.of("com.therandomlabs.randompatches.integration");
	}
}
