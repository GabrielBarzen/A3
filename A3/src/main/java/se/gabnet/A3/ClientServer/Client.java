package se.gabnet.A3.ClientServer;

import se.gabnet.A3.SOA.SOAClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private long startTime;
    private long endTime;
    private int numClients;
    private int finishedClients;
    private CountDownLatch latch;
    private final int port = 7777;
    private final String ip ="localhost";
    private int numRuns;
    private long[] runtimes;

    public Client(int numClients, int numRuns) {
        this.numClients = numClients;
        this.numRuns = numRuns;
        runtimes = new long[this.numRuns];

        ExecutorService tp = Executors.newFixedThreadPool(10);
        for (int i = 0; i < this.numRuns; i++) {
            latch = new CountDownLatch(this.numClients);

            startTime = System.currentTimeMillis();
            for (int j = 0; j < this.numClients; j++) {
                tp.execute(new requester());
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endTime = System.currentTimeMillis();

            runtimes[i] = endTime  - startTime;
        }
        System.out.println("num clients : " + this.numClients + ", times : " + Arrays.toString(runtimes));
        int total = 0;
        for (long runtime : runtimes) {
            total += runtime;
        }
        System.out.println("Avg for num : " + this.numClients + " = " + total/runtimes.length);
    }

    class requester extends Thread {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(ip, port);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                String response = ois.readUTF();
//                System.out.println(++finishedClients + " " + response);

                ois.close();
                oos.close();
                socket.close();
                latch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
