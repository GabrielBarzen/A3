package se.gabnet.A3.SOA;

import org.springframework.web.reactive.function.client.WebClient;
import java.util.concurrent.CountDownLatch;

public class SOAClient extends Thread {
    private static long startTime;
    private static long endTime;
    private static int numClients;
    private static CountDownLatch latch;



    public static void main(String[] args) throws InterruptedException {

        numClients = 50;
        latch = new CountDownLatch(numClients);

        startTime = System.currentTimeMillis();

        for (int i = 0; i < numClients; i++) {
            new SOAClient().start();
        }
        latch.await();
        endTime = System.currentTimeMillis();
        System.out.println("Time taken : " + (endTime - startTime));
    }

    @Override
    public void run() {
        WebClient client = WebClient.create();

        WebClient.ResponseSpec responseSpec = client.get()
                .uri("http://localhost:8080/books")
                .retrieve();

        System.out.println(responseSpec.bodyToMono(String.class).block());


        latch.countDown();
    }
}
