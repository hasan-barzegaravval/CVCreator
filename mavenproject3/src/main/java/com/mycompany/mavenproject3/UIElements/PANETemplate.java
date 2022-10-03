/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.Definitions.DATATemplate;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

/**
 * The generic super class for panes which represents List of DATA.
 * This class extends TitledPane.The elements on this pane are:
 * <ul>
 * <li> the Checkbox to select the pane
 * <li> Database insert button. insert the pane data to database and return id.
 * <li> Database update button. update the pane data on database with given id.
 * </ul> 
 * @author Hasan Barzegaravval
 * @version 1.0
 * @see TitledPane
 * @see DATATemplate
 * @param <DATA>  This is the subject of the 
 */
public abstract class PANETemplate<DATA extends DATATemplate> extends TitledPane {
    private CheckBox ch;
    private Button insertDBbtn,loadfromDBbtn;
    private DBViewer<DATA> DBView;
    private String  Type;
    /**
     * This constructor initialize the Pane general settings and set functions
     * for the buttons inset and update.
     * @param type This is the Type of the Data. It can be replaced by Parametrized methods on generics. 
     */
    public PANETemplate(String type){
        ch=new CheckBox();
        ch.getStyleClass().add("Selection_ch");
        GridPane gp=new GridPane();
        insertDBbtn=new Button();
        insertDBbtn.getStyleClass().add("insert_btn");
        insertDBbtn.setTooltip(new Tooltip("Insert as new entry to Database"));
        loadfromDBbtn=new Button();
        loadfromDBbtn.getStyleClass().add("load_btn");
        loadfromDBbtn.setTooltip(new Tooltip("Load from Database"));
        insertDBbtn.setOnAction((e)->DBInsert());
        loadfromDBbtn.setOnAction((e)->DBLoad());
        gp.add(ch, 0, 0);
        gp.add(insertDBbtn, 1, 0);
        gp.add(loadfromDBbtn, 2, 0);
        gp.setHgap(5.0);
        this.setGraphic(gp);        
        this.getStylesheets().add(FINALS.STYLES+"Panes.css");  
        
        Type=type;
    }
    /**
     * isSelected determines whether the current instance of pane is selected or not.
     * @return True if the Pane is selected and False if it is not selected.
     */
    public boolean isSelected(){
        return ch.isSelected();
    };
    /**
     * This method returns data corresponding to the UI elements on the pane.
     * Each pane must implement its own version.     * 
     * @return DATA the generic data type matches with the generic pane DATA.
     */
    abstract DATA getInfo();
    /**
     * This method Load the data from a table viewer which represents the database data.
     * DBView is a table view pane and it is initialized with data from database.
     * 
     */
    void DBLoad(){        
        DBView=FINALS.CurrentSession.getDBViewers().get(Type);
        if (DBView!=null){            
            DBView.SetMultiSelctionmode(false);        
            DBView.getLoader().setOnAction((e)->{this.setInfo(DBView.SelectedItem());});
            DBView.Show();
        }  
    };
    /**
     * This is to insert the Pane data to the database and its the action on insertDBbtn.
     * The method must be implemented for each pane class accordingly.
     */
    abstract void DBInsert();
    /** This method set the data on the Pane instance UI elements. 
     *  pane class should implement its own version.
     * @param D 
     */
    abstract void setInfo(DATA D);
    /**
     * Check whether the length of a string in char[] is less than 100.
     * It prints the complete words that their sum of their characters are about 
     * 100. If you are expecting a long word at the end rewrite the code.This is
     * only for TitledPanes text setting.
     * @param A input string. 
     * @return  a string with a length of about 100. If the string is not complete
     * then a ... sign is added to show continuation. 
     */
    public String textchange(String A){
        try{
            String[] words=A.trim().split(" ");
            StringBuilder str=new StringBuilder();        
            for(String s:words){
                if (str.length()<100){
                    str.append(s).append(" ");
                }else{
                    if (words.length>10){str.append("...");}
                    break;
                }
            }
            return str.toString();
        }catch(NullPointerException ex){
            return "";
        }
    }
}
