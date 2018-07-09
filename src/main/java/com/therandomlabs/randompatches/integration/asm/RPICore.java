package com.therandomlabs.randompatches.integration.asm;

import java.io.File;
import java.util.Map;
import com.therandomlabs.randompatches.RandomPatches;
import com.therandomlabs.randompatches.asm.RPCore;
import com.therandomlabs.randompatches.integration.RPIStaticConfig;
import com.therandomlabs.randompatches.integration.RPIntegration;
import com.therandomlabs.randompatches.integration.asm.transformer.MorpheusTransformer;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.common.versioning.VersionParser;
import net.minecraftforge.fml.common.versioning.VersionRange;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import static com.therandomlabs.randompatches.asm.RPTransformer.register;

@IFMLLoadingPlugin.SortingIndex(9000)
@IFMLLoadingPlugin.MCVersion(RPIntegration.MC_VERSION)
@IFMLLoadingPlugin.Name(RPIntegration.NAME)
public class RPICore implements IFMLLoadingPlugin {
	private static File modFile;
	private static boolean randomPatchesInstalled;

	@Override
	public String[] getASMTransformerClass() {
		try {
			Class.forName("com.therandomlabs.randompatches.RandomPatches");

			final VersionRange range =
					VersionParser.parseRange(RPIntegration.RANDOMPATCHES_VERSION_RANGE);
			final ArtifactVersion version =
					new DefaultArtifactVersion(RandomPatches.MODID, RandomPatches.VERSION);

			if(range.containsVersion(version)) {
				randomPatchesInstalled = true;
			}
		} catch(ClassNotFoundException ignored) {}

		if(randomPatchesInstalled) {
			RPIStaticConfig.reload();
			registerTransformers();
		} else {
			RPIntegration.LOGGER.error("RandomPatches %s or higher not found. " +
					"RandomPatches Integration will be disabled.",
					RPIntegration.RANDOMPATCHES_MINIMUM_VERSION);
		}

		return new String[] {};
	}

	@Override
	public String getModContainerClass() {
		return randomPatchesInstalled ?
				"com.therandomlabs.randompatches.integration.asm.RPICoreContainer" : null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if(randomPatchesInstalled) {
			modFile = RPCore.getModFile(data, RPICore.class,
					"com/therandomlabs/randompatches/integration/asm");
		}
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	public static File getModFile() {
		return modFile;
	}

	private static void registerTransformers() {
		if(RPIStaticConfig.morpheusSetSpawnMessagePatch) {
			register("net.quetzi.morpheus.helpers.MorpheusEventHandler",
					new MorpheusTransformer());
		}
	}
}
