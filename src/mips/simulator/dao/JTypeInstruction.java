package mips.simulator.dao;


public class JTypeInstruction extends Instruction {
	private int immediateAddress;
		
	public JTypeInstruction(byte[] instructionBytes) {
		super(instructionBytes);
		immediateAddress = ((instructionBytes[3] & 0x02) << 24) | ((instructionBytes[2] & 0xFF) << 16)
				| ((instructionBytes[1] & 0xFF) << 8) | (instructionBytes[0] & 0xFF);
	}	
	
	public int getImmediateAddress() {
		return immediateAddress;
	}
}