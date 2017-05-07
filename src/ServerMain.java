import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by deansponholz on 5/6/17.
 */
public class ServerMain {

    public final static int PORT = 12345;

    public ServerMain(){

    }

    public static int bitLength;

    public static BigInteger prime;
    static ServerSocket serverSocket = null;
    static Socket client = null;
    static BufferedReader fromClient = null;
    static PrintWriter toClient = null;


    final ThreadLocal<Runnable> possiblePrimeThread = new ThreadLocal<Runnable>() {

        @Override
        protected Runnable initialValue() {
            return () -> {
                prime = BigInteger.probablePrime(bitLength, new Random());
                System.out.flush();
                System.out.println(prime);
                toClient.println(prime);
                System.out.flush();

                try {
                    serverSocket.close();
                    Thread sc = new Thread(server_thread);
                    sc.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }
    };

    Runnable server_thread = () -> {

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for a connection ...");
            client = serverSocket.accept();
            System.out.println("Connected to " + client.getInetAddress());

            fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            toClient = new PrintWriter(client.getOutputStream(), true);

            try {

                bitLength = Integer.parseInt(fromClient.readLine());
                Thread possiblePrime = new Thread(possiblePrimeThread.get());
                possiblePrime.start();


            }catch (IOException e){
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    };

    public static void main(String args[]){
        ServerMain ServerMain = new ServerMain();
        Thread server = new Thread(ServerMain.server_thread);
        server.start();
    }
}
