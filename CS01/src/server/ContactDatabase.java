package server;

import shared.Contact;
import java.util.ArrayList;
import java.util.List;

public class ContactDatabase {
    private List<Contact> contacts;

    public ContactDatabase() {
        contacts = new ArrayList<>();
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void updateContact(int index, Contact contact) {
        contacts.set(index, contact);
    }

    public void deleteContact(int index) {
        contacts.remove(index);
    }
}
