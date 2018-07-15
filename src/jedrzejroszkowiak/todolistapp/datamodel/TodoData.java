package jedrzejroszkowiak.todolistapp.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class TodoData {
    private static TodoData instance = new TodoData();
    private static String filename = "ToDoListItems.txt";

    private static DateTimeFormatter formatter;
    private static ObservableList<TodoItem> todoItems;

    private TodoData() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public static TodoData getInstance() {
        return instance;
    }

    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public static void addTodoItem(TodoItem item) {
        if (item != null) {
            todoItems.add(item);
        }
    }

    public static void editTodoItem(TodoItem item, TodoItem newItem) {
        if (item != null) {
//            int position = todoItems.indexOf(item);
//            todoItems.set(position, newItem);
            todoItems.remove(item);
            todoItems.add(newItem);
        }
    }


    public void loadTodoItems() throws IOException {
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while((input = br.readLine()) != null) {
                String[] inputPieces = input.split("\t");
                String shortDescription = inputPieces[0];
                String details = inputPieces[1];
                String dateString = inputPieces[2];

                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                TodoItem item = new TodoItem(shortDescription, details, date);
                todoItems.add(item);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    public void storeTodoItems() throws IOException {
        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<TodoItem> iterator = todoItems.iterator();
            while(iterator.hasNext()) {
                StringBuilder s = new StringBuilder();
                TodoItem item = iterator.next();
                s.append(item.getShortDescription() + "\t");
                s.append(item.getDetails() + "\t");
                s.append(item.getDeadline().format(formatter));
                bw.write(s.toString());
                bw.newLine();
            }
        } finally {
            if (bw != null){
                bw.close();
            }
        }
    }

    public void deleteTodoItem(TodoItem item) {
        if (item != null) {
            todoItems.remove(item);
        }
    }
}
