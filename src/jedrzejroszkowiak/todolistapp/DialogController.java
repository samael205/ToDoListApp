package jedrzejroszkowiak.todolistapp;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import jedrzejroszkowiak.todolistapp.datamodel.TodoData;
import jedrzejroszkowiak.todolistapp.datamodel.TodoItem;

import java.time.LocalDate;


public class DialogController {
    @FXML
    private TextField shortDescriptionField;

    @FXML
    private TextArea detailsArea;

    @FXML
    private DatePicker deadlinePicker;

    private TodoItem newItem;


    public TodoItem processResults() {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadlinePicker.getValue();

        TodoItem newItem = new TodoItem(shortDescription, details, deadlineValue);
        TodoData.getInstance().addTodoItem(newItem);
        return newItem;
    }

    public void fillFields(TodoItem item) {
        String shortDescription = item.getShortDescription();
        String details = item.getDetails();
        LocalDate deadlineValue = item.getDeadline();

        shortDescriptionField.setText(shortDescription);
        detailsArea.setText(details);
        deadlinePicker.setValue(deadlineValue);
    }

    public TodoItem processEditResults(TodoItem item) {
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate deadlineValue = deadlinePicker.getValue();

        TodoItem newItem = new TodoItem(shortDescription, details, deadlineValue);
        TodoData.getInstance().editTodoItem(item, newItem);
        return newItem;
    }
}
