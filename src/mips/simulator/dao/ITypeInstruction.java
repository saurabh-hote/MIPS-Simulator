package mips.simulator.dao;

public class ITypeInstruction extends Instruction {
	private byte regTarget;
	private byte regSource;
	private int immediateVal;

	public ITypeInstruction(byte[] instructionBytes) {
		super(instructionBytes);
		regSource = (byte) (((instructionBytes[3] & 0x03) << 3 | (instructionBytes[2] & 0xE0) >> 5) & 0x1F);
		regTarget = (byte) (instructionBytes[2] & 0x1F);
		immediateVal = (instructionBytes[1] << 8) | (instructionBytes[0] & 0xFF);
 	}	
	
	public int getRegTarget() {
		return regTarget;
	}

	public int getRegSource() {
		return regSource;
	}

	public int getImmediateVal() {
		return immediateVal;
	}


}
