package com.therandomlabs.randompatches.integration.core;

import java.io.File;
import java.util.Map;
import com.therandomlabs.randompatches.integration.RPIntegration;
import com.therandomlabs.randompatches.util.RPUtils;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.MCVersion(RPIntegration.MC_VERSION)
@IFMLLoadingPlugin.Name(RPIntegration.NAME)
@IFMLLoadingPlugin.TransformerExclusions("com.therandomlabs.randompatches.integration.core")
public class RPICore implements IFMLLoadingPlugin {
	private static boolean initialized;
	private static File modFile;

	@Override
	public String[] getASMTransformerClass() {
		if (!initialized) {
			RPIntegration.init();
			initialized = true;
		}

		return new String[] {};
	}

	@Override
	public String getModContainerClass() {
		return "com.therandomlabs.randompatches.integration.core.RPICoreContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		modFile = RPUtils.getModFile(
				data, RPICore.class, "com/therandomlabs/randompatches/integration/core"
		);
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	public static File getModFile() {
		return modFile;
	}
}
