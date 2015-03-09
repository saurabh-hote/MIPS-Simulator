package mips.simulator.dao;

import java.util.Vector;

import mips.simulator.context.AppContext;

public class InstructionQueue {
	private InstructionQueue() {
	}

	private static InstructionQueue instructionQueue;

	private static Vector<Instruction> instructionList = new Vector<Instruction>();

	public static InstructionQueue getInstance() {
		if (instructionQueue == null) {
			instructionQueue = new InstructionQueue();
		}
		return instructionQueue;
	}

	public void addToQueue(Instruction instruction) {
		instructionList.add(instruction);		
	}

	public Instruction getFromQueue() {
		if (AppContext.PROGRAM_COUNTER < instructionList.size()) {
			return instructionList.get(AppContext.PROGRAM_COUNTER);
		} else {
			return null;
		}
	}
}
