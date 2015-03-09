package mips.simulator.dao;

import mips.simulator.context.AppContext;

public class Instruction {
	private int opCode;
	
	public Instruction(byte[] instructionBytes) {
		if (instructionBytes.length > AppContext.INSRUCTION_LENGTH) {
			this.opCode = -1;
			System.out.println("Instruction with invalid length found. Skipping");
		} else {
			this.opCode = (instructionBytes[AppContext.INSRUCTION_LENGTH -1] & 0xFC) >> 2;
		}
	}	
		
	public int getOpCode() {
		return opCode;
	}
}
