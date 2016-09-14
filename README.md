# CoolCompiler

This was a project to implement a Compiler and Code Generator for the Cool programming language. (https://en.wikipedia.org/wiki/Cool_(programming_language))

## Overview

#### PA 2 - Lexer
part 1 of the project, to create a lexer (i.e. handles comments, white spaces, and creates tokens from given source code)
You can think of tokens as a list of tuples such as (integer, 5), (symbol, +), (inteeger, 10) to represent the expression "5 + 10".

The code and instructions for running the lexer can be found inside the "PA 2 - Lexer" folder. To highlight the key files, they have been duplicated to the main directory:
cool.lex - contains the lexer brains
testLex.cl - contains extensive test cases for the lexer

#### PA 3 - Parser
part 2 of the project, to parse the output of the lexer (i.e. use the token output to build a parse parser tree while checking the code follows proper grxammer (checks for syntax errors such as "3+;=(" ))
You can think of parser trees as root: (symbol, +), child_1: (integer, 5), child_2: (integer, 10) to represent the expression "5 + 10". A google image search of "compiler parser tree" brings up great examples to help visualize what this looks like on a bigger scale.

The code and instructions for running the parser can be found inside the "PA 3 - Parser" folder.To highlight the key files, they have been duplicated to the main directory:
cool.cup - contains the parser rules
parser_test_for_errrors.cl - contains test cases for bad input source code that the parser is expected to handle
parser_test.cl - contains good test cases to check the parser outputs correct parser trees

#### PA 4 - Semantic Analysis
part 3 of the project, to perform semantic analsyis (i.e. look over the parser tree outputed by the parser and check for semantic errors such as calling a method that does not exist, or passing in incorrect argument types / number of arguments to a method call, or having a child class try to override a variable declared in the parent class, etc).

The code and instructions for running the semantic analyzer can be found inside the "PA 4 - semantic analyzeer" folder. To highlight the key files, they have been duplicated to the main directory:
cool-tree.java - does the semantic analysis
ClassTable.java - defines what a class table should look like, adds some helper methods for finding classes' shared parent for example, and implements default classes such as Boolean, String, IO, Object, and Int
semantic_test_bad.cl - contains incorrect seemantic cases
semantic_test_good.cl - contains correct semantic cases

#### PA 5 - Code Generation
part 4 of the project, to perform code generation (i.e. spit out MIPS code).

The code and instructions for running the code gen can be found inside the "PA 5 - cgen" folder. To highlight the key files, they have been duplicated to the main directory:
cool-tree.java - generates the main body of the MIPS program
CgenClassTable.java - generates top segments of MIP programs, i.e. the .data and .text segments
tests.cl - test cases
