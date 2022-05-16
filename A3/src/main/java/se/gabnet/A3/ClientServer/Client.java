package ClientServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class Client extends Thread {
    private static long startTime;
    private static long endTime;
    private static int numClients;
    private static int finishedClients;
    private static CountDownLatch latch;
    private final int port = 7777;
    private final String ip ="localhost";

    public static void main(String[] args) throws InterruptedException {

        numClients = 50;
        latch = new CountDownLatch(numClients);

        startTime = System.currentTimeMillis();

        for (int i = 0; i < numClients; i++) {
            new Client().start();
        }
        latch.await();
        endTime = System.currentTimeMillis();
        System.out.println("Time taken : " + (endTime-startTime));
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(ip, port);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            String response = ois.readUTF();
            System.out.println(++finishedClients + " " + response);

            ois.close();
            oos.close();
            socket.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
