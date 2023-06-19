package models;

import javafx.scene.paint.Color;
import util.Status;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.util.Callback;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Task implements Serializable {
    private String taskID;
    private String tasksTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status state;
    private String tasksDescription;
    private Member assignedMember;

        public Task(String name, LocalDate startDate, LocalDate endDate, Status state, String description, Member member) {
        this.taskID = UUID.randomUUID().toString();
        this.tasksTitle = name;
        this.tasksDescription = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.assignedMember = member;
        }

    public Task(String name, LocalDate StartDate, LocalDate EndDate, Status state, String taskID, String Description, int d) {
        this.taskID = taskID;
        this.tasksTitle = name;
        this.tasksDescription = Description;
        this.startDate = StartDate;
        this.endDate = EndDate;
        this.state = state;
    }

    public Task(String name, LocalDate StartDate, LocalDate EndDate, String Description, Member assignedMember ) {
        this.taskID = UUID.randomUUID().toString();
        this.tasksTitle = name;
        this.tasksDescription = Description;
        this.startDate = StartDate;
        this.endDate = EndDate;
        this.state = Status.not_Started;
        this.assignedMember = assignedMember;
    }

    public Member getAssignedMember () {
            return assignedMember;
    }

    public void setAssignedMember(Member assignedMember) {
            this.assignedMember = assignedMember;
    }

    public String getTasksDescription(){
            return tasksDescription;
    }

    public void setTasksDescription(String tasksDescription) {
            this.tasksDescription = tasksDescription;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public String getTasksTitle() {
        return tasksTitle;
    }

    public void setTasksTitle(String tasksTitle) {
        this.tasksTitle = tasksTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate StartDate) {
        this.startDate = StartDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate EndDate) {
        this.endDate = EndDate;
    }





    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(T... items) {
        return new Callback<TableColumn<S, T>, TableCell<S, T>>() {

            private Callback<TableColumn<S, T>, TableCell<S, T>> callback = ChoiceBoxTableCell.forTableColumn(items);

            @Override
            public TableCell<S, T> call(TableColumn<S, T> param) {
                ChoiceBoxTableCell<S, T> cell = (ChoiceBoxTableCell<S, T>) this.callback.call(param);
                cell.setEditable(true);
                return cell;
            }

        };
    }

    @Override
    public String toString() {
        return "models.Task{" +
                "taskID='" + taskID + '\'' +
                ", tasksTitle='" + tasksTitle + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + String.valueOf(state) + '\'' +
                '}';
    }
}