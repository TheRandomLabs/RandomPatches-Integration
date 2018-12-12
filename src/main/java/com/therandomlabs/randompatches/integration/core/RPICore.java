package com.therandomlabs.randompatches.integration.core;

import java.io.File;
import java.util.Map;
import com.therandomlabs.randompatches.RandomPatches;
import com.therandomlabs.randompatches.integration.RPIntegration;
import com.therandomlabs.randompatches.util.RPUtils;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.SortingIndex(RandomPatches.SORTING_INDEX - 1)
@IFMLLoadingPlugin.MCVersion(RPIntegration.MC_VERSION)
@IFMLLoadingPlugin.Name(RPIntegration.NAME)
public class RPICore implements IFMLLoadingPlugin {
	private static File modFile;

	@Override
	public String[] getASMTransformerClass() {
		RPIntegration.init();
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
