package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ContactServer {
    public static void main(String[] args) {
        ContactService service = new ContactService();
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, service).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
