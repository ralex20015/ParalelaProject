import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MyWindow extends JFrame implements ActionListener {

    //private final JTextField txtAmountOfNumbers;
    private JTextArea areaUnorderedWords, areaOrderedWords;
    private JButton btnAdd, btnSequential, btnForkJoin, btnClean, btnExecuteService;

    private JLabel lblExecutor, lblFork, lblSequential, lblQuantityOfWords;
    private String [] arrayOfWords;
    private final int numberOfCores = 8;
    private ForkJoinPool forkJoinPool= new ForkJoinPool(numberOfCores);
    private boolean hasText;
    private Font font = new Font("Segoe UI", Font.PLAIN, 18);
    private IServer server;
    private String nameOfWindow;

    public MyWindow(IServer server, String nameOfWindow) throws RemoteException {
        this.server = server;
        this.nameOfWindow = nameOfWindow;
        initComponents();
        cleanAll();
        server.registerWindow(this);
    }

    public void initComponents(){
        areaUnorderedWords = new JTextArea();
        areaOrderedWords = new JTextArea();
        JScrollPane sp = new JScrollPane(areaUnorderedWords);
        JScrollPane sp2 = new JScrollPane(areaOrderedWords);
        sp.setBounds(20,130,270,420);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sp2.setBounds(340,130,270,420);
        sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        areaUnorderedWords.setBackground(new Color(230,128,65));
        areaOrderedWords.setBackground(new Color(230,128,65));
        btnAdd = new JButton("Agregar texto");
        btnSequential = new JButton("Secuencial");
        btnSequential.setActionCommand("Add");
        btnClean = new JButton("Limpiar");
        btnForkJoin = new JButton("Fork/Join");
        btnExecuteService = new JButton("Execute Service");
        JLabel label = new JLabel("Texto sin ordenar ");
        JLabel lblOrdenado = new JLabel("Texto ordenado");
        lblOrdenado.setFont(font);
        label.setFont(font);
        lblQuantityOfWords = new JLabel("Words: ");
        lblSequential = new JLabel("as");
        lblFork = new JLabel("asd");
        lblExecutor = new JLabel("asdas");

        //We set every component on the window
        label.setBounds(80,45,label.getPreferredSize().width+20,label.getPreferredSize().height);
        lblQuantityOfWords.setBounds(20,60,150,40);
        lblOrdenado.setBounds(400,45,lblOrdenado.getPreferredSize().width+20,lblOrdenado.getPreferredSize().height);
        lblSequential.setBounds(400,0,100,30);
        lblFork.setBounds(500,0,100,30);
        lblExecutor.setBounds(590,0,100,30);
        //txtAmountOfNumbers.setBounds(140,15,200,30);
        btnAdd.setBounds(20,600,btnAdd.getPreferredSize().width,btnAdd.getPreferredSize().height);
        btnSequential.setBounds(150 ,600, btnSequential.getPreferredSize().width, btnSequential.getPreferredSize().height);
        btnClean.setBounds(265 ,600,btnClean.getPreferredSize().width,btnClean.getPreferredSize().height);
        btnForkJoin.setBounds(350 ,600,120,btnForkJoin.getPreferredSize().height);
        btnExecuteService.setBounds(490 ,600,btnExecuteService.getPreferredSize().width + 20,
                btnExecuteService.getPreferredSize().height);
        areaUnorderedWords.setBounds(20,160,270,420);
        areaOrderedWords.setBounds(340,160,270,420);
        areaUnorderedWords.setEditable(false);
        areaOrderedWords.setEditable(false);
        areaOrderedWords.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        areaUnorderedWords.setFont(new Font("Segoe UI",Font.PLAIN, 16));

        setSize(700,700);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        btnAdd.addActionListener(this);
        btnAdd.setActionCommand("Add");
        btnSequential.addActionListener(this);
        btnSequential.setActionCommand("Sequential");
        btnForkJoin.addActionListener(this);
        btnForkJoin.setActionCommand("Fork");
        btnExecuteService.addActionListener(this);
        btnExecuteService.setActionCommand("Executor");
        btnClean.addActionListener(this);
        btnClean.setActionCommand("Clean");

        add(label);
        add(btnAdd);
        add(btnSequential);
        add(btnClean);
        add(btnForkJoin);
        add(btnExecuteService);
        add(sp);
        add(sp2);
        add(lblSequential);
        add(lblFork);
        add(lblExecutor);
        add(lblQuantityOfWords);
        add(lblOrdenado);
    }

    private void cleanAll(){
        areaUnorderedWords.setText("");
        areaOrderedWords.setText("");
//        lblExecutor.setText("");
//        lblFork.setText("");
//        lblSequential.setText("");
    }

    private void generateArrayOfWords() throws RemoteException {
        String text = JOptionPane.showInputDialog(null,
                "Ingresa el texto que deseas:");
//        if(text!=null){
//            if (!text.equals("") && hasText){
//                int conta = 0;
//                String [] temp = Arrays.copyOf(arrayOfWords, arrayOfWords.length + text.split(" ").length);
//                for (int i = arrayOfWords.length; i < temp.length ;conta++, i++) {
//                    temp[i] = text.split(" ")[conta];
//                }
//                arrayOfWords = temp;
//            }
//            if(!text.equals("") && !hasText){
//                arrayOfWords = text.split(" ");
//                hasText = true;
//            }
//        }
        if (text != null){
            server.appendTextToServer(text,nameOfWindow);
        }else {
            JOptionPane.showMessageDialog(this,"Debe ingresar un texto!");
        }

        lblQuantityOfWords.setText("Total palabras: "+arrayOfWords.length);
        setTextOnAreaComponent(areaUnorderedWords, arrayOfWords);
    }

    private void setTextOnAreaComponent(JTextArea textArea, String []arrayOfWords){
        for (int i = 0; i < arrayOfWords.length; i++)
        {
            if (i % 3 == 0){
                textArea.append("\n");
            }
            textArea.append(arrayOfWords[i] + " ");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Add")){
            cleanAll();
            //
            // generateArrayOfWords();
        }
        if (action.equals("Sequential")){
            cleanAll();
            setTextOnAreaComponent(areaUnorderedWords, arrayOfWords);

            String[] copy = arrayOfWords.clone();
            String time = "<html>Secuencial<br>"+sequentialProcess(copy) + " ns"+"</html>";
            lblSequential.setText(time);
            setTextOnAreaComponent(areaOrderedWords,copy);
        }
        if (action.equals("Fork")){
            cleanAll();
            setTextOnAreaComponent(areaUnorderedWords, arrayOfWords);
            String[] copy = arrayOfWords.clone();
            String time = "<html>Fork<br>"+forkProcess(copy) + " ns"+"</html>";
            lblFork.setText(time);
            setTextOnAreaComponent(areaOrderedWords,copy);
        }

        if (action.equals("Executor")){
            cleanAll();
            setTextOnAreaComponent(areaUnorderedWords, arrayOfWords);
            String[] copy = arrayOfWords.clone();
            String time = "<html>Executor<br>"+executorProcess(copy) + " ns"+"</html>";

            lblExecutor.setText(time);
            setTextOnAreaComponent(areaOrderedWords,copy);
        }

        if (action.equals("Clean")){
            clean();
        }
    }

    private long sequentialProcess(String[] array)
    {
        long startTime = System.nanoTime();
                                                                                                                                                System.out.println();
        WordSort words = new WordSort();
        words.sort(array, 0, array.length - 1);
        return System.nanoTime() - startTime;
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

    private void clean(){
        cleanAll();
        arrayOfWords = null;
        hasText = false;
        lblExecutor.setText("");
        lblFork.setText("");
        lblExecutor.setText("");
    }
}
