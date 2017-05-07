import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
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
    private int PORT = 13013;
    Socket socket = null;
    BufferedReader fromServer = null;
    PrintWriter toServer = null;

    Random rand = new Random();



    public ClientUI(){


        jFrame = new JFrame("Final Project - Dean Sponholz");
        //program exits when frame closes
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);

        genPrimeJPanel = new JPanel();
        testJpanel = new JPanel();
        resultJPanel = new JPanel();

        ipAddressTextField = new JTextField("Enter an IP address");
        ipAddressTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {

                ipAddressTextField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        bitLengthTextField = new JTextField("Enter a bit length");
        bitLengthTextField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                bitLengthTextField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                
            }
        });
        genPrimeButton = new JButton("Generate Prime");
        genPrimeButton.addActionListener(new GenPrimeListener());
        genPrimeJPanel.add(ipAddressTextField);
        genPrimeJPanel.add(bitLengthTextField);
        genPrimeJPanel.add(genPrimeButton);

        testButton = new JButton("Test Button");
        testButton.addActionListener(new TestListener());
        testJpanel.add(testButton);

        resultTextArea = new JTextArea("Result Displayed Here                                                                                                                                                                                                                                                                             ");
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
        jFrame.requestFocusInWindow();
        jFrame.pack();

    }

    final ThreadLocal<Runnable> getPrimeThread = new ThreadLocal<Runnable>(){
        @Override
        protected Runnable initialValue(){
            return() -> {
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(ipAddress, PORT), 6000);
                    resultTextArea.setText("Connected and Thinking...");
                    fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    toServer = new PrintWriter(socket.getOutputStream(), true);
                    toServer.println(bitLength);
                    resultTextArea.setBackground(Color.WHITE);
                    resultTextArea.setText(fromServer.readLine());
                    resultTextArea.setCaretPosition(0);
                    genPrimeButton.setEnabled(true);
                } catch (SocketTimeoutException e){
                    resultTextArea.setBackground(Color.RED);
                    resultTextArea.setText("Connection Timed Out - Check IP Address");
                    genPrimeButton.setEnabled(true);
                } catch (NoRouteToHostException e){
                    resultTextArea.setBackground(Color.RED);
                    resultTextArea.setText("Incorrect IP Address Number");
                    genPrimeButton.setEnabled(true);
                } catch (UnknownHostException e){
                    resultTextArea.setBackground(Color.RED);
                    resultTextArea.setText("Invalid IP Address Format");
                    genPrimeButton.setEnabled(true);
                } catch (ConnectException e){
                    resultTextArea.setBackground(Color.RED);
                    resultTextArea.setText("Incorrect IP Address Number");
                    genPrimeButton.setEnabled(true);
                } catch (IOException e){
                    e.printStackTrace();
                }
            };
        }
    };

    private class GenPrimeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ipAddress = ipAddressTextField.getText();
            //ipAddress = "10.70.23.55";

            try{

                bitLength = Integer.parseInt(bitLengthTextField.getText());

                if (bitLength <=1){
                    resultTextArea.setBackground(Color.RED);
                    resultTextArea.setText("Bit Length must be greater than 1");
                    return;
                }
                else{
                    resultTextArea.setBackground(Color.white);
                    genPrimeButton.setEnabled(false);
                    resultTextArea.setText("Attempting to Connect to Server");
                    Thread thread = new Thread(getPrimeThread.get());
                    thread.start();
                }
            } catch (NumberFormatException e1){
                resultTextArea.setBackground(Color.RED);
                resultTextArea.setText("Bit Length not a number");
                return;
            }
            bitLength = Integer.parseInt(bitLengthTextField.getText());

        }
    }

    private class TestListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            testJpanel.setBackground(color);

        }
    }


    public static void main(String args[]){
        ClientUI clientUI = new ClientUI();
    }

}
