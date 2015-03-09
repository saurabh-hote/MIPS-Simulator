package mips.simulator.context;

import java.util.ArrayDeque;
import java.util.Deque;

import mips.simulator.dao.Instruction;

public class AppContext {
	private static AppContext appContext;
	public static final int INSRUCTION_LENGTH = 4; // It is a simulator for 32 bit instruction
	public static final int OPCODE_LENGTH = 6; // in bits
	public static final int NO_OF_PIPELINE_STAGES = 6; // in bits
	public static final int NO_OF_REGISTERS = 32;
	public static final int VIRTUAL_MEMORY_ADDRESS_BITS = 21; // in bits

	private static int totalCyclesWithDataForwarding = 0; // It is a simulator for 32 bit instruction
	private static int totalCyclesWithoutDataForwarding = 0; // It is a simulator for 32 bit instruction
	public static int PROGRAM_COUNTER = 0;
	
	//data structures to calculate the stalls without forwarding
	private static int MAX_BUFFERED_INSTRUCTIONS = 3;
	public static Deque<Instruction> bufferedInstructions = new ArrayDeque<>(MAX_BUFFERED_INSTRUCTIONS);
	
	private AppContext() {
	}
	 
	public static AppContext getInstance() {
		if (appContext == null) {
			appContext = new AppContext();
		}
		return appContext;
	}
		
	public void incrementCycleCount(int count) {
		totalCyclesWithDataForwarding += count;
	}
	
	public void incrementCycleCountWF(int count) {
		totalCyclesWithoutDataForwarding += count;
	}
	
	public int getCycleCount() {
		return totalCyclesWithDataForwarding;
	}
	
	public int getCycleCountWF() {
		return totalCyclesWithoutDataForwarding;
	}
}
