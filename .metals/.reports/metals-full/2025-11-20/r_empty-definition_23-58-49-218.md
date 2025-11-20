error id: file://<WORKSPACE>/src/main/scala/cpu/Decoder.scala:`<none>`.
file://<WORKSPACE>/src/main/scala/cpu/Decoder.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 2230
uri: file://<WORKSPACE>/src/main/scala/cpu/Decoder.scala
text:
```scala
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
        // R-type
        is ("b0110011".U) {
            io.ctrlSignal.ctrlRegWrite := true.B
            io.ctrlSignal.ctrlALUSrc := false.B
            io.ctrlSignal.ctrlMemRead := false.B
            io.ctrlSignal.ctrlMemWrite := false.B
            io.ctrlSignal.ctrlMemToReg := false.B
            io.ctrlSignal.ctrlBranch := false.B
            io.ctrlSignal.ctrlJump := false.B

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

            // register addresses
            io.regAddr.rs1_addr := io.instr(19,15)
            io.regAddr.rs2_@@addr := io.instr(24,20

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.