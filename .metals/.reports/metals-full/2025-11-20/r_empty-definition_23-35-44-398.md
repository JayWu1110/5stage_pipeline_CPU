error id: file://<WORKSPACE>/src/main/scala/cpu/Alu.scala:`<none>`.
file://<WORKSPACE>/src/main/scala/cpu/Alu.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -chisel3/io/pc.
	 -chisel3/util/io/pc.
	 -cpu/OP_TYPES.io.pc.
	 -io/pc.
	 -scala/Predef.io.pc.
offset: 2015
uri: file://<WORKSPACE>/src/main/scala/cpu/Alu.scala
text:
```scala
package cpu

import chisel3._
import chisel3.util._
import cpu.OP_TYPES._

class Alu_io extends Bundle {
    val to_branch = Output(Bool())
    val alu_result = Output(UInt(32.W))
    val jump_addr = Output(UInt(32.W))

    /// TODO ///
    val rs1_data = Input(UInt(32.W)) // connect to data_read1 in Regfile.scala
    val rs2_data = Input(UInt(32.W)) // connect to data_read2 in Regfile.scala
    val imm = Input(UInt(12.W))
    val AluCrl = Input(UInt(5.W)) // connect to ctrlALUOp in Bundle.scala
    val pc = Input(UInt(32.W)) // current pc
}

class Alu extends Module {
    val io = IO(new Alu_io())

    val to_branch = WireDefault(false.B)
    val alu_result = WireDefault(0.U(32.W))
    val jump_addr = WireDefault(0.U(32.W))

    /// TODO ///
    /// sign-extend 12-bit imm to 32-bit ///
    val imm_se = Cat(Fill(20, io.imm(11)), io.imm).asSInt

    /// TODO ///
    // perform ALU operations based on AluCrl
    switch (io.AluCrl) {
        is (OP_NOP) {
            alu_result := 0.U
        }
        is (OP_ADD) {
            alu_result := io.rs1_data + io.rs2_data
        }
        is (OP_SUB) {
            alu_result := io.rs1_data - io.rs2_data
        }
        is (OP_AND) {
            alu_result := io.rs1_data & io.rs2_data
        }
        is (OP_OR) {
            alu_result := io.rs1_data | io.rs2_data
        }
        is (OP_XOR) {
            alu_result := io.rs1_data ^ io.rs2_data 
        }
        is (OP_SLT) {
            alu_result := (io.rs1_data.asSInt < io.rs2_data.asSInt).asUInt
        }
        is (OP_SLL) {
            alu_result := io.rs1_data << io.rs2_data(4,0)
        }
        is (OP_SRL) {
            alu_result := io.rs1_data >> io.rs2_data(4,0)
        }
        is (OP_SRA) {
            alu_result := (io.rs1_data.asSInt >> io.rs2_data(4,0)).asUInt
        }
        is (OP_BEQ) {
            jump_addr := (io.pc.asSInt + imm_se).asUInt
            to_branch := (io.rs1_data === io.rs2_data)
        }
        is (OP_BNE) {
            jump_addr := (io.p@@c.asSInt + imm_se).asUInt
            to_branch := (io.rs1_data =/= io.rs2_data)
        }
        is (OP_BLT) {
            jump_addr := (io.pc.asSInt + imm_se).asUInt
            to_branch := (io.rs1_data.asSInt < io.rs2_data.asSInt)
        }
        is (OP_BGE) {
            jump_addr := (io.pc.asSInt + imm_se).asUInt
            to_branch := (io.rs1_data.asSInt >= io.rs2_data.asSInt)
        }
        is (OP_JAL) {            
            jump_addr := (io.pc.asSInt + imm_se).asUInt
            alu_result := io.pc + 4.U
        }
        is (OP_JALR) {
            jump_addr := ((io.rs1_data.asSInt + imm_se).asUInt) & (~1.U(32.W))
            alu_result := io.pc + 4.U
        }
        is (OP_LUI) {
            alu_result := io.imm.asUInt << 12
        }
        is (OP_AUIPC) {
            alu_result := io.pc + (io.imm.asUInt << 12)
        }
    }

    // output
    io.alu_result := alu_result
    io.to_branch := to_branch
    io.jump_addr := jump_addr
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.