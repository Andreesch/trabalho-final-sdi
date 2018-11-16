package sdi;
import java.rmi.ConnectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteObjectInterface extends Remote{
    void connectionTest() throws RemoteException, ConnectException;
    String echo(String oqImprimir) throws RemoteException;
	MessageData storeUserMessage(String message) throws RemoteException;
	List<MessageData> getMessageHistory() throws RemoteException;
	MessageData storeUserMessage(MessageData message) throws RemoteException;
}
