package sdi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class HelloServidora extends UnicastRemoteObject implements Hello{
	private static final long serialVersionUID = 1L;

	public HelloServidora() throws RemoteException{
		//TODO
		// Conectar com outro banco
		// chamar o getMessageHistory do outro servidor
		// salvar a lista recupera deste outro servidor
	}
	
	List<MessageData> list = new ArrayList<>();

	@Override
    public void echo(String oqImprimir) throws RemoteException{
    	System.out.println(oqImprimir);
        JOptionPane.showMessageDialog(null,oqImprimir.toUpperCase(),"Mensagem do Objeto Cliente",JOptionPane.INFORMATION_MESSAGE);
        MessageData messageData = storeUserMessage(oqImprimir);
        replicateUserMessage(messageData);
        //TODO: Retornar para o cliente
    }

	@Override
    public MessageData storeUserMessage(String message) {
    	MessageData messageData = new MessageData(message);
    	this.list.add(messageData);
    	return messageData;
    	
    }

	@Override
    public void storeUserMessage(MessageData message) {
    	this.list.add(message);    	
    }

	@Override
    public List<MessageData> getMessageHistory() {
    	return this.list;
    }
	
	private void replicateUserMessage(MessageData messageData) {
		//TODo percorrer com for todos os servidores
		//Conectar em cada um
		//objetoRemoto.storeUserMessage(messageData);
	}

    
    private Hello connect() {
    	return connect(null);
    }
    
    private Hello connect(Integer serverId) {
    	Hello ret = null;
    	
    	if(serverId == null) {

	        List<String> servidores = getListaServidores();
	        for(String name : servidores) {            
	            try{            	
	            	ret = (Hello) Naming.lookup("rmi://localhost/" + name);                
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
    	} else {
    		String serverName = "Server" + serverId;   
    		
            try{            	
            	ret = (Hello) Naming.lookup("rmi://localhost/" + serverName);
            }
            catch(RemoteException re){
                System.out.println("Erro Remoto: "+re.toString());
            }
            catch(Exception e){
                System.out.println("Erro Local: "+e.toString());
            }    		
    	}
        
        return ret;    	
    }
    
    private static List<String> getListaServidores() {
    	List<String> ret = new ArrayList<>();
    	for(int i = 1; i <= AplicacaoServidora.MAX_SERVER_QUANTITY; i++) {
    		ret.add("Server" + i);
    	}
		return ret;    	
    }
}
