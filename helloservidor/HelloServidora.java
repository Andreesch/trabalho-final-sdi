package helloservidor;

import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.swing.*;
import java.rmi.server.UnicastRemoteObject;

public class HelloServidora extends UnicastRemoteObject implements Hello{
    public HelloServidora() throws RemoteException{}

    public void imprimirOla(String oqImprimir) throws RemoteException{
        JOptionPane.showMessageDialog(null,oqImprimir.toUpperCase(),"Mensagem do Objeto Cliente",JOptionPane.INFORMATION_MESSAGE);
    }
}
