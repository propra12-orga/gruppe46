import java.io.*;
import java.net.*;

/**
 * 
 */

/**
 * @author Simon
 * Klasse um netzwerk verbindungsaufbau und datenuebertragung zu handlen
 */
public class Network {
	
	public static String hostname = "127.0.0.1";
	
	private boolean host = false;
	private ServerSocket server;
	private Socket client;
	private DataOutputStream out;
	private DataInputStream in;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ObjectInputStream ois2;
	private ObjectOutputStream oos2;
	private boolean connected = false;
	
	public Network(boolean hosting) {
		host = hosting;
		if(host) {
			try {
				server = new ServerSocket(12346);
				server.setSoTimeout(100);
			} catch ( InterruptedIOException e1 ) {
				  e1.printStackTrace();
			} catch (SocketException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		} else {
			try {
				client = new Socket(hostname,12346);
				out = new DataOutputStream(client.getOutputStream());
				ois2 = new ObjectInputStream(client.getInputStream());
				ois= new ObjectInputStream(client.getInputStream());
				connected = true;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void destroy() {
		if(host) {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(client != null) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isHost() {
		return host;
	}

	public void setHost(boolean host) {
		this.host = host;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	public boolean pollConnect() {
		if(host) {
			if(connected) return true;
			try{
				client = server.accept();
				connected = true;
				oos2 = new ObjectOutputStream(client.getOutputStream());
				in = new DataInputStream(client.getInputStream());			
				oos = new ObjectOutputStream(client.getOutputStream());
			} catch (SocketTimeoutException e) {
				connected = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return connected;
		} else {
			return true;
		}
	}
	
	public void send(int num) {
		try {
			out.write(num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendmap (Feld[][] spielfeld, int[] coords){
		try {
			oos2.reset();
			oos2.writeObject(coords);
			oos2.flush();
			oos.reset();
			oos.writeObject(spielfeld);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int recv() {
		int out = -1;
		try {
			if (this.isHost()){
				while(in.available()>0) {
					out = in.read();
				switch(out){
				case 1: Game.getPlayer(1).setRightMovement(true);break;
				case 2: Game.getPlayer(1).setLeftMovement(true);break;
				case 3: Game.getPlayer(1).setUpMovement(true);break;
				case 4: Game.getPlayer(1).setDownMovement(true);break;
				case 5: Game.getPlayer(1).setPlantingBomb(true);break;
				case 6: Game.getPlayer(1).setUsingSpecials(true);break;
				}
			}
			}

			
			
			if (!(this.isHost())){	
				int[] coordsInput=(int[]) ois2.readObject();
				Game.spielfeld= (Feld[][]) ois.readObject();
				if (0==coordsInput[0] && 0==coordsInput[1] && 0==coordsInput[2] && 0==coordsInput[3]){
					System.exit(1);
				} 
				Game.getPlayer(0).setx( coordsInput[0]);
				Game.getPlayer(0).sety( coordsInput[1]);
				Game.getPlayer(1).setx( coordsInput[2]);
				Game.getPlayer(1).sety( coordsInput[3]);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e){}
		return out;
	}
}
