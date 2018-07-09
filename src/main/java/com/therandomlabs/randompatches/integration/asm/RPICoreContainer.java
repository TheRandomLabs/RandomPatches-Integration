package com.therandomlabs.randompatches.integration.asm;

import java.io.File;
import java.net.URL;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;
import com.therandomlabs.randompatches.asm.RPCoreContainer;
import com.therandomlabs.randompatches.integration.RPIntegration;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;

public class RPICoreContainer extends DummyModContainer {
	public RPICoreContainer() {
		super(RPCoreContainer.loadMetadata(RPICore.getModFile(), RPIntegration.MODID,
				RPIntegration.NAME, RPIntegration.VERSION));
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
		bus.register(new RPIntegration());
		return true;
	}

	@Override
	public List<String> getOwnedPackages() {
		return ImmutableList.of("com.therandomlabs.randompatches.integration");
	}
}
