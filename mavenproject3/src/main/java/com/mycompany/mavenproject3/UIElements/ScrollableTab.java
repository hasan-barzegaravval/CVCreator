/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.Definitions.DATATemplate;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;

/**
 * This is the generic superclass of Tabs which extends Tab class. The superclass contains:
 * <ul>
 * <li> A Scrollable Pane
 * <li> A GridPane
 * <li> A loader button. can be applied to load the tab from a cv on database.
 * </ul>
 * Each Scrollable tab includes two sections:
 * <ul>
 * <li> Immutable section. This section does not change the content and size (of UI elements) dynamically.
 * <li> Mutable section which contains the UI elements which represents lists of Panes and it is dynamic.
 * </ul>
 * the mutable section is created from a list of panes which extends PANETemplate but are grouped into 
 * an expandable set.
 * @author Hasan Barzegaravval
 * @version 1.0
 * @see  Tab
 * @see  ExpandableSet
 * @param <DATA>    The DATA type for the immutable section.
 * @param <MUTDATA> The Immutable section which is a subclass of DATATemplate.
 * @param <PANE>    A Pane class which is a subclass of PANETemplate.
 */
public  abstract class ScrollableTab<DATA,MUTDATA extends DATATemplate,PANE extends PANETemplate> extends Tab {
    private ScrollPane Scrollerpan;
    private GridPane Contentpan;
    private DBViewer<DATA> DBView;
    private Button loadfromDBbtn;
    private String Type;
    /**
     * This method create and initialize the general elements and contents of a scrollaleTab.
     * it creates and arrange the mutable and immutable sections as well as DB loader button.
     * if the Tab only contains mutable or immutable types, it automatically show or hide the corresponding pane.
     * the container of the loader button should be selected for each subclass tab. 
     * @param Name This is the name of the created Tab shown on the tab head section.
     */
    protected void init(String Name){
        Contentpan=new GridPane();
        loadfromDBbtn=new Button("Load from DB");
        loadfromDBbtn.setOnAction((e)->DBLoad());
        GridPane GP=ImmutableSection();
        ExpandableSet<MUTDATA,PANE> exp=MutableSection();        
        
        if (GP!=null)  {GP.getStyleClass().add("Immutable");Contentpan.add(GP, 0, 0);}
        if (exp!=null) {exp.getStyleClass().add("Muttable");Contentpan.add(exp,0,1);}
        
        Contentpan.setVgap(10.0);
        Contentpan.setHgap(10.0);
        Contentpan.setPadding(new Insets(10.0,10.0,10.0,10.0));
        Scrollerpan=new ScrollPane();
        Scrollerpan.setContent(Contentpan);
        //Scrollerpan.getStylesheets().add(FINALS.STYLES+"ScrollerTab.css");
        Contentpan.getStyleClass().add("ContentPan");  
        this.setContent(Scrollerpan);
        this.setText(Name);  
        
    }
    /**
     * Method to create and initialize the immutable section. each tab should implement its own 
     * version. 
     * @return A GridPane contains the immutable UI elements.
     */
    abstract GridPane ImmutableSection();
    /**
     * Method to create and initialize the mutable section. each tab should implement its own 
     * version. 
     * @return An ExpandableSet which contains a list of TitledPanes views.
     */
    abstract ExpandableSet<MUTDATA,PANE> MutableSection();
    /**
     * Method is to setInfo for the tab and populate its UI elements on immutable section.  
     * @param data DATA for the immutable section.
     */
    abstract void setInfo(DATA data);
    /**
     * This method returns the Immutable section Info.
     * @return DATA for the immutable section.
     */
    abstract DATA getInfo();
    /**
     * A method that shows the database table to get data for the Tab.
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
     * This a getter for database loader button. 
     * @return Button loadfromDBbtn (Loader from database)
     */
    public Button getloadfromDBbtn(){
        return loadfromDBbtn;
    }
    /** This is a getter for DBView      * 
     * @return DBView. Database data viewer. 
     */
    public DBViewer<DATA> getDBView(){
        return DBView;
    }
    /**
     * To set the type of data for the DBViewer to show.
     * @param Type Type of Data in DBViewer. 
     */
    public void setDBView(String Type){this.Type=Type;};

    
    
}
