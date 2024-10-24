package server;

import shared.Contact;

import java.util.List;

public class ContactService {
    private ContactDatabase database;

    public ContactService() {
        database = new ContactDatabase();
    }

    public List<Contact> getAllContacts() {
        return database.getAllContacts();
    }

    public void addContact(Contact contact) {
        database.addContact(contact);
    }

    public void updateContact(int index, Contact contact) {
        database.updateContact(index, contact);
    }

    public void deleteContact(int index) {
        database.deleteContact(index);
    }
}
