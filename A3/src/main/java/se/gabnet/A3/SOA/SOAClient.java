package se.gabnet.A3.SOA;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SOAClient {
    private long startTime;
    private long endTime;
    private int numClients;
    private int numRuns;
    private CountDownLatch latch;
    private long[] runtimes;



    public SOAClient(int numClients, int numRuns) {
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
            WebClient client = WebClient.create();

            WebClient.ResponseSpec responseSpec = client.get()
                    .uri("http://localhost:8080/books")
                    .retrieve();

            responseSpec.bodyToMono(String.class).block();


            latch.countDown();
        }
    }
}
