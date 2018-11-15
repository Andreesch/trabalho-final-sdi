import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

      private static void displayStartupMessage() {
          StringBuilder sb = new StringBuilder();
          sb.append("***********************************************************");
          sb.append("******************  Welcome to FooClient  *****************");
          sb.append("***********************************************************");
          System.out.println(sb);
      }
    
    public static void main(String[] args) {
        try {
            Client.displayStartupMessage();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input = "";
            while(!input.equalsIgnoreCase("exit")) {
                System.setSecurityManager(new RMISecurityManager());
        
                String url = "rmi:///Server12345";
        
                MessageInterface server = null;
                Registry r = LocateRegistry.getRegistry(12345);

                server = (MessageInterface)r.lookup("Server2000");
                System.out.println("\nPlease enter a command from the following:\n");
                System.out.println("sdv - Single data value followed by the index (ex 'sdv 4'");
                System.out.println("exit - quit application");
                input = br.readLine();
                
                String values[] = input.split(" ");
                
                if (values[0].equalsIgnoreCase("sdv") && values.length == 2) {      
                    int x = Integer.parseInt(values[1]);
                    int n = server.getSingleDataValue(x);
                    System.out.println("Value at x = " + n);
                }
                else System.out.println("Unknown command");
            }
      } // Catch and display RMI exceptions
      catch (RemoteException e) { 
          e.printStackTrace(); 
      } // Other exceptions are probably user syntax errors, so show usage.
      catch (Exception e) { 
        e.printStackTrace();
        System.err.println("Usage: java [-Dbank=<url>] Bank$Client " + 
                           "<cmd> <name> <password> [<amount>]");
        System.err.println("where cmd is: open, close, deposit, " + 
                           "withdraw, balance, history");
      }
    }
}