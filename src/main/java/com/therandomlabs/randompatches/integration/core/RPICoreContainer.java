package com.therandomlabs.randompatches.integration.core;

import java.io.File;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.therandomlabs.randompatches.RPUtils;
import com.therandomlabs.randompatches.core.RPCoreContainer;
import com.therandomlabs.randompatches.integration.RPIEventHandler;
import com.therandomlabs.randompatches.integration.RPIntegration;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.versioning.VersionRange;

public class RPICoreContainer extends RPCoreContainer {
	public RPICoreContainer() {
		super(RPUtils.loadMetadata(
				RPICore.getModFile(),
				RPIntegration.MOD_ID,
				RPIntegration.NAME,
				RPIntegration.VERSION
		));

		RPIEventHandler.containerInit();
	}

	@Override
	public File getSource() {
		return RPICore.getModFile();
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		Loader.instance().setActiveModContainer(this);
		bus.register(new RPIntegration());
		return true;
	}

	@Override
	public List<String> getOwnedPackages() {
		return ImmutableList.of("com.therandomlabs.randompatches.integration");
	}

	@Override
	public VersionRange acceptableMinecraftVersionRange() {
		return RPUtils.createVersionRange("[" + RPIntegration.MC_VERSION + "]");
	}
}
