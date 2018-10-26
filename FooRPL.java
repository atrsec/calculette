public class FooRPL{
	public static void main(String[] args){
		PileRPL pile = new PileRPL();
		ObjEmpilable oe1 = new ObjEmpilable(21);
		ObjEmpilable oe2 = new ObjEmpilable(42);
		pile.push(oe1);
		System.out.println(pile);
		pile.push(oe2);
		System.out.println(pile);
		pile.add();
		System.out.println(pile);
		//SOU
		pile.push(oe2);
		System.out.println(pile);
		pile.sou();
		System.out.println(pile);
		//MUL
		ObjEmpilable oe3 = new ObjEmpilable(2);
		pile.push(oe3);
		System.out.println(pile);
		pile.mul();
		System.out.println(pile);
		//DIV
		pile.push(oe3);
		System.out.println(pile);
		pile.div();
		System.out.println(pile);
		pile.push(oe3);
		pile.push(oe3);
		pile.push(oe3);
		//test max
		pile.push(oe3);
		pile.push(oe3);
		pile.push(oe3);
		pile.push(oe3);
		pile.push(oe3);
		int i = pile.push(oe3);
		System.out.println(pile);

		System.out.println(i);
		
	}
}
