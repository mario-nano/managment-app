package controllers;

import creators.ComponentCreator;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Member;
import models.Project;
import util.Validation;
import models.Member;
import java.util.ArrayList;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

public class MemberController {

    public MemberController() {}

    public final List<Member> members = new ArrayList<>();

    public Member getMember(String username, Project project) {

        for (Member member : project.getMembers()) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }
        return null;
    }

    public boolean addNewMember(TextField memberUsernameField, TextField memberNameField, TextField memberRoleField, Project project, ListView listViewMembers) {
        try {
            boolean usernameIsEmpty = Validation.isEmpty(memberUsernameField.getText());
            boolean nameIsEmpty = Validation.isEmpty(memberNameField.getText());
            boolean roleIsEmpty = Validation.isEmpty(memberRoleField.getText());

            String username = memberUsernameField.getText();
            String name = memberNameField.getText();
            String role = memberRoleField.getText();

            Member testMember = getMember(username, project);

            if (!usernameIsEmpty && !nameIsEmpty && !roleIsEmpty && testMember == null) {
                Member member = new Member(username, name, role);
                project.getMembers().add(member);
                //listViewMembers.refresh();

                //This is another solution that is pretty easy and looks right on the user end
                listViewMembers.getItems().add(member);
                listViewMembers.getItems().remove(member);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List<Member> getMembers() {
        return members;
    }

    public Member getMember(String id) {

        for (Member member : members) {
            if (member.getUsername().equals(id)) {
                return member;
            }
        }
        return null;
    }

    public boolean removeMember(Stage window, TextField soughtMember, Project project, ListView listViewMembers) {
        try {
            boolean usernameIsEmpty = Validation.isEmpty(soughtMember.getText());
            String username = soughtMember.getText();
            Member member = getMember(username, project);

            if(!usernameIsEmpty && member != null) {
                project.getMembers().remove(member);
                listViewMembers.refresh();
                return true;
            }
            else {
                throw new Exception();
            }
        } catch (Exception e) {
            return false;
        }
    }

}
