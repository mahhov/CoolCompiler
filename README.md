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

The code and instructions for running the code gen can be found inside the "PA 5 - cgen" folder. To highlight the key files, they have been duplicaed to the main directory:

cool-tree.java - generates the main body of the MIPS program

CgenClassTable.java - generates top segments of MIP programs, i.e. the .data and .text segments

tests.cl - test cases

#### Readme's
Each part's directory has its respective readme with full description of the details.  
The implementation details from each section's readme have been copy pasted below.

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
			
## PARSER
	Design Decisions:
		First, we completed the grammar as specified in the cool manual. We had to add the grammar for the features, formals, the and missing expressions (which included lets, cases, if's, while's, block expressions, and more). Second we added precedence rules, again as specified in the cool manual. Lastly, we added error recovery for 3 of the 4 cases we were instructed to do so in the project specs, since one of the cases (the error in class declarations) was already done for us.
	
	Lists:
		For lists, we follow the principle of mathcing "list := list [seperator] element". So for example, in block expressions, we have the production "exp_list_semi -> expr ; | exp_list_semi expr SEMI" (not including the error case for simplicity).
		
	Let's:
		We use the production "Let -> let nested_let" and "nested_let -> ID:TYPE [<-expr] [[,ID:TYPE [<-expr]]]* in expr".
		
	Error handling:
		Handling bad class delcarations was done for us in the skeleton code. We handle errors within features, by adding the production "feature := error;", which will consider everything from the error up to the next semi colon as the error, and continue onwards after the semi colon, (assuming the next few inputs after the semi colon can be matched, otherwise CUP just moves on to the next semicolon). We handle errors within let assignments by considering everything from the error to either the next comma or next IN as the error. This let's us move on to the next let-assignment. Lastly, we handle errors within block expressions by moving to the next semicolon, like we did with features.
	
	good.cl and bad.cl:
		In good.cl, we have employed all parts of the cool grammar, from class constructors, to optional parameters, to dispatches. In addition, we have done multi-level nests of expressions, made sure to test precedences, and also tested the ambiguity with let statements. In bad.cl, we have kept the kept the 4 class definition tests provided. We have added more wrong class declarations and made sure the parser does not think the good class definitions are reported as wrong as well, in order to make sure the parser recovers from bad class definitions. We also test for grammer errors such as blank block expressions, using identifiers for types, and types for identifiers, missing parameters required by operators and keywords, and making sure ambigiuos comparers (which are unassociative in cool) are caught as errors. We make sure the parser recoevers from bad let bindings, by making sure that exactly the number of bad bindings we have is the number of errors reported for the let expression (which includes good bindings as well), and no more or less. Similarly, we make sure the parser recover from bad expressions within block expressions and features, by making sure it reports each error individually, and no more nor less.
		
