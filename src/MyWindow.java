import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Arrays;

public class MyWindow extends JFrame implements ActionListener {

    //private final JTextField txtAmountOfNumbers;
    private JTextArea areaUnorderedWords, areaOrderedWords;
    private JButton btnAdd, btnSequential, btnForkJoin, btnClean, btnExecuteService;

    private JLabel lblExecutor, lblFork, lblSequential, lblQuantityOfWords;
    private String [] arrayOfWords;
    private Font font = new Font("Segoe UI", Font.PLAIN, 18);
    private IServer server;
    private String nameOfWindow;

    public MyWindow(IServer server, String nameOfWindow) throws RemoteException {
        this.server = server;
        this.nameOfWindow = nameOfWindow;
        initComponents();
        cleanAll();
    }

    public void initComponents() throws RemoteException {
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
        btnAdd.setBounds(20,580,btnAdd.getPreferredSize().width,btnAdd.getPreferredSize().height);
        btnSequential.setBounds(150 ,580, btnSequential.getPreferredSize().width, btnSequential.getPreferredSize().height);
        btnClean.setBounds(265 ,580,btnClean.getPreferredSize().width,btnClean.getPreferredSize().height);
        btnForkJoin.setBounds(350 ,580,120,btnForkJoin.getPreferredSize().height);
        btnExecuteService.setBounds(490 ,580,btnExecuteService.getPreferredSize().width + 20,
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
        server.registerWindow(this);
        serviceForGettingTheArrayOfWords();
    }

    private void cleanAll(){
        areaUnorderedWords.setText("");
        areaOrderedWords.setText("");
    }
    private void serviceForGettingTheArrayOfWords(){
        while (true){
            try {
                Thread.sleep(500);
                getArray();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void getArray() throws RemoteException {
        if (arrayOfWords == null && server.getCurrentWords() != null){
//            arrayOfWords = Arrays.copyOf(server.getCurrentWords(),server.getCurrentWords().length);
            arrayOfWords = server.getCurrentWords().clone();
        }
        if (arrayOfWords != null && server.getCurrentWords() != null) {
            if (server.getCurrentWords().length > arrayOfWords.length){
//                arrayOfWords = Arrays.copyOf(server.getCurrentWords(),server.getCurrentWords().length);
                arrayOfWords = server.getCurrentWords().clone();
            }
        }
        if (server.getCurrentWords() == null){
            arrayOfWords = null;
        }
        setTextOnAreaComponent(areaUnorderedWords,arrayOfWords);
    }


    private void appendWords() throws RemoteException {
        String text = JOptionPane.showInputDialog(null,
                "Ingresa el texto que deseas:");

        if (text != null){
            server.appendTextToServer(text);
        }else{
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar el texto");
        }
    }

    private void setTextOnAreaComponent(JTextArea textArea, String []arrayOfWords){
        if (arrayOfWords != null){
            StringBuilder text = new StringBuilder();
            for (int i = 0; i < arrayOfWords.length; i++)
            {
                if (i % 3 == 0){
                    text.append("\n");
                }
                text.append(arrayOfWords[i]);
                text.append(" ");
            }
            textArea.setText(text.toString());
            lblQuantityOfWords.setText("Total de palabras: "+arrayOfWords.length);
        }else{
            textArea.setText("");
            lblQuantityOfWords.setText("Total de palabras: "+0);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals("Add")){
            cleanAll();
            try {
                appendWords();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (action.equals("Sequential")){
            String time = null;
            try {
                Data data = server.getResult("Sequential",nameOfWindow);
                if (data != null){
                    time = "<html>Secuencial<br>"+ data.getTime()+ " ns"+"</html>";
                    setTextOnAreaComponent(areaOrderedWords, data.getArrayOfOrderedWords());
                    lblSequential.setText(time);
                }

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
            if (action.equals("Fork")){
              String time = null;
                try {
                    Data data = server.getResult("Fork",nameOfWindow);
                    if (data != null){
                        time = "<html>Fork<br>"+ data.getTime()+ " ns"+"</html>";
                        setTextOnAreaComponent(areaOrderedWords, data.getArrayOfOrderedWords());
                        lblFork.setText(time);
                    }

                } catch (RemoteException ex) {
                    throw new RuntimeException(ex);
                }
            }

        if (action.equals("Executor")){
            String time = null;
            try {
                Data data = server.getResult("Executor",nameOfWindow);
                if (data != null){
                    time = "<html>Executor<br>"+ data.getTime()+ " ns"+"</html>";
                    setTextOnAreaComponent(areaOrderedWords, data.getArrayOfOrderedWords());
                    lblExecutor.setText(time);
                }

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (action.equals("Clean")){
            try {
                clean();
                server.limpiar(nameOfWindow);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void clean() {
        arrayOfWords = null;
        lblSequential.setText("");
        lblFork.setText("");
        lblExecutor.setText("");
        cleanAll();

    }
    public String getNameOfTheWindow() {
        return nameOfWindow;
    }
}
