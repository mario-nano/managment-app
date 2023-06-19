package creators;

import controllers.*;
import controllers.MemberController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Member;
import models.Milestone;
import models.Project;
import models.Task;
import util.Status;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;


public class SceneCreator {

    private final MemberController memberController = new MemberController();
    private final MilestoneController milestoneController = new MilestoneController();
    private final ProjectController projectController = new ProjectController();
    private final AddTask addTask = new AddTask();


    private VBox projectData;
    private BorderPane bp;
    private TextField nameField, budgetField, memberUsernameField, memberNameField, memberRoleField, soughtMember, mileName, mileDelName;
    private DatePicker startDateField, endDateField, milestoneDateFields;
    private Button goToTasksPageButton;
    private Button goToTeamPageButton;
    private Button goToMilestonePageButton;
    private Button goToStartPageButton;

    public SceneCreator() {

    }
        public Scene createStartMenu(Stage window) throws FileNotFoundException {
        bp = new BorderPane();
        Scene scene = new Scene(bp, 1080, 720);
        scene.getStylesheets().addAll("assets/InitialPageStyle.css", "assets/RegularButtonStyle.css");

        ImageView logo = ComponentCreator.createImageView("src/assets/PlanettoLogo.png", 100);

        HBox startButtons = new HBox(30);

        Button newProject = ComponentCreator.createButton("New Project");
        newProject.setId("regularButton");
        newProject.setOnAction(e -> createProjectWindow(window));

        Button loadProject = ComponentCreator.createButton("Load Project");
        loadProject.setId("regularButton");
        loadProject.setOnAction(e -> createLoadProjectWindow(window));

        startButtons.setAlignment(Pos.CENTER);
        startButtons.getChildren().addAll(newProject, loadProject);

        VBox startComponents = new VBox(80);
        startComponents.setAlignment(Pos.CENTER);
        startComponents.getChildren().addAll(logo, startButtons);
        bp.setCenter(startComponents);

        return scene;
    }

    public void createProjectWindow(Stage startWindow) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Create a Project");

        final int EXPECTED_AMOUNT_OF_COMPONENTS = 6;

        bp = new BorderPane();

        projectData = new VBox(15);
        projectData.setAlignment(Pos.CENTER);

        Scene scene = new Scene(bp,350, 350);
        scene.getStylesheets().add("assets/RegularButtonStyle.css");

        Text helpText = ComponentCreator.createText("Create a project", 30, false);

        nameField = ComponentCreator.createTextField("Insert a name for the project");
        startDateField = ComponentCreator.createDatePicker("Choose the start date");
        endDateField = ComponentCreator.createDatePicker("Choose the end date");
        budgetField = ComponentCreator.createTextField("Insert the budget");

        Button addNewProject = ComponentCreator.createButton("Add new project");
        addNewProject.setId("regularButton");
        addNewProject.setOnAction(e -> {
            Project newProject = projectController.createNewProject(nameField, startDateField, endDateField, budgetField);
            if (newProject != null) {
                try {
                    createProjectMainPage(startWindow, newProject);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                window.close();
            } else {
                if (projectData.getChildren().size() > EXPECTED_AMOUNT_OF_COMPONENTS) { //This if statement deletes previous errorText if it exists
                    projectData.getChildren().remove(EXPECTED_AMOUNT_OF_COMPONENTS);
                }
                projectData.getChildren().add(ComponentCreator.createText("Incorrect input", 10, true));
            }
        });

        projectData.getChildren().addAll(helpText, nameField, startDateField, endDateField, budgetField, addNewProject);
        bp.setCenter(projectData);
        projectData.setAlignment(Pos.CENTER);
        window.setScene(scene);
        window.showAndWait();
    }

    public void createLoadProjectWindow(Stage startWindow) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(250);

        final int EXPECTED_AMOUNT_OF_COMPONENTS = 2;

        HBox hBox = new HBox(10);
        VBox vBox = new VBox(10);

        nameField = ComponentCreator.createTextField("Name of the project to load");

