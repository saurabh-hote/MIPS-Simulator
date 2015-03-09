package mips.simulator.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import mips.simulator.context.AppContext;
import mips.simulator.dao.Instruction;
import mips.simulator.dao.InstructionFactory;
import mips.simulator.dao.InstructionQueue;
import mips.simulator.misc.InstructionType;

public class InstructionParser {
	private File exeFile;

	public InstructionParser(File exeFile) {
		this.exeFile = exeFile;
	}

	public boolean validate() {
		if (exeFile.getTotalSpace() % (AppContext.INSRUCTION_LENGTH * 8) != 0) {
			return false;
		}
		return true;
	}

	public void parseInstructions() {
		//start parsing the instructions and add them into the 
		//global queue
		try {
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(exeFile));
			//int offset = 0;
			byte[] tempData = new byte[AppContext.INSRUCTION_LENGTH];
			byte[] readData = new byte[AppContext.INSRUCTION_LENGTH];
			while (bufferedInputStream.read(tempData) != -1) {
				//converting from big-endian to little-endian style
				int reverseIndex = AppContext.INSRUCTION_LENGTH - 1;
				for (byte dataByte : tempData) {
					readData[reverseIndex] = dataByte;
					reverseIndex--;
				}
				
				//offset += AppContext.INSRUCTION_LENGTH;
				//create a new Instruction object and add to queue
				InstructionType typeOfInstr = InstructionType.getInstructionType((readData[AppContext.INSRUCTION_LENGTH -1] & 0xFC) >> 2);
				Instruction instruction = InstructionFactory.getInstructionObj(typeOfInstr, readData);
				if (instruction != null) {
					InstructionQueue.getInstance().addToQueue(instruction);
					System.out.println("Added Instruction of " + typeOfInstr);
				}
			}

			bufferedInputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Exe parsing completed!");
	}
}
