import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements MessageInterface {

    private static final long serialVersionUID = -4982932302868830173L;
    private ArrayList<Server> servers = new ArrayList<Server>();
    private ArrayList<Integer> data = new ArrayList<Integer>();
    private String serverName;
    private int port;
    
    public Server() throws RemoteException { super(); }
    
    public void broadcastInvalidate(int index) throws RemoteException {
        for(Server s : servers) {
            
        }
    }

    public int[] getDateStore() throws RemoteException {
        int[] intArray = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            intArray[i] = data.get(i);
        }
        return intArray;
    }

    public int getSingleDataValue(int index) throws RemoteException {
        System.out.println("Client requested data at index: " + index);
        return data.get(index);
    }

    public boolean sendBroadcast(int[] data) throws RemoteException {
        return false;
    }

    public boolean sendUpdate(int index, int value) throws RemoteException {
        return false;
    }

    
    public void setServerName(String name) {
        this.serverName = name;
    }
    public String getServerName() {
        return this.serverName;
    }
    public void displayStartupMessage() {
          StringBuilder sb = new StringBuilder();
          sb.append("***********************************************************");
          sb.append("******************  Welcome to FooServer  *****************");
          sb.append("***********************************************************");
          System.out.println(sb);
      }
      
      public void getPort(BufferedReader br) throws IOException {
          System.out.println("****  Please enter a port for the Server to listen on  ****");
          boolean isInteger = false;
          while(!isInteger) {
              System.out.print("****  port: ");
              String port = br.readLine();
              try {
                  this.port = Integer.parseInt(port);
                  isInteger = true;
              } catch(NumberFormatException ne) {
                  System.out.println("****  ERROR: You have entered an invalid port, please try again.");
              }
          }
      }
    
      public void testData() {
          data.add(11);
          data.add(12);
          data.add(13);
          data.add(14);
          data.add(15);
          data.add(16);
          data.add(17);
          data.add(18);
          data.add(19);
      }

	public void setPort(int port) {
		this.port = port;
	}
      
}
