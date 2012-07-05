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
	
	public String hostname = "127.0.0.1";
	
	private boolean host = false;
	private ServerSocket serverObject;
	private ServerSocket serverData;
	private Socket clientObject;
	private Socket clientData;
	private DataOutputStream out;
	private DataInputStream in;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private boolean connected = false;
	
	public Network(boolean hosting, String hostname) {
		host = hosting;
		if(host) {
			try {
				serverObject = new ServerSocket(12345);
				serverObject.setSoTimeout(100);
				serverData = new ServerSocket(12346);
				serverData.setSoTimeout(100);
			} catch ( InterruptedIOException e1 ) {
				  e1.printStackTrace();
			} catch (SocketException e2) {
				e2.printStackTrace();
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		} else {
			this.hostname = hostname;
			try {
				clientObject = new Socket(hostname,12345);
				clientData = new Socket(hostname,12346);
				out = new DataOutputStream(clientData.getOutputStream());
				in = new DataInputStream(clientData.getInputStream());
				ois= new ObjectInputStream(clientObject.getInputStream());
				oos= new ObjectOutputStream(clientObject.getOutputStream());
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
				serverObject.close();
				serverData.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(clientObject != null) clientObject.close();
				if(clientData != null) clientData.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				clientObject.close();
				clientData.close();
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
				clientData = serverData.accept();
				clientObject = serverObject.accept();
				connected = true;
				out = new DataOutputStream(clientData.getOutputStream());
				in = new DataInputStream(clientData.getInputStream());			
				oos = new ObjectOutputStream(clientObject.getOutputStream());
				ois = new ObjectInputStream(clientObject.getInputStream());
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
	
	public String getHostname() {
		if(host) {
			try {
				return InetAddress.getLocalHost().toString();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hostname;
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
			while(in.available()>0) {
				out = in.read();
			}
			
			if(!host) {
				Game.spielfeld= (Feld[][]) ois.readObject();
			}
				
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
}
