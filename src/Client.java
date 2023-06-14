import javax.swing.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private static String HOST = "localhost";
    private static int PORT = 1005;

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(HOST,PORT);
            IServer server = (IServer) registry.lookup("RMI");
            String nameOfTheWindow = JOptionPane.showInputDialog("Ingrese el nombre del cliente: ");
            new MyWindow(server,nameOfTheWindow);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
