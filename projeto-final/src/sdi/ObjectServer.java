package sdi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ObjectServer extends UnicastRemoteObject implements RemoteObjectInterface{
	private static final long serialVersionUID = 1L;
	
	String serverName;

	public ObjectServer(String serverName) throws RemoteException{
		
		this.serverName = serverName;
		
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
    	storeUserMessage(messageData);
    	return messageData;
    	
    }

	@Override
    public MessageData storeUserMessage(MessageData message) {
		System.out.println("Salvando mensagem: " + message.getMessage());
    	this.list.add(message);
		return message;
    }

	@Override
    public List<MessageData> getMessageHistory() {
    	return this.list;
    }
	
	private void replicateUserMessage(MessageData messageData) {
		List<String> listaServidores = getListaServidores();
		for (String serverName : listaServidores) {
			RemoteObjectInterface remoteObject = connect(serverName);
			
			if(remoteObject == null) {
				System.out.println("Não foi possível replicar para o servidor " + serverName);
				continue;
			}

	        try {
	        	remoteObject.storeUserMessage(messageData);
			} catch (RemoteException e) {
				System.out.println("Não foi possível replicar para o servidor " + serverName);
			}
		}
	}

    
    private RemoteObjectInterface connect() {
    	return connect(null);
    }
    
    private RemoteObjectInterface connect(String serverNameParam) {
    	RemoteObjectInterface ret = null;
    	
    	if(serverNameParam == null) {

	        List<String> servidores = getListaServidores();
	        for(String serverName : servidores) {            
	            try{            	
	            	ret = (RemoteObjectInterface) Naming.lookup("rmi://localhost/" + serverName);                
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
            try{            	
            	ret = (RemoteObjectInterface) Naming.lookup("rmi://localhost/" + serverNameParam);
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
    	for(int i = 1; i <= ServerApplication.MAX_SERVER_QUANTITY; i++) {
    		String serverName = "Server" + i;
    		if(!serverName.equals(serverName)) {
    			ret.add("Server" + i);
    		}
    	}
		return ret;    	
    }
}
