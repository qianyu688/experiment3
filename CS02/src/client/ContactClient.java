package client;

import javax.swing.*;

public class ContactClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactGUI gui = new ContactGUI();
            gui.setVisible(true);
        });
    }

}
