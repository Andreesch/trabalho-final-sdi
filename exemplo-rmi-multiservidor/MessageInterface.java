import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageInterface extends Remote {
    boolean sendUpdate(int index, int value) throws RemoteException;
    
    boolean sendBroadcast(int data[]) throws RemoteException;
    
    void broadcastInvalidate(int index) throws RemoteException;
    
    int[] getDateStore() throws RemoteException;
    
    int getSingleDataValue(int index) throws RemoteException;
}