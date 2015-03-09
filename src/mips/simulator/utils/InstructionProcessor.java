package mips.simulator.utils;

import mips.simulator.context.AppContext;
import mips.simulator.context.Memory;
import mips.simulator.context.RegisterFile;
import mips.simulator.dao.ITypeInstruction;
import mips.simulator.dao.JTypeInstruction;
import mips.simulator.dao.RTypeInstruction;

public class InstructionProcessor {

	private static InstructionProcessor instructionFile;
	private InstructionProcessor() {
	}
	
	public static InstructionProcessor getInstance() {
		if (instructionFile == null) {
			instructionFile = new InstructionProcessor();
		}
		return instructionFile;
	}
	
	public void executeADD(RTypeInstruction instruction) { 

		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource1()) + 
				regFile.getRegisterValue(instruction.getRegSource2());

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;

		/*
	� Add (with overflow)
	Description:
	Adds two registers and stores the result in a register
	Operation:
	$d = $s + $t; advance_pc (4);
	Syntax:
	add $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d000 0010 0000
		 */ 
	}

	public void executeADDI(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource()) + 
				instruction.getImmediateVal();

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;

		/*
	-- Add immediate (with overflow)

	Description:
	Adds a register and a sign-extended immediate value and stores the result in a register
	Operation:
	$t = $s + imm; advance_pc (4);
	Syntax:
	addi $t, $s, imm
	Encoding:
	0010 00ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeADDIU(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource()) & 0xFFFFFFFF +
				instruction.getImmediateVal();
		regFile.setRegisterValue(instruction.getRegTarget(), result);
		AppContext.PROGRAM_COUNTER++;
		/* -- Add immediate unsigned (no overflow)

	Description:
	Adds a register and a sign-extended immediate value and stores the result in a register
	Operation:
	$t = $s + imm; advance_pc (4);
	Syntax:
	addiu $t, $s, imm
	Encoding:
	0010 01ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeADDU(RTypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource1())  & 0xFFFFFFFF + 
				regFile.getRegisterValue(instruction.getRegSource2()) & 0xFFFFFFFF;

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/* -- Add unsigned (no overflow)

	Description:
	Adds two registers and stores the result in a register
	Operation:
	$d = $s + $t; advance_pc (4);
	Syntax:
	addu $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d000 0010 0001
		 */ 
	}

	public void executeAND(RTypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource1()) &
				regFile.getRegisterValue(instruction.getRegSource2());

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/* -- Bitwise and

	Description:
	Bitwise ands two registers and stores the result in a register
	Operation:
	$d = $s & $t; advance_pc (4);
	Syntax:
	and $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d000 0010 0100
		 */ 
	}

	public void executeANDI(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource()) & 
				instruction.getImmediateVal();

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/* -- Bitwise and immediate

	Description:
	Bitwise ands a register and an immediate value and stores the result in a register
	Operation:
	$t = $s & imm; advance_pc (4);
	Syntax:
	andi $t, $s, imm
	Encoding:
	0011 00ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public boolean executeBEQ(ITypeInstruction instruction) {
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegSource())  == 
				regFile.getRegisterValue(instruction.getRegTarget())) {
			AppContext.PROGRAM_COUNTER += instruction.getImmediateVal() & 0xFFFF;
			return true;
		} else {
			AppContext.PROGRAM_COUNTER++;
			return false;
		}
		
		/* -- Branch on equal

	Description:
	Branches if the two registers are equal
	Operation:
	if $s == $t advance_pc (offset << 2)); else advance_pc (4);
	Syntax:
	beq $s, $t, offset
	Encoding:
	0001 00ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public boolean executeBGEZ(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegTarget()) >= 0) {
			AppContext.PROGRAM_COUNTER += instruction.getImmediateVal() & 0xFFFF;
			return true;
		} else {
			AppContext.PROGRAM_COUNTER++;
			return false;
		}
		/* -- Branch on greater than or equal to zero

	Description:
	Branches if the register is greater than or equal to zero
	Operation:
	if $s >= 0 advance_pc (offset << 2)); else advance_pc (4);
	Syntax:
	bgez $s, offset
	Encoding:
	0000 01ss sss0 0001 iiii iiii iiii iiii
		 */ 
	}

	public boolean executeBGEZAL(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegTarget()) >= 0) {
			regFile.setRegisterValue(31, AppContext.PROGRAM_COUNTER + 1);
			AppContext.PROGRAM_COUNTER += instruction.getImmediateVal() & 0xFFFF;
			return true;
		} else {
			AppContext.PROGRAM_COUNTER++;
			return false;
		}
		/* -- Branch on greater than or equal to zero and link

	Description:
	Branches if the register is greater than or equal to zero and saves the return address in $31
	Operation:
	if $s >= 0 $31 = PC + 8 (or nPC + 4); advance_pc (offset << 2)); else advance_pc (4);
	Syntax:
	bgezal $s, offset
	Encoding:
	0000 01ss sss1 0001 iiii iiii iiii iiii
		 */ 
	}

	public boolean executeBGTZ(ITypeInstruction instruction) {
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegTarget()) > 0) {
			AppContext.PROGRAM_COUNTER += instruction.getImmediateVal() & 0xFFFF;
			return true;
		} else {
			AppContext.PROGRAM_COUNTER++;
			return false;
		}
		/* -- Branch on greater than zero

	Description:
	Branches if the register is greater than zero
	Operation:
	if $s > 0 advance_pc (offset << 2)); else advance_pc (4);
	Syntax:
	bgtz $s, offset
	Encoding:
	0001 11ss sss0 0000 iiii iiii iiii iiii
		 */ 
	}

	public boolean executeBLEZ(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegTarget()) <= 0) {
			AppContext.PROGRAM_COUNTER += instruction.getImmediateVal() & 0xFFFF;
			return true;
		} else {
			AppContext.PROGRAM_COUNTER++;
			return false;
		}

		/* -- Branch on less than or equal to zero

	Description:
	Branches if the register is less than or equal to zero
	Operation:
	if $s <= 0 advance_pc (offset << 2)); else advance_pc (4);
	Syntax:
	blez $s, offset
	Encoding:
	0001 10ss sss0 0000 iiii iiii iiii iiii
		 */ 
	}

	public boolean executeBLTZ(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegTarget()) < 0) {
			AppContext.PROGRAM_COUNTER += instruction.getImmediateVal() & 0xFFFF;
			return true;
		} else {
			AppContext.PROGRAM_COUNTER++;
			return false;
		}

		/* -- Branch on less than zero

	Description:
	Branches if the register is less than zero
	Operation:
	if $s < 0 advance_pc (offset << 2)); else advance_pc (4);
	Syntax:
	bltz $s, offset
	Encoding:
	0000 01ss sss0 0000 iiii iiii iiii iiii
		 */ 
	}

	public boolean executeBLTZAL(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegTarget()) < 0) {
			regFile.setRegisterValue(31, AppContext.PROGRAM_COUNTER + 1);
			AppContext.PROGRAM_COUNTER += instruction.getImmediateVal() & 0xFFFF;
			return true;
		} else {
			AppContext.PROGRAM_COUNTER++;
			return false;
		}
		/* -- Branch on less than zero and link

	Description:
	Branches if the register is less than zero and saves the return address in $31
	Operation:
	if $s < 0 $31 = PC + 8 (or nPC + 4); advance_pc (offset << 2)); else advance_pc (4);
	Syntax:
	bltzal $s, offset
	Encoding:
	0000 01ss sss1 0000 iiii iiii iiii iiii
		 */ 
	}

	public boolean executeBNE(ITypeInstruction instruction) {
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegSource()) != 
				regFile.getRegisterValue(instruction.getRegTarget())) {
			AppContext.PROGRAM_COUNTER += instruction.getImmediateVal() & 0xFFFF;
			return true;
		} else {
			AppContext.PROGRAM_COUNTER++;
			return false;
		}
		
		/* -- Branch on not equal

	Description:
	Branches if the two registers are not equal
	Operation:
	if $s != $t advance_pc (offset << 2)); else advance_pc (4);
	Syntax:
	bne $s, $t, offset
	Encoding:
	0001 01ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeDIV(RTypeInstruction instruction) {
		
		RegisterFile regFile = RegisterFile.getInstance();
		
		int result = regFile.getRegisterValue(instruction.getRegSource1()) /
				regFile.getRegisterValue(instruction.getRegSource2());

		regFile.setLO(result); 
		result = regFile.getRegisterValue(instruction.getRegSource1()) %
				regFile.getRegisterValue(instruction.getRegSource2());
		regFile.setHI(result); 
		
		AppContext.PROGRAM_COUNTER++;
		/* -- Divide

	Description:
	Divides $s by $t and stores the quotient in $LO and the remainder in $HI
	Operation:
	$LO = $s / $t; $HI = $s % $t; advance_pc (4);
	Syntax:
	div $s, $t
	Encoding:
	0000 00ss ssst tttt 0000 0000 0001 1010
		 */ 
	}

	public void executeDIVU(RTypeInstruction instruction) { 
		
		RegisterFile regFile = RegisterFile.getInstance();
		
		int result = (regFile.getRegisterValue(instruction.getRegSource1()))/
				(regFile.getRegisterValue(instruction.getRegSource2()));

		regFile.setLO(result); 
		result = (regFile.getRegisterValue(instruction.getRegSource1())) %
				(regFile.getRegisterValue(instruction.getRegSource2()));
		regFile.setHI(result); 
		
		AppContext.PROGRAM_COUNTER++;

		/* -- Divide unsigned

	Description:
	Divides $s by $t and stores the quotient in $LO and the remainder in $HI
	Operation:
	$LO = $s / $t; $HI = $s % $t; advance_pc (4);
	Syntax:
	divu $s, $t
	Encoding:
	0000 00ss ssst tttt 0000 0000 0001 1011
		 */ 
	}

	public boolean executeJ(JTypeInstruction instruction) {
		AppContext.PROGRAM_COUNTER = (AppContext.PROGRAM_COUNTER + 1) & 0xF0000000 
				| instruction.getImmediateAddress() << 2;
		return true;
		
		/* -- Jump

	Description:
	Jumps to the calculated address
	Operation:
	PC = nPC; nPC = (PC & 0xf0000000) | (target << 2);
	Syntax:
	j target
	Encoding:
	0000 10ii iiii iiii iiii iiii iiii iiii
		 */ 
	}

	public boolean executeJAL(JTypeInstruction instruction) { 
		RegisterFile regfile = RegisterFile.getInstance();
		regfile.setRegisterValue(31, AppContext.PROGRAM_COUNTER + 1);
		AppContext.PROGRAM_COUNTER = (AppContext.PROGRAM_COUNTER + 1) & 0xF0000000 
				| instruction.getImmediateAddress() & 0xFFFF;
		return true;
		
		/* -- Jump and link

	Description:
	Jumps to the calculated address and stores the return address in $31
	Operation:
	$31 = PC + 8 (or nPC + 4); PC = nPC; nPC = (PC & 0xf0000000) | (target << 2);
	Syntax:
	jal target
	Encoding:
	0000 11ii iiii iiii iiii iiii iiii iiii
		 */ 
	}

	public void executeJR(RTypeInstruction instruction) { 
		RegisterFile regfile = RegisterFile.getInstance();
		AppContext.PROGRAM_COUNTER = regfile.getRegisterValue(instruction.getRegTarget());

		/* -- Jump register

	Description:
	Jump to the address contained in register $s
	Operation:
	PC = nPC; nPC = $s;
	Syntax:
	jr $s
	Encoding:
	0000 00ss sss0 0000 0000 0000 0000 1000
		 */ 
	}

	public void executeLB(ITypeInstruction instruction) {
		
		RegisterFile regfile = RegisterFile.getInstance();
		Memory memory=Memory.getInstance();
		byte value = memory.getValue(regfile.getRegisterValue(instruction.getRegSource()
				+instruction.getImmediateVal()));
		regfile.setRegisterValue(instruction.getRegTarget(), value);
		AppContext.PROGRAM_COUNTER++;
		/* -- Load byte

	Description:
	A byte is loaded into a register from the specified address.
	Operation:
	$t = MEM[$s + offset]; advance_pc (4);
	
	Syntax:
	lb $t, offset($s)
	Encoding:
	1000 00ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeLUI(ITypeInstruction instruction) {
		RegisterFile regfile = RegisterFile.getInstance();
		Memory memory=Memory.getInstance();
		
		int value=memory.getValue((instruction.getImmediateVal() << 16));
		
		regfile.setRegisterValue(instruction.getRegTarget(), value);
		AppContext.PROGRAM_COUNTER++;
		/* -- Load upper immediate

	Description:
	The immediate value is shifted left 16 bits and stored in the register. The lower 16 bits are zeroes.
	Operation:
	$t = (imm << 16); advance_pc (4);
	Syntax:
	lui $t, imm
	Encoding:
	0011 11-- ---t tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeLW(ITypeInstruction instruction) { 
		RegisterFile regfile = RegisterFile.getInstance();
		Memory memory=Memory.getInstance();
		int index = regfile.getRegisterValue(instruction.getRegSource())
				+ instruction.getImmediateVal();
		byte valueMSB = memory.getValue(index);
		byte valueLSB = memory.getValue(index + 1);
		int result  = valueMSB << 8;
		result +=  valueLSB;
		regfile.setRegisterValue(instruction.getRegTarget(), result);
		AppContext.PROGRAM_COUNTER++;
		/* -- Load word

	Description:
	A word is loaded into a register from the specified address.
	Operation:
	$t = MEM[$s + offset]; advance_pc (4);
	Syntax:
	lw $t, offset($s)
	Encoding:
	1000 11ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeMFHI(RTypeInstruction instruction) { 
		RegisterFile regfile = RegisterFile.getInstance();
		regfile.setRegisterValue(instruction.getRegTarget(), regfile.getHI());
		AppContext.PROGRAM_COUNTER++;	
		
		/* -- Move from HI

	Description:
	The contents of register HI are moved to the specified register.
	Operation:
	$d = $HI; advance_pc (4);
	Syntax:
	mfhi $d
	Encoding:
	0000 0000 0000 0000 dddd d000 0001 0000
		 */ 
	}

	public void executeMFLO (RTypeInstruction instruction) { 
		RegisterFile regfile = RegisterFile.getInstance();
		regfile.setRegisterValue(instruction.getRegTarget(), regfile.getLO());
		AppContext.PROGRAM_COUNTER++;	

		/*-- Move from LO

	Description:
	The contents of register LO are moved to the specified register.
	Operation:
	$d = $LO; advance_pc (4);
	Syntax:
	mflo $d
	Encoding:
	0000 0000 0000 0000 dddd d000 0001 0010
		 */ 
	}

	public void executeMULT(RTypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		
		int result = regFile.getRegisterValue(instruction.getRegSource1()) *
				regFile.getRegisterValue(instruction.getRegSource2());

		regFile.setLO(result); 
		AppContext.PROGRAM_COUNTER++;
		
		/* -- Multiply

	Description:
	Multiplies $s by $t and stores the result in $LO.
	Operation:
	$LO = $s * $t; advance_pc (4);
	Syntax:
	mult $s, $t
	Encoding:
	0000 00ss ssst tttt 0000 0000 0001 1000
		 */ 
	}

	public void executeMULTU(RTypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		
		int result = regFile.getRegisterValue(instruction.getRegSource1()) & 0xFFFFFFFF *
				regFile.getRegisterValue(instruction.getRegSource2()) & 0xFFFFFFFF;

		regFile.setLO(result); 
		AppContext.PROGRAM_COUNTER++;

		/* -- Multiply unsigned

	Description:
	Multiplies $s by $t and stores the result in $LO.
	Operation:
	$LO = $s * $t; advance_pc (4);
	Syntax:
	multu $s, $t
	Encoding:
	0000 00ss ssst tttt 0000 0000 0001 1001
		 */ 
	}

	public void executeNOOP(RTypeInstruction instruction) {
		AppContext.PROGRAM_COUNTER++;
		/* -- no operation

	Description:
	Performs no operation.
	Operation:
	advance_pc (4);
	Syntax:
	noop
	Encoding:
	0000 0000 0000 0000 0000 0000 0000 0000
		 */ 
	}


	public void executeOR(RTypeInstruction instruction) {
		
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource1()) |
				regFile.getRegisterValue(instruction.getRegSource2());

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/* -- Bitwise orANS

	Description:
	Bitwise logical ors two registers and stores the result in a register
	Operation:
	$d = $s | $t; advance_pc (4);
	Syntax:
	or $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d000 0010 0101
		 */ 
	}

	public void executeORI(ITypeInstruction instruction) {
		
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource()) |
				instruction.getImmediateVal();

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/*-- Bitwise or immediate

	Description:
	Bitwise ors a register and an immediate value and stores the result in a register
	Operation:
	$t = $s | imm; advance_pc (4);
	Syntax:
	ori $t, $s, imm
	Encoding:
	0011 01ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeSB(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		Memory memory = Memory.getInstance();
		
		byte value = (byte) (regFile.getRegisterValue(instruction.getRegTarget()) & 0xFF) ;
		memory.setValue( regFile.getRegisterValue(instruction.getRegSource())
				+instruction.getImmediateVal(), value);
		AppContext.PROGRAM_COUNTER++;	
/*
	Description:
	The least significant byte of $t is stored at the specified address.
	Operation:
	MEM[$s + offset] = (0xff & $t); advance_pc (4);
	Syntax:
	sb $t, offset($s)
	Encoding:
	1010 00ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeSLL(RTypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		int value = regFile.getRegisterValue(instruction.getRegSource2()) 
				<< instruction.getShift() ;
		regFile.setRegisterValue(regFile.getRegisterValue(instruction.getRegTarget()), value);
		AppContext.PROGRAM_COUNTER++;	
		/* -- Shift left logical

	Description:
	Shifts a register value left by the shift amount listed in the instruction and places the result in a third register. Zeroes are shifted in.
	Operation:
	$d = $t << h; advance_pc (4);
	Syntax:
	sll $d, $t, h
	Encoding:
	0000 00ss ssst tttt dddd dhhh hh00 0000
		 */ 
	}

	public void executeSLLV(RTypeInstruction instruction) {
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource2()) <<
				regFile.getRegisterValue(instruction.getRegSource1());

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/* -- Shift left logical variable

	Description:
	Shifts a register value left by the value in a second register and places the result in a third register. Zeroes are shifted in.
	Operation:
	$d = $t << $s; advance_pc (4);
	Syntax:
	sllv $d, $t, $s
	Encoding:
	0000 00ss ssst tttt dddd d--- --00 0100
		 */ 
	}

	public void executeSLT(RTypeInstruction instruction) { 
		//incorrect
		RegisterFile regFile = RegisterFile.getInstance();
		if (regFile.getRegisterValue(instruction.getRegSource1()) <
				regFile.getRegisterValue(instruction.getRegSource2())) {
			regFile.setRegisterValue(instruction.getRegTarget(), 1);
		} else {
			regFile.setRegisterValue(instruction.getRegTarget(), 0);
		}
		AppContext.PROGRAM_COUNTER++;
		/* -- Set on less than (signed)

	Description:
	If $s is less than $t, $d is set to one. It gets zero otherwise.
	Operation:
	if $s < $t $d = 1; advance_pc (4); else $d = 0; advance_pc (4);
	Syntax:
	slt $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d000 0010 1010
		 */ 
	}

	public void executeSLTI(ITypeInstruction instruction) {
		RegisterFile regFile = RegisterFile.getInstance();
		if(regFile.getRegisterValue(instruction.getRegSource()) <
				instruction.getImmediateVal()){
			regFile.setRegisterValue(instruction.getRegTarget(), 1);
		}else{
			regFile.setRegisterValue(instruction.getRegTarget(), 0);
		}
		AppContext.PROGRAM_COUNTER++;
		/* -- Set on less than immediate (signed)

	Description:
	If $s is less than immediate, $t is set to one. It gets zero otherwise.
	Operation:
	if $s < imm $t = 1; advance_pc (4); else $t = 0; advance_pc (4);
	Syntax:
	slti $t, $s, imm
	Encoding:
	0010 10ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeSLTIU(ITypeInstruction instruction) { 
		RegisterFile regFile = RegisterFile.getInstance();
		if((regFile.getRegisterValue(instruction.getRegSource()) & 0xFFFFFFFF) <
				(instruction.getImmediateVal() & 0xFFFF)){
			regFile.setRegisterValue(instruction.getRegTarget(), 1);
		}else{
			regFile.setRegisterValue(instruction.getRegTarget(), 0);
		}
		AppContext.PROGRAM_COUNTER++;
		/* -- Set on less than immediate unsigned

	Description:
	If $s is less than the unsigned immediate, $t is set to one. It gets zero otherwise.
	Operation:
	if $s < imm $t = 1; advance_pc (4); else $t = 0; advance_pc (4);
	Syntax:
	sltiu $t, $s, imm
	Encoding:
	0010 11ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeSLTU(RTypeInstruction instruction) {
		//incorrect
		RegisterFile regFile = RegisterFile.getInstance();
		if((regFile.getRegisterValue(instruction.getRegSource1()) & 0xFFFFFFFF) <
				(instruction.getRegSource2() & 0xFFFFFFFF)){
			regFile.setRegisterValue(instruction.getRegTarget(), 1);
		}else{
			regFile.setRegisterValue(instruction.getRegTarget(), 0);
		}
		AppContext.PROGRAM_COUNTER++;
		/* -- Set on less than unsigned

	Description:
	If $s is less than $t, $d is set to one. It gets zero otherwise.
	Operation:
	if $s < $t $d = 1; advance_pc (4); else $d = 0; advance_pc (4);
	Syntax:
	sltu $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d000 0010 1011
		 */ 
	}

	public void executeSRA(RTypeInstruction instruction) {
		
		RegisterFile regFile = RegisterFile.getInstance();
		int value = regFile.getRegisterValue(instruction.getRegSource2()) 
				>> instruction.getShift() ;
		regFile.setRegisterValue(regFile.getRegisterValue(instruction.getRegTarget()), value);
		AppContext.PROGRAM_COUNTER++;
		/* -- Shift right arithmetic

	Description:
	Shifts a register value right by the shift amount (shamt) and places the value in the destination register. The sign bit is shifted in.
	Operation:
	$d = $t >> h; advance_pc (4);
	Syntax:
	sra $d, $t, h
	Encoding:
	0000 00-- ---t tttt dddd dhhh hh00 0011
		 */ 
	}

	public void executeSRL(RTypeInstruction instruction) {
		//TODO: need to cross check
		
		RegisterFile regFile = RegisterFile.getInstance();
		int value = regFile.getRegisterValue(instruction.getRegSource2()) 
				>> instruction.getShift() ;
		regFile.setRegisterValue(regFile.getRegisterValue(instruction.getRegTarget()), value);
		AppContext.PROGRAM_COUNTER++;
		/* -- Shift right logical

	Description:
	Shifts a register value right by the shift amount (shamt) and places the value in the destination register. Zeroes are shifted in.
	Operation:
	$d = $t >> h; advance_pc (4);
	Syntax:
	srl $d, $t, h
	Encoding:
	0000 00-- ---t tttt dddd dhhh hh00 0010
		 */ 
	}

	public void executeSRLV(RTypeInstruction instruction) {
		//incorrect
				RegisterFile regFile = RegisterFile.getInstance();
				int value= regFile.getRegisterValue(instruction.getRegSource2()) <<
						regFile.getRegisterValue(instruction.getRegSource1());
				regFile.setRegisterValue(instruction.getRegTarget(), value);
				
				AppContext.PROGRAM_COUNTER++;	
		/* -- Shift right logical variable

	Description:
	Shifts a register value right by the amount specified in $s and places the value in the destination register. Zeroes are shifted in.
	Operation:
	$d = $t >> $s; advance_pc (4);
	Syntax:
	srlv $d, $t, $s
	Encoding:
	0000 00ss ssst tttt dddd d000 0000 0110
		 */ 
	}

	public void executeSUB(RTypeInstruction instruction) { 
		
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource1())- 
				regFile.getRegisterValue(instruction.getRegSource2());

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/* -- Subtract

	Description:
	Subtracts two registers and stores the result in a register
	Operation:
	$d = $s - $t; advance_pc (4);
	Syntax:
	sub $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d000 0010 0010
		 */ 
	}

	public void executeSUBU(RTypeInstruction instruction) { 
		
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource1()) - 
				regFile.getRegisterValue(instruction.getRegSource2());

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/* -- Subtract unsigned

	Description:
	Subtracts two registers and stores the result in a register
	Operation:
	$d = $s - $t; advance_pc (4);
	Syntax:
	subu $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d000 0010 0011
		 */ 
	}

	public void executeSW(ITypeInstruction instruction) { 
		
		RegisterFile regFile = RegisterFile.getInstance();
		Memory memory = Memory.getInstance();
		
		int value = regFile.getRegisterValue(instruction.getRegTarget());
		int index = regFile.getRegisterValue(instruction.getRegSource())
				+instruction.getImmediateVal();
		memory.setValue( index, (byte)(value >> 8 & 0xFF));
		memory.setValue( index + 1, (byte)(value & 0xFF));
	
		AppContext.PROGRAM_COUNTER++;	
		/* -- Store word

	Description:
	The contents of $t is stored at the specified address.
	Operation:
	MEM[$s + offset] = $t; advance_pc (4);
	Syntax:
	sw $t, offset($s)
	Encoding:
	1010 11ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

	public void executeSYSCALL(RTypeInstruction instruction) {
		//incorrect
		
		AppContext.PROGRAM_COUNTER++;	
		/* -- System call

	Description:
	Generates a software interrupt.
	Operation:
	advance_pc (4);
	Syntax:
	syscall
	Encoding:
	0000 00-- ---- ---- ---- ---- --00 1100
		 */ 
	}


	public void executeXOR(RTypeInstruction instruction) {	
	
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource1()) ^ 
			regFile.getRegisterValue(instruction.getRegSource2());

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		
		/* -- Bitwise exclusive or

	Description:
	Exclusive ors two registers and stores the result in a register
	Operation:
	$d = $s ^ $t; advance_pc (4);
	Syntax:
	xor $d, $s, $t
	Encoding:
	0000 00ss ssst tttt dddd d--- --10 0110
		 */ 
	}

	public void executeXORI(ITypeInstruction instruction) { 
		
		RegisterFile regFile = RegisterFile.getInstance();
		int result = regFile.getRegisterValue(instruction.getRegSource()) ^ 
				instruction.getImmediateVal();

		regFile.setRegisterValue(instruction.getRegTarget(), result); 
		AppContext.PROGRAM_COUNTER++;
		/* -- Bitwise exclusive or immediate

	Description:
	Bitwise exclusive ors a register and an immediate value and stores the result in a register
	Operation:
	$t = $s ^ imm; advance_pc (4);
	Syntax:
	xori $t, $s, imm
	Encoding:
	0011 10ss ssst tttt iiii iiii iiii iiii
		 */ 
	}

}
