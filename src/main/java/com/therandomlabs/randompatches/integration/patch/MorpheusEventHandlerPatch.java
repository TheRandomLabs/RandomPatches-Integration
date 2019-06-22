package com.therandomlabs.randompatches.integration.patch;

import com.therandomlabs.randompatches.core.Patch;
import com.therandomlabs.randompatches.integration.config.RPIConfig;
import net.quetzi.morpheus.helpers.References;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;

public final class MorpheusEventHandlerPatch extends Patch {
	@Override
	public boolean apply(ClassNode node) {
		final InsnList instructions = findInstructions(node, "bedClicked");
		LdcInsnNode messageString = null;

		for(int i = 0; i < instructions.size(); i++) {
			final AbstractInsnNode instruction = instructions.get(i);

			if(instruction.getOpcode() == Opcodes.LDC) {
				messageString = (LdcInsnNode) instruction;

				if(References.SPAWN_SET.equals(messageString.cst)) {
					messageString.cst = RPIConfig.Misc.morpheusSetSpawnMessage;
					break;
				}

				messageString = null;
			}
		}

		final AbstractInsnNode createTextComponent = messageString.getNext();
		final MethodInsnNode sendMessage = (MethodInsnNode) createTextComponent.getNext();

		//Load true
		instructions.insert(createTextComponent, new InsnNode(Opcodes.ICONST_1));

		//Call EntityPlayer#sendStatusMessage
		sendMessage.name = getName("sendStatusMessage", "func_146105_b");
		sendMessage.desc = "(Lnet/minecraft/util/text/ITextComponent;Z)V";

		return true;
	}
}
