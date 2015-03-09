package mips.simulator.utils;

import mips.simulator.context.AppContext;
import mips.simulator.dao.ITypeInstruction;
import mips.simulator.dao.Instruction;
import mips.simulator.dao.JTypeInstruction;
import mips.simulator.dao.RTypeInstruction;

public class InstructionExecutor {

	public static boolean execute(Instruction instruction) {
		InstructionProcessor processor = InstructionProcessor.getInstance();
		switch (instruction.getOpCode()) {
		case 0: // R-Type instructions

			switch (((RTypeInstruction) instruction).getFunction()) {
			case 0:
				if (((RTypeInstruction) instruction).getShift() == 0) {
					processor.executeNOOP((RTypeInstruction) instruction);
				} else {
					processor.executeSLL((RTypeInstruction) instruction);
				}
				break;
			case 2:
				processor.executeSRL((RTypeInstruction) instruction);
				break;
			case 3:
				processor.executeSRA((RTypeInstruction) instruction);
				break;
			case 4:
				processor.executeSLLV((RTypeInstruction) instruction);
				break;
			case 6:
				processor.executeSRLV((RTypeInstruction) instruction);
				break;
			case 8:
				processor.executeJR((RTypeInstruction) instruction);
				break;
			case 12:
				processor.executeSYSCALL((RTypeInstruction) instruction);
				break;
			case 16:
				processor.executeMFHI((RTypeInstruction) instruction);
				break;
			case 18:
				processor.executeMFLO((RTypeInstruction) instruction);
				break;
			case 24:
				processor.executeMULT((RTypeInstruction) instruction);
				break;
			case 25:
				processor.executeMULTU((RTypeInstruction) instruction);
				break;
			case 32:
				processor.executeADD((RTypeInstruction) instruction);
				break;
			case 33:
				processor.executeADDU((RTypeInstruction) instruction);
				break;
			case 34:
				processor.executeSUB((RTypeInstruction) instruction);
				break;
			case 35:
				processor.executeSUBU((RTypeInstruction) instruction);
				break;
			case 36:
				processor.executeAND((RTypeInstruction) instruction);
				break;
			case 37:
				processor.executeOR((RTypeInstruction) instruction);
				break;
			case 38:
				processor.executeXOR((RTypeInstruction) instruction);
				break;
			case 41:
				processor.executeSLT((RTypeInstruction) instruction);
				break;
			case 43:
				processor.executeSLTU((RTypeInstruction) instruction);
				break;
			case 50:
				processor.executeDIV((RTypeInstruction) instruction);
				break;
			case 51:
				processor.executeDIVU((RTypeInstruction) instruction);
				break;

			}
			break;
		case 1:
			switch (((ITypeInstruction) (instruction)).getRegTarget()) {
			case 0:
				return processor.executeBLTZ((ITypeInstruction) instruction);
			case 1:
				return processor.executeBGEZ((ITypeInstruction) instruction);
			case 16:
				return processor.executeBLTZAL((ITypeInstruction) instruction);
			case 17:
				return processor.executeBGEZAL((ITypeInstruction) instruction);
			}
			break;
		case 2:
			return processor.executeJ((JTypeInstruction) instruction);
		case 3:
			return processor.executeJAL((JTypeInstruction) instruction);
		case 4:
			return processor.executeBEQ((ITypeInstruction) instruction);
		case 5:
			return processor.executeBNE((ITypeInstruction) instruction);
		case 6:
			return processor.executeBLEZ((ITypeInstruction) instruction);
		case 7:
			return processor.executeBGTZ((ITypeInstruction) instruction);
		case 8:
			processor.executeADDI((ITypeInstruction) instruction);
			break;
		case 9:
			processor.executeADDIU((ITypeInstruction) instruction);
			break;
		case 10:
			processor.executeSLTI((ITypeInstruction) instruction);
			break;
		case 11:
			processor.executeSLTIU((ITypeInstruction) instruction);
			break;
		case 12:
			processor.executeANDI((ITypeInstruction) instruction);
			break;
		case 13:
			processor.executeORI((ITypeInstruction) instruction);
			break;
		case 14:
			processor.executeXORI((ITypeInstruction) instruction);
			break;
		case 15:
			processor.executeLUI((ITypeInstruction) instruction);
			break;
		case 32:
			processor.executeLB((ITypeInstruction) instruction);
			break;
		case 35:
			processor.executeLW((ITypeInstruction) instruction);
			break;
		case 40:
			processor.executeSB((ITypeInstruction) instruction);
			break;
		case 43:
			processor.executeSW((ITypeInstruction) instruction);
			break;
		default:
			AppContext.PROGRAM_COUNTER++;
		}
		return false;
	}

}
