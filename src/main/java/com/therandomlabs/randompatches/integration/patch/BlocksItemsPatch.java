package com.therandomlabs.randompatches.integration.patch;

import com.therandomlabs.randompatches.core.Patch;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

//https://github.com/mezz/JustEnoughItems/issues/1189#issuecomment-498078956
public final class BlocksItemsPatch extends Patch {
	@Override
	public boolean apply(ClassNode classNode) {
		for(FieldNode field : classNode.fields) {
			if((field.access & 0x1F) == (Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC)) {
				field.access = Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL;
			}
		}

		return true;
	}
}
