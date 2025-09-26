package HARSH;
import java.io.*;
import java.util.*;

public class NotesApp {
    private static final String FILE_NAME = "notes.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n--- Notes App ---");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete Note");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addNote(sc);
                case 2 -> viewNotes();
                case 3 -> deleteNote(sc);
                case 4 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    // Add note
    private static void addNote(Scanner sc) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            System.out.print("Enter note: ");
            String note = sc.nextLine();
            fw.write(note + System.lineSeparator());
            System.out.println("Note added!");
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    // View notes
    private static void viewNotes() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int count = 1;
            System.out.println("\n--- Notes ---");
            while ((line = br.readLine()) != null) {
                System.out.println(count + ". " + line);
                count++;
            }
            if (count == 1) {
                System.out.println("No notes found.");
            }
        } catch (IOException e) {
            System.out.println("No notes file found. Add some notes first.");
        }
    }

    // Delete note
    private static void deleteNote(Scanner sc) {
        List<String> notes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                notes.add(line);
            }
        } catch (IOException e) {
            System.out.println("No notes to delete.");
            return;
        }

        if (notes.isEmpty()) {
            System.out.println("No notes to delete.");
            return;
        }

        // show notes with index
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ". " + notes.get(i));
        }

        System.out.print("Enter note number to delete: ");
        int num = sc.nextInt();
        sc.nextLine(); // consume newline

        if (num < 1 || num > notes.size()) {
            System.out.println("Invalid note number.");
            return;
        }

        notes.remove(num - 1);

        // rewrite file
        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            for (String n : notes) {
                fw.write(n + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error updating file.");
        }

        System.out.println("Note deleted!");
    }
}