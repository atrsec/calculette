import java.io.*;
import java.net.*;
import java.util.*;

public class UIRPL{

	private BufferedReader in;
	private PrintStream out;
	private PileRPL pile;

UIRPL(int taillePile, String args[])throws IOException{
	pile = new PileRPL(taillePile);
	init_flux(args);
	String log = "";
	boolean loop = true;
	while(loop){
		String str = in.readLine();
		log += str + "\n";
		if (str.equals("exit"))
			loop = false;
		else {
			String[] operands = str.split(" ");
			try {
				parse(operands);
			}catch (NumberFormatException e){
				out.println("Syntax error !");
			}
			out.println(pile);
		}
	}
	if (!(args.length == 2 && args[0].equals("local")) && args.length != 3){
		out.println("Voulez-vous sauvegardez votre session ? [filename/non]");
		String logFile = in.readLine();
		//LOG
		if (logFile != null && !logFile.toLowerCase().equals("non")){
			try {
				logSession(logFile, log);
			} catch (IOException e) {
				out.println("La sauvegarde des logs a échoué");
			}
		}
	}
	//Close file
	try {
		exit();
	} catch (IOException e) {
		out.println("Erreur à la fermeture des fichiers");
	}
}

public void exit() throws IOException {
	this.in.close();
	this.out.close();
}

public void logSession(String filename, String logs) throws IOException{
	PrintWriter logFile = new PrintWriter( new BufferedWriter (new FileWriter(filename)));
	logFile.write(logs);
	logFile.close();
}


public void init_flux(String args[]) throws IOException{
	if (args.length == 0 || (args.length == 1 && args[0].toLowerCase().equals("local"))){
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = new PrintStream(System.out);
	}
	else if (args.length == 2 && args[0].toLowerCase().equals("local")){
		this.in = new BufferedReader( new FileReader(args[1]) );
		this.out = new PrintStream(System.out);
	}
	else if ((args.length == 3 || args.length == 2) && args[0].toLowerCase().equals("network")){
		ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[1]));
		Socket socket = serverSocket.accept();
		this.out = new PrintStream(socket.getOutputStream());
		if (args.length == 3)
			this.in = new BufferedReader(new FileReader(args[2]));
		else
			this.in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	else
		usage();
}

public void parse(String[] operands)throws NumberFormatException{
	for (int i = 0; i < operands.length; i++){
		try{
			switch (operands[i]) {
				case("+"):
				case("add"):
					this.pile.add();
					break;
				case("-"):
				case("sou"):
					this.pile.sou();
					break;
				case("*"):
				case("mul"):
					this.pile.mul();
					break;
				case("/"):
				case("div"):
					this.pile.div();
					break;
				case("push"):
					break;
				case("pop"):
					this.pile.pop();
					break;
				case("drop"):
					this.pile.drop();
					break;
				case("swap"):
					this.pile.swap();
					break;
				case("clear"):
					this.pile.clear();
					break;
				default:
					this.pile.push(new ObjEmpilable(Integer.parseInt(operands[i])));
					break;
		}} catch (ArrayIndexOutOfBoundsException e){
			this.out.println("Pas assez d'éléments dans la pile !");
		}
	}
}

public void usage(){
	System.err.println("Syntax error !!! USAGE:\njava UIRPL:\n\trun calculator in interactive mode.\njava UIRPL local:\n\trun calculator in interactive mode.\njava UIRPL local LOGFILENAME:\n\tredo the session in LOGFILENAME\njava network PORT:\n\tCreate socket localhost:PORT and run calculatorover the network.\njava network PORT LOGFILENAME:\n\tCreate socket localhost:PORT and redo the session in LOGFILENAME");
	System.exit(1);
}


public static void main(String args[]) throws Exception{
	try{
		UIRPL uiRPL = new UIRPL(50, args);
	} catch(IOException e){
		if (args.length == 1)
			System.err.println("Ouverture du fichier " + args[0] + " impossible !");
		else if (args.length == 2)
			System.err.println("Ouverture du socket 127.0.0.1:" + args[1] + " impossible !");
		System.exit(1);
	}
}
}

