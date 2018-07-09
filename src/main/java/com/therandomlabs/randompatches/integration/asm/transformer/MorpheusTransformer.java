package com.therandomlabs.randompatches.integration.asm.transformer;

import com.therandomlabs.randompatches.asm.Transformer;
import net.quetzi.morpheus.helpers.References;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class MorpheusTransformer extends Transformer {
	public static final String SEND_STATUS_MESSAGE = getName("sendStatusMessage", "a");
	public static final String TEXT_COMPONENT =
			getName("net/minecraft/util/text/ITextComponent", "hh");

	@Override
	public void transform(ClassNode node) {
		final MethodNode method = findMethod(node, "bedClicked");
		LdcInsnNode messageString = null;

		for(int i = 0; i < method.instructions.size(); i++) {
			final AbstractInsnNode instruction = method.instructions.get(i);

			if(instruction.getOpcode() == Opcodes.LDC) {
				messageString = (LdcInsnNode) instruction;

				if(References.SPAWN_SET.equals(messageString.cst)) {
					break;
				}

				messageString = null;
			}
		}

		final AbstractInsnNode createTextComponent = messageString.getNext();
		final MethodInsnNode sendMessage = (MethodInsnNode) createTextComponent.getNext();

		method.instructions.insert(createTextComponent, new InsnNode(Opcodes.ICONST_1));

		sendMessage.name = SEND_STATUS_MESSAGE;
		sendMessage.desc = "(L" + TEXT_COMPONENT + ";Z)V";
	}
}
