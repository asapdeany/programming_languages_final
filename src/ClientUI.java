import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * Created by deansponholz on 5/6/17.
 */
public class ClientUI extends JPanel {

    private JFrame jFrame;
    private JPanel genPrimeJPanel, testJpanel, resultJPanel;
    private JButton genPrimeButton, testButton;
    private JTextField bitLengthTextField, ipAddressTextField;
    private JTextArea resultTextArea;
    private JScrollPane resultScroll;

    private String ipAddress;
    private int bitLength;

    Socket socket = null;
    BufferedReader fromServer = null;
    PrintWriter toServer = null;

    Random rand = new Random();

    boolean isServerConnected = false;


    public ClientUI(){


        jFrame = new JFrame("Final Project");
        //program exits when frame closes
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);

        genPrimeJPanel = new JPanel();
        testJpanel = new JPanel();
        resultJPanel = new JPanel();

        ipAddressTextField = new JTextField("Enter an IP address");
        bitLengthTextField = new JTextField("Enter a bit length");
        genPrimeButton = new JButton("Generate Prime");
        genPrimeButton.addActionListener(new GenPrimeListener());
        genPrimeJPanel.add(ipAddressTextField);
        genPrimeJPanel.add(bitLengthTextField);
        genPrimeJPanel.add(genPrimeButton);

        testButton = new JButton("Test Button");
        testButton.addActionListener(new TestListener());
        testJpanel.add(testButton);

        resultTextArea = new JTextArea("Result Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed HereResult Displayed Here");
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);
        resultTextArea.setColumns(20);
        resultTextArea.setRows(5);
        resultTextArea.setEditable(false);

        resultScroll = new JScrollPane(resultTextArea);
        resultJPanel.add(resultScroll);

        jFrame.add(genPrimeJPanel, BorderLayout.NORTH);
        jFrame.add(testJpanel, BorderLayout.CENTER);
        jFrame.add(resultJPanel, BorderLayout.SOUTH);

        jFrame.setVisible(true);
        jFrame.pack();


    }

    /*
    final ThreadLocal<Runnable> getPrimeThread = new ThreadLocal<Runnable>(){
        @Override
        protected Runnable initialValue(){
            return() -> {

            }
        }
    }
    */

    Runnable client_run = () -> {
        try {

            socket = new Socket(ipAddress, 12345);
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toServer = new PrintWriter(socket.getOutputStream(), true);
            toServer.println(bitLength);
        }catch (IOException e){
            e.printStackTrace();
        }
    };


    private class GenPrimeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //ipAddress = ipAddressTextField.getText();
            ipAddress = "10.70.23.55";
            bitLength = Integer.parseInt(bitLengthTextField.getText());

            Runnable client_runnable = () -> {
                try {

                    socket = new Socket(ipAddress, 12345);
                    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    toServer = new PrintWriter(socket.getOutputStream(), true);
                    toServer.println(bitLength);
                    resultTextArea.setBackground(Color.WHITE);
                    resultTextArea.setText(fromServer.readLine());
                    resultTextArea.setCaretPosition(0);
                    isServerConnected = true;

                    //socket.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            };
            client_runnable.run();

            /*

            try{
                bitLength = Integer.parseInt(bitLengthTextField.getText());
                if (bitLength <=1){
                    resultTextArea.setBackground(Color.RED);
                    return;
                }
                else{
                    client_runnable.run();
                }
            } catch (NumberFormatException e1){
                //e1.printStackTrace();
                resultTextArea.setText("Not a number");
                return;
            }
            */

        }
    }

    private class TestListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());

            testButton.setForeground(color);
            genPrimeButton.setForeground(color);
            //testButton.setOpaque(true);

        }
    }



}
