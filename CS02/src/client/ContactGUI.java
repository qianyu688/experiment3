package client;

import shared.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ContactGUI extends JFrame {
    private DefaultListModel<Contact> contactListModel;
    private JList<Contact> contactList;
    private ContactController controller;

    public ContactGUI() {
        controller = new ContactController(this);
        setTitle("个人通讯录");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        contactListModel = new DefaultListModel<>();
        contactList = new JList<>(contactListModel);
        add(new JScrollPane(contactList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("添加联系人");
        JButton updateButton = new JButton("修改联系人");
        JButton deleteButton = new JButton("删除联系人");

        addButton.addActionListener(this::addContact);
        updateButton.addActionListener(this::updateContact);
        deleteButton.addActionListener(this::deleteContact);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadContacts();
    }

    public void loadContacts() {
        List<Contact> contacts = controller.getAllContacts();
        updateContactList(contacts);
    }

    private void updateContactList(List<Contact> contacts) {
        contactListModel.clear();
        for (Contact contact : contacts) {
            contactListModel.addElement(contact);
        }
    }

    private void addContact(ActionEvent e) {
        Contact contact = showContactDialog(null);
        if (contact != null) {
            controller.addContact(contact);
            loadContacts();
        }
    }

    private void updateContact(ActionEvent e) {
        Contact selectedContact = contactList.getSelectedValue();
        if (selectedContact != null) {
            Contact contact = showContactDialog(selectedContact);
            if (contact != null) {
                controller.updateContact(contact);
                loadContacts();
            }
        } else {
            JOptionPane.showMessageDialog(this, "请选择一个联系人进行修改");
        }
    }

    private void deleteContact(ActionEvent e) {
        Contact selectedContact = contactList.getSelectedValue();
        if (selectedContact != null) {
            controller.deleteContact(selectedContact);
            loadContacts();
        } else {
            JOptionPane.showMessageDialog(this, "请选择一个联系人进行删除");
        }
    }

    private Contact showContactDialog(Contact contact) {
        String name = JOptionPane.showInputDialog(this, "输入姓名:", contact != null ? contact.getName() : "");
        String address = JOptionPane.showInputDialog(this, "输入地址:", contact != null ? contact.getAddress() : "");
        String phone = JOptionPane.showInputDialog(this, "输入电话:", contact != null ? contact.getPhone() : "");
        if (name != null && address != null && phone != null) {
            return new Contact(name, address, phone);
        }
        return null;
    }
}
