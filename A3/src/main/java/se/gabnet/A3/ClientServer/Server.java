package ClientServer;

import Shared.DatabaseAccess;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private int clients = 0;
    private final int port = 7777;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        System.out.println("Waiting for client connections");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                new ConnectedClient(serverSocket.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectedClient extends Thread {
        private final Socket socket;

        public ConnectedClient(Socket socket) {
            this.socket = socket;
            this.start();
            System.out.println("Client #" + ++clients + " connected : " + socket);
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                DatabaseAccess db = new DatabaseAccess();
                oos.writeUTF(db.f_get_books().toString());
                oos.flush();

                oos.close();
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
