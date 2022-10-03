/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.Definitions.*;
import java.io.IOException;
import javafx.fxml.FXML;
import com.mycompany.mavenproject3.UIElements.EducationTab;
import com.mycompany.mavenproject3.UIElements.HeaderTab;
import com.mycompany.mavenproject3.UIElements.LanguagesTab;
import com.mycompany.mavenproject3.UIElements.ProfileTab;
import com.mycompany.mavenproject3.UIElements.PublicationsTab;
import com.mycompany.mavenproject3.UIElements.ReferencesTab;
import com.mycompany.mavenproject3.UIElements.SkillsTab;
import com.mycompany.mavenproject3.UIElements.WorkExperienceTab;
import java.io.File;
import java.net.URL;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * This is the main controller of the program. 
 * There are multiple methods called by UI elements:
 * <ul>
 * <li>DatabaseConnector which manage the database connection.
 * <li>Project Loading processes through both JSON file and database.
 * <li>Project Saving and save as to both JSON and database.
 * <li>Create HTML CV file from data and templates provided.
 * </ul>
 * @author Hasan Barzegaraavval
 * @version 1.0
 * @since  1.0
 * 
 */
public class MainController implements Initializable,FINALS{    
    private CV CurrentCV;
    @FXML
    private TabPane MainTabPane;
    @FXML
    private TextField HostNametxt,PortNtxt,UserNametxt,NewCVNametxt,CreateNewDBtxt,ProjectNametxt,LoadedFilePathtxt,SaveAsFilePathtxt;    
    @FXML
    private TextField LoadMainJStxt,LoadLibJStxt,LoadHTMLtxt,LoadCSStxt,CreateHtmltxt;
    @FXML
    private TextArea ConnectStatustxtA;
    @FXML
    private PasswordField Passpwf;
    @FXML 
    private TitledPane MySqlDBCtpan; 
    @FXML
    private ComboBox LoadDBscmb,LoadProjectscmb,SaveDBcmb;
    @FXML
    private CheckBox CreateNewDBch;
    /**
     * DataBase connection method. Read connection information from user inputs 
     * and establish the connection.
     * @param E is an Action event from the button click. 
     */
    @FXML
    public void DatabaseConnector(ActionEvent E){        
        try{
            if(FINALS.DBC!=null) {if (FINALS.DBC.getstatus().equals("Connected")) FINALS.DBC.close();}
            FINALS.DBC.connect(HostNametxt.getText(),PortNtxt.getText(),UserNametxt.getText(),Passpwf.getText());
            ConnectStatustxtA.setText(FINALS.DBC.getstatus());             
            LoadDBscmb.setItems(FXCollections.observableArrayList(FINALS.DBC.DBList()));
            SaveDBcmb.setItems(FXCollections.observableArrayList(FINALS.DBC.DBList()));            
        }catch(SQLException e){
            ConnectStatustxtA.setText(e.getMessage());            
        }
            MySqlDBCtpan.setText("Database Connection (MySQL) :"+FINALS.DBC.getstatus());
            
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
            //MainTabPane.setPrefHeight(540);
            //MainTabPane.setPrefWidth(800);
            MainTabPane.getStylesheets().add(STYLES+"ScrollerTab.css");
            CurrentCV=CurrentSession.getCurrentCV();
    }
    /**
     * This method loads projects names from a selected database.
     * @param E is an Action event from the button click. 
     */
    @FXML
    public void LoadProjectNames(ActionEvent E){
        try {
            LoadProjectscmb.setItems(FXCollections.observableArrayList(FINALS.DBC.ProjectList((String) LoadDBscmb.getValue())));
        }catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method load database data only to the program. no project is opened.
     */
    @FXML
    public void LoadDataBaseOnly(){
        try{
            DBC.setDBname(LoadDBscmb.getValue().toString());
            CurrentSession.initSESSIONwithDB(FINALS.DBC);      
        }catch(SQLException ex){
            AlertException.ALERT("Error",ex);
        }
    }
    /**
     * Load a project from database by selected project name and database by user.
     */
    @FXML
    public void LoadProjectfromDB(){
        try{
            DBC.setDBname(LoadDBscmb.getValue().toString());
            CurrentSession.initSESSIONwithDB(FINALS.DBC);
            CurrentSession.setPROJECTNAME(LoadProjectscmb.getValue().toString());
            CurrentCV=CurrentSession.CVLoader(LoadProjectscmb.getValue().toString());
            createproject(CurrentCV);       
        }catch(SQLException ex){
            AlertException.ALERT("Error", ex);
        }
    }
    /**
     * This method create a new empty project.
     * @param e is an Action event from the button click. 
     */
    @FXML
    public void CreateNewProject(Event e){ 
        CurrentSession.setCurrentCV(new CV(ProjectNametxt.getText()));
        CurrentCV=CurrentSession.getCurrentCV();
        createproject(CurrentCV);        
    } 
    /**
     * This method save the CV to the database.
     * @param E is an Action event from the button click. 
     */
    @FXML
    public void SavetoDB(ActionEvent E){
        try{
            GetData();
            CurrentCV.updateMysql(); 
        }catch(AlertException al){
            
        }
    }
    /**
     * This method performs CV Save as to the database.
     * @param E is an Action event from the button click. 
     */
    @FXML
    public void SaveastoDB(ActionEvent E){
        if((NewCVNametxt.getText().isEmpty()||NewCVNametxt.getText().isBlank())){ 
            AlertException.ALERT("Set a CV name. Its Null.","CV name is not set","Error");
        }else if(CurrentCV==null){
            AlertException.ALERT("No active project,Create or load a project.","Null Current CV","Error");
        }else{
            CurrentCV.setName(NewCVNametxt.getText());        
            if (CreateNewDBch.isSelected()){ 
                String Dbname=CreateNewDBtxt.getText();
                if((Dbname.isEmpty()||Dbname.isBlank())){
                    AlertException.ALERT("Database name is not set.set a databse name","Databse name is not set","Error");                       
                }else{
                    try {
                        CurrentCV=CVMigrate.Migrateto(CurrentCV,Dbname);
                    }catch(SQLException ex){
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        AlertException.ALERT("Error",ex);
                    }
                } 
            }else if(!(SaveDBcmb.getValue().toString().isEmpty())){
                String Dbname=SaveDBcmb.getValue().toString();
                if (Dbname.equals(FINALS.DBC.getDBname())){
                    try{
                        GetData();
                        CurrentCV.insertMysql();
                    }catch(AlertException aex){};
                }else{
                    try {
                        CurrentCV=CVMigrate.Migrateto(CurrentCV,Dbname);
                    } catch (SQLException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                        AlertException.ALERT("Error",ex);
                    }
                }
                
            } 
        }        
        
    }
    /**
     * Check listener for new database creation. if selected it allows the user 
     * to enter the new name for Database.     * 
     */
    @FXML
    public void NewDBcheck(){
        CreateNewDBtxt.setDisable(!CreateNewDBch.isSelected());
    }
    /**
     * This method populate UI elements (Tabs) by information of a given CV.
     * @param cv it is the loaded cv object. It can contain an empty cv for new project.
     */
    private void createproject(CV cv){
        if (MainTabPane.getTabs().size()>1) MainTabPane.getTabs().remove(1,MainTabPane.getTabs().size());
        MainTabPane.getTabs().add(new HeaderTab("Header",cv.getHeader()));
        MainTabPane.getTabs().add(new ProfileTab("Profile",cv.getProfile()));
        MainTabPane.getTabs().add(new EducationTab("Education",cv.getEducation()));
        MainTabPane.getTabs().add(new WorkExperienceTab("WorkExperience",cv.getWorkExperience()));
        MainTabPane.getTabs().add(new SkillsTab("Skills",cv.getSkills()));
        MainTabPane.getTabs().add(new LanguagesTab("Languages",cv.getLanguages()));
        MainTabPane.getTabs().add(new ReferencesTab("References",cv.getReferences()));
        MainTabPane.getTabs().add(new PublicationsTab("Publications",cv.getPublications()));

    }
    /**
     * This method Write the CV JSON to the same file as loaded. 
     * @param E is an Action event from the button click. 
     */    
    @FXML
    public void JsonWriterSave(ActionEvent E){        
        try {
            GetData();
            CurrentCV.updateor();
            ObjectMapper mapper=new ObjectMapper();
            String CVjson=mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(CurrentCV);
            if (CurrentSession.getJsonFilePath()==null||CurrentSession.getJsonFilePath().isBlank()){
                JsonWriterSaveas(E);
            }else{
                try{
                    Files.write(Paths.get(CurrentSession.getJsonFilePath()), CVjson.getBytes());
                }catch(IOException ex){
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    AlertException.ALERT("Error",ex);
                }
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error",ex);
        }catch(AlertException aex){};
    }
    /**
     * This method performs JSON save as operation and write a CV to a new JSON file.
     * @param E is an Action event from the button click.  
     */
    @FXML
    public void JsonWriterSaveas(ActionEvent E){
        try{
            GetData();
            CurrentCV.updateor();
            ObjectMapper mapper=new ObjectMapper();
            String CVjson=mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(CurrentCV);
            try{
                String s=(CurrentSession.getJsonFilePath()==null||CurrentSession.getJsonFilePath().isBlank())?RESOURCEFOLDER+SEP+"JSONS"+SEP:CurrentSession.getJsonFilePath();
                FileChooser fch=new FileChooser();
                fch.setInitialDirectory(Paths.get(s).getParent().toFile());
                fch.setInitialFileName(CurrentCV.getName()+".json");
                s=fch.showSaveDialog(new Stage()).getAbsolutePath();
                if (!s.contains(".json")) s=s+".json";
                Files.write(Paths.get(s), CVjson.getBytes());
                SaveAsFilePathtxt.setText(s);
            }catch(IOException ex){
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                AlertException.ALERT("Error",ex);
            }
        }catch(JsonProcessingException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error",ex);
        }catch(AlertException aex){};
    }
    /**
     * This method load and read a CV JSON file.
     */
    @FXML
    public void JsonReader(){
        ObjectMapper mapper=new ObjectMapper();
        String s=RESOURCEFOLDER+SEP+"JSONS"+SEP;
        try {
            FileChooser fch=new FileChooser();
            fch.setInitialDirectory(new File(s));
            s=fch.showOpenDialog(new Stage()).getAbsolutePath();
            CurrentSession.setCurrentCV(mapper.readValue(Paths.get(s).toFile(),CV.class));
            CurrentCV=CurrentSession.getCurrentCV();
            LoadedFilePathtxt.setText(s);
            createproject(CurrentCV);
            CurrentSession.setJsonFilePath(s);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error",ex);
        }catch(Exception ex){
            AlertException.ALERT("Error",ex);
        }
            
    }
    /**
     * An HTML CV creator. it creates an HTML cv from the tempalte HTML CSS and Javascript library.
     */
    @FXML
    public void CreateHTMLbyDefaults(){
        String s=RESOURCEFOLDER+SEP+"HTMLCSSJS"+SEP;
        try {
            CreateHTML(s+"Template.html",s+"CSS.css",s+"Lib.js",s+"Main.js");
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error",ex);
        }        
    }
    /**
     * Load an HTML template file for the HTML creator. 
     * @param E is an Action event from the button click. 
     */
    @FXML
    public void LoadHTMLbtn(ActionEvent E){
        FileChooser fch=new FileChooser();
        fch.setTitle("Choose HTML Template File");
        fch.setInitialDirectory(new File(CurrentSession.getDefaultDirectory()));        
        LoadHTMLtxt.setText(fch.showOpenDialog(new Stage()).getAbsolutePath());        
    }
    /**
     * Load a CSS file for HTML CV creator.
     */
    @FXML
    public void LoadCSSbtn(){
        FileChooser fch=new FileChooser();
        fch.setTitle("Choose CSS Template File");
        fch.setInitialDirectory(new File(CurrentSession.getDefaultDirectory()));        
        LoadCSStxt.setText(fch.showOpenDialog(new Stage()).getAbsolutePath()); 
    }
    /** Load JS library file for HTML CV creator.
     * 
     */
    @FXML
    public void LoadJSLibbtn(){
        FileChooser fch=new FileChooser();
        fch.setTitle("Choose JS Template File");
        fch.setInitialDirectory(new File(CurrentSession.getDefaultDirectory()));        
        LoadLibJStxt.setText(fch.showOpenDialog(new Stage()).getAbsolutePath()); 
    }
    /**
     * Load JavaScript Main function for the HTML creator.
     */
    @FXML
    public void LoadJSMainbtn(){
        FileChooser fch=new FileChooser();
        fch.setTitle("Choose HTML Template File");
        fch.setInitialDirectory(new File(CurrentSession.getDefaultDirectory()));        
        LoadMainJStxt.setText(fch.showOpenDialog(new Stage()).getAbsolutePath()); 
    }
    /**
     * This method is called when button is clicked and it loads all the files loaded by user
     * to create HTML (non default templates).
     */
    @FXML
    public void CreateHTMLbtn(){
        try {
            CreateHtmltxt.setText(CreateHTML(LoadHTMLtxt.getText(),LoadCSStxt.getText(),LoadLibJStxt.getText(),LoadMainJStxt.getText()));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error",ex);
        }
    }
    /**
     * This method combines HTML template,JS library and main files, JSON data and 
     * CSS to create an HTML CV. 
     * @param HTMLfile  The HTML template file.
     * @param CSSfile   The CSS file for CV styling.
     * @param JSLibfile The JS class Library that includes elements classes on HTML CV.
     * @param JSMainfile The JS main file that return HTML views on HTML CV.
     * @return a string contains the created HTML CV absolute Path.
     * @throws JsonProcessingException on JSON processing failure.
     */
    public String CreateHTML(String HTMLfile,String CSSfile,String JSLibfile,String JSMainfile) throws JsonProcessingException{
        try{GetData();
        CurrentCV.updateor();
        
        ObjectMapper mapper=new ObjectMapper();
        CV GoPublic=(CV)CurrentCV.Clone();
        GoPublic.getHeader().getInfo().forEach((e)->e.setImageFilePath(""));
        GoPublic.getProfile().setImageFilePath("");
        String CVjson=mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(GoPublic);
        String s=RESOURCEFOLDER+SEP+"HTMLCSSJS"+SEP;
        try {
            String HTML=Files.readString(Paths.get(HTMLfile));
            String CSS=Files.readString(Paths.get(CSSfile));
            String JSLib=Files.readString(Paths.get(JSLibfile));
            String JSMain=Files.readString(Paths.get(JSMainfile));
            int STYLELoc=HTML.indexOf("<!--Style Sheet-->")+("<!--Style Sheet-->").length();
            int JSLIBLoc=HTML.indexOf("<!--Code Libraries-->")+("<!--Code Libraries-->").length();
            int JSONLoc=HTML.indexOf("<!--JSON Data-->")+("<!--JSON Data-->").length();
            int MAINLoc=HTML.indexOf("<!--Main Script-->")+("<!--Main Script-->").length();
            StringBuilder FinalHTML=new StringBuilder();
            FinalHTML.append(HTML.substring(0,STYLELoc)).append("\n"+"<style>"+"\n").append(CSS).append("\n"+"</style>")
                    .append(HTML.substring(STYLELoc,JSLIBLoc))
                    .append("\n"+"<script>"+"\n").append(JSLib).append("\n"+"</script>")
                    .append(HTML.substring(JSLIBLoc,JSONLoc))
                    .append("\n"+"<script>"+"\n"+"Data=").append(CVjson).append("\n"+"</script>")
                    .append(HTML.substring(JSONLoc,MAINLoc))
                    .append("\n"+"<script>").append(JSMain).append("\n"+"</script>")
                    .append(HTML.substring(MAINLoc));
            FileChooser fch=new FileChooser();
            fch.setInitialFileName(CurrentCV.getName()+".html");
            fch.setInitialDirectory(Paths.get(HTMLfile).getParent().toFile());    
            String savedfilepath=fch.showSaveDialog(new Stage()).getAbsolutePath();
            Files.writeString(Paths.get(savedfilepath),FinalHTML.toString(),StandardOpenOption.CREATE);
            return savedfilepath;
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            AlertException.ALERT("Error",ex);
            return null;
        }
        }catch(AlertException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);            
            return null;
        }
    }
        
    /**
     * This method get the latest updated data from the UI elements on the Tabs and updates the CurrentCV instance.
     * @throws AlertException if CurrentCV is null or the CV name is not set. 
     */
    public void GetData() throws AlertException{
        
            if (CurrentCV==null) throw new AlertException("CV is not defined yet. create or load a cv.","CV is not defined","Error");
            if ((CurrentCV.getName()==null)) throw new AlertException("Set a CV name. Its Null.","CV name is not set","Error");
            if ((CurrentCV.getName().isEmpty())) throw new AlertException("Set a CV name. Its Empty.","CV name is not set","Error");
            CurrentCV.setHeader(((HeaderTab) MainTabPane.getTabs().get(1)).getInfo());
            CurrentCV.setProfile(((ProfileTab) MainTabPane.getTabs().get(2)).getInfo());
            CurrentCV.setEducation(((EducationTab) MainTabPane.getTabs().get(3)).getInfo());
            CurrentCV.setWorkExperience(((WorkExperienceTab) MainTabPane.getTabs().get(4)).getInfo());
            CurrentCV.setSkills(((SkillsTab) MainTabPane.getTabs().get(5)).getInfo());
            CurrentCV.setLanguages(((LanguagesTab) MainTabPane.getTabs().get(6)).getInfo());
            CurrentCV.setReferences(((ReferencesTab) MainTabPane.getTabs().get(7)).getInfo());
            CurrentCV.setPublications(((PublicationsTab) MainTabPane.getTabs().get(8)).getInfo());
        
    }
}


