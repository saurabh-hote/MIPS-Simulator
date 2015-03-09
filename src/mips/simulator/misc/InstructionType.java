package mips.simulator.misc;

public enum InstructionType {
	R_TYPE,  I_TYPE, J_TYPE, C_TYPE, INVALID;
	
	public static InstructionType getInstructionType(int opcode) {
		if (opcode == 0) {
			return InstructionType.R_TYPE;
		} else if (opcode == 2 || opcode == 3) {
			return InstructionType.J_TYPE;
		} else 	if (opcode == 1 || opcode > 3){
			return InstructionType.I_TYPE;
		} else {
			return InstructionType.C_TYPE;
		}
	}
}