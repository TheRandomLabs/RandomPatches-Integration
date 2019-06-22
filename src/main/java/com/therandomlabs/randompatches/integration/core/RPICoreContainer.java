package com.therandomlabs.randompatches.integration.core;

import java.io.File;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.therandomlabs.randompatches.core.RPCoreContainer;
import com.therandomlabs.randompatches.integration.RPIntegration;
import com.therandomlabs.randompatches.integration.config.RPIGuiConfigFactory;
import com.therandomlabs.randompatches.util.RPUtils;
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

		RPIntegration.containerInit();
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
	public VersionRange acceptableMinecraftVersionRange() {
		return Loader.instance().getMinecraftModContainer().getStaticVersionRange();
	}

	@Override
	public String getGuiClassName() {
		return RPIGuiConfigFactory.class.getName();
	}

	@Override
	public List<String> getOwnedPackages() {
		return ImmutableList.of("com.therandomlabs.randompatches.integration");
	}
}
