/*

    Server.java
    Written by Ben Hadlington (bh9g22)
    - Handles the server.

    CREATED: 06/11/2022
    UPDATED: 06/11/2022

*/

import java.net.Socket;

import java.io.InputStreamReader;
import java.io.BufferedReader;

/*

    Class

*/

public class ThreadHandler implements Runnable {
    private final Socket clientSocket;

    public ThreadHandler(Socket socket) {
       clientSocket = socket;
    }

    public void handleThread() {
        try {
            InputStreamReader socketInputReader = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader socketBuffer = new BufferedReader(socketInputReader);
            String msg = socketBuffer.readLine();
            System.out.println(msg);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void run() {
        while (true) {
            handleThread();
        }
    }
}