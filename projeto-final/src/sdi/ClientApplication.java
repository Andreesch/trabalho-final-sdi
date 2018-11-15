package sdi;

import java.rmi.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ClientApplication{
		
    public static void main(String argv[]){    	
    	sendMessage();
    	getAndShowMessageHistory();       
        
    }
    
    private static RemoteObjectInterface connect() {
    	RemoteObjectInterface ret = null;

        List<String> servidores = getListaServidores();
        for(String name : servidores) {            
            try{            	
            	ret = (RemoteObjectInterface) Naming.lookup("rmi://localhost/" + name);                
                break;
            }
            catch(RemoteException re){
                System.out.println("Erro Remoto: "+re.toString());
            }
            catch(Exception e){
                System.out.println("Erro Local: "+e.toString());
            }
        }
        
        if(ret == null) {
        	System.out.println("Não foi possível conectar em nenhum servidor!"); //TODO reescrever
        }
        
        return ret;    	
    }
    
    private static void sendMessage() {

        String dados;        
        dados = JOptionPane.showInputDialog(null,"Entre com o dado a ser impresso pelo Objeto Remoto","Entrada de Dados",JOptionPane.QUESTION_MESSAGE);
        
        RemoteObjectInterface objetoRemoto;
        objetoRemoto = connect();
        
        if(objetoRemoto == null) {
        	System.out.println("Não foi possível conectar em nenhum servidor!"); //TODO reescrever
        	return;
        }
        
        try {
			objetoRemoto.echo(dados);
		} catch (RemoteException e) {
			// TODO Mostrar mensagem de erro ao usuário
			e.printStackTrace();
		}
    }
    
    private static void getAndShowMessageHistory() {
        RemoteObjectInterface objetoRemoto;
        objetoRemoto = connect();
        
        if(objetoRemoto == null) {
        	return;
        }
    	
        try {
			List<MessageData> list = objetoRemoto.getMessageHistory();
			
			for (MessageData messageData : list) {
				System.out.println(messageData.getMessage()); //TODO exibir data
			}
			
		} catch (RemoteException e) {
			// TODO Mostrar mensagem de erro ao usuário
			e.printStackTrace();
		}
    }
    
    private static List<String> getListaServidores() {
    	List<String> ret = new ArrayList<>();
    	for(int i = 1; i <= ServerApplication.MAX_SERVER_QUANTITY; i++) {
    		ret.add("Server" + i);
    	}
		return ret;    	
    }
}