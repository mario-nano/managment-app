package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


import static java.util.Objects.requireNonNull;

public class Project implements Serializable {

    private final String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private double budget;
    private final List<Task> taskList;
    private final List<Milestone> milestoneList;
    private final List<Member> memberList;

    public Project(String name, LocalDate startDate, LocalDate endDate, double budget) {
        this.name = requireNonNull(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.taskList = new ArrayList<>();
        this.milestoneList = new ArrayList<>();
        this.memberList = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<Milestone> getMilestoneList() {
        milestoneList.sort(Comparator.comparing(Milestone::getMileDate));
        return milestoneList;
    }

    public List<Member> getMembers() {
        return memberList;
    }
}
