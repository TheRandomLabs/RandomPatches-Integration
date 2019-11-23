package com.therandomlabs.randompatches.integration.patch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.therandomlabs.randomlib.TRLUtils;
import com.therandomlabs.randompatches.core.Patch;
import gd.izno.mc.muon.MuonHeightMap;
import gd.izno.mc.muon.MuonHooks;
import gd.izno.mc.muon.MuonUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;

//We fix the infinite loop that occurs here:
//https://github.com/MinimumContent/muon/blob/d74f9c4347329b5eef627e60259872735fc8c764/src/
//main/java/gd/izno/mc/muon/MuonHooks.java#L97
//The loop ends when it finds a non-air block
//Because we can't patch MuonHooks directly, we patch the already patched
//StructureVillagePieces$Village and replace Muon's hook with our own version
public final class VillagePatch extends Patch {
	public static final class Hook {
		private static final Method GET_HEIGHT =
				TRLUtils.findMethod(MuonHeightMap.class, "getHeight", int.class, int.class);

		public static int getAverageGroundLevel(
				StructureComponent village, World world, StructureBoundingBox structureBoundingBox
		) {
			final StructureBoundingBox villageBoundingBox = village.getBoundingBox();

			int i = 0;
			int j = 0;
			int minY = -1;

			int minX = villageBoundingBox.minX - 1;
			int minZ = villageBoundingBox.minZ - 1;
			int maxX = villageBoundingBox.maxX + 1;
			int maxZ = villageBoundingBox.maxZ + 1;

			final EnumFacing facing = village.getCoordBaseMode();

			if (facing != null && (maxX - minX + 1 != 8 || maxZ - minZ + 1 != 8)) {
				switch (facing) {
				case NORTH:
					minZ = villageBoundingBox.maxZ;
					break;
				case SOUTH:
					maxZ = villageBoundingBox.minZ;
					break;
				case WEST:
					minX = villageBoundingBox.maxX;
					break;
				case EAST:
					maxX = villageBoundingBox.minX;
					break;
				}
			} else {
				i = -4;
			}

			if ((facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH)) {
				minX += Math.min(2, ((maxX - minX) / 2) - 1);
				maxX -= Math.min(2, ((maxX - minX) / 2) - 1);
			} else if ((facing == EnumFacing.EAST || facing == EnumFacing.WEST)) {
				minZ += Math.min(2, ((maxZ - minZ) / 2) - 1);
				maxZ -= Math.min(2, ((maxZ - minZ) / 2) - 1);
			}

			final BlockPos.MutableBlockPos currentBlock = new BlockPos.MutableBlockPos();

			for (int x = minX; x <= maxX; x++) {
				for (int z = minZ; z <= maxZ; z++) {
					int y = world.provider.getAverageGroundLevel() - 2;

					if (!(x >= villageBoundingBox.minX && x <= villageBoundingBox.maxX &&
							z >= villageBoundingBox.minZ && z <= villageBoundingBox.maxZ)) {
						final StructureBoundingBox chunkBoundingBox =
								new StructureBoundingBox(x & ~15, z & ~15, x | 15, z | 15);
						final MuonHeightMap found =
								MuonHooks.findTerrain(villageBoundingBox, chunkBoundingBox);

						currentBlock.setPos(x, y, z);

						final int height = found == null ? -1 : getHeight(found, x, z);

						if (height != -1) {
							y = Math.max(y, height);
						} else {
							BlockPos topBlock =
									MuonUtils.getTopSolidOrLiquidBlock(world, currentBlock);
							Block block = world.getBlockState(topBlock).getBlock();

							while (block == Blocks.AIR) {
								topBlock = topBlock.down();

								//This is our patch
								if (topBlock.getY() < 0) {
									break;
								}

								block = world.getBlockState(topBlock).getBlock();
							}

							y = Math.max(y, topBlock.getY());
						}

						i += y;
						j++;

						if (minY == -1 || y < minY) {
							minY = y;
						}
					}
				}
			}

			if (j > 0) {
				if (i / j >= minY + 1) {
					return minY + 1;
				}

				return i / j + 1;
			}

			return -1;
		}

		private static int getHeight(MuonHeightMap heightMap, int x, int z) {
			try {
				return (int) GET_HEIGHT.invoke(heightMap, x, z);
			} catch (IllegalAccessException | InvocationTargetException ignored) {}

			return -1;
		}
	}

	@Override
	public boolean apply(ClassNode classNode) {
		final InsnList instructions =
				findInstructions(classNode, "getAverageGroundLevel", "func_74889_b");

		for (int i = 0; i < instructions.size(); i++) {
			final AbstractInsnNode instruction = instructions.get(i);

			if (instruction.getOpcode() == Opcodes.INVOKESTATIC) {
				final MethodInsnNode method = (MethodInsnNode) instruction;

				if ("gd/izno/mc/muon/MuonHooks".equals(method.owner)) {
					method.owner = getName(VillagePatch.class) + "$Hook";
					break;
				}
			}
		}

		return true;
	}
}
