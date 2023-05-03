import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class MyWindow extends JFrame implements ActionListener {

    //private final JTextField txtAmountOfNumbers;
    private final JTextArea areaWords, areaOrderedWords;
    private final JButton btnAdd, btnMerge, btnForkJoin, btnClean, btnExecuteService;

    private final JLabel lblMerge, lblFork;

    public MyWindow(){
        //txtAmountOfNumbers = new JTextField();
        areaWords = new JTextArea();
        areaOrderedWords = new JTextArea();
        JScrollPane sp = new JScrollPane(areaWords);
        JScrollPane sp2 = new JScrollPane(areaOrderedWords);
        sp.setBounds(20,60,270,420);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sp2.setBounds(340,60,270,420);
        sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        areaWords.setBackground(new Color(230,128,65));
        areaOrderedWords.setBackground(new Color(230,128,65));
        btnAdd = new JButton("Agregar datos");
        btnMerge = new JButton("Secuencial");
        btnMerge.setActionCommand("Add");
        btnClean = new JButton("Limpiar");
        btnForkJoin = new JButton("Fork/Join");
        btnExecuteService = new JButton("Execute Service");
        JLabel label = new JLabel("Ingrese el texto: ");
        lblMerge = new JLabel("as");
        lblFork = new JLabel("asd");

        label.setBounds(20,20,label.getPreferredSize().width+20,label.getPreferredSize().height);
        lblMerge.setBounds(400,0,100,30);
        lblFork.setBounds(500,0,100,30);
        //txtAmountOfNumbers.setBounds(140,15,200,30);
        btnAdd.setBounds(20,500,btnAdd.getPreferredSize().width,btnAdd.getPreferredSize().height);
        btnMerge.setBounds(150 ,500,btnMerge.getPreferredSize().width,btnMerge.getPreferredSize().height);
        btnClean.setBounds(245 ,500,btnClean.getPreferredSize().width,btnClean.getPreferredSize().height);
        btnForkJoin.setBounds(335 ,500,120,btnForkJoin.getPreferredSize().height);
        btnExecuteService.setBounds(470 ,500,btnExecuteService.getPreferredSize().width + 20,
                btnExecuteService.getPreferredSize().height);
        areaWords.setBounds(20,60,270,420);
        areaOrderedWords.setBounds(340,60,270,420);

        setSize(650,600);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        btnMerge.addActionListener(this);
       // addListeners();

        add(label);
        //add(txtAmountOfNumbers);
        add(btnAdd);
        add(btnMerge);
        add(btnClean);
        add(btnForkJoin);
        add(btnExecuteService);
        add(sp);
        add(sp2);
        add(lblMerge);
        add(lblFork);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Add")){
            System.out.println("Si entro");
            String allText = areaWords.getText();
            String[] arr = allText.split(" ");
            Words words = new Words(arr);
            words.orderWords();
            String orderedWords = "";
            for (int i = 0; i < arr.length; i++) {
                orderedWords += arr[i]+" ";
            }
            areaOrderedWords.setText(orderedWords);
        }
    }
}
