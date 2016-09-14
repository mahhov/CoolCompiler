class Main {
   	main() : Int {
       3	
    };
};

class A {x:Int;};
class B inherits A{y:Int; z():Int{x+y};};
class C inherits B{z():Int{x+y};};

class TestGoodOperators {
	x:Int <- 3+1*z;
	z:Int;
	y:Bool <- (false);
	w:Bool <- not y;
	u:Bool <- y=w;
	t:Bool <- not (x<=z);
};

class TestGoodControlers {
	x():A{{
		if true then 3+4 else 4 fi;
		while false loop 3 pool;
		3;
		if true then new A else new B fi;
	}};
};

class TestingLet { 
	scope:Bool <- false;
	x():A{{
		let scope:Int in {scope+2; new A;};
	}};
};

class TestingCase { 
	scope:Bool <- false;
	x():A{{
		case scope of
			scope: Int => scope+1;
		esac;

		case scope of
			x: Bool => new B;
			x: String => new C;
		esac;
	}};
};
