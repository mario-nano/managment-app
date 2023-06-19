package controllers;

import models.Member;
import models.Task;

public class TaskAssigner {


    public void assignTaskToMember(models.Task task, models.Member member){
        member.assignTask(task);
    }

    public void showAllMembers(MemberController memberController){
        System.out.println(memberController.getMembers());
    }
    public void assignTask(Member member, Task task){
        member.assignTask(task);
    }

}
