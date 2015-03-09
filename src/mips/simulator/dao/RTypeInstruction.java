package mips.simulator.dao;


public class RTypeInstruction extends Instruction {
	private byte regTarget;
	private byte regSource1;
	private byte regSource2;
	private byte shift;
	private byte function;
	
	public RTypeInstruction(byte[] instructionBytes) {
		super(instructionBytes);
		
		//assign values to different fields
		regSource1 = (byte) ((instructionBytes[3] & 0x03) << 3 | (instructionBytes[2] & 0xE0) >> 5);
		regSource2 = (byte) (instructionBytes[2] & 0x1F);
		regTarget = (byte) ((instructionBytes[1] & 0xF8) >> 3);
		shift = (byte) ((instructionBytes[1] & 0x07) << 2 | (instructionBytes[0] & 0xC0) >> 6);
		function = (byte) (instructionBytes[0] & 0x3F);
	}	

	public int getRegTarget() {
		return regTarget;
	}

	public int getRegSource1() {
		return regSource1;
	}

	public int getRegSource2() {
		return regSource2;
	}

	public int getShift() {
		return shift;
	}

	public int getFunction() {
		return function;
	}

}
