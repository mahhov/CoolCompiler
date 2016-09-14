class Expr {
	x:X <- "WARMUP FOR TESTING FEATURES";
	expr : Expr <- "Expr";
	expr():Expr{expr <- "Expr"};
	expr : Expr <- "Expr";
	expr(expr:Expr, expr:Expr, expr:Expr):Expr{expr <- "Expr"};
	expr : Expr <- "Expr";
	expr():Expr{"Expr"};
	expr : Expr <- "Expr";
	expr : Expr <- "Expr";
	expr():Expr{not expr};
	expr : Expr <- "Expr";
};

class Dog inherits Cat {
	x:X<- " THIS TESTS CLASSES, FEATURES, AND FORMALS";
	x:X <- 3/0;
	y():Y{{1;2;3;4;5;}};
	x:X;
	y():Y{3};
	y(z:Z):Y{3};
	y(z:Z,z:Z):Y{3};
	y(z:Z,z:Z,z:Z):Y{3};
	y(z:Z,z:Z,z:Z,z:Z,z:Z,z:Z,z:Z,z:Z,z:Z):Y{3};
	x:X<-isvoid while 4 loop ~3 pool;
	y():Y{({({({3;});});})};
};

Class TestingEmptyClass {
};

class WarmUp {
	forTesting():Expressions{{
		"WARM UP FOR TESTING EXPRESSIONS";
		let x:Int, y:Int in x;
		
		case 3 of
			x:X=>3;
			y:Y=>z+y;
			x:Y=>(2*2+9<3);
			z:Z=>{3;1;4;5;1;3;5;5;2;4;};
		esac;
		
		while 3=3 loop  
			case 3 of
				x:X=>let x:Int, y:Int in x;
				y:Y=>if z+y then z else y fi;
				x:Y=>new X;
				z:Z=>isvoid x;
			esac
		pool;
	}};
};

class TestsAll inherits Expressions {
	y():Y{{
		"THIS TESTS SIMPLE EXPRESSIONS";
		x<-3;
		false;
		y<-true;
		z+"asdf"-391;
		z9fuaisdfj;
		(not 9 = 4);
		~9<zit<-pop;
		isvoid 3/a*3-9+f;
		new X;
		{3;};
		2<=3;
	}};
	
	y():Y{{
		"THIS TESTS IF, WHILE, AND DISPATCH";
		while {23;9;~x935;} loop if 3 then while if 9 then x<-3 else (2) fi loop not 3 pool else while 9 loop 1 pool fi pool;
		d();
		d(3);
		d(3,1,4,1,if 3 then x else y fi);
		91+3@TTT.ttt( (xxx), zzz, ({3;311;1;3;}) );
		91+3@TTT.ttt(3,1,4,1,if 3 then x else y fi, 3,1,r);
		91+9.ttt(3,1,4,1,if 3 then x else y fi, 3,1,r);
	}};
	
	y():Y{{
		"THIS TESTS LETS AND CASES";
		"BASIC LETS";
		let a:Int in x;
		let a:Int <- 4 in x;
		let a:Int, y:Y, z:Z<-zz, a:A, a:B, b:B<-c, d:Q in f;
		"NESTED LETS";
		let a:Int, y:Y, z:Z<-zz, a:A, a:B, b:B<-c, d:Q in let a:Int, y:Y, z:Z<-zz, a:A, a:B, b:B<-c, d:Q in let a:Int, y:Y, z:Z<-zz, a:A, a:B, b:B<-c, d:Q in let a:Int, y:Y, z:Z<-zz, a:A, a:B, b:B<-c, d:Q in f;
		"BASIC CASES";
		case 3+3 of x:X=>e; esac;
		case 3 of x:Z=>e; x:X=>ee; fj:X=>e; esac;
		"NESTED CASES";
		case case case 3 of x:Z=>e; x:X=>ee; fj:X=>e; esac of x:Z=>e; x:X=>case case 3 of x:Z=>e; x:X=>ee; fj:X=>e; esac of x:Z=>e; x:X=>ee; fj:X=>e; esac; fj:X=>e; esac of x:Z=>e; x:X=>case 3 of x:Z=>case 3 of x:Z=>e; x:X=>ee; fj:X=>e; esac; x:X=>ee; fj:X=>e; esac; fj:X=>e; esac;
		"MIXING CASE AND LET";
		case let a:Int <- case 3+3 of x:X=>e; esac in x of x:X=>let a:Int <- 4 in case 3+3 of x:X=>e; esac; esac; 
	}};
};

class Precedence {
	x:X<-"TESTING PRECEDENCE";
	precedence() : Precedence{{
		3+3*5+3-(3*5+1)+3*(3-3<1)*3=5-8;
		let x:Int<-3, y:Int in x*5+213+3*5+3-(3*5+1)+3*(3-3<1)*3=5-8;
		isvoid 3 <= ((3<3)=2) +3 *1 *~3 - not x<-4+if x then 4*isvoid 3*4 else while ~x.r() loop not 3+9 pool fi;
		"LET ambiguity";
		let x:X in 3*5;
		let x:X in 3+5;
		let x:X in 3+(x<-4);
		let x:X in x.hello();
		let x:X in x.hello()+3;
		let x:X in x = 4;
	}};
};
