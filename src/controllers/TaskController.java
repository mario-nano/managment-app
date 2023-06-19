package controllers;

import javafx.scene.control.Alert;
import models.Project;
import models.Task;
import util.Status;
import models.Member;

import java.time.LocalDate;

public class TaskController {

    public TaskController() {}

    public void addTask(Task task, Project project) {
        project.getTaskList().add(task);
        System.out.println(task);
    }

    public void showAllMembers(MemberController memberController){
        memberController.getMembers();
    }

}
