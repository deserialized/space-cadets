/*

    Server.java
    Written by Ben Hadlington (bh9g22)
    - Handles the server.

    CREATED: 06/11/2022
    UPDATED: 06/11/2022

*/

import java.io.IOException;

import java.net.Socket;
import java.net.ServerSocket;

import java.util.ArrayList;

/*

    Class

*/

public class Server extends Thread {
    private ServerSocket serverSocket;
    private ArrayList<Socket> sockets = new ArrayList<Socket>();

    public Server() {
        try {
            serverSocket = new ServerSocket(3069);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void handleServer() {
        try {
            Socket clientSocket = serverSocket.accept();
            sockets.add(clientSocket);
            System.out.println("A user has joined the chatroom!\nThere is now " + sockets.size() + " user(s).");

            ThreadHandler newThread = new ThreadHandler(clientSocket);
            new Thread(newThread).start();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void run() {
        while (true) {
            handleServer();
        }
    }

    public static void main(String[] args) {
        Server newServer = new Server();
        newServer.start();
    }
}