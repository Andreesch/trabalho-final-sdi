package sdi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
public class ServerApplication{	
	
	public static final int MAX_SERVER_QUANTITY = 10;

	@SuppressWarnings("resource")
    public static void main(String argv[]){
        ObjectServer objetoServidor;
        System.out.println("Servidor de ECHO - Versão Cliente");
        
        try{	
        	String serverId = null;
        	
        	while(serverId == null || !validateServerId(serverId)) {
        		
        		System.out.println("Para o IDENTIFICADOR do servidor, digite um número entre 1 e " + MAX_SERVER_QUANTITY + ":");
				Scanner s = new Scanner(System.in);
				serverId = s.nextLine();
        	}        	

            String serverName = "Server" + serverId;
            objetoServidor = new ObjectServer(serverName);
            Naming.rebind("rmi://localhost/" + serverName, objetoServidor);
            System.out.println("\n" + serverName + " iniciado!");
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
    		System.out.println("\nValor inválido!\n");
    		return false;
    	}
    	
    	boolean ret = id > 0 && id <= MAX_SERVER_QUANTITY;
    	
    	if (!ret) {
    		System.out.println("\nValor inválido!\n");
    		return false;
    	} else {
    		return true;
    	}
    }
}
