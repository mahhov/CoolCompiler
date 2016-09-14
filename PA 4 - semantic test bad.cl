class A inherits NonExistantClass{};


class B {x:Int; y(x:Int):Int{1};};
class AttrRedefiner inherits B {x:Int;};
class MethodRedefiner inherits B {y(x:Bool):Int{1};};
class MethodRedefiner2 inherits B {y(x:Int,y:Int):Int{1};};
class MethodRedefiner3 inherits B {y(x:Bool,y:Int):Int{1};};


class C {
	x:Int;
	x:String;
	y:Int<-"str";
	z():Int{0};
	z():Int{0};
};


class D {}; class D {};


class E inherits Int{};
class F inherits String{};
class G inherits Bool{};


class H inherits L{}; -- H->L->K->H
class K inherits H{};
class L inherits K{};
class M inherits N{}; -- M->N->O->N
class N inherits O{};
class O inherits N{};
class P inherits P{}; -- P->P


-- of course, you can test only one of these 3 main tests at a time
class Main {
	main() : SELF_TYPE {
       self	
    };
	main() : Int {
       self	
    };
};
class Main { 
   	main(x:Int) : Int {
       self	
    };
};
class Main { -- this is a proper main method/class, removing it will be an error
   	main() : Int {
       3	
    };
};


class Q { 
	x():NoExist{9}; 
};



class R { 
	y(x:NoExist):Int{9}; 	
	z(x:SELF_TYPE):Int{9};
	w(x:Int, x: Int):Int{9}; 	
};


class S { 
	x:NoExist;
};


class T { 
	x:Int<-"s";
	y():Bool{x<-"s"};
};


class IfWhileCase { 
	caseScope:Int <- 1;
	x():Int{{
		if 3 then 0 else 0 fi;
		
		while 0 loop 0 pool;
		
		case noSuchVar of
			x: NoExist => 3;
			x: Int => "duplicate 1";
			x: Int => "duplicate 2";
			caseScope: String => caseScope+1;
			x: Bool => caseScope+1; -- this branch wont be an error
		esac;
		
		9;
	}};
};


class TestingOperators { 
	x():Int{{
		3+"";
		not 3;
		4<"";
		1="";
		9;
	}};
};


class WrongOutputType { 
	x():Int{""};
};


class TestingLet { 
	scopeLet:Int <- 5;
	x():Int{{
		let x:NonExist in 3;
		let x:Int<-"string" in 3;
		let scopeLet:Bool in scopeLet+1;
		let scopeLet:Bool in scopeLet=false; -- this let is good code
		9;
	}};
};


class TestingNew {
	x():Int{{
		new NonExist;
		3;
	}};
};
