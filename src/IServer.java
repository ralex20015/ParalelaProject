import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
    public void appendTextToServer(String textToAppend, String nameOfWindow)throws RemoteException;
    void registerWindow(MyWindow myWindow)throws RemoteException;
    void methodRequested() throws RemoteException;
    void limpiar(String nameOfWindow)throws RemoteException;
}
