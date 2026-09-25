import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactManager {

    private ArrayList<Contact> contacts =
            new ArrayList<>();

    private static final String FILE_NAME =
            "contacts.txt";

    public ContactManager() {

        loadContacts();
        sortContacts();

    }

    public void addContact(Contact contact) {

        contacts.add(contact);

        sortContacts();

        saveContacts();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public Contact searchContact(String name) {

        for (Contact contact : contacts) {

            if (contact.getName()
                    .equalsIgnoreCase(name)) {

                return contact;
            }
        }

        return null;
    }

    public void deleteAtIndex(int index) {

        if (index >= 0 &&
                index < contacts.size()) {

            contacts.remove(index);

            saveContacts();
        }
    }

    public void sortContacts() {

        Collections.sort(
                contacts,
                Comparator.comparing(
                        Contact::getName
                )
        );
    }

    public void saveContacts() {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME)
                    );

            for (Contact contact : contacts) {

                writer.write(
                        contact.getName()
                                + ","
                                + contact.getPhone()
                );

                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void loadContacts() {

        File file =
                new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line =
                    reader.readLine()) != null) {

                String[] parts =
                        line.split(",");

                if (parts.length == 2) {

                    contacts.add(
                            new Contact(
                                    parts[0],
                                    parts[1]
                            )
                    );
                }
            }

            reader.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}