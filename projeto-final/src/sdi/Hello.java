package sdi;
import java.rmi.*;
import java.util.List;

public interface Hello extends Remote{
    void echo(String oqImprimir) throws RemoteException;
	MessageData storeUserMessage(String message) throws RemoteException;
	List<MessageData> getMessageHistory() throws RemoteException;
	void storeUserMessage(MessageData message);
}
