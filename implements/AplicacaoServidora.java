package helloservidor;

import java.rmi.*;
public class AplicacaoServidora{
    public static void main(String argv[]){
        HelloServidora objetoServidor;

        try{
            objetoServidor = new HelloServidora();
            Naming.rebind("rmi://localhost:1099/HelloServidora",objetoServidor);
            System.out.println("ObjetoServidor esta ativo!");
        }
        catch(RemoteException re){
            System.out.println("Erro Remoto: "+re.toString());
        }
        catch(Exception e){
            System.out.println("Erro Local: "+e.toString());
        }
    }
}
