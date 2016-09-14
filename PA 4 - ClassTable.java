/*
Copyright (c) 2000 The Regents of the University of California.
All rights reserved.

Permission to use, copy, modify, and distribute this software for any
purpose, without fee, and without written agreement is hereby granted,
provided that the above copyright notice and the following two
paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR
DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES ARISING OUT
OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF THE UNIVERSITY OF
CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE PROVIDED HEREUNDER IS
ON AN "AS IS" BASIS, AND THE UNIVERSITY OF CALIFORNIA HAS NO OBLIGATION TO
PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
*/

// This is a project skeleton file

import java.io.PrintStream;
import java.util.HashMap;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;
		
		// class inheritance graph
		public HashMap<String, Class_> graph;

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class = 
	    new class_c(0, 
		       TreeConstants.Object_, 
		       TreeConstants.No_class,
		       new Features(0)
			   .appendElement(new method(0, 
					      TreeConstants.cool_abort, 
					      new Formals(0), 
					      TreeConstants.Object_, 
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.type_name,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.copy,
					      new Formals(0),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0))),
		       filename);
	
	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class = 
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_string,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_int,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0))),
		       filename);

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class = 
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// Bool also has only the "val" slot.
	class_c Bool_class = 
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
		       TreeConstants.Str,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.Int,
					    new no_expr(0)))
			   .appendElement(new attr(0,
					    TreeConstants.str_field,
					    TreeConstants.prim_slot,
					    new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.length,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.concat,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

	/* Do something with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */

	// insert basic types into graph
	graph.put(TreeConstants.Object_.getString(), Object_class);
	graph.put(TreeConstants.Int.getString(), Int_class);
	graph.put(TreeConstants.Bool.getString(), Bool_class);
	graph.put(TreeConstants.Str.getString(), Str_class);
	graph.put(TreeConstants.IO.getString(), IO_class);		 
					 
					 
	// NOT TO BE INCLUDED IN SKELETON
	
	/*Object_class.dump_with_types(System.err, 0);
	IO_class.dump_with_types(System.err, 0);
	Int_class.dump_with_types(System.err, 0);
	Bool_class.dump_with_types(System.err, 0);
	Str_class.dump_with_types(System.err, 0);*/
    }
	


public ClassTable(Classes cls) {
	semantErrors = 0;
	errorStream = System.err;
	/* fill this in */

	// initialize hashMap to represent class graph
	graph  = new HashMap<String, Class_>();
	// ass basic classes
	installBasicClasses();
	
	// fill the empty graph
	for(int i = 0; i < cls.getLength(); i++){
		Class_ c = (Class_) (cls.getNth(i));
		String key = c.getName().getString();
		
		// check for no duplicates
		if (graph.containsKey(key))
			semantError(c.getFilename(), c).println("duplicate class declaration : " + key);
		
		// insert into graph
		graph.put(key, c);
		AbstractSymbol parent = c.getParent();
		
		// make sure does not inherit from int, string, or boolean
		if (parent == TreeConstants.Bool || parent == TreeConstants.Int || parent == TreeConstants.Str) {
			semantError(c.getFilename(), c).println("inheritance from Int, String, or Bool found : " + key);
			parent = null;
		}
		
		// check for inheritance loops
		while (parent != null && parent != TreeConstants.Object_) {
			if (parent.getString().equals(key)) {
				semantError(c.getFilename(), c).println("inheritance loop found : " + key);
				parent = null;
			} else if (graph.containsKey(parent.getString()))
				parent = graph.get(parent.getString()).getParent();
			else
				parent = null;
		}
	}
	
	// check for any missing inheritances
	for(int i = 0; i < cls.getLength(); i++) {
		Class_ c = (Class_) (cls.getNth(i));
		AbstractSymbol parent = c.getParent();
		if (parent != TreeConstants.Object_ && !graph.containsKey(parent.getString())) 
			semantError(c.getFilename(), c).println("missing inheritance : " + parent.getString());
	}
	
	// check for Main class
	Class_ mainC = graph.get("Main");
	if (mainC == null)
		semantError().println("missing Main Class");
	else {
	
		// check for main method
		Features mainCFeatures = mainC.getFeatures();
		int mainFound = 0;
		for (int i = 0; i < mainCFeatures.getLength(); i++) {
			Feature f = (Feature) (mainCFeatures.getNth(i));
			if (f instanceof method && ((method)f).name == TreeConstants.main_meth) {
				mainFound++;
				// check for no arguments
				if (((method)f).formals.getLength() != 0)
					semantError(mainC.getFilename(), f).println("main method has arguments but it shouldn't");
			}
		}
		if (mainFound == 0)
			semantError(mainC.getFilename(), mainC).println("missing Main Method");
		else if (mainFound > 1)
			semantError(mainC.getFilename(), mainC).println("duplicate Main Methods : " + mainFound);
	}
	
}

	// added by me
	public boolean existsClass(String className) {
		return graph.containsKey(className);
	}

	// added by me
	public AbstractSymbol getParent(String childName) {
		if (!graph.containsKey(childName))
			return TreeConstants.Object_;
		return graph.get(childName).getParent();
	}

	// added by me. returns true if and only if assignType (param 3) a subclass of declaredType (param 2)
	public boolean isSubClass(AbstractSymbol selfClass, AbstractSymbol declaredType, AbstractSymbol assignType) {
		if (assignType == TreeConstants.SELF_TYPE && declaredType != TreeConstants.SELF_TYPE)
			return isSubClass(selfClass, declaredType, selfClass);
		
		if (assignType == TreeConstants.SELF_TYPE && declaredType == TreeConstants.SELF_TYPE)
			return true;
			
		if (assignType != TreeConstants.SELF_TYPE && declaredType == TreeConstants.SELF_TYPE)
			return false;
			
		AbstractSymbol childType = assignType;
		//System.out.println("is sub class " + declaredType + " >? " + assignType );
		while (assignType != TreeConstants.Object_ && assignType != declaredType) {
			assignType = getParent(assignType.getString());
			//System.out.println("     is sub class " + declaredType + " >? " + assignType );
		}
		return (assignType == declaredType);
	}
	
	// added by me, returns Least common parent
	public AbstractSymbol commonParent(AbstractSymbol c1, AbstractSymbol c2) {
		if (c1 == c2)
			return c1;
			
		if (isSubClass(null, c2, c1))
			return c2;
			
		AbstractSymbol parent = c1;
		while (!isSubClass(null, parent, c2))
			parent = getParent(parent.getString());
		return parent;
	}


    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
	errorStream.print(filename + ":" + t.getLineNumber() + ": ");
	return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
	semantErrors++;
	return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
	return semantErrors != 0;
    }

    // NOT TO BE INCLUDED IN SKELETON
    public static void main(String[] args) {
	new ClassTable(null).installBasicClasses();
    }
}
			  
