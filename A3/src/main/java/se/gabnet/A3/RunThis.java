package se.gabnet.A3;

import se.gabnet.A3.ClientServer.Client;
import se.gabnet.A3.ClientServer.Server;

public class RunThis {


    public static void main(String[] args) {
        for (int i = 1; i < 11; i++) {
            new Client(i*10,10);
        }

    }


}
