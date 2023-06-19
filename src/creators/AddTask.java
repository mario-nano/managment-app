package creators;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Member;
import models.Project;
import models.Task;
import util.Status;

import java.time.LocalDate;
import java.util.Collection;

public class AddTask { 
        public void display(ObservableList<Task> g, TableView<Task> g2, ChoiceBox choiceBoxRole, Project project) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add task");

        window.setMinHeight(365);
        window.setMinWidth(300);

        Label labelName = new Label("Title");
        Label labelStartDate = new Label("Start Date");
        Label labelEndDate = new Label("End Date");
        Button buttonConfrim = new Button("Confirm");
        Label labelmember = new Label("Assign member");

        TextField fieldName = new TextField();
        ComboBox<Member> memberComboBox = ComponentCreator.createComboBox("Assign member", project.getMembers());
        Label labeltasksDescription = new Label("Description");

        TextField fieldtasksDescription = new TextField();

        DatePicker fieldStartDate = new DatePicker();
        DatePicker fieldEndDate = new DatePicker();

        labelName.setPrefSize(100, 30);
        labelStartDate.setPrefSize(100, 30);
        labelEndDate.setPrefSize(50, 30);
        labeltasksDescription.setPrefSize(100, 30);
        labelmember.setPrefSize(100, 30);

        fieldtasksDescription.setPrefSize(180, 30);
        fieldName.setPrefSize(180, 30);
        fieldStartDate.setPrefSize(180, 30);
        fieldEndDate.setPrefSize(180, 30);
        buttonConfrim.setPrefSize(240, 30);


        labelName.setTranslateX(20);
        labelName.setTranslateY(0);
        labeltasksDescription.setTranslateX(20);
        labeltasksDescription.setTranslateY(40);
        labelStartDate.setTranslateX(20);
        labelStartDate.setTranslateY(80);
        labelEndDate.setTranslateX(20);
        labelEndDate.setTranslateY(120);
        labelmember.setTranslateX(12);
        labelmember.setTranslateY(155);


        fieldName.setTranslateX(100);
        fieldName.setTranslateY(0);
        fieldtasksDescription.setTranslateX(100);
        fieldtasksDescription.setTranslateY(40);
        fieldStartDate.setTranslateX(100);
        fieldStartDate.setTranslateY(80);
        fieldEndDate.setTranslateX(100);
        fieldEndDate.setTranslateY(120);
        buttonConfrim.setTranslateX(35);
        buttonConfrim.setTranslateY(225);
        memberComboBox.setTranslateX(100);
        memberComboBox.setTranslateY(160);
        memberComboBox.setMaxWidth(180);


        Group root = new Group();
        root.getChildren().addAll(labelName,labelStartDate,labelEndDate,fieldName,fieldStartDate,
                fieldEndDate,buttonConfrim,labeltasksDescription,fieldtasksDescription,labelmember, memberComboBox);

        StackPane final_group = new StackPane(root);
        buttonConfrim.setOnAction(e ->
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
            String description = fieldtasksDescription.getText();
            LocalDate startDate = fieldStartDate.getValue();
            LocalDate endDate = fieldEndDate.getValue();
            Member member = memberComboBox.getValue();
            if ( startDate.getYear() <= endDate.getYear() ) {
                if ( startDate.getMonthValue() <= endDate.getMonthValue() ) {
                    if ( startDate.getDayOfMonth() <= endDate.getDayOfMonth() ) {
                        System.out.println("Done");
                    } else {
                        new Alert(Alert.AlertType.CONFIRMATION, "Start date is greater than end date!").show();
                        window.close();
                        return;
                    }
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Start date is greater than end date!").show();
                    window.close();
                    return;
                }
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "Start date is greater than end date!").show();
                window.close();
                return;
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
            if ( !name.equals("") && !startDate.equals("") && !endDate.equals("") ) {

                Task newTask = new Task(name,startDate,endDate, vi, description, member);
                project.getTaskList().add(newTask);
                g.add(newTask);
                g.remove(newTask);
                fieldName.setText("");
                fieldStartDate.setValue(null);
                fieldEndDate.setValue(null);
                memberComboBox.getValue();


                choiceBoxRole.getSelectionModel().selectFirst();
                window.close();
            } else {
                new Alert(Alert.AlertType.WARNING, "Name Task , Start Date and End Date fields cannot be empty!").show();
            }
            g.addAll(project.getTaskList());
        });
        Scene scene = new Scene(final_group, 300, 270);
        window.setScene(scene);
        window.showAndWait();
    }
}