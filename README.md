## Project Overview

This project focuses on implementing various concepts from **formal languages** and **compiler theory**. It includes work on **deterministic finite automata (DFA)**, **lexers**, **parsers**, and **translators**. Each component addresses different exercises from formal language theory and compiler design.

### Key Components:

1. **DFA (Deterministic Finite Automata)**:
    - Located in the `DFA` folder, this section contains Java programs that implement DFAs for exercises 1.2 to 1.10. Each DFA is accompanied by its corresponding diagram.
    - Exercise 1.7 also includes a variant with a **non-deterministic finite automaton (NFA)**, with only three states.

2. **Lexer**:
    - Located in the `Lexer 2.1` and `Lexer 2.2-2.3` folders, this section includes lexers and classes for recognizing numerical constants. Two solutions are provided, one of which is fully commented.

3. **Parser**:
    - Located in the `Parser 3.1` and `Parser 3.2` folders, this section includes parsers and corresponding grammar files (`Grammatica.txt`). These files contain the productions, FIRST and FOLLOW sets, and guiding sets for each grammar.

4. **Evaluator**:
    - Located in the `Valutatore 4.1` folder, this section includes an evaluator and the necessary classes for its execution.

5. **Translator**:
    - The final four folders include multiple versions of a **translator**, developed through various exercises.
        - **Translator 5.1**: The base version of the translator. Optimizations were made to eliminate unnecessary `emitLabel` and `GOto` statements, simplifying the control flow.
        - **Translator 5.2**: Enhanced to handle binary and unary boolean operators. The `bexprlist` method was modified to handle boolean expressions efficiently.
        - **Translator 5.2.2**: A further optimized version, reducing `GOto` calls, particularly for the `and` and `or` operators.
        - **Translator 5.3**: An advanced version of the translator that optimizes boolean operations by executing the opposite operation to save on `GOto` calls in `if` and `while` statements.

## Instructions for Running

1. **Compilation**:
    - Each `.java` file can be compiled using a standard Java compiler. Navigate to the directory containing the files and run:
      ```bash
      javac <filename>.java
      ```

2. **Execution**:
    - After compiling, run the Java programs using:
      ```bash
      java <filename>
      ```

3. **Structure**:
    - Each folder corresponds to a specific exercise or group of exercises, with clear documentation and comments within the code explaining the functionality.

## Project Structure

```
/DFA
  - DFA-related exercises and diagrams
/Lexer 2.1
  - Lexer for numerical constants (Exercise 2.1)
/Lexer 2.2-2.3
  - Lexer for additional exercises (2.2 and 2.3)
/Parser 3.1
  - Parser and grammar file (Exercise 3.1)
/Parser 3.2
  - Parser and grammar file (Exercise 3.2)
/Valutatore 4.1
  - Evaluator and supporting classes (Exercise 4.1)
/Translator 5.1
  - Base version of the translator (Exercise 5.1)
/Translator 5.2
  - Translator handling boolean operators (Exercise 5.2)
/Translator 5.2.2
  - Optimized version reducing `GOto` usage (Exercise 5.2.2)
/Translator 5.3
  - Final optimized translator version (Exercise 5.3)
```

## Contact

For any questions or further information, please contact **Federico Paschetta**.
