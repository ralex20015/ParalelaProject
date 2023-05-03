import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MyWindow extends JFrame implements ActionListener {

    //private final JTextField txtAmountOfNumbers;
    private JTextArea areaUnorderedWords, areaOrderedWords;
    private JButton btnAdd, btnSequential, btnForkJoin, btnClean, btnExecuteService;

    private JLabel lblExecutor, lblFork, lblSequential;
    private String [] arrayOfWords;
    private final int numberOfCores = 8;
    private ForkJoinPool forkJoinPool= new ForkJoinPool(numberOfCores);

    public MyWindow(){
        initComponents();
        cleanAll();
    }

    public void initComponents(){
        areaUnorderedWords = new JTextArea();
        areaOrderedWords = new JTextArea();
        JScrollPane sp = new JScrollPane(areaUnorderedWords);
        JScrollPane sp2 = new JScrollPane(areaOrderedWords);
        sp.setBounds(20,60,270,420);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sp2.setBounds(340,60,270,420);
        sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        areaUnorderedWords.setBackground(new Color(230,128,65));
        areaOrderedWords.setBackground(new Color(230,128,65));
        btnAdd = new JButton("Agregar datos");
        btnSequential = new JButton("Secuencial");
        btnSequential.setActionCommand("Add");
        btnClean = new JButton("Limpiar");
        btnForkJoin = new JButton("Fork/Join");
        btnExecuteService = new JButton("Execute Service");
        JLabel label = new JLabel("Ingrese el texto: ");
        lblSequential = new JLabel("as");
        lblFork = new JLabel("asd");
        lblExecutor = new JLabel("asdas");

        //We set every component on the window
        label.setBounds(20,20,label.getPreferredSize().width+20,label.getPreferredSize().height);
        lblSequential.setBounds(400,0,100,30);
        lblFork.setBounds(500,0,100,30);
        //txtAmountOfNumbers.setBounds(140,15,200,30);
        btnAdd.setBounds(20,500,btnAdd.getPreferredSize().width,btnAdd.getPreferredSize().height);
        btnSequential.setBounds(150 ,500, btnSequential.getPreferredSize().width, btnSequential.getPreferredSize().height);
        btnClean.setBounds(245 ,500,btnClean.getPreferredSize().width,btnClean.getPreferredSize().height);
        btnForkJoin.setBounds(335 ,500,120,btnForkJoin.getPreferredSize().height);
        btnExecuteService.setBounds(470 ,500,btnExecuteService.getPreferredSize().width + 20,
                btnExecuteService.getPreferredSize().height);
        areaUnorderedWords.setBounds(20,60,270,420);
        areaOrderedWords.setBounds(340,60,270,420);
        areaUnorderedWords.setEditable(false);
        areaOrderedWords.setEditable(false);

        setSize(650,600);
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

        add(label);
        //add(txtAmountOfNumbers);
        add(btnAdd);
        add(btnSequential);
        add(btnClean);
        add(btnForkJoin);
        add(btnExecuteService);
        add(sp);
        add(sp2);
        add(lblSequential);
        add(lblFork);
    }

    private void cleanAll(){
        areaUnorderedWords.setText("");
        areaOrderedWords.setText("");
        lblExecutor.setText("");
        lblFork.setText("");
        lblSequential.setText("");
    }

    private void generateArrayOfWords(){
        String text = JOptionPane.showInputDialog(null,
                "Ingresa el texto que deseas:");
        if(text!=null){
            if(!text.equals("")){
                arrayOfWords = text.split(" ");
            }
        }
        setTextOnAreaComponent(areaUnorderedWords, arrayOfWords);
    }

    private void setTextOnAreaComponent(JTextArea textArea, String []arrayOfWords){
        for (int i = 1; i < arrayOfWords.length; i++)
        {
            if (i % 5 == 0){
                textArea.append("\n");
            }
            textArea.append(arrayOfWords[i - 1] + " ");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Add")){
            cleanAll();
            generateArrayOfWords();
        }
        if (action.equals("Sequential")){
            cleanAll();
            setTextOnAreaComponent(areaUnorderedWords, arrayOfWords);

            String[] copy = arrayOfWords.clone();
            lblSequential.setText(sequentialProcess(copy)+ " ns");
            setTextOnAreaComponent(areaOrderedWords,copy);
        }
        if (action.equals("Fork")){
            cleanAll();
            setTextOnAreaComponent(areaUnorderedWords, arrayOfWords);
            String[] copy = arrayOfWords.clone();
            lblFork.setText(forkProcess(copy)+ " ns");
            setTextOnAreaComponent(areaOrderedWords,copy);
        }
    }

    private long sequentialProcess(String[] array)
    {
        long startTime = System.nanoTime();
        WordSort words = new WordSort();
        words.sort(array, 0, array.length - 1);
        return System.nanoTime() - startTime;
    }

    private long executorProcess(String[] array)
    {
        long startTime = System.nanoTime();
        Runnable r = () -> {
           // WordSort merge = new WordSort(array, 0, array.length - 1);
            //merge.sort();
        };
        ExecutorService executor = Executors.newFixedThreadPool(numberOfCores);
        executor.execute(r);
        return System.nanoTime() - startTime;
    }


    private long forkProcess(String[] array)
    {
        long startTime = System.nanoTime();

        ForkJoin forkJoin = new ForkJoin(array,0,array.length -1);
        forkJoinPool.invoke(forkJoin);

        return System.nanoTime() - startTime;
    }
}
