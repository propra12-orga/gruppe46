import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;


public class Client implements Runnable{
Socket zumServer;
	
	public Client(InetAddress ip) throws IOException{
				zumServer= new Socket(ip,12345);
		
	}
	public void run() {
		try {
		Scanner in = new Scanner(zumServer.getInputStream());
		PrintWriter out = new PrintWriter(zumServer.getOutputStream());		
		ObjectInputStream ois= new ObjectInputStream(zumServer.getInputStream());
		out.print("Hallo Server");
		do{}
		while(!in.nextLine().equals("Hallo Client"));
		int breit = in.nextInt();
		int hoch = in.nextInt();
		Object spielfeld=  new Feld[breit][hoch];
		spielfeld=ois.readObject();
		out.print("ready");
		out.flush();
		//Renderer einrichten?
		while (zumServer.isConnected()){
			//Eingabe abfragen
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {out.print("links");out.flush();}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {out.print("rechts");out.flush();}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {out.print("oben");out.flush();}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {out.print("unten");out.flush();}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {out.print("bombe");out.flush();}
			if (Keyboard.isKeyDown(Keyboard.KEY_RMENU)) {out.print("extra");out.flush();}
			spielfeld=ois.readObject();
			//aktuelles Spielfeld zeichnen?
			
		}
		
		
		in.close();
		out.close();
		ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
