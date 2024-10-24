import shared.Contact;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

class ContactGUI extends JFrame {
    private DefaultListModel<Contact> contactListModel;
    private JList<Contact> contactList;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ContactGUI() {
        setTitle("个人通讯录");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);
        add(new JScrollPane(contactList), BorderLayout.CENTER);

        JButton addButton = new JButton("添加联系人");
        addButton.addActionListener(e -> addContact());
        add(addButton, BorderLayout.SOUTH);

        connectToServer();
        loadContacts();
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

    private void loadContacts() {
        try {
            out.writeObject("GET_ALL");
            java.util.List<Contact> contacts = (java.util.List<Contact>) in.readObject();
            updateContactList(contacts);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addContact() {
        String name = JOptionPane.showInputDialog("输入姓名:");
        String address = JOptionPane.showInputDialog("输入地址:");
        String phone = JOptionPane.showInputDialog("输入电话:");
        if (name != null && address != null && phone != null) {
            Contact contact = new Contact(name, address, phone);
            try {
                out.writeObject("ADD");
                out.writeObject(contact);
                loadContacts(); // 刷新联系人列表
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateContactList(List<Contact> contacts) {
        contactListModel.clear();
        for (Contact contact : contacts) {
            contactListModel.addElement(contact);
        }
    }
}