## SEMANTICS
	ClassTable:
		Added the methods:
			commonParent, which takes in 2 classes, and returns the "least" common parent, i.e. the common parent that's lowest on the inheritance graph.
			isSubClass, which takes in 2 classes (and what SELF_CLASS should evaluate to), and returns true if the 2nd class is equal to or the child of the first class.
			getParent, which takes in a class, and returns the parent class.
			existsClass, which takes in a class, and checks if it's definied in our inheritance graph.
		Filled out ClassTable:
			The idea is simple step-by-step procedure. First we initialize an empty inheritance graph (represented by a table hashMap), and insert the basic classes into it. Then in the first pass of our classes, for each class c, we make sure there doesn't already exist a class with the same name in the graph, insert c into the graph, make sure c does not inherit from Int, Bool, or Str, and check if inserting c created an inheritance loop (this is done by traversing up c's ancestors until we reach either Object, or c again). Then in the second pass, for each class, we make sure it's parent exists in the graph, i.e., we check for missing inheritnace. And lastly, we traverse the classes one last time, to make sure we have a class called Main. Once we find this class, we traverse it's methods and make sure it has no more nor less than 1 main method, which must not take in any paramters.
			
	cool-tree.java
		Filling Symbol Tables (pass 1)
			First, we create empty symbol tables. Class Table is filled out as we described above. Then, we traverse each class c, and for each, we create 2 hashmaps, one for it's methods mapping the method name to the method, and another for it's attributes, mapping the attribute to the attribute type. We then insert these into our method and variable tables respectivley, with the class c as the key.
		Checking Tables (pass 2)
			We then do some basic checking on those tables we filled. Such as making sure child classes don't try to use same attribute names as any of their ancestors have. And making sure if a child has a method of the same name as one of its ancestors, then the 2 methods must share the same signatures. And make sure methods dont use duplicate names for parameters, dont have paramenters of type SELF_TYPE, and all parameter types exist.
		Traversing Tree's (pass 3)
			Traversal and Scope
				To beginour tree traversal, we begin with a simple linear traversal of each class. For each class c, we enter a new scope, into which we add all the attributes and mehods of class c and c's ancestors. We then linearly traverse it's features. For attribute features, we simply verify the attribute type exists, and that if an initializing assignment is made, that it is of that type. For method attributes, we first verify the method's return type exists, then add into our scope the method's paramters, semantic check the expression body, and finally make sure to leave the method scope (with the method parameters). After traversing each of c's features in such a way, we make sure to leave c's scope as well, before moving on to the next class.
			More on Scope
				The only other places we have to further modify the current scope, is during let expressions, case expressions, and dispatches. Let's in that we simply add the let's left part variable declaration to the scope after type checking it's initialization (if an initialization was provided) but before moving on to type checking it's body. Case's we likewise, before evaluating each branch, we create a new scope and add the branch's left hand declaration to the current scope, and we leave that scope once we finish that branch clause. And for dispatches, after finsihing type checking the left-hand and parameters, we simply enter a new scope, add into the scope the parameters, type check the method body, and then leave the scope.
		Semanting Expressions
			Overview
				Expressions are traversed recurseively. As in, for the expression "x <- 3+2", we begin by type checking the entire expression, but we will need to type check "3+2" before we can complete the type check of the entire expression. This is achieved by having each expression take care of it's own semantic check. In other words, each expression is told to evaluate it's own type, and stores its own type. Sub expressions withen a parent expression will be required to evaluate and store their types as the parent is trying to compute its own type. Each type of expression has its own semantic method defined, which adheres to the cool manual.
			Assignment
				We simply make sure the variable is declared, and that the value expresion evalutes to the same type (or sub-type) as the variable's declared type. We set the type of the assignment to the variable type.
			Dispatch
				Some details that were described above are left out here. We evaluate the types of the parameters, determine the method to be dispatched, check for mathcing number and type of parameters as the method is defined to accept, evaluate type of the method body, and make sure the body evlautes to the same type as the method is supposed to return. We set the type of the dispatch to the return type of the method.
			If and While
				We make sure the predicate is boolean and evaluate types of the then/else clauses for if's and the body for while's. For if's we then set the type of the if to the least common parent of the then and else clause types. For while's we set the type to Object.
			Cases
				We iterate through the branches, make sure no 2 or more branches share the same type for the predicate (i.e. case x of x:Int=>expr; y:Int=>expr; esac : is not allowed since both branches share the Int type of predicate). Then we check that each branch's type of predicate is a defined type. Then as described above, we evalute each branches in their local scopes which includes for each branch that branch's assignment. And lastly set the type of the case expression to the least common parent of all the branch types.
			Block
				We iterate and evalute the type of each expression in the block. In the end, we set the type of the block to the type of the last expression in the block.
			Let
				We eavluate type of the initialization in the assignment, if an initialization is provided. Then make sure the type declared for the assignment is an existing type, and a supperclass of the type of the initialization. Then as described aboev, modify the scope for the let body, in which we then evaluate the body and set the let expressions type to the body's type.
			+, -, *, / Operators
				We simply evaluate the type of each side expression, and check that both side expressions are of type Int. Then set the operator's type to Int as well.
			Negate
				Similar to operators, we compute type of expression, make sure it's an integer, and set negations type to integer.
			< and <= Comparators
				Similar to operators, we evaluate the type of both side-expressions, make sure they're both integers, and set the comparotor's type to boolean.
			= equality
				We evaluate the types of both side expressions. Make sure that if either is an int, bool, or str, that they other must be the same type. And then set the equality's type to boolean.
			Not, Comp
				We evaluate the type of the expression and make sure it is a boolean, and then set the type of the comp to boolean.
			Constants
				We set the constants type to the appropiate type in each case; i.e, integer, boolean, or string.
			New
				We verify the type given exists, and then set the new expression's type to that type.
			Isvoid, no_expr
				Simply set the isvoid expression's type to boolean, and no_expr's type to No_type.
			Object
				We simply set the expression's type to the type of the object.
				
		Test Cases (mygood.cl and mybad.cl)
			Note
				Note that good.cl and bad.cl is a concatenation of many individual bad tests I tested. In other words, it's messy to look at the output as a whole. Moreover, bad.cl contains multiple tests that cause the checker to halt. Therefore, very few of the test cases will be seen by simply running ./mysemant bad.cl, the tests not being tested should be commented out so the other tests can be seen.
			What's Tested In bad.cl
				Main
					Lack of main class
					Lack of main method
					Multiple main methods
					Main method acceting parameters
				Inheritance:
					Inheriting from non-existant classes
					Overriding inherrited attributes
					Overriding methods with new return types
					Overriding methods with different number of or type of actuals (or both)
					Multiple attribute and method definitions within a class
					Multiple class definitions
					Inheritance from basic classes
					Inheritance Loops
				Method Signatures
					Methods returning non-existant types
					Methods accepting parameters of non-existant types
					Methods accepting parameters of SELF_TYPE
					Methods having multiple parameters of the same name
				Attribute
					Declaring attribues of non-existant types
				Expressions
					Assignment of wrong types (both in methods and attribute initializations and elsewhere)
					Testing if and while expressions with non-boolean predicates
					Testing case expression branch scoping
					Testing case with branch types of non-defined types
					Testing case with multiple branches of the same type
				Operators
					Testing the operators with wrong type's of left/right hand expressions
				Method
					Returning the wrong type
				Let
					Testing let scoping
					Testing trying to use undefined types in let assignments
					Assigning an expression of mismatching type in a let assignment
				New
					New with undefined type				
				
			What's Tested in good.cl
				Basicly the opposite of everything above. Ther's a proper main class, proper inheritance graph, proper expressions and types and etc. Basicly good.cl contains a good main class and main method, all method signatures are proper (no duplicate names, no SELF_TYPE's, non non-existant parameter types), methods return proper types, all method overwrites are done correctly, all expressions proplery typed, semi-complicated yet correct inheritances, all used types declared, testing proper inheritance of features, proper scoping, etc. I included multiple "trick" cases; for example, having a case with branches that evaluate to type B and C, as the last expression of an expression block making up the body of a method of return type A, where A>B>C.

