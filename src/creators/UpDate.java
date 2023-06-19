package creators;

import util.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Task;

import java.time.LocalDate;
import java.util.ArrayList;

public class UpDate {
    public static void display(ObservableList<Task> g, TableView<Task> g2, ChoiceBox choiceBoxRole) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Update Task");
        ArrayList<String> ID = new ArrayList<>();
        window.setMinWidth(300);
        int j = 0;
        for (Task i : g) {

            ID.add(i.getTaskID());
            j++;
        }
        ChoiceBox choiceBoxRole2 = new ChoiceBox(FXCollections.observableArrayList(ID));
        Label labelID = new Label("ID");
        Label labelstate = new Label("Statue");
        Label labeltasksDescription = new Label("tasks Descript");

        TextField fieldtasksDescription = new TextField();
        Label labelName = new Label("Name Task");
        Label labelStartDate = new Label("Start Date");
        Label labelEndDate = new Label("End Date");
        Button buttonAdd = new Button("Confirm");
        TextField fieldName = new TextField();
        DatePicker fieldStartDate = new DatePicker();
        DatePicker fieldEndDate = new DatePicker();


        labeltasksDescription.setPrefSize(100, 30);

        fieldtasksDescription.setPrefSize(180, 30);
        labelName.setPrefSize(100, 30);
        labelStartDate.setPrefSize(100, 30);
        labelEndDate.setPrefSize(50, 30);
        labelID.setPrefSize(50, 30);
        labelstate.setPrefSize(50, 30);

        choiceBoxRole2.setPrefSize(180, 30);
        choiceBoxRole.setPrefSize(180, 30);

        fieldName.setPrefSize(180, 30);
        fieldStartDate.setPrefSize(180, 30);
        fieldEndDate.setPrefSize(180, 30);
        buttonAdd.setPrefSize(240, 30);


        labeltasksDescription.setTranslateX(20);
        labeltasksDescription.setTranslateY(85);
        fieldtasksDescription.setTranslateX(100);
        fieldtasksDescription.setTranslateY(85);
        labelID.setTranslateX(20);
        labelID.setTranslateY(5);
        labelName.setTranslateX(20);
        labelName.setTranslateY(45);
        labelStartDate.setTranslateX(20);
        labelStartDate.setTranslateY(125);
        labelEndDate.setTranslateX(20);
        labelEndDate.setTranslateY(165);
        labelstate.setTranslateX(20);
        labelstate.setTranslateY(215);
        fieldName.setTranslateX(100);
        fieldName.setTranslateY(45);
        fieldStartDate.setTranslateX(100);
        fieldStartDate.setTranslateY(125);
        fieldEndDate.setTranslateX(100);
        fieldEndDate.setTranslateY(165);
        buttonAdd.setTranslateX(30);
        buttonAdd.setTranslateY(260);
        choiceBoxRole2.setTranslateX(100);
        choiceBoxRole2.setTranslateY(5);
        choiceBoxRole.setTranslateX(100);
        choiceBoxRole.setTranslateY(215);

        Group root = new Group();
        root.getChildren().add(labelstate);
        root.getChildren().add(labelID);
        root.getChildren().add(labelName);
        root.getChildren().add(labelStartDate);
        root.getChildren().add(labelEndDate);
        root.getChildren().add(fieldName);
        root.getChildren().add(fieldStartDate);
        root.getChildren().add(fieldEndDate);
        root.getChildren().add(buttonAdd);
        root.getChildren().add(choiceBoxRole2);
        root.getChildren().add(choiceBoxRole);
        root.getChildren().add(labeltasksDescription);
        root.getChildren().add(fieldtasksDescription);

        StackPane final_group = new StackPane(root);
        choiceBoxRole.getSelectionModel().selectFirst();

        choiceBoxRole2.getSelectionModel().selectFirst();


        buttonAdd.setOnAction(e ->
        {
            if ( fieldName.getText() == "" || fieldtasksDescription.getText() == "" ) {
                new Alert(Alert.AlertType.WARNING, "Name Task , Start Date and End Date fields cannot be empty!").show();
                window.close();
                return;
            }
            if ( fieldStartDate.getValue() == null ) {
                new Alert(Alert.AlertType.CONFIRMATION, "The Date is Wrong!").show();
                window.close();
                return;

            }
            if ( fieldEndDate.getValue() == null ) {
                new Alert(Alert.AlertType.CONFIRMATION, "The Date is Wrong!").show();
                window.close();
                return;
            }

            String name = fieldName.getText();
            LocalDate StartDate = fieldStartDate.getValue();
            LocalDate EndDate = fieldEndDate.getValue();
            String Description = fieldtasksDescription.getText();
            String state2 = choiceBoxRole2.getSelectionModel().getSelectedItem().toString();
            if ( StartDate.getYear() <= EndDate.getYear() ) {
                if ( StartDate.getMonthValue() <= EndDate.getMonthValue() ) {
                    if ( StartDate.getDayOfMonth() <= EndDate.getDayOfMonth() ) {
                        System.out.println("Done");
                    } else {
                        new Alert(Alert.AlertType.CONFIRMATION, "The Date is Wrong! because the date start greater than end date").show();
                        window.close();
                        return;
                    }
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "The Date is Wrong! because the date start greater than end date").show();
                    window.close();
                    return;
                }
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "The Date is Wrong! because the date start greater than end date").show();
                window.close();
                return;
            }
            int d = 0;
            for (Task i : g) {

                if ( state2 == i.getTaskID() ) {
                    break;
                }
                d++;
            }

            String state = choiceBoxRole.getSelectionModel().getSelectedItem().toString();
            Status vi;
            if ( state.equals("not_Started") ) {
                vi = Status.not_Started;
            } else if ( state.equals("InProgress") ) {
                vi = Status.InProgress;
            } else {
                vi = Status.Done;
            }
            int d4 = 0;
            if ( !name.equals("") && !StartDate.equals("") && !EndDate.equals("") ) {

                g.set(d, new Task(name, StartDate, EndDate, vi, state2, Description, d4));
                fieldName.setText("");
                fieldStartDate.setValue(null);
                fieldEndDate.setValue(null);
                choiceBoxRole.getSelectionModel().selectFirst();
                choiceBoxRole2.getSelectionModel().selectFirst();
                window.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Name Task , Start Date and End Date fields cannot be empty!").show();
            }

        });
        Scene scene = new Scene(final_group, 300, 370);
        window.setScene(scene);
        window.showAndWait();


    }
}
