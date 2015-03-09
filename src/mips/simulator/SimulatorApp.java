package mips.simulator;
/*
 * This is a simulator program for 6 stage MIPS architecture.
 * 
 * The simulator program takes the MIPS complied binary file as input and gives the CPI required for 
 * execution as output.
 * 
 * The program takes care of the following scenarios - 
 * 1. Stall cycles caused due to the RAW hazard because of register address conflicts
 * 2. Stall cycles caused due to jump/branch instructions
 * 
 * No branch prediction is implemented so far. Thus, 3 stall cycles are added for every branch taken
 * Operand Forwarding is assumed causing only single stall cycles in case of RAW Hazard
 * 
 * */

import java.io.File;
import mips.simulator.context.Memory;
import mips.simulator.context.RegisterFile;
import mips.simulator.parser.InstructionParser;

public class SimulatorApp {

	public static void main (String args[]) {
		//Create application object 
		if (args.length < 1) {
			System.out.println("Usage: java Simulator <name_of_exe>");
			System.exit(0);
		}
		
		//perform Register file and memory loading
		RegisterFile.initialize();
		Memory.initialize();
		
		File exeFile = new File(args[0]);
		if (exeFile.exists()) {
		
			//Launch a parsing thread which will add every parsed instruction to the queue
			InstructionParser parser = new InstructionParser(exeFile);
			if (parser.validate()) {
				parser.parseInstructions();
				
				MIPSProcessor simulator = new MIPSProcessor();
				simulator.startSimulation();
			} else {
				System.out.println("File " + args[1] + " is not a valid exe.");
			}
	
		} else {
			System.out.println("File " + args[1] + " not found. Terminating Simulation Process.");
			System.exit(0);
		}
	}
}
