package server;

import shared.Contact;
import java.util.List;

public class ContactService {
    private ContactDatabase database;

    public ContactService() {
        database = new ContactDatabase();
    }

    public void addContact(Contact contact) {
        database.addContact(contact);
    }

    public void updateContact(Contact contact) {
        int index = database.getAllContacts().indexOf(contact);
        if (index >= 0) {
            database.updateContact(index, contact);
        }
    }

    public void deleteContact(Contact contact) {
        int index = database.getAllContacts().indexOf(contact);
        if (index >= 0) {
            database.deleteContact(index);
        }
    }

    public List<Contact> getAllContacts() {
        return database.getAllContacts();
    }
}
