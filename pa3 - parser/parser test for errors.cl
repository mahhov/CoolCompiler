class GoodClass{};
class BadClasses should be able to be skipped by our parser, and it should be able to move on to the next class;
class GoodClass{};
class badClass1{};
class GoodClass{};
class BadClass2(){};
class GoodClass{};
class BadClass3;
class GoodClass{};
class a BadClass4;
class GoodClass{};
class BadClass5 inherits;
class GoodClass{};
(* the 4 tests provided for us *)
Class b inherits A {};
Class C inherits a {};
Class D inherts A {};
Class E inherits A {;


class BadCode {
	x:X<-"BADD CeDO Incoomgni";

	empty() : block{{	}};

	identifer(): forType{};
	Type(): forIdentifier{};

	missing(): Params {{
		let in x;
		let x<-3 in x;
		let x:X,x in x;
		let x:X, in x;
		asWeSaw: OurParserCan <- "recover from bad expressions within a block expression"
		andItCanMove: OnToTheNext <- "expression within the block";
	}};
	
	missingOperands: X <- +3;
	missingOperands: X <- +3;
	else: KeyWordIdentifiers <- 4;
	else: KeyWordIdentifiers <- 4;
	keyWordTypes: else <- 4;
	keyWordTypes: else <- 4;
	
	unassociative:comparers <- 3<3<3;
	unassociative:comparers <- 3<(3<3);
	unassociative:comparers <- (3<3)<3;
	unassociative:comparers <- (3<3<3);
	unassociative:comparers <- (3<(3)<3);
	unassociative:comparers <- (3)<3<3;
	
	recovering(): FromLetBindingErrors{
		let a:A<-1, a, b:B<-3, b<-4, c:C<-5, C<-6 in "that should get us 3 errors, one for each of the bad bindings of the 6 total provided"
	};
	
	asWeSaw: OurParserCan <- "recover from bad features and move on to the next features";
	
};