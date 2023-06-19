package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Member implements Serializable {

    private final String username;
    private final String name;
    private final String role;
    private ArrayList<Task> assignedTasks;

    public Member(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return String.format("%s (%s) : %s", getName(), getUsername(), role);
    }

    public void assignTask(Task task){
        //Adding to  member's list of assigned tasks a new task
        assignedTasks.add(task);
    }

}