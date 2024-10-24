package client;

import javax.swing.*;
import shared.Contact;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class ContactClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private DefaultListModel<Contact> contactListModel;
    private JList<Contact> contactList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactClient().init());
    }

    private void init() {
        JFrame frame = new JFrame("个人通讯录");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);
        frame.add(new JScrollPane(contactList), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton addButton = new JButton("添加联系人");
        JButton updateButton = new JButton("修改联系人");
        JButton deleteButton = new JButton("删除联系人");

        addButton.addActionListener(e -> addContact());
        updateButton.addActionListener(e -> updateContact());
        deleteButton.addActionListener(e -> deleteContact());

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        frame.add(panel, BorderLayout.SOUTH);

        connectToServer();
        loadContacts();

        frame.setVisible(true);
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
            List<Contact> contacts = (List<Contact>) in.readObject();
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
                loadContacts(); // 确保添加后刷新联系人列表
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            Contact contact = contactListModel.get(selectedIndex);
            String name = JOptionPane.showInputDialog("修改姓名:", contact.getName());
            String address = JOptionPane.showInputDialog("修改地址:", contact.getAddress());
            String phone = JOptionPane.showInputDialog("修改电话:", contact.getPhone());

            if (name != null && address != null && phone != null) {
                contact = new Contact(name, address, phone);
                try {
                    out.writeObject("UPDATE");
                    out.writeInt(selectedIndex);
                    out.writeObject(contact);
                    loadContacts(); // 刷新联系人列表
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "请先选择一个联系人！");
        }
    }

    private void deleteContact() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            try {
                out.writeObject("DELETE");
                out.writeInt(selectedIndex);
                loadContacts(); // 刷新联系人列表
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "请先选择一个联系人！");
        }
    }

    private void updateContactList(List<Contact> contacts) {
        contactListModel.clear();
        for (Contact contact : contacts) {
            contactListModel.addElement(contact);
        }
    }
}
