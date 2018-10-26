public class ObjEmpilable{

	protected int value;

ObjEmpilable(int value){
	this.value = value;
}

public ObjEmpilable add(ObjEmpilable obj){
	ObjEmpilable newObj = new ObjEmpilable(this.value + obj.value);
	return newObj;
}

public ObjEmpilable sou(ObjEmpilable obj){
	ObjEmpilable newObj = new ObjEmpilable(this.value - obj.value);
	return newObj;
}

public ObjEmpilable mul(ObjEmpilable obj){
	ObjEmpilable newObj = new ObjEmpilable(this.value * obj.value);
	return newObj;
}

public ObjEmpilable div(ObjEmpilable obj){
	ObjEmpilable newObj = new ObjEmpilable(this.value / obj.value);
	return newObj;
}

public String toString(){
	return this.value + "";
}
}
