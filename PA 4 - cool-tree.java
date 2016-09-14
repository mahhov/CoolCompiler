// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////



import java.util.Enumeration;
import java.io.PrintStream;
import java.util.Vector;
import java.util.HashMap;
import java.util.Hashtable;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol getName();
    public abstract AbstractSymbol getParent();
    public abstract AbstractSymbol getFilename();
    public abstract Features getFeatures();

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;                                 
    public AbstractSymbol get_type() { return type; }           
    public Expression set_type(AbstractSymbol s) { type = s; return this; } 
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }
		public void semant(Class_ c, SymbolTable symbTable){}; // added by me
}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
	    ((Class_)e.nextElement()).dump_with_types(out, n + 2);
        }
    }
    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
	<p>
        Your checker should do the following two things:
	<ol>
	<li>Check that the program is semantically correct
	<li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
	</ol>
	<p>
	You are free to first do (1) and make sure you catch all semantic
    	errors. Part (2) can be done in a second stage when you want
	to test the complete compiler.
    */
	
	// added by me. used to add a class's methods and attributes to the symbol table
	private void addClassMethodsAttributesToTable(Class_ c, SymbolTable symbTable) {
		HashMap<AbstractSymbol, method> methods = new HashMap<AbstractSymbol, method>();
		HashMap<AbstractSymbol, AbstractSymbol> variables = new HashMap<AbstractSymbol, AbstractSymbol>();

		AbstractSymbol cname = c.getName();
		Features features = c.getFeatures();
		
		for (int j = 0; j < features.getLength(); j++) {
			Feature f = (Feature) (features.getNth(j));
			// method
			if (f instanceof method) {
				method m = (method) f;
				// check for duplicate method declaration
				if (methods.containsKey(m.name))
					symbTable.classTable.semantError(c.getFilename(), m).println("duplicate declaration of method " + m.name);
				else
					methods.put(m.name, m);
			// attribute
			} else {
				attr a = (attr) f;
				// check for duplicate attribute declaration
				if (variables.containsKey(a.name))
					symbTable.classTable.semantError(c.getFilename(), a).println("duplicate declaration of attribute " + a.name);
				else 
					variables.put(a.name, a.type_decl);
			}
		}
		
		symbTable.methodLookup().put(cname, methods);
		symbTable.variableLookup().put(cname, variables);
	}
		
	public void semant() {
		/* ClassTable constructor may do some semantic analysis */
		ClassTable classTable = new ClassTable(classes);
		if (classTable.errors()) {
				System.err.println("Compilation halted due to static semantic errors.");
				System.exit(1);
		}

		/* some semantic analysis code may go here */
		
		// fill in method and attribute tables
		SymbolTable symbTable = new SymbolTable();
		symbTable.setClassTable(classTable);
		for (Class_ c : symbTable.classTable.graph.values())
			addClassMethodsAttributesToTable(c, symbTable);
		
		// make sure same-name methods & attributes not declared in both parent and child classes
		// (or, in the case of methods, share signatures if multiple are defined along the ancestory)
		// as a bonus, also check methods don't have multiple parameters of the same name, that no method parameters are declared as self-types, and types declared for parameters exist
		HashMap<AbstractSymbol, HashMap<AbstractSymbol, method>> methodLookup = (HashMap<AbstractSymbol, HashMap<AbstractSymbol, method>>) (symbTable.methodLookup());
		HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>> variableLookup = (HashMap<AbstractSymbol, HashMap<AbstractSymbol, AbstractSymbol>>) (symbTable.variableLookup());
		for(int i = 0; i < classes.getLength(); i++){
			Class_ c = (Class_) (classes.getNth(i));
			AbstractSymbol cname = c.getName();
			Features features = c.getFeatures();
			
			for (int j = 0; j < features.getLength(); j++) {
				Feature f = (Feature) (features.getNth(j));
				// method
				if (f instanceof method) {
					method m = (method) f;
					// if multiple declarations along ancestory, must share signature
					boolean declaredParent = false; // method of same name found in parent class
					boolean declaredParentAndCompatible = false; // method of same name in parent class has same signature
					AbstractSymbol parent = classTable.getParent(cname.getString());
					while (!declaredParent && parent != TreeConstants.Object_) {
						declaredParent = methodLookup.get(parent).containsKey(m.name);
						if (declaredParent) {
							method parentMethod = methodLookup.get(parent).get(m.name);
							declaredParentAndCompatible = m.sharesSignature(parentMethod);
						}
						parent = classTable.getParent(parent.getString());
					}
					if (declaredParent && !declaredParentAndCompatible)
						symbTable.classTable.semantError(c.getFilename(), m).println("method incorrectly redefined from parent class " + m.name);
					// checks method signatures (no have duplicate param names, no params of self-type, param types are valid)
					if (m.badParams(symbTable))
						symbTable.classTable.semantError(c.getFilename(), m).println("bad method parameters (either multiple parameters of the same name, or parameters of type SELF_TYPE, or undefined parameter types) in method " + m.name);
						
				// attribute
				} else {
					attr a = (attr) f;
					boolean declaredParent = false;
					AbstractSymbol parent = classTable.getParent(cname.getString());
					while (!declaredParent && parent != TreeConstants.Object_) {
						declaredParent = variableLookup.get(parent).containsKey(a.name);
						parent = classTable.getParent(parent.getString());
					}
					if (declaredParent)
						symbTable.classTable.semantError(c.getFilename(), a).println("attribute already declared in parent class " + a.name);
				}
			}
		}
		
		// traverse class trees and recursively semant them
		for(int i = 0; i < classes.getLength(); i++) {
			Class_ c = (Class_) (classes.getNth(i));
			AbstractSymbol cname = c.getName();
			Features features = c.getFeatures();
			
			// create class scope
			Hashtable<AbstractSymbol, AbstractSymbol> ht = new Hashtable<AbstractSymbol, AbstractSymbol>();
			AbstractSymbol ancestor = cname;
			while (ancestor != TreeConstants.Object_) {
				ht.putAll((HashMap<AbstractSymbol, AbstractSymbol>) (symbTable.variableLookup().get(ancestor)));
				ancestor = symbTable.classTable.getParent(ancestor.getString());
			}
			ht.putAll((HashMap<AbstractSymbol, AbstractSymbol>) (symbTable.variableLookup().get(ancestor)));
			symbTable.enterScope(ht);
			
			for (int j = 0; j < features.getLength(); j++) {
				Feature f = (Feature) (features.getNth(j));
				
				// method
				if (f instanceof method) {
					method m = (method) f;
					
					// verify return type of method exists
					if (m.return_type != TreeConstants.SELF_TYPE  && !symbTable.classTable.existsClass(m.return_type.getString()))
						symbTable.classTable.semantError(c.getFilename(), m).println("Return type of method not defined " + m.name + " with type " + m.return_type);
					
					// create method scope
					symbTable.enterScope();
					for (int k = 0; k < m.formals.getLength(); k++) {
						formalc form = (formalc) (m.formals.getNth(k));
						symbTable.addId(form.name, form.type_decl);
					}
				
					// recursively type check method's expression
					// and verify method body evaluates to the type it's declared to return
					m.expr.semant(c, symbTable);
					if (!symbTable.classTable.isSubClass(c.getName(), m.return_type, m.expr.get_type()))
						symbTable.classTable.semantError(c.getFilename(), m).println("Return type of method mismatch " + m.name + " expected return type " + m.return_type + " but it's body evaluates to " + m.expr.get_type());
						
					// leave method scope
					symbTable.exitScope();
					
				// attribute
				} else {
					attr a = (attr) f;
					a.init.semant(c, symbTable);
					
					// verify attribute type exist
					if (a.type_decl != TreeConstants.SELF_TYPE && !symbTable.classTable.existsClass(a.type_decl.getString()))
						symbTable.classTable.semantError(c.getFilename(), a).println("Type of attribute not defined " + a.name + " with type " + a.type_decl);
					
					// verify proper type assignment
					if (a.init.get_type() != TreeConstants.No_type && !symbTable.classTable.isSubClass(c.getName(), a.type_decl, a.init.get_type()))
						symbTable.classTable.semantError(c.getFilename(), a).println("Assigned type of attribute mismatch " + a.name + " declared type " + a.type_decl + " but assigned " + a.init.get_type());
				}
			}
			
			// leave class scope
			symbTable.exitScope();
		}
		
		if (classTable.errors()) {
				System.err.println("Compilation halted due to static semantic errors.");
				System.exit(1);
		}
	}

}


