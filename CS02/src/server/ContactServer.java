package server;

import java.io.*;
import java.net.*;

public class ContactServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("服务器已启动，等待连接...");
            ContactService service = new ContactService();
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, service).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