        Button loadButton = ComponentCreator.createButton("Load");
        loadButton.setId("regularButton");
        loadButton.setOnAction(e -> {
            Project loadedProject = projectController.loadProject(nameField);
            if (loadedProject != null) {
                try {
                    createProjectMainPage(startWindow, loadedProject);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                window.close();
            } else {
                if (vBox.getChildren().size() > EXPECTED_AMOUNT_OF_COMPONENTS) { //This if statement deletes previous errorText if it exists
                    vBox.getChildren().remove(EXPECTED_AMOUNT_OF_COMPONENTS);
                }
                vBox.getChildren().add(ComponentCreator.createText("Incorrect input", 10, true));
            }
        });

        Button cancelButton = ComponentCreator.createButton("Cancel");
        cancelButton.setId("regularButton");
        cancelButton.setOnAction(e -> window.close());

        hBox.getChildren().addAll(cancelButton, loadButton);
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(nameField, hBox);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox);
        scene.getStylesheets().add("assets/RegularButtonStyle.css");
        window.setScene(scene);
        window.showAndWait();
    }

    public void createProjectMainPage(Stage window, Project project) throws FileNotFoundException {
        Text projectTitle = ComponentCreator.createText(project.getName(), 50, false);
        ImageView logo = ComponentCreator.createImageView("src/assets/PlanettoLogo.png", 100);
        Text ETA = projectController.numberOfWeeks(project.getEndDate());

        HBox header = new HBox(50);
        header.getChildren().addAll(projectTitle, ETA);
        header.setAlignment(Pos.CENTER);

        VBox center = new VBox(30);

        Text memberAmount = ComponentCreator.createText("Amount of members: " + project.getMembers().size(), 30, false);
        Text taskAmount = ComponentCreator.createText("Amount of tasks: " + project.getTaskList().size(), 30, false);
        Text milestoneAmount = ComponentCreator.createText("Amount of milestones: " + project.getMilestoneList().size(), 30, false);

        VBox projectDetails = new VBox(10);
        projectDetails.getChildren().addAll(memberAmount, taskAmount, milestoneAmount);
        projectDetails.setStyle("-fx-background-color: #d3d3d3");
        projectDetails.setMaxWidth(350);
        projectDetails.setAlignment(Pos.CENTER);


        HBox footer = new HBox(10);
        final int EXPECTED_AMOUNT_OF_COMPONENTS = 5;

        goToTasksPageButton = ComponentCreator.createTaskPageButton(window,project);
        goToTeamPageButton = ComponentCreator.createTeamPageButton(window, project);
        goToMilestonePageButton = ComponentCreator.createMilestonePageButton(window, project);
        goToStartPageButton = ComponentCreator.createStartPageButton(window);

        Button saveProjectButton = ComponentCreator.createButton("Save Project");
        saveProjectButton.setId("regularButton");
        saveProjectButton.setOnAction(e -> {
            try {
                FileController.saveData(project.getName() + ".txt", project);
                if (footer.getChildren().size() > EXPECTED_AMOUNT_OF_COMPONENTS) { //This if statement deletes previous errorText if it exists
                    footer.getChildren().remove(EXPECTED_AMOUNT_OF_COMPONENTS);
                }
                Text saveSuccessfulText = ComponentCreator.createText("Data saved properly", 10, false);
                saveSuccessfulText.setFill(Color.GREEN);
                footer.getChildren().add(saveSuccessfulText);

            } catch (Exception i) {
                if (footer.getChildren().size() > EXPECTED_AMOUNT_OF_COMPONENTS) { //This if statement deletes previous errorText if it exists
                    footer.getChildren().remove(EXPECTED_AMOUNT_OF_COMPONENTS);
                }
                footer.getChildren().add(ComponentCreator.createText("Data didn't save properly", 10, true));
            }
        });

        footer.getChildren().addAll(goToTasksPageButton,goToTeamPageButton,goToMilestonePageButton,goToStartPageButton,saveProjectButton);
        footer.setAlignment(Pos.CENTER);

        center.getChildren().addAll(logo, header, projectDetails, footer);
        center.setAlignment(Pos.CENTER);
        bp = new BorderPane();

        bp.setCenter(center);
        Scene projectMainPageScene = new Scene(bp, 1080, 720);
        projectMainPageScene.getStylesheets().addAll("assets/RegularButtonStyle.css", "assets/PageButtonStyle.css");
        window.setScene(projectMainPageScene);
    }

    public void createTeamPage(Stage window, Project project) throws FileNotFoundException {
        BorderPane bPane = new BorderPane();
        ObservableList<Member> list= FXCollections.observableList(project.getMembers());

        ImageView teamPageIcon = ComponentCreator.createImageView("src/assets/TeamPageIcon.png", 125);

        ListView<Member> listViewMembers = new ListView<>(list);
        listViewMembers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewMembers.setMaxHeight(450);
        listViewMembers.setPrefWidth(300);

        HBox hBoxPages = new HBox(10);
        HBox hBoxMain = new HBox(100);
        VBox vBoxMain = new VBox(75);
        VBox vBoxAdd = new VBox(10);
        VBox vBoxRemove = new VBox(10);
        VBox vBoxAll = new VBox(20);

        memberUsernameField = ComponentCreator.createTextField("Enter Username");
        memberNameField = ComponentCreator.createTextField("Enter Name");
        memberRoleField = ComponentCreator.createTextField("Enter Role");
        soughtMember = ComponentCreator.createTextField("Enter Username");

        Button addMemberButton = ComponentCreator.createButton("Add a new member");
        addMemberButton.setId("regularButton");
        addMemberButton.setOnAction(e -> {
            if (!memberController.addNewMember(memberUsernameField, memberNameField, memberRoleField, project, listViewMembers)) {
                if (vBoxAdd.getChildren().size() > 4) { //This if statement deletes previous errorText if it exists
                    vBoxAdd.getChildren().remove(4);
                }
                vBoxAdd.getChildren().add(ComponentCreator.createText("Incorrect input", 10, true));
            } else {
                if (vBoxAdd.getChildren().size() > 4) { //This if statement deletes previous errorText if it exists
                    vBoxAdd.getChildren().remove(4);
                }
                memberUsernameField.setText("");
                memberNameField.setText("");
                memberRoleField.setText("");
            }
        });

        Button removeMemberButton = ComponentCreator.createButton("Remove a member");
        removeMemberButton.setId("regularButton");
        removeMemberButton.setOnAction(e -> {
            if (!memberController.removeMember(window, soughtMember, project, listViewMembers)) {
                if (vBoxRemove.getChildren().size() > 2) { //This if statement deletes previous errorText if it exists
                    vBoxRemove.getChildren().remove(2);
                }
                vBoxRemove.getChildren().add(ComponentCreator.createText("Incorrect input", 10, true));
            } else {
                if (vBoxRemove.getChildren().size() > 2) { //This if statement deletes previous errorText if it exists
                    vBoxRemove.getChildren().remove(2);
                }
                soughtMember.setText("");
            }
        });

        goToTasksPageButton = ComponentCreator.createTaskPageButton(window,project);
        goToTeamPageButton = ComponentCreator.createTeamPageButton(window, project);
        goToMilestonePageButton = ComponentCreator.createMilestonePageButton(window, project);
        goToStartPageButton = ComponentCreator.createStartPageButton(window);
        Button goToMainPageButton = ComponentCreator.createMainPageButton(window,project);

        vBoxAdd.getChildren().addAll(memberUsernameField, memberNameField, memberRoleField, addMemberButton);

        vBoxRemove.getChildren().addAll(soughtMember, removeMemberButton);

        vBoxMain.getChildren().addAll(vBoxAdd, vBoxRemove);
        vBoxMain.setAlignment(Pos.CENTER);

        hBoxMain.getChildren().addAll(listViewMembers, vBoxMain);

        hBoxPages.getChildren().addAll(goToMainPageButton, goToTasksPageButton, goToTeamPageButton, goToMilestonePageButton, goToStartPageButton);
        hBoxMain.setAlignment(Pos.CENTER);
        bPane.setTop(hBoxPages);

        vBoxAll.getChildren().addAll(teamPageIcon, hBoxMain);
        vBoxAll.setAlignment(Pos.TOP_CENTER);
        bPane.setCenter(vBoxAll);

        Scene teamPageScene = new Scene(bPane,1080, 720);

        teamPageScene.getStylesheets().addAll("assets/RegularButtonStyle.css", "assets/PageButtonStyle.css");


        window.setScene(teamPageScene);
    }

    public void milestoneHomePage(Stage window, Project project) throws FileNotFoundException {

        ImageView mileLogo = ComponentCreator.createImageView("src/assets/MilestoneLogo.png", 100);
        ObservableList<Milestone> list= FXCollections.observableList(project.getMilestoneList());
        ListView<Milestone> listView = new ListView<>(list);

        listView.setMaxHeight(450);
        listView.setPrefWidth(300);
        listView.refresh();

        BorderPane mileP = new BorderPane( );
        HBox hBoxPages = new HBox(10);
        HBox hBoxMain = new HBox(100);
        VBox vBoxMain = new VBox(75);
        VBox vBoxAdd = new VBox(10);
        VBox vBoxRemove = new VBox(10);
        VBox vBoxAll = new VBox(20);

        mileName = ComponentCreator.createTextField("Enter Milestone Name");
        milestoneDateFields=ComponentCreator.createDatePicker("Choose the Milestone date");

        Button addMilestoneButton = ComponentCreator.createButton("Add new Milestone");
        addMilestoneButton.setId("regularButton");
        addMilestoneButton.setOnAction(e -> {
            try {
                if (mileName.getText().equals("") || mileName.getText() == null || milestoneDateFields.getValue().equals("") || milestoneDateFields.getValue() == null) {
                    ComponentCreator.createAlert("Name Alert", "Wrong or Missing Input!");
                }else {
                    milestoneController.addMilestone(mileName.getText(), milestoneDateFields.getValue(), project, milestoneController.mileStatus(milestoneDateFields.getValue()),listView);
                    listView.refresh();}
                mileName.setText("");
                milestoneDateFields.setValue(null);


            }catch (Exception e1){
                ComponentCreator.createAlert("Name Alert", "Wrong or Missing Input!");
            }
        });


        mileDelName = ComponentCreator.createTextField("Remove Milestone");
        Button removeMilestoneButton = ComponentCreator.createButton("Remove Milestone");
        removeMilestoneButton.setId("regularButton");
        removeMilestoneButton.setOnAction(e ->  {

            milestoneController.removeMilestone(mileDelName.getText(),project ,listView);
            listView.refresh();
            mileDelName.setText("");
        });

        goToTasksPageButton = ComponentCreator.createTaskPageButton(window,project);
        goToTeamPageButton = ComponentCreator.createTeamPageButton(window, project);
        goToStartPageButton = ComponentCreator.createStartPageButton(window);
        Button goToMainPageButton = ComponentCreator.createMainPageButton(window,project);

        goToMilestonePageButton = ComponentCreator.createButton("Milestone Page");
        goToMilestonePageButton.setId("pageButton");

        listView.setMaxSize(900,400);
        listView.refresh();

        vBoxAdd.getChildren().addAll(mileName, milestoneDateFields, addMilestoneButton);
        vBoxAdd.setAlignment(Pos.CENTER);

        vBoxRemove.getChildren().addAll(mileDelName, removeMilestoneButton);
        vBoxRemove.setAlignment(Pos.CENTER);

        vBoxMain.getChildren().addAll(vBoxAdd, vBoxRemove);
        vBoxMain.setAlignment(Pos.CENTER);

        hBoxMain.getChildren().addAll(listView, vBoxMain);

        hBoxPages.getChildren().addAll(goToMainPageButton, goToTasksPageButton, goToTeamPageButton, goToMilestonePageButton, goToStartPageButton);
        hBoxMain.setAlignment(Pos.CENTER);
        mileP.setTop(hBoxPages);

        vBoxAll.getChildren().addAll(mileLogo, hBoxMain);
        vBoxAll.setAlignment(Pos.TOP_CENTER);
        mileP.setCenter(vBoxAll);

        Scene milePageScene = new Scene(mileP,1080, 720);
        milePageScene.getStylesheets().addAll("assets/RegularButtonStyle.css", "assets/PageButtonStyle.css");
        window.setScene(milePageScene);
        window.show();

    }

        public void displayPageTasks(Project project, Stage window) throws FileNotFoundException {
            Status[] Statues = {Status.not_Started, Status.InProgress, Status.Done};

            Button buttonUpDate = new Button("update Task");
            Button buttonToPrint = new Button("Print All Task On Console");

            Button buttonAdd = new Button("Create Task");
            Button buttonDelete = new Button("Delete Selected Task");
            Button buttonDeleteAll = new Button("Delete All Task");

            HBox header = new HBox(10);
            VBox middle = new VBox(10);

            goToTasksPageButton = ComponentCreator.createButton("Tasks page");
            goToTasksPageButton.setId("pageButton");
            Button goToMainPageButton = ComponentCreator.createMainPageButton(window, project);
            goToTeamPageButton = ComponentCreator.createTeamPageButton(window, project);
            goToMilestonePageButton = ComponentCreator.createMilestonePageButton(window, project);
            goToStartPageButton = ComponentCreator.createStartPageButton(window);

            header.getChildren().addAll(goToMainPageButton,goToTasksPageButton,goToTeamPageButton,goToMilestonePageButton,goToStartPageButton);
            TableView<Task> table = new TableView();

            ChoiceBox choiceBoxRole = new ChoiceBox(FXCollections.observableArrayList(Statues));

            ObservableList<Task> data = FXCollections.observableArrayList(project.getTaskList());

//************************************************************************************
            TableColumn<Task, String> columnName = new TableColumn<>("Title");
            columnName.setCellValueFactory(new PropertyValueFactory<>("tasksTitle"));
//************************************************************************************

//************************************************************************************

            TableColumn<Task, LocalDate> columnStart_Date = new TableColumn<>("Start Date");
            columnStart_Date.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//************************************************************************************

//************************************************************************************

            TableColumn<Task, LocalDate> columnEnd_Date = new TableColumn<>("End Date");
            columnEnd_Date.setCellValueFactory(new PropertyValueFactory<>("endDate"));

//************************************************************************************

//************************************************************************************
            TableColumn<Task, Status> columnState = new TableColumn<>("status");
            columnState.setCellValueFactory(new PropertyValueFactory<>("state"));
            columnState.setCellFactory(Task.forTableColumn(Statues));
            columnState.setOnEditCommit((TableColumn.CellEditEvent<Task, Status> t) -> {
                ((Task) t.getTableView()
                        .getItems()
                        .get(t.getTablePosition().getRow()))
                        .setState(t.getNewValue());
            });
//************************************************************************************

//************************************************************************************
            TableColumn<Task, String> columnID = new TableColumn<>("ID");
            columnID.setCellValueFactory(new PropertyValueFactory<>("taskID"));

//************************************************************************************

//************************************************************************************
            TableColumn<Task, String> columnTasksDescription = new TableColumn<>("Description");
            columnTasksDescription.setCellValueFactory(new PropertyValueFactory<>("tasksDescription"));
//************************************************************************************

//************************************************************************************
            TableColumn<Task, String> columnMembers = new TableColumn<>("Members");
            columnMembers.setCellValueFactory(new PropertyValueFactory<>("assignedMember"));
//************************************************************************************
            table.setEditable(true);
            table.getColumns().addAll(columnID, columnName, columnTasksDescription, columnStart_Date, columnEnd_Date, columnState, columnMembers);
            table.setItems(data);
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            table.setPrefSize(900, 350);
            columnID.setPrefWidth(100);
            columnName.setPrefWidth(100);
            columnStart_Date.setPrefWidth(150);
            columnEnd_Date.setPrefWidth(150);
            columnState.setPrefWidth(100);
            columnTasksDescription.setPrefWidth(200);
            columnMembers.setPrefWidth(100);

            buttonUpDate.setPrefSize(180, 30);
            buttonAdd.setPrefSize(180, 30);
            buttonToPrint.setPrefSize(180, 30);
            buttonDelete.setPrefSize(180, 30);
            buttonDeleteAll.setPrefSize(180, 30);


            table.setTranslateX(245);
            table.setTranslateY(25);

            buttonAdd.setTranslateX(30);
            buttonAdd.setTranslateY(75);

            buttonDelete.setTranslateX(30);
            buttonDelete.setTranslateY(115);

            buttonDeleteAll.setTranslateX(30);
            buttonDeleteAll.setTranslateY(155);

            buttonUpDate.setTranslateX(30);
            buttonUpDate.setTranslateY(195);

            buttonToPrint.setTranslateX(30);
            buttonToPrint.setTranslateY(240);

            Group root = new Group();
            choiceBoxRole.getSelectionModel().selectFirst();
            ImageView imageView = ComponentCreator.createImageView("src/assets/PlanettoLogo.png", 100);

            imageView.setFitHeight(200);
            imageView.setFitWidth(200);

            imageView.setTranslateX(20);
            imageView.setTranslateY(-60);

            root.getChildren().addAll(table,buttonAdd,buttonDelete,buttonDeleteAll,buttonUpDate,imageView,buttonToPrint);

            StackPane content = new StackPane(root);
            middle.getChildren().addAll(header, content);
            Scene scene = new Scene(middle, 1200, 720);
            scene.getStylesheets().addAll("assets/RegularButtonStyle.css", "assets/PageButtonStyle.css");
            window.setResizable(false);
            window.setTitle("Page Tasks");
            window.setScene(scene);
            window.show();

            buttonAdd.setStyle("      -fx-background-color: \n" +
                    "        #000000,\n" +
                    "        linear-gradient(#7ebcea, #2f4b8f),\n" +
                    "        linear-gradient(#426ab7, #263e75),\n" +
                    "        linear-gradient(#395cab, #223768);\n" +
                    "    -fx-background-insets: 0,1,2,3;\n" +
                    "    -fx-background-radius: 3,2,2,2;\n" +
                    "    -fx-text-fill: white;\n" +
                    "    -fx-font-family: Galada;" +
                    "    -fx-font-size: 12px;");

            buttonToPrint.setStyle("-fx-font-size: 12px;");
            buttonAdd.setOnAction(e ->addTask.display(data, table, choiceBoxRole, project));
            buttonToPrint.setOnAction(e -> {
                int j = 1;
                for (Task i : data) {
                    System.out.println("********************************************************************{{{{" + "Task Number :" + j + "}}}}********************************************************************");
                    System.out.println(i.toString());
                    System.out.println("***************************************************************************************************************************************************************************");
                    System.out.println("");

                    j++;
                }
                if ( data.isEmpty() ) {
                    System.out.println("Dont Have Any Task");
                }
            });
            columnName.setStyle("-fx-alignment:CENTER");
            columnEnd_Date.setStyle("-fx-alignment:CENTER");
            columnID.setStyle("-fx-alignment:CENTER");
            columnMembers.setStyle("-fx-alignment:CENTER");
            columnState.setStyle("-fx-alignment:CENTER");
            columnTasksDescription.setStyle("-fx-alignment:CENTER");
            columnStart_Date.setStyle("-fx-alignment:CENTER");

            buttonDelete.setOnAction(e -> {
                table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
            });

            buttonDeleteAll.setOnAction(e -> {
                table.getItems().clear();
            });

            buttonUpDate.setOnAction(e ->
            {
                if ( data.isEmpty() ) {
                    new Alert(Alert.AlertType.WARNING, "No Have Any Task In Page Task").show();
                } else {
                    UpDate.display(data, table, choiceBoxRole);
                }
            });
        }

    }