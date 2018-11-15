package helloservidor;

import java.rmi.*;
import javax.swing.*;

public class AplicacaoCliente{
    public static void main(String argv[]){
        Hello objetoRemoto;
        String dados;

        try{
            objetoRemoto = (Hello) Naming.lookup("rmi://localhost:1099/HelloServidora");
            dados = JOptionPane.showInputDialog(null,"Entre com o dado a ser impresso pelo Objeto Remoto","Entrada de Dados",JOptionPane.QUESTION_MESSAGE);
            objetoRemoto.imprimirOla(dados);
        }
        catch(RemoteException re){
            JOptionPane.showMessageDialog(null,"Erro Remoto: "+re.toString(),"Erro Remoto",JOptionPane.WARNING_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro Local: "+e.toString(),"Erro Local",JOptionPane.WARNING_MESSAGE);
        }
    }
}