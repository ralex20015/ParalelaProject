import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class ImplementationServer extends UnicastRemoteObject implements IServer{

    public ArrayList<MyWindow> windows;
    private boolean hasText;
    private String []arrayOfWords;
//    private String[] method;


    public ImplementationServer()throws RemoteException{
        windows = new ArrayList<>();
        arrayOfWords = null;
        hasText = false;
    }

    @Override
    public void appendTextToServer(String text, String nameOfWindow) throws RemoteException {
        if(text!=null){
            if (!text.equals("") && hasText){
                int conta = 0;
                String [] temp = Arrays.copyOf(arrayOfWords, arrayOfWords.length + text.split(" ").length);
                for (int i = arrayOfWords.length; i < temp.length ;conta++, i++) {
                    temp[i] = text.split(" ")[conta];
                }
                arrayOfWords = temp;
            }
            if(!text.equals("") && !hasText){
                arrayOfWords = text.split(" ");
                hasText = true;
            }
        }
    }

    @Override
    public void registerWindow(MyWindow myWindow) throws RemoteException {
        this.windows.add(myWindow);
    }

    @Override
    public void methodRequested() throws RemoteException {

    }

    @Override
    public void limpiar(String nameOfWindow) throws RemoteException {

    }
}
