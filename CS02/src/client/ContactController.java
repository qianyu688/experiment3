package client;

import shared.Contact;

import java.io.*;
import java.net.*;
import java.util.List;

public class ContactController {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ContactController(ContactGUI gui) {
        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> getAllContacts() {
        try {
            out.writeObject("GET_ALL");
            return (List<Contact>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addContact(Contact contact) {
        try {
            out.writeObject("ADD");
            out.writeObject(contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(Contact contact) {
        try {
            out.writeObject("UPDATE");
            out.writeObject(contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(Contact contact) {
        try {
            out.writeObject("DELETE");
            out.writeObject(contact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
