package controllers;

import creators.ComponentCreator;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Project;
import util.Validation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProjectController {
    public ProjectController () {

    }

    public Project createNewProject(TextField nameField, DatePicker startDateField, DatePicker endDateField, TextField budgetField) {
        try {
            boolean parseDouble = Validation.tryParseDouble(budgetField.getText());
            boolean nameIsEmpty = Validation.isEmpty(nameField.getText());
            boolean startDateIsEmpty = Validation.isEmpty(startDateField.getValue());
            boolean endDateIsEmpty = Validation.isEmpty(endDateField.getValue());

            if(parseDouble && !nameIsEmpty && !startDateIsEmpty && !endDateIsEmpty) {
                Project newProject = new Project(nameField.getText(), startDateField.getValue(), endDateField.getValue(), Double.parseDouble(budgetField.getText()));
                FileController.saveData(nameField.getText() + ".txt", newProject);
                return newProject;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Project loadProject(TextField projectNameTF) {
        String projectName = projectNameTF.getText();
        try {
            return FileController.loadData(projectName + ".txt");
        } catch (Exception e) {
            return null;
        }
    }
    public Text numberOfWeeks(LocalDate endDate) {
        Text numberOfWeeks;
        if (noDays(endDate) > 14) {
            numberOfWeeks = ComponentCreator.createText("Deadline : Almost " + noDays(endDate) / 7 + " weeks left", 18, false);
        } else if (noDays(endDate) <= 14 && noDays(endDate) > 2) {
            numberOfWeeks = ComponentCreator.createText("Deadline : Almost " + noDays(endDate) + " Days left", 18, false);
        } else if (noDays(endDate) == 1) {
            numberOfWeeks = ComponentCreator.createText("Deadline : Tomorrow", 18, false);
        } else if (noDays(endDate) == 0) {
            numberOfWeeks = ComponentCreator.createText("Deadline : Today", 18, false);
        } else {
            numberOfWeeks = ComponentCreator.createText("Deadline : Missed", 18, false);
        }
        return numberOfWeeks;
    }

    public long noDays(LocalDate endDate) {
        LocalDate date1 = LocalDate.now();
        return ChronoUnit.DAYS.between(date1, endDate);
    }
}
