import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class ImplementationServer extends UnicastRemoteObject implements IServer{

    public ArrayList<MyWindow> windows;
    private boolean hasText;
    private String []arrayOfWords;
    private final int numberOfCores = 8;
    private HashMap<MyWindow,String> methods;
    private ForkJoinPool forkJoinPool= new ForkJoinPool(numberOfCores);


    public ImplementationServer()throws RemoteException{
        windows = new ArrayList<>();
        arrayOfWords = null;
        hasText = false;
        methods = new HashMap<>();
    }

    @Override
    public void appendTextToServer(String text) throws RemoteException {
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
    public Data getResult(String method, String nameOfWindow) throws RemoteException {
        if (isValid()){
            for (MyWindow window : windows) {
                if (window.getNameOfTheWindow().equals(nameOfWindow)) {
                    if (method.equals("Sequential")) {
                        String[] copy = Arrays.copyOf(arrayOfWords, arrayOfWords.length);
                        return sequentialProcess(copy);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String [] getCurrentWords() {
        return arrayOfWords;
    }

    @Override
    public void limpiar(String nameOfWindow) throws RemoteException {

    }
    private Data sequentialProcess(String[] array)
    {
        long startTime = System.nanoTime();
        System.out.println();
        WordSort words = new WordSort();
        words.sort(array, 0, array.length - 1);
        long finalTime = System.nanoTime() - startTime;
        return new Data(finalTime,array);
    }

    private long executorProcess(String[] array)
    {
        // número de hilos que se utilizarán para ordenar
        int wordsPerThread = array.length / numberOfCores;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfCores);

        for (int i = 0; i < numberOfCores; i++) {
            int start = i * wordsPerThread;
            int end = (i == numberOfCores- 1) ? array.length : (i + 1) * wordsPerThread;

            Runnable task = new MyExecutor(array, start, end);
            executor.execute(task);
        }
        long startTime = System.nanoTime();

        executor.shutdown();

        WordSort wordSort = new WordSort();
        wordSort.sort(array,0,array.length - 1);

        return System.nanoTime() - startTime;
    }


    private long forkProcess(String[] array)
    {
        ForkJoin forkJoin = new ForkJoin(array,0,array.length -1);
        long startTime = System.nanoTime();
        forkJoinPool.invoke(forkJoin);
        return System.nanoTime() - startTime;
    }
    private boolean isValid(){
        return arrayOfWords != null;
    }
}
