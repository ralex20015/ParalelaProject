import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
    public void appendTextToServer(String textToAppend)throws RemoteException;
    void registerWindow(MyWindow myWindow)throws RemoteException;

    Data getResult(String method, String nameOfWindow) throws RemoteException;
    String [] getCurrentWords() throws RemoteException;
    void limpiar(String nameOfWindow)throws RemoteException;
}
