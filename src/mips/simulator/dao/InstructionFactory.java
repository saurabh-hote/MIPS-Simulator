package mips.simulator.dao;

import mips.simulator.misc.InstructionType;

public class InstructionFactory {

	public static Instruction getInstructionObj(InstructionType type, byte[] instructionBytes) {
		switch (type) {
		case R_TYPE:
			return new RTypeInstruction(instructionBytes);
		case I_TYPE:
			return new ITypeInstruction(instructionBytes);
		case J_TYPE:
			return new JTypeInstruction(instructionBytes);
		default:
			return null;
		}
	}
}
