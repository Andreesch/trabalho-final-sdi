package sdi;
import java.rmi.*;
import java.util.List;

public interface RemoteObjectInterface extends Remote{
    String echo(String oqImprimir) throws RemoteException;
	MessageData storeUserMessage(String message) throws RemoteException;
	List<MessageData> getMessageHistory() throws RemoteException;
	MessageData storeUserMessage(MessageData message) throws RemoteException;
}
