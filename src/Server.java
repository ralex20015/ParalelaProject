import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    private static String HOST = "192.168.1.85";
    private static int PORT = 1005;

    public static void main(String[] args) {
        try {
            Registry rmi = LocateRegistry.createRegistry(PORT);
            System.setProperty("java.rmi.server.hostname",HOST);
            rmi.rebind("RMI", new ImplementationServer());
            System.out.println("Servidor Activo");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Servidor No Activo");
        }
    }
}
