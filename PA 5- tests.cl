# Test 1

class Main inherits IO {
	a:Int<-23;
	main():Object{
		{
		out_int(foo(5,13));
		}
	};

	foo(x:Object, a:Int):Int {
		case x of 
			a:A1 => 0;
			a:A => 1;
			a:A2 => 2;
			a:A2m => 3;
			a:Int => a+10;
		esac
	};

};

class Parent {};
class A  inherits Parent {};
class A1  inherits A {};
class A2  inherits A {};
class A2n  inherits A2 {};
class A2m  inherits A2 {};
class B  inherits Parent {};



# Test 2

class Main inherits IO {
	c:C<-new C;
	main():Object{
		out_int(c.b())
	};

};
class B {
	b():Int {0};
};
class C inherits B {
	b():Int {1};
};


# Test 2.5

class Main inherits IO {
	c:C<-new C;
	main():Object{
		out_int(c@B.b())
	};

};
class B {
	b():Int {0};
};
class C inherits B {
	b():Int {1};
};


# Test 3

class Main inherits IO {
	a:Int <- 1;
	main():Object{
		foo(2)
	};
	foo(a:Int):Int{
		a+
		(let a:Int in 3+a)+
		(let a:Int<-3 in 4+a)+
		(let b:Int in 5+a)
	};
};

# Test 4

class Main inherits IO {
	a:Int<-1;
	b:Int<-2;
	c:Object;
	main():Object{{
		out_int(if a<b then 4-1 else 3/5*1);
		out_int(if isvoid a then 0 else 1);
		out_int(if isvoid c then 0 else 1);
	}};
};


# Test 5

class Main inherits IO {
	f:Foo<-new Foo;
	main():Object{
		out_int(a.p)
	};
};
class Foo{
	a:Int;
	p():Int{a};
};

# Test 6

class Main inherits IO {
	b:Int<-1;
	main():Object{
		out_int(foo(3))
	};
	foo(a:Int):Int{a+b+5}
};
