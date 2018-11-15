import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MasterServer {

    public void displayStartupMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("***********************************************************");
        sb.append("******************  Welcome to FooServer  *****************");
        sb.append("***********************************************************");
        System.out.println(sb);
    }

    public int getPort(BufferedReader br) throws IOException {
        System.out.println("**** Please enter a port for the Server to listen on, enter 0 to finish ****");
        int port = 0;
        while (true) {
            System.out.print("****  port: ");
            String ports = br.readLine();
            System.out.println("You entered port: " + ports + "\n");
            try {
                port = Integer.parseInt(ports);
                break;
            } catch (NumberFormatException ne) {
                System.out.println("****  ERROR: You have entered an invalid port, please try again.");
            }
        }
        return port;
    }

    private void createServers() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int port = 0;
        displayStartupMessage();
        while(true) {
            port = getPort(br);
            if(port==0)break;
            Server server = new Server();
            server.testData();
            server.setServerName("Server" + port);
            server.setPort(port);
            UnicastRemoteObject.unexportObject(server, true);
            MessageInterface stub = (MessageInterface) UnicastRemoteObject
                    .exportObject(server, port);
            Registry registry = LocateRegistry.createRegistry(port);

            try {
                registry.bind(server.getServerName(), stub);
            } catch (AlreadyBoundException ae) {
                registry.rebind(server.getServerName(), stub);
            }
            System.out.println("Listening on port: " + port + " to server: "
                    + server.getServerName());
        }
    }

    public static void main(String[] args) {
        try {
            MasterServer ms = new MasterServer();
            ms.createServers();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // Force an exit because there might be other threads
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1); // Force an exit because there might be other threads
        }
    }
}   
