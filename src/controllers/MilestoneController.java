package controllers;


import creators.ComponentCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import models.Milestone;
import models.Project;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MilestoneController {


    public Milestone getMileName(String mileName, Project project) {

        for (Milestone milestone : project.getMilestoneList()) {
            if (milestone.getMileName().equals(mileName)) {
                return milestone;
            }
        }
        return null;
    }


        public void addMilestone(String mileName, LocalDate mileDate, Project project, String mileStatus, ListView<Milestone> listViewM) {
            Milestone cloneMilestone = getMileName(mileName, project);


            if (cloneMilestone == null) {
                Milestone milestone = new Milestone(mileName, mileDate, mileStatus);
                project.getMilestoneList().add(milestone);
                ObservableList<Milestone> list = FXCollections.observableList(project.getMilestoneList());
                listViewM.setItems(list);



        }else {
            ComponentCreator.createAlert("Name Alert", "Wrong or Missing Input!");
        }



    }

        public void removeMilestone(String mileName, Project project, ListView<Milestone> listView) {
            Milestone cloneMilestone = getMileName(mileName, project);

        if (cloneMilestone == null) {
            ComponentCreator.createAlert("Name Alert", "Wrong or Missing Input!");
        } else {
            project.getMilestoneList().remove(cloneMilestone);
            listView.refresh();
        }
    }

    public String mileStatus(LocalDate deadLine){
        String mileStatus;
        if (noDays(deadLine) > 14) {
            mileStatus = "Deadline : Almost " + noDays(deadLine) / 7 + " weeks left";
        } else if (noDays(deadLine) <= 14 && noDays(deadLine) > 2) {
            mileStatus = "Deadline : Almost " + noDays(deadLine) + " Days left";
        } else if (noDays(deadLine) == 1) {
            mileStatus = "Deadline : Tomorrow";
        } else if (noDays(deadLine) == 0) {
            mileStatus = "Deadline : Today";
        } else {
            mileStatus = "Deadline : Missed";
        }
        return mileStatus;
    }

    public long noDays(LocalDate endDate) {
        LocalDate date1 = LocalDate.now();
        return ChronoUnit.DAYS.between(date1, endDate);
    }


}







