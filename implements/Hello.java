package helloservidor;

import java.rmi.*;

public interface Hello extends Remote{
    void imprimirOla(String oqImprimir) throws RemoteException;
}
