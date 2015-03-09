package mips.simulator.misc;

public enum InstructionStage {
	FETCH(1), DECODE(1), READ_REG(1), EXECUTE(1), MEMORY(1), WRITE_BACK(1);

	private int cyclesForCompletion;
	
	private InstructionStage(int cyclesForCompletion) {
		this.cyclesForCompletion = cyclesForCompletion;
	}
	
	public int getCyclesForCompletion() {
		return cyclesForCompletion; 
	}
	
	public static int getTotalCyclesForInstrction() {
		int totalCycles = 0;
		for (InstructionStage stage : InstructionStage.values()) {
			totalCycles += stage.getCyclesForCompletion();
		}
		return totalCycles;
	}
	
	public static int getStallCyclesForBranchInstrction() {
		int stallCycles = 0;
		for (InstructionStage stage : InstructionStage.values()) {
			if (stage.equals(FETCH) || stage.equals(DECODE)) {
				continue;
			}
			stallCycles += stage.getCyclesForCompletion();
		}
		return stallCycles;
	}
	
	public static int getStallCyclesForRAWHazard() {
		//add fetch and execute instruction stalls
		return EXECUTE.cyclesForCompletion;
	}
	
	public static int getStallCyclesForInstructionRelativeIndex(int index) {
		switch (index) {
		case 1:
			return 3;
		case 2: 
			return 2;
		case 3:
			return 1;
		default:
			return 0;
		}
	}

}
