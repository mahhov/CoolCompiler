# CoolCompiler

This was a project to implement a Compiler and Code Generator for the Cool programming language. (https://en.wikipedia.org/wiki/Cool_(programming_language))

## OVERVIEW

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

## LEXER
	Design Decisions
		Variablees
			Two variables were added: stringErr and nest_comment_lvl. StringErr is used to determine if, when in the string state, our current string has encountered an error already (such as being too long). Then this booelan variable helps prevent us from sending multipe too-long error symbols or adding the string to the string table when the string ends, and helps control other similar behaviors that are meant to be different when working with a non-error string and a error string. The second variable, nest_comment_lvl, is simply there to help track how deep we are in block comments when in the block comment state. This allows us to know when to return to the initial state.		
		State
			There are 4 states, initial(normal), string, line comment, and block comment. I chose these four states, because within each, pattern matches would be treated the same. I.e., within strings, all alpha-numeric characters are treated a certain way, whereas in normal mode, different combinations of alpha-numeric characters are treated differnt ways (intiger literal, object identifier, keywoard, etc). As another example, in line comments, all but new lines can be treated similarly, whereas in block comments, not only are new lines treated differntly than new lines in line comments, but other matches like *)'s and (*'s have to be specially treated, unlike in line comments.
	
	Code Correct
		Identifier Matches
			We classify any pattern that begins with a lowrcase letter, and contains alpha-numeric-underscore characters only as an object identifier. Similarly, anything that begins with an upercase letter, and contains alpha-numeric-underscore characters only is classified as a type identifier. We make sure to put these matches below the keyword matches. This means when something like "Else" is encountered, it is classified as a keyword instead of a type identifier.l But "Elsek" is still correctly classified as a type identifier, since it matches a longer substring as a type identifier, than it does with the keyword "Else". Integer identifiers, on the other hand, need not be placed below the keyword patterns, since keywords do not start with numeric characters.
		Added keywords and operators
			We added the keyword true because the given skeleton code did not include it. We make sure that it matches to all case-combinatations of its characters as long as the first letter is lowercase, as specified by the specs. We also added the assign and left arrow operators as two-character matches.
		Line Comments
			Line comments are handled pretty simply. When we meet the pattern "--" we enter line comment state. This pattern will not be confused with a minus operator, since "--" will match a substring of length 2 with the pattern "--" but only a substring of length 1 with the "-" pattern". Once we are in the line comment state, everything until the next new line is simply ignored, including non-new-line-whitespaces and alphanumeric characters. Once we do reach the new line, we return to the intial state (of course, updating curr_lineno as we also do when meeting a new line in initial state, string state, or block comment state).
		Block Comments
			Block comments are similarly simple. Every time we meet a (*, we increment our block comment depth tracking variable, nest_comment_lvl. And of course, if we were in initial state when we met the (*, we enter the block comment state. If we meet *) in the initial state, on the other hand, we return the "unmatched *)" error as specified in the specs. But if we meet *) while in the block state, we simply decrement our counter nest_comment_lvl. When nest_comment_lvl reaches 0, we return to the initial state. While in block comment state, we ignore everything (other than "(*", "*)", and new lines). We make sure that if a new line is in between the "*" and the ")" or "(", then those are not considered going deeper or shallower in our nest level, in accordance to the reference lexer.
		Strings
			When we reach a " while in the initial state, we enter the string state. When we see a " that isn't preceded by \, we return to the initial state. While in the string state, we handle \t, \n, \b, and \f specially, as specified in the specs. We make sure \" isn't mistaken as a sign to leave the string state, but actually seen as a double quotation that's part of the string. New lines preceded by \ are permited, we simply increment curr_lineno and add the new line to our string buffer. On the other hand, new lines not preceded by \ cause us to return an error as specified in the spec. Everything else we simply add to the string buffer. We take special care to treat \c as simply c (except the exceptions listed above) in accordance to the spcecs. But when doing so, we are careful to not treat \\string as simply string, but instead as \string (i.e., we want to allow the programmer to include \'s in their strings if they wish). Other errors, such as too long strings are also handled correctly; we make sure to turn on the boolean flag stringErr, to make sure the contents of our current string buffer aren't stored in the strings table, and also to make sure the error isn't returned multiple times if, for example, we had a string like "very long string ... /n very long string" since such strings would have multiple matches while in the string state.
		Eof handling
			When end of file is reached, if we are in the middle of a block comment or string, we return the appropiate error symbol, before sending the EOF symbol. If we were instead in the inital state, or line comment state, we do not need to send any error, and simply return the EOF symbol.
	
	Tests
		Comments
			We thourouglhy test everything specified. The description of our tests and which lines of mytest.cl are testing which details is at the top of mytest.cl. But to summarize, tests 1, 7, 13, and 14 test that block comments can extend multiple lines, nest and unest propperly, and that we return errors when encountering an EOF mid-block-comment. Test 15 makes sure emtpy block comments, and a mix and match of different *'s, )'s, ('s, and white spaces do not confuse the lexer. Test 5, in comparison, deals with line comments, and makes sure they are termintead with new lines. Lastly, test 16 makes sure an unmatched *) is properly reported as an error. EoF mid-block-comments were also tested, but removed from test.cl since they would require the test.cl to end immediatly after.
		Strings
			Test 2 tests a simple string, while 3 and 4 make sure new lines in strings with and without escapes are handled properly. Test 9 then tests an assortmet of \'s, white spaces, and other character combinations within strings and makes sure they are all properly handled by the lexer. Test 10 makes sure the null character in a string is reported as an error, but that the two-character substring "\0" is not. Lastly, tests 11 and 12 not only makes sure too-long strings are properly reported as errors, but multipe-error string are also reported appropriately (e.g. a too long string that is unterminated should return the both respective errors with proper line numbers). Also, as with comments, EoF mid-strings were  tested, but removed from test.cl since they would require the test.cl to end immediatly after. EoF mid-string error in combination with too-long string and null-char error all within the same string was also tested.
		Others
			Test 6 makes sure recognized tokens are properly reported. Test 8 makes sure type identifiers, object identifiers, keywords, and booleans are all properly distinguished, (i.e. else is a keyword, elsee is a object identifier, fAlse is a boolean, False is a type identifier, etc.). Test 11 also makes sure too long integers don't report errors. While test 17 makes sure that all keywords are properly matched.
			
