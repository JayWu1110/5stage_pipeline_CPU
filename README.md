# Chisel 5-Stage Pipelined CPU

A 32-bit, 5-stage pipelined CPU implemented in [Chisel](https://www.chisel-lang.org/). This project implements a basic RISC-V instruction set architecture, featuring robust hardware hazard resolution and data forwarding.

## Features

*   **5-Stage Pipeline:** Instruction Fetch (IF), Instruction Decode (ID), Execute (EX), Memory (MEM), and Write-Back (WB).
*   **Hazard Resolution:** 
    *   **Data Forwarding:** Eliminates data hazards by forwarding results from EX/MEM and MEM/WB stages directly to the EX or ID stages.
    *   **Load-Use Stalls:** Automatically detects load-use dependencies and stalls the pipeline accordingly.
    *   **Control Hazards:** Flushes pipeline stages (IF/ID, ID/EX) when a branch or jump is taken.
*   **Memory Integration:** Separate Instruction Memory (`InstMem`) and Data Memory (`DataMem`) with support for `.hex` file loading.
*   **Automated Testing:** Built-in testbench using `Chiseltest` to verify execution against golden memory states.

## Environmental Setup

### Prerequisites
Install JDK 17 and sbt (Scala Build Tool)
*   You can verify your installations using the commands:
```bash
java -version
sbt --version
```

### Compilation and Testing
Inside the project directory, run the following commands. You can replace the values and file paths as needed:
```bash
PIPELINE=1 INST_FILE=pattern/p1.hex GOLDEN_FILE=pattern/p1_golden.hex sbt test
```

### Waveforms
Generated .vcd files are in test_run_dir/.
```bash
gtkwave test_run_dir/<test_name>/<module_name>.vcd
```
