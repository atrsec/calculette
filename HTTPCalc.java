import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPCalc{

	private BufferedReader in;
	private PrintStream out;
	private PileRPL pile;

HTTPCalc(int taillePile, String args[])throws Exception{
	pile = new PileRPL(taillePile);
	init_flux(args);
	String log = "";
	boolean loop = true;
	while(loop){
		String str = lecture();
		log += str + "\n";
		System.out.print(str);
		out.print(str);
	}
/*	if (!(args.length == 2 && args[0].equals("local")) && args.length != 3){
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
	}*/
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

public void parse(String op)throws NumberFormatException{
	String[] operands = op.split(" ");
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
		HTTPCalc HTTPCalc = new HTTPCalc(50, args);
	} catch(IOException e){
		if (args.length == 1)
			System.err.println("Ouverture du fichier " + args[0] + " impossible !");
		else if (args.length == 2)
			System.err.println("Ouverture du socket 127.0.0.1:" + args[1] + " impossible !");
		System.exit(1);
	}
}

public String lecture() throws Exception {
	String request = "";
	String newLine = "";
	int length = 0;
	while (!(newLine = this.in.readLine()).equals("") && newLine != null){
		request += newLine + "\n";
	}
	int indexQm = request.indexOf("?");
	if (indexQm == -1)
		return response(0);
	String uri = request.substring(indexQm + 1, request.indexOf("HTTP") - 1);
	String encodedOp = uri.substring(uri.indexOf("=") + 1, uri.length());
	try {
		parse(URLDecoder.decode(encodedOp, "UTF-8"));
		return response(0);
	} catch(NumberFormatException e){
		return response(1);
	}
}

public String response(int code){
	String body = "<form action=\"http://localhost:4444\">" + "Operation:<br>" + "<input type=\"text\" name=\"operation\" value=\"\"><br>";
	if (code == 1)
		body += "<br>Syntax ERROR<br>";
	body += "Pile:<br>" + this.pile.toHTML() + "<br><br>" + "<input type=\"submit\" value=\"Submit\"></form>";
	String header = "HTTP/1.1 200 OK\nHost: localhost:4444\nConnection: Keep-alive\nContent-Length: " + body.length() + "\n\n";
	return header + body;

}

}

