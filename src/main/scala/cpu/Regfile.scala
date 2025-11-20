package cpu

import chisel3._
import chisel3.util._

class Regfile_io extends Bundle {
    /// TODO ///
    val ctrlRegWrite = Input(Bool())
    val reg_addr = Flipped(new RegAddr()) // input
    val data_write = Input(UInt(32.W))
    val data_read1 = Output(UInt(32.W))
    val data_read2 = Output(UInt(32.W))
}

class Regfile extends Module {
    val io = IO(new Regfile_io())

    // declare 32 registers each 32 bits
    val regs = Reg(Vec(32, UInt(32.W)))

    /// TODO ///
    // read
    io.data_read1 := regs(io.reg_addr.rs1_addr)
    io.data_read2 := regs(io.reg_addr.rs2_addr)

    when(io.reg_addr.rs1_addr === 0.U) {
        io.data_read1 := 0.U
    }
    when(io.reg_addr.rs2_addr === 0.U) {
        io.data_read2 := 0.U
    }

    // write
    when(io.ctrlRegWrite && io.reg_addr.rd_addr =/= 0.U) {
        regs(io.reg_addr.rd_addr) := io.data_write

}
