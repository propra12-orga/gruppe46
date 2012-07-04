import java.io.IOException;
import java.io.ObjectOutputStream;
		import java.io.PrintWriter;
		import java.net.ServerSocket;
		import java.net.Socket;
import java.util.Scanner;
		
		
public class Hosting implements Runnable{
Socket client;
	public Hosting(Socket client){
		this.client=client;
	}
	
	public void run() {
				               
				Player p = Game.players.get(1);
				//Handshake und weitere Vorbereitungen
				Scanner in = new Scanner(client.getInputStream());
				PrintWriter out = new PrintWriter(client.getOutputStream());
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				do {}
				while(!in.nextLine().equals("Hallo Server"));
				out.print("Hallo Client");
				do{}
				while(!Main.t1.isAlive());
				out.print(Game.breit);
				out.print(Game.hoch);
				out.flush();
				oos.writeObject(Game.spielfeld);
				oos.flush();
				
				do{}
				while(!in.nextLine().equals("ready"));
				
				//Schleife laeuft solange der Thread von Game lebt
				try {
										
					while (Main.t1.isAlive()){
					String input = in.nextLine();
					
					if (input.equals("rechts")) p.setRightMovement(true); 
					else if (input.equals("links")) p.setLeftMovement(true);
					else if (input.equals("oben")) p.setUpMovement(true);
					else if (input.equals("unten")) p.setDownMovement(true);
					else if (input.equals("bombe")) p.setPlantingBomb(true);
					else if (input.equals("extra")) p.setUsingSpecials(true);
					
					Game.lock1.lock();
					oos.writeObject(Game.spielfeld);
					oos.flush();
					Game.lock1.unlock();
					}
				} catch (IOException e) {

				}
			}
		}

		


