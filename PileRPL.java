public class PileRPL{
	private int NBOBJMAX;
	private int nbObj;
	private ObjEmpilable[] pile;

PileRPL(){
	this(3);
}
PileRPL(int nbObjMax){
	this.NBOBJMAX = nbObjMax;
	this.nbObj = 0;
	pile = new ObjEmpilable[nbObjMax];
}

public int push(ObjEmpilable obj){
	if (this.isFull())
		return 1;
	pile[nbObj] = obj;
	nbObj++;
	return 0;
}

public ObjEmpilable pop(){
	ObjEmpilable obj = pile[nbObj - 1];
	nbObj--;
	return obj; 
}

public void drop(){
	ObjEmpilable obj = pile[nbObj - 1];
	nbObj--;
}

public void swap(){
	ObjEmpilable tmp = pile[nbObj - 1];
	pile[nbObj - 1] = pile[nbObj - 2];
	pile[nbObj - 2] = tmp;
}

public void clear(){
	this.nbObj = 0;
}

public boolean isEmpty(){
	return (nbObj <= 0);
}

public boolean isFull(){
	return (nbObj >= NBOBJMAX);
}

public int add(){
	ObjEmpilable obj1 = this.pop();
	ObjEmpilable obj2 = this.pop();
	this.push(obj1.add(obj2));
	return 0;
}

public int sou(){
	ObjEmpilable obj1 = this.pop();
	ObjEmpilable obj2 = this.pop();
	this.push(obj2.sou(obj1));
	return 0;
}

public int mul(){
	ObjEmpilable obj1 = this.pop();
	ObjEmpilable obj2 = this.pop();
	this.push(obj2.mul(obj1));
	return 0;
}

public int div(){
	ObjEmpilable obj1 = this.pop();
	ObjEmpilable obj2 = this.pop();
	this.push(obj2.div(obj1));
	return 0;
}

public int getNbObj(){
	return this.nbObj;
}

public String toString(){
	String pile = "";
	int i = this.nbObj - 1;
	while (i >= 0){
		pile += "!" + this.pile[i] + "!\n";
		i--;
	}
	pile += "+-+";
	return pile;
}

public String toHTML(){
	String pile = "";
	int i = this.nbObj - 1;
	while (i >= 0){
		pile += "!" + this.pile[i] + "!</br>";
		i--;
	}
	pile += "+-+";
	return pile;
}

public ObjEmpilable[] getPile(){
	return this.pile;
}

}
