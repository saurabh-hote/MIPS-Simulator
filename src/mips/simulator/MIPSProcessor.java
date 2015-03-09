package mips.simulator;

import mips.simulator.context.AppContext;
import mips.simulator.dao.Instruction;
import mips.simulator.dao.InstructionQueue;
import mips.simulator.misc.InstructionStage;
import mips.simulator.utils.InstructionExecutor;
import mips.simulator.utils.Validator;

/*
 This class is the main class for running simulation
  */
public class MIPSProcessor {
	
	public void startSimulation() {
		int insructionExecutionCount = 0;
		InstructionQueue instructionQueue = InstructionQueue.getInstance();
		Instruction instruction = null;
		do {
			instruction = instructionQueue.getFromQueue();
			if (instruction == null) {
				//TODO:  calculate the total cycles taken
				System.out.println("Execution completed!");
				System.out.println("Total instructions executed  = " + insructionExecutionCount);
				System.out.println("Total cycles required for execution with forwarding = " + AppContext.getInstance().getCycleCount());
				System.out.println("Total cycles required for execution without forwarding = " + AppContext.getInstance().getCycleCountWF());
				System.out.println("CPI with forwarding = " + (double)AppContext.getInstance().getCycleCount()/ insructionExecutionCount);
				System.out.println("CPI without forwarding = " + (double)AppContext.getInstance().getCycleCountWF()/ insructionExecutionCount);
				break;
			} else {

				//execute the actual instruction
				boolean branchTaken = InstructionExecutor.execute(instruction);
				insructionExecutionCount++;
				System.out.println("Instruction no " + insructionExecutionCount + " executed successfully!");

				//now adjust stalls accordingly
				//normal case, add stall for fetch stage
				AppContext.getInstance().incrementCycleCount(InstructionStage.FETCH.getCyclesForCompletion());
				AppContext.getInstance().incrementCycleCountWF(InstructionStage.FETCH.getCyclesForCompletion());
				
				int instructionRelativeIndex = Validator.checkForRAWHazard(instruction);
				if(instructionRelativeIndex > 0) {
					//case with forwarding
					if (instructionRelativeIndex == 1) {
						AppContext.getInstance().incrementCycleCount(InstructionStage.getStallCyclesForRAWHazard());
					}
					
					AppContext.getInstance().incrementCycleCountWF(InstructionStage.getStallCyclesForInstructionRelativeIndex(
							instructionRelativeIndex));
				} 
				
				//check for branch/jump instruction
				if (branchTaken) {
					AppContext.getInstance().incrementCycleCount(InstructionStage.getStallCyclesForBranchInstrction());
					AppContext.getInstance().incrementCycleCountWF(InstructionStage.getStallCyclesForBranchInstrction());
				} 
			}
			AppContext.bufferedInstructions.add(instruction);
		} while (true);
		
	}

}
