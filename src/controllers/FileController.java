package controllers;

import models.Project;

import java.io.*;

public class FileController {

    public static void saveData(String nameOfFile, Project project) throws IOException {
            ObjectOutputStream saveFile = new ObjectOutputStream(new FileOutputStream(nameOfFile));
            saveFile.writeObject(project);
    }

    public static Project loadData(String nameOfFile) throws IOException, ClassNotFoundException {
        ObjectInputStream loadFile = new ObjectInputStream(new FileInputStream(nameOfFile));
        return (Project) loadFile.readObject();
    }
}
