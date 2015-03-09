package mips.simulator.context;

import java.util.HashMap;


public class Memory {
	private static Memory virtualMemory;
	private static HashMap<Integer, Byte> memoryMap;
	private Memory() {
		memoryMap = new HashMap<>();
	}

	public static void initialize() {
		virtualMemory = new Memory();
	}

	public static Memory getInstance() {
		if (virtualMemory == null) {
			initialize();
		} 
		return virtualMemory;
	}
	
	public byte getValue(int address) {
		return memoryMap.get(address);
	}
	
	public void setValue(int address, byte val) {
		memoryMap.put(address, val);
	}
	
}
