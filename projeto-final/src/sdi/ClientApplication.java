package sdi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ClientApplication{
	public static String DEFAULT_ERROR_CONNECTION_MESSAGE = "Não foi possível conectar a nenhum servidor. Por favor, certifique-se que há pelo menos um servidor ligado!\n";
		
    public static void main(String argv[]){
    	createMenuToUser();       
        
    }
    
    private static void createMenuToUser() {
    	System.out.println("Servidor de ECHO - Versão Cliente");
    	System.out.println("\n----- Opções disponíveis -----");
    	System.out.println("[1] - Enviar mensagem ao servidor");
    	System.out.println("[2] - Obter histórico de mensagens enviadas");
    	System.out.println("[0] - Sair");
    	System.out.println("----- Opções disponíveis -----\n");
    	
    	Integer choice = null;
    	
    	do {
    		System.out.print("Opção desejada: ");
        	Scanner scanner = new Scanner(System.in);
    	    choice = scanner.nextInt();
    	
	    	// envio de msg
	    	if (choice == 1) {
	        	sendMessage();
	    	} else if (choice == 2) {
	    		getAndShowMessageHistory();
	    	} else if (choice == 0) {
	    		return;
	    	} else {
	    		System.out.println("\nOpção inválida! digite uma opção informada acima.");
	    	}
	    	
    	} while (choice != 0);
    }
    
    private static RemoteObjectInterface connect() {
    	RemoteObjectInterface ret = null;

        List<String> servidores = getServerList();
        for(String name : servidores) {            
            try{            	
            	ret = (RemoteObjectInterface) Naming.lookup("rmi://localhost/" + name);            	
           		ret.connectionTest();            	
                break;
            }
            catch(RemoteException re){
        		//Servidor registrado, mas não conectado, irá passar para o próximo
            	ret = null;
            	continue;
            }
            catch(Exception e){
        		//Servidor registrado, mas não conectado, irá passar para o próximo
            	ret = null;
            	continue;
            }
        }
        
        if(ret == null) {
        	System.out.println(DEFAULT_ERROR_CONNECTION_MESSAGE);
        }
        
        return ret;    	
    }
    
    private static void sendMessage() {

        String dados;        
        dados = JOptionPane.showInputDialog(null,"Entre com o dado a ser impresso pelo Objeto Remoto","Entrada de Dados",JOptionPane.QUESTION_MESSAGE);
        
        if (dados == null) {
        	System.out.println("Não inseriu nenhuma mensagem, retornando ao menu.\n");
        	return;
        }
        
        RemoteObjectInterface objetoRemoto;
        objetoRemoto = connect();
        
        if(objetoRemoto == null) {
        	System.out.println(DEFAULT_ERROR_CONNECTION_MESSAGE);
        	return;
        }
        
        try {
			String serverResponse = objetoRemoto.echo(dados);
			
			if (serverResponse != null && !serverResponse.isEmpty()) {
				System.out.println(serverResponse + "\n");
			}
		} catch (RemoteException e) {
			// TODO Mostrar mensagem de erro ao usuário
			System.out.println(DEFAULT_ERROR_CONNECTION_MESSAGE);
		}
    }
    
    private static void getAndShowMessageHistory() {
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        RemoteObjectInterface objetoRemoto;
        objetoRemoto = connect();
        
        if(objetoRemoto == null) {
        	return;
        }
    	
        System.out.println();
        
        try {
			List<MessageData> list = objetoRemoto.getMessageHistory();
			
			if (list == null || list.size() == 0) {
				System.out.println("Não há nenhuma mensagem no histórico!");
				return;
			}
			
			for (MessageData messageData : list) {
				System.out.println("["+ sdf.format(messageData.getDate()) + "]" + ": " +messageData.getMessage());
			}
			
			System.out.println();
			
		} catch (RemoteException e) {
			// TODO Mostrar mensagem de erro ao usuário
			System.out.println(DEFAULT_ERROR_CONNECTION_MESSAGE);
		}
    }
    
    private static List<String> getServerList() {
    	List<String> ret = new ArrayList<>();
    	for(int i = 1; i <= ServerApplication.MAX_SERVER_QUANTITY; i++) {
    		ret.add("Server" + i);
    	}
		return ret;    	
    }
}