/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    /** Creates "class_c" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	    ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }
    public AbstractSymbol getFilename() { return filename; }
    public Features getFeatures()       { return features; }

}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
    /** Creates "method" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	    ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
	expr.dump_with_types(out, n + 2);
    }

		// returns true if m and this share same formals and return type
		public boolean sharesSignature(method m) {
			if (return_type != m.return_type)
				return false;
			if (formals.getLength() != m.formals.getLength())
				return false;
			for (int i = 0; i < formals.getLength(); i++)
				if (((formalc) (formals.getNth(i))).type_decl != ((formalc) (m.formals.getNth(i))).type_decl)
					return false;
			return true;
		}
		
		// return true if duplicate names exist in formals, i.e. cat (x:Int, x:Int, x:String): Int {...}
		// returns true has parameters of self-type, i.e. cat (x:SELF_TYPE): Int {...}
		// returns true if parameter type does not exist, i.e. cat (x:NonExistantClass): Int {...}
		// returns false otherwise
		public boolean badParams(SymbolTable symbTable) {
			for (int i = 0; i < formals.getLength(); i++) {
				formalc f1 = (formalc) (formals.getNth(i));
				if (f1.type_decl == TreeConstants.SELF_TYPE || !symbTable.classTable.existsClass(f1.type_decl.getString()))
					return true;
				for (int j = i + 1; j < formals.getLength(); j++) {
					formalc f2 = (formalc) (formals.getNth(j));
					if (f1.name == f2.name)
						return true;
				}
			}
			return false;
		}
		
}


/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
    /** Creates "attr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
    }

}


/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }

}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression expr;
    /** Creates "branch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	expr.dump_with_types(out, n + 2);
    }
}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
    /** Creates "assign" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
	expr.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){
		// make sure variable declared
		if (!symbTable.isInTable(name)) {
			symbTable.classTable.semantError(c.getFilename(), this).println("assignment to undeclared identifier " + name);
			set_type(TreeConstants.Object_);
			return;
		}
			
		// make sure assigned expr type matches or is subclass of declared type
		AbstractSymbol declaredType = (AbstractSymbol) symbTable.lookup(name);
		expr.semant(c, symbTable);
		AbstractSymbol assignType = expr.get_type();
		if (symbTable.classTable.isSubClass(c.getName(), declaredType, assignType))
			set_type(assignType);
		else {
			symbTable.classTable.semantError(c.getFilename(), this).println("assignment type mismatch " + name + " expected type " + declaredType + " but received type " + assignType);
			set_type(TreeConstants.Object_);
		}
	}
		
}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "static_dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){	
		expr.semant(c, symbTable);

		// compute types of arguments
		for (int i = 0; i < actual.getLength(); i++) {
			Expression e = (Expression) (actual.getNth(i));
			e.semant(c, symbTable);
		}
		
		// find method
		HashMap<AbstractSymbol, method> methods = (HashMap<AbstractSymbol, method>) (symbTable.methodLookup().get(type_name));
		// no method found
		if (!methods.containsKey(name))	{
			symbTable.classTable.semantError(c.getFilename(), this).println("can't static dispatch to undefined method " + name);
			set_type(TreeConstants.Object_);
			return;
		}
		
		// method found
		method m = (method) (methods.get(name));
		
		// check expr is subclass of static type given
		if (!symbTable.classTable.isSubClass(c.getName(), type_name, expr.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("static dispatch left-expression not of proper type for method " + name + " expected " + type_name + " got " + expr.get_type());
			set_type(TreeConstants.Object_);
			return;
		}
		
		// type check arguments
		if (m.formals.getLength() != actual.getLength())
			symbTable.classTable.semantError(c.getFilename(), this).println("dispatch with incorrect number of arguments, expected  " + m.formals.getLength() + " received " + actual.getLength());
		else {
			for (int i = 0; i < actual.getLength(); i++) {
				Expression e = (Expression) (actual.getNth(i));
				formalc f = (formalc) (m.formals.getNth(i));
				if (!symbTable.classTable.isSubClass(c.getName(), f.type_decl, e.get_type()))
					symbTable.classTable.semantError(c.getFilename(), this).println("dispatch with incompatible types, on argument " + i + " of method " + m.name + " expected type " + f.type_decl + " received " + e.get_type());
			}
		}
	
		// set type
		if (m.return_type == TreeConstants.SELF_TYPE)
			set_type(expr.get_type());
		else
			set_type(m.return_type);
	}
		
}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){	
		expr.semant(c, symbTable);
		AbstractSymbol curC = expr.get_type();
		if (curC == TreeConstants.SELF_TYPE)
			curC = c.getName();

		// compute types of arguments
		for (int i = 0; i < actual.getLength(); i++) {
			Expression e = (Expression) (actual.getNth(i));
			e.semant(c, symbTable);
		}
		
		// find method
		boolean foundMethod = false;
		HashMap<AbstractSymbol, method> methods = null;
		while (!foundMethod && curC != TreeConstants.No_class) {
			methods = (HashMap<AbstractSymbol, method>) (symbTable.methodLookup().get(curC));
			if (methods.containsKey(name))
				foundMethod = true;
			else
				curC = symbTable.classTable.getParent(curC.getString());
		}
		
		// no method found
		if (!foundMethod) {
			symbTable.classTable.semantError(c.getFilename(), this).println("can't dispatch to undefined method " + name);
			set_type(TreeConstants.Object_);
			return;
		}
		
		// method found
		method m = (method) (methods.get(name));
		
		// type check arguments
		if (m.formals.getLength() != actual.getLength())
			symbTable.classTable.semantError(c.getFilename(), this).println("dispatch with incorrect number of arguments, expected  " + m.formals.getLength() + " received " + actual.getLength());
		else {
			for (int i = 0; i < actual.getLength(); i++) {
				Expression e = (Expression) (actual.getNth(i));
				formalc f = (formalc) (m.formals.getNth(i));
				if (!symbTable.classTable.isSubClass(c.getName(), f.type_decl, e.get_type()))
					symbTable.classTable.semantError(c.getFilename(), this).println("dispatch with incompatible types, on argument " + i + " of method " + m.name + " expecgetNameted type " + f.type_decl + " received " + e.get_type());
			}
		}
	
		// set type
		if (m.return_type == TreeConstants.SELF_TYPE)
			set_type(expr.get_type());
		else
			set_type(m.return_type);
	}
}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
    /** Creates "cond" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
	pred.dump_with_types(out, n + 2);
	then_exp.dump_with_types(out, n + 2);
	else_exp.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		pred.semant(c, symbTable);
		then_exp.semant(c, symbTable);
		else_exp.semant(c, symbTable);
		if (symbTable.classTable.isSubClass(c.getName(), TreeConstants.Bool, pred.get_type()))
			set_type(symbTable.classTable.commonParent(then_exp.get_type(), else_exp.get_type()));
		else {
			symbTable.classTable.semantError(c.getFilename(), this).println("if statement has non-boolean predicate");
			set_type(TreeConstants.Object_);
		}
	}
		
}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
    /** Creates "loop" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
	pred.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){
		pred.semant(c, symbTable);
		body.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Bool, pred.get_type()))
			symbTable.classTable.semantError(c.getFilename(), this).println("while loop has non-boolean predicate");
		set_type(TreeConstants.Object_);
	}
		
}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
    /** Creates "typcase" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
	expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	    ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		expr.semant(c, symbTable);
		
		// iterate branches and find common ancestor of each's type
		AbstractSymbol outType = null;
		for (int i = 0; i < cases.getLength(); i++) {
			branch b = (branch) (cases.getNth(i));	
			
			// check no duplicate branch types
			for (int j = i + 1; j < cases.getLength(); j++) {
				branch b2 = (branch) (cases.getNth(j));
				if (b2.type_decl == b.type_decl) {
					symbTable.classTable.semantError(c.getFilename(), b2).println("case expression contains duplicate branches of same type " + b.type_decl);
					break;
				}
			}
			
			// check that branch type exists
			if (!symbTable.classTable.existsClass(b.type_decl.getString()))
				symbTable.classTable.semantError(c.getFilename(), b).println("case branch uses undefined type " + b.type_decl);
			
			// evaluate type of the current branch
			symbTable.enterScope();
			symbTable.addId(b.name, b.type_decl);
			b.expr.semant(c, symbTable);
			symbTable.exitScope();
			if (i == 0)
				outType = b.expr.get_type();
			else
				outType = symbTable.classTable.commonParent(outType, b.expr.get_type());
		}
		
		set_type(outType);
	}

}


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
    /** Creates "block" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		for (int i = 0; i < body.getLength(); i++) {
			Expression e = (Expression) (body.getNth(i));
			e.semant(c, symbTable);
		}
		set_type(((Expression) (body.getNth(body.getLength() - 1))).get_type());
	}

}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
    /** Creates "let" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
	dump_AbstractSymbol(out, n + 2, identifier);
	dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){
		// compute type of initialization expression
		init.semant(c, symbTable);
		
		// make sure type_decl is defined type
		if (type_decl != TreeConstants.SELF_TYPE && !symbTable.classTable.existsClass(type_decl.getString()))
			symbTable.classTable.semantError(c.getFilename(), this).println("let declaration uses undeclared type " + type_decl);
		
		// make sure initialization expression (if it exists) has type type_decl
		else if (init.get_type() != TreeConstants.No_type && !symbTable.classTable.isSubClass(c.getName(), type_decl, init.get_type()))
			symbTable.classTable.semantError(c.getFilename(), this).println("let tries to initialize a type of " + type_decl + " with an expression of type " + init.get_type());
		
		// create let's scope
		symbTable.enterScope();
		symbTable.addId(identifier, type_decl);
		
		// evalute body
		body.semant(c, symbTable);
		
		// leave let scope
		symbTable.exitScope();
		
		// set type of let expression to type of body
		set_type(body.get_type());
	}	
		
}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "plus" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		e2.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e1.get_type()) || !symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e2.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("+ operator used on non-integer expression " + e1.get_type() + " and " +  e2.get_type());
			set_type(TreeConstants.Object_);
		} else
			set_type(TreeConstants.Int);
	}
}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "sub" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		e2.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e1.get_type()) || !symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e2.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("- operator used on non-integer expression " + e1.get_type() + " and " +  e2.get_type());
			set_type(TreeConstants.Object_);
		} else
			set_type(TreeConstants.Int);
	}
}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "mul" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		e2.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e1.get_type()) || !symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e2.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("* operator used on non-integer expression " + e1.get_type() + " and " +  e2.get_type());
			set_type(TreeConstants.Object_);
		} else
			set_type(TreeConstants.Int);
	}
		
}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "divide" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
	
	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		e2.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e1.get_type()) || !symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e2.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("/ operator used on non-integer expression " + e1.get_type() + " and " +  e2.get_type());
			set_type(TreeConstants.Object_);
		} else
			set_type(TreeConstants.Int);
	}
	
}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
    /** Creates "neg" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e1.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("~ operator used on non-integer expression " + e1.get_type());
			set_type(TreeConstants.Object_);
		} else
			set_type(TreeConstants.Int);
	}	
		
}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "lt" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		e2.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e1.get_type()) || !symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e2.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("< operator used on non-integer expression " + e1.get_type() + " and " +  e2.get_type());
			set_type(TreeConstants.Object_);
		} else
			set_type(TreeConstants.Bool);
	}

}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "eq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		e2.semant(c, symbTable);
		
		boolean e1Basic = (symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e1.get_type())
										|| symbTable.classTable.isSubClass(c.getName(), TreeConstants.Bool, e1.get_type())
										|| symbTable.classTable.isSubClass(c.getName(), TreeConstants.Str, e1.get_type()));
		boolean e2Basic = (symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e2.get_type())
										|| symbTable.classTable.isSubClass(c.getName(), TreeConstants.Bool, e2.get_type())
										|| symbTable.classTable.isSubClass(c.getName(), TreeConstants.Str, e2.get_type()));
		
		if (!e1Basic && !e2Basic)
			set_type(TreeConstants.Bool);
		
		else if (e1Basic && e2Basic && e1.get_type() == e2.get_type()){
			set_type(TreeConstants.Bool);
			
		}	else {
			symbTable.classTable.semantError(c.getFilename(), this).println("= operator used on expressions of different types, where at least one was a basic type " + e1.get_type() + " and " +  e2.get_type());
			set_type(TreeConstants.Object_);
		}
		
	}

}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "leq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		e2.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e1.get_type()) || !symbTable.classTable.isSubClass(c.getName(), TreeConstants.Int, e2.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("<= operator used on non-integer expression " + e1.get_type() + " and " +  e2.get_type());
			set_type(TreeConstants.Object_);
		} else
			set_type(TreeConstants.Bool);
	}
		
}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
    /** Creates "comp" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		if (!symbTable.classTable.isSubClass(c.getName(), TreeConstants.Bool, e1.get_type())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("not operator used on non-boolean expression " + e1.get_type());
			set_type(TreeConstants.Object_);
		} else
			set_type(TreeConstants.Bool);
	}
		
}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "int_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
	dump_AbstractSymbol(out, n + 2, token);
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		set_type(TreeConstants.Int);
	}

}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
    /** Creates "bool_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
	dump_Boolean(out, n + 2, val);
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		set_type(TreeConstants.Bool);
	}

}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "string_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
	out.print(Utilities.pad(n + 2) + "\"");
	Utilities.printEscapedString(out, token.getString());
	out.println("\"");
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		set_type(TreeConstants.Str);
	}

}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
    /** Creates "new_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
	dump_AbstractSymbol(out, n + 2, type_name);
	dump_type(out, n);
    }

	public void semant(Class_ c, SymbolTable symbTable) { 
		// substitute for self_type
		AbstractSymbol t = type_name;
		// if (t == TreeConstants.SELF_TYPE)
			// t = c.getName();
		
		// verify type exists
		if (t != TreeConstants.SELF_TYPE && !symbTable.classTable.existsClass(t.getString())) {
			symbTable.classTable.semantError(c.getFilename(), this).println("new expression with undefined type " + t);
			set_type(TreeConstants.Object_);
		} else			
			set_type(t);
	}
}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
    /** Creates "isvoid" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		e1.semant(c, symbTable);
		set_type(TreeConstants.Bool);
	}

}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		set_type(TreeConstants.No_type);
	}

}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
    /** Creates "object" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
	dump_AbstractSymbol(out, n + 2, name);
	dump_type(out, n);
    }
		
	public void semant(Class_ c, SymbolTable symbTable){
		if (name == TreeConstants.self)
			set_type(TreeConstants.SELF_TYPE);
		else if (symbTable.isInTable(name))
			set_type((AbstractSymbol) (symbTable.lookup(name)));
		else {
			symbTable.classTable.semantError(c.getFilename(), this).println("undeclared identifier " + name);
			set_type(TreeConstants.Object_);
		}
	}

}


