package com.therandomlabs.randompatches.integration.patch;

import com.therandomlabs.randompatches.core.Patch;
import com.therandomlabs.randompatches.integration.RPIStaticConfig;
import net.quetzi.morpheus.helpers.References;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MorpheusEventHandlerPatch extends Patch {
	@Override
	public void apply(ClassNode node) {
		final MethodNode method = findMethod(node, "bedClicked");
		LdcInsnNode messageString = null;

		for(int i = 0; i < method.instructions.size(); i++) {
			final AbstractInsnNode instruction = method.instructions.get(i);

			if(instruction.getOpcode() == Opcodes.LDC) {
				messageString = (LdcInsnNode) instruction;

				if(References.SPAWN_SET.equals(messageString.cst)) {
					messageString.cst = RPIStaticConfig.morpheusSetSpawnMessage;
					break;
				}

				messageString = null;
			}
		}

		final AbstractInsnNode createTextComponent = messageString.getNext();
		final MethodInsnNode sendMessage = (MethodInsnNode) createTextComponent.getNext();

		method.instructions.insert(createTextComponent, new InsnNode(Opcodes.ICONST_1));

		sendMessage.name = getName("sendStatusMessage", "func_146105_b");
		sendMessage.desc = "(Lnet/minecraft/util/text/ITextComponent;Z)V";
	}
}