## Code Generation
	A couple design decisions to note. We store multiple static maps in CgenClassTable to allow us to access information about our class organizations. Specifically, we have 1) a map mapping each class's method to the dispatch offset for that method in the class's dispatch table, 2) a map mapping each class's attributes to where the offset of that attribute on the object's heap space relative to the object, 3) a map mapping each class to the number of "offsprings" it has (i.e., class a -> # of a's children + # of a's grandchildren + # of a's great...), and 4) a vector that contains our classes ordered in the same order as their class tags are. The (3) and (4) are essential to our implementation of the type-case code generation, which we will explain below.
	
	Another decision we made was to pass in the following to each expression's code generation method: 1) the printstream so that output can be written to the output file. 2) The current class, so that SELF can be resolved as well as dispatchs that don't specify a left side expression. 3) A mapping of formals to where they're located on the stack point (relative to frame pointer). 4) A mapping of local variables (declared in let's and type cases) to where they are located on the stack pointer (relative to the frame pointer), and 5) fpOffset, a variable remembering how far apart the sp is from the fp, in order to allow us to record where on the stack (relative to the fp) we are storing local variables. fpOffset is incremented when something is pushed onto the stack, and decremented when something is popped off.
	
	The basic flow of code generation follows the comments given in the skeleton files.
	
	First, we decide which class should get which tag, i.e., we let Object have tag 0, Int 1, Bool 2, Str 3, etc. The important invariant we must maintain in doing so is that, all of class A's offspring (children, grandchildren, etc) have a class tag greater than class A, and that non-ancestor has a class tag that's greater than A's, but less than any of A's ancestors. For example, if class C1 and C2 inherit from B, and B and A both inherit from Parent, then this invariant insures that the class tag ordering will be either P, A, B, C1, C2 or P, B, C1, C2, A. This is very important for our implementation of tpe-case code generation.
	
	Second, we compute (and store in a map as described above) how many offspring each class has. This and the above are what allows our type-case implementations to work. Essentially, for the example above, if we chose class ordering P, B, C1, C2, A, then to check if object x is of type B, we just check if x's class tag is greater than or equal to B's, and less than or equal to C2's; if so, then x is a of type B or a decedent of B. The # of offspring mapping is required to easily compute the class tag of C2 when just given B's class tag.
	
	Third, we fill in the class object table, class dispatches sections, class prototypes sections, and object initializers as described in the runtime manual. For each of them, we simply iterate through the classes in order of their class tags and fill the appropiate sections. For fillign dispatch section for a given class, we recursivelly build off it's ancestors dispatch tables. Likewise for initializer sections.
	
	Lastly we fill in the code for all the methods of all the classes (except of course object, int, str, boolean, and io). This is straight forward if looked at recursively. We begin each method with a label, pushing fp, s0, ra to the stack, copying the a0 (self) to s0, then recursively generating the code for the body, popping back off the fp, s0, and ra from the stack, then popping off the passed in arguments from the stack, and lastly add a return statement.
	
	For computing the code for the actual body expressions, the general idea is as follows.
	
	For assign expressions, we check the local mapping (described above), then if not found the formals mapping (described above), then if still not found the class attribute mappings (described above). The location the identifier is, is where we store the value of $a0 (which was computed when recursively generating the code for the right hand expression).
	
	For dispatches, push arguments to the stack (after recursivelly computing them), then compute the left hand side (or assign it SELF if there is no left hand side provided), then check that it's not a void dispatch (if it is, we jump to _dispatch_abort after appropriating filename and linenumber), then jump to the dispatched mehod label, which we simply lookup in the class-method to method-offset-in-dispatch-table map.
	
	For static dispatches, we do the same, except we use the specified class when reffering to the the class-method to method-offset-in-dispatch-table map, instead of using the class of the left hand side expression.
	
	For if expressions, we compute the predicate expr, then the then or else expr depending on the predicate.
	
	For loops, we compute the predicate, then the body (if predicate was true) and repeat until predicate is false.
	
	For type cases, we described the general idea above. The bigger idea is, check if the expression is null, check which case it matches to if not null (_case_abort2 if s), add the mathcing branch identifier to local map, compute the branch expressin, and then check if no branches matched (_case_abort if so).
	
	For blocks, we simply iterate through the expressions, and compute each seperately.
	
	For Let, we compute the assignment (if one was not provided, then just use the default value appropiate for the type of identifer), store the identifier to the local mapping (and stack), use this local mapping in computing the expression, then remove the identifier from the local mapping (and stack).
	
	For +, -, /, * operators, we comute the each side expression, copy one of the integer objects that resulted, modify it's value to the result of the appropriate operation, and keep this resulting object in $a0.
	
	For comparisons, we do something similar to what we did for the operators. We compute the side expressions, compute the comparison, and jump appropriately depending on if the comparison was true or false, returning false or true depending on where we jumped.
	
	String, boolean, and int constants simply load the appropiate constant to $a0.
	
	New simply determines the prototype object address, copy the prototype object, jump to the initialization section of the object, and keeps that object in $a0.
	
	Isvoid is basicly just like the other comparetors except it compares the given expression result to 0.
	
	noexpr generates no code.
	
	And identifiers do the same as assignments, excpet they load instead of storing from/to the appropiate location. In addition, identifiers also check for SELF, and if so, load $s0 to the result ($a0).
