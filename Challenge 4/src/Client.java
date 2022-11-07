/*

    Client.java
    Written by Ben Hadlington (bh9g22)
    - Handles the client.

    CREATED: 06/11/2022
    UPDATED: 06/11/2022

*/

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

import java.util.Scanner;

/*

    Class

*/

public class Client {
    private Socket socket;
    private final String username;

    public Client() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter your username:");
        username = inputScanner.nextLine();

        try {
            socket = new Socket("localhost", 3069);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void handleClient() {
        try {
            PrintWriter printer = new PrintWriter(socket.getOutputStream());

            Scanner inputScanner = new Scanner(System.in);
            System.out.println("Input a message to broadcast:");
            String msg = inputScanner.nextLine();

            msg = username + ": " + msg;
            printer.println(msg);
            printer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void run() {
        while (true) {
            handleClient();
        }
    }

    public static void main(String[] args) {
        Client newClient = new Client();
        newClient.run();
    }
}