package cpu

import chisel3._
import chisel3.util._


class Decoder_io extends Bundle {
    /// TODO ///
    val instr = Input(UInt(32.W))
    val imm = Output(UInt(32.W)) // connect to imm in Alu.scala
    val ctrlSignal = Output(new CtrlSignal()) // from Bundle.scala
    val regAddr = Output(new RegAddr()) // from Bundle.scala
}

class Decoder extends Module {
    val io = IO(new Decoder_io())

    /// TODO ///
    // extract fields from instruction
    val opcode = io.instr(6,0)

    switch (opcode) {
        // R-type: add, sub, and, or, xor, slt, sll, srl, sra
        is ("b0110011".U) {

            // default control signals for R-type
            io.ctrlSignal.ctrlRegWrite := true.B
            io.ctrlSignal.ctrlMemRead := false.B
            io.ctrlSignal.ctrlMemWrite := false.B
            io.ctrlSignal.ctrlALUSrc := false.B
            io.ctrlSignal.ctrlMemToReg := false.B
            io.ctrlSignal.ctrlJump := false.B
            io.ctrlSignal.ctrlBranch := false.B

            io.regAddr.rs1_addr := io.instr(19,15)
            io.regAddr.rs2_addr := io.instr(24,20)
            io.regAddr.rd_addr := io.instr(11,7)

            // ALU operation decoding based on funct3 and funct7
            val funct3 = io.instr(14,12)
            val funct7 = io.instr(31,25)

            when (funct3 === "b000".U && funct7 === "b0000000".U) {
                io.ctrlSignal.ctrlALUOp := OP_ADD
            } .elsewhen (funct3 === "b000".U && funct7 === "b0100000".U) {
                io.ctrlSignal.ctrlALUOp := OP_SUB
            } .elsewhen (funct3 === "b111".U) {
                io.ctrlSignal.ctrlALUOp := OP_AND
            } .elsewhen (funct3 === "b110".U) {
                io.ctrlSignal.ctrlALUOp := OP_OR
            } .elsewhen (funct3 === "b100".U) {
                io.ctrlSignal.ctrlALUOp := OP_XOR
            } .elsewhen (funct3 === "b010".U) {
                io.ctrlSignal.ctrlALUOp := OP_SLT
            } .elsewhen (funct3 === "b001".U) {
                io.ctrlSignal.ctrlALUOp := OP_SLL
            } .elsewhen (funct3 === "b101".U && funct7 === "b0000000".U) {
                io.ctrlSignal.ctrlALUOp := OP_SRL
            } .elsewhen (funct3 === "b101".U && funct7 === "b0100000".U) {
                io.ctrlSignal.ctrlALUOp := OP_SRA
            } .otherwise {
                io.ctrlSignal.ctrlALUOp := OP_NOP
            }   
        }
    }
}
