package creators;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Member;
import models.Project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ComponentCreator {
    private static final SceneCreator sc = new SceneCreator();

    public static Button createButton(String text) {
        Button button = new Button(text);
        button.setFocusTraversable(false);
        return button;
    }

    public static Button createMainPageButton(Stage window,Project project ) {
        Button goToMainPageButton = createButton("Main page");

        goToMainPageButton.setOnAction(e -> {
            try {
                sc.createProjectMainPage(window, project);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        goToMainPageButton.setId("pageButton");
        return goToMainPageButton;
    }

    public static Button createTaskPageButton(Stage window, Project project) {
        Button goToTasksPageButton = createButton("Tasks page");
        goToTasksPageButton.setOnAction(e -> {
            try {
                sc.displayPageTasks(project, window);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        goToTasksPageButton.setId("pageButton");
        return goToTasksPageButton;
    }

    public static Button createStartPageButton(Stage window) {
        Button goToStartPageButton = createButton("Start Page");
        goToStartPageButton.setOnAction(e -> {
            try {
                window.setScene(sc.createStartMenu(window));
            } catch (FileNotFoundException fileNotFoundException) {
                ComponentCreator.createAlert("File not found!", "PlanettoLogo.png could not be found!");
            }
        });
        goToStartPageButton.setId("pageButton");
        return goToStartPageButton;
    }

    public static Button createMilestonePageButton(Stage window, Project project) {
        Button goToMilestonePageButton = createButton("Milestone Page");
        goToMilestonePageButton.setOnAction(e -> {
            try {
                sc.milestoneHomePage(window, project);

            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();

            }
        });
        goToMilestonePageButton.setId("pageButton");
        return goToMilestonePageButton;
    }

    public static Button createTeamPageButton(Stage window, Project project) {
        Button goToTeamPageButton = createButton("Go to team page");
        goToTeamPageButton.setOnAction(e -> {
            try {
                sc.createTeamPage(window, project);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        goToTeamPageButton.setId("pageButton");
        return goToTeamPageButton;
    }

    public static ImageView createImageView(String path, double size) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(path));
        final ImageView imageView = new ImageView(image);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public static TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setMaxSize(200, 10);
        textField.setPromptText(promptText);
        textField.setFocusTraversable(false);
        return textField;
    }

    public static Text createText(String content, int textSize, boolean isError) {
        Text text = new Text(content);
        text.setFont(Font.font("Galada", FontWeight.BOLD, textSize));
        if (isError) {
            text.setFill(Color.RED);
        }
        return text;
    }

    public static DatePicker createDatePicker(String promptText) {
        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);
        datePicker.setPromptText(promptText);
        datePicker.setFocusTraversable(false);
        datePicker.setPrefSize(200, 10);
        return datePicker;
    }

    public static void createAlert(String title, String content) {
        Alert wrong = new Alert(Alert.AlertType.ERROR);
        wrong.setTitle(title);
        wrong.setContentText(content);
        wrong.setHeaderText(null);
        wrong.showAndWait();
    }

    public static ColorPicker createColorPicker(String promptText) {
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setEditable(false);
        colorPicker.setPromptText(promptText);
        colorPicker.setFocusTraversable(false);
        colorPicker.setMinSize(200, 20);
        return colorPicker;
    }

    public static ComboBox<Member> createComboBox(String promptText, List<Member> values) {
        ComboBox<Member> comboBox = new ComboBox<>();
        ObservableList<Member> observableList = FXCollections.observableList(values);
        comboBox.setEditable(false);
        comboBox.setAccessibleText("Assign member");
        comboBox.setFocusTraversable(false);
        comboBox.setPrefSize(200, 20);
        comboBox.setItems(observableList);
        return comboBox;
    }
}
