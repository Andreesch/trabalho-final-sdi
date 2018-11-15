package sdi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
public class AplicacaoServidora{	
	
	public static final int MAX_SERVER_QUANTITY = 10;

	@SuppressWarnings("resource")
    public static void main(String argv[]){
        HelloServidora objetoServidor;

        try{	
        	String serverId = null;
        	
        	while(serverId == null || !validateServerId(serverId)) {
        		System.out.println("\nDigite um número entre 1 e " + MAX_SERVER_QUANTITY);
				Scanner s = new Scanner(System.in);
				serverId = s.nextLine();
        	}        	
        	
            objetoServidor = new HelloServidora();
            String serverName = "Server" + serverId;
            Naming.rebind("rmi://localhost/" + serverName, objetoServidor);
            System.out.println(serverName + " iniciado!");
        }
        catch(RemoteException re){
            System.out.println("Erro Remoto: "+re.toString());
        }
        catch(Exception e){
            System.out.println("Erro Local: "+e.toString());
        }
    }
    
    private static boolean validateServerId(String idString) {
		Integer id;
    	try {
    		id = Integer.valueOf(idString);
    	} catch(Exception e) {
    		System.out.println("\nValor inválido!");
    		return false;
    	}
    	
    	return id > 0 && id <= MAX_SERVER_QUANTITY;    	
    }
}
