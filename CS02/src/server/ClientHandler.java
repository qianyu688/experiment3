package server;

import shared.Contact;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private ContactService service;

    public ClientHandler(Socket socket, ContactService service) {
        this.clientSocket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String command;
            while ((command = (String) in.readObject()) != null) {
                System.out.println("Received command: " + command);
                switch (command) {
                    case "GET_ALL":
                        List<Contact> contacts = service.getAllContacts();
                        out.writeObject(contacts);
                        break;
                    case "ADD":
                        Contact contactToAdd = (Contact) in.readObject();
                        service.addContact(contactToAdd);
                        break;
                    case "UPDATE":
                        Contact contactToUpdate = (Contact) in.readObject();
                        service.updateContact(contactToUpdate);
                        break;
                    case "DELETE":
                        Contact contactToDelete = (Contact) in.readObject();
                        service.deleteContact(contactToDelete);
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
