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
				//in = new DataInputStream(client.getInputStream());
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
				//out = new DataOutputStream(client.getOutputStream());
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
	
	public void sendmap (Feld[][] spielfeld){
		try {
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
			//while(in.available()>0) {
			//	out = in.read();
			//}
			
			
				
				Game.spielfeld= (Feld[][]) ois.readObject();
				
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
}
