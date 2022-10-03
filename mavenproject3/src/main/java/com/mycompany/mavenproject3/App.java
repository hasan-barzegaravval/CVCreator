/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;
import com.mycompany.mavenproject3.Commons.FINALS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * Program HTML CV maker.
 * The current program helps managing user CV information and allows you to integrate 
 * you CV into a dynamic single HTML file through HTML creator. It allows your 
 * CV information to be stored on a database , JSON files or as an HTML CV.
 * With the current program it is possible to simply manage the information on the CV
 * and create different versions as quickly. The current version of the program, it is written for:
 * <ul>
 * <li> end users: Users of final jar or executable files.
 * <li> developers: To either use the jars or elaborate as they find it useful, whole or part of 
 * the codes provided. 
 * </ul>
 * As this program is intended to be used by many, it is tried to be kept as simple but generic as 
 * to the author knowledge.
 * The intention of the author was not to create a generic framework and for that reason, 
 * generic approaches are avoided as much as possible that does not harm the extendability of the code
 * to reduce the complexity.
 * @author Hasan Barzegaravval
 * @version 1.0
 * @since 1.0
 */
public class App extends Application {
    private static Scene scene;    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"),820,560);
        stage.setScene(scene);
        stage.setMaxWidth(820);
        stage.setMaxHeight(1200);
        stage.setResizable(false);
        stage.getIcons().add(FINALS.APPICON);
        stage.show();
    }
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch();
    }
}