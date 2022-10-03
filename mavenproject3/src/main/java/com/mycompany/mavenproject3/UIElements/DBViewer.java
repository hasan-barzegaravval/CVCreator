/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.Definitions.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
/**
 * This class create a database data viewer. 
 * It creates a table and populates the table with the data loaded from database.
 * for string tables like achievements, data are mapped to Item object firstly and then
 * populated to the table view. 
 * The class has multiple overloaded constructor versions:
 * <ul>
 * <li> Constructor with no input.
 * <li> Constructor with data as a map and list of properties to be shown from data.
 * <li> Constructor with map of string data with a name of the property to be shown (a double column table) 
 * </ul>
 * @author Hasan Barzegaravval
 * @version 1.0
 * @since   1.0
 * @see     TableView
 * @param <DATA> a generic type representing the type of the data on the table view.Example (COMPANY)
 */

public class DBViewer<DATA> {
    private Stage stage;
    private Scene scene;
    private GridPane GP;
    private TableView TB;
    private Button loadbtn;
    private Label lbl;
    private List<DATA> Selected; 
    private List<TableColumn<DATA,String>> TBC1;
    private double Height=0.0;
    public DBViewer(){
        init(); 
    
        
    }
    /**
     * Constructor for viewer
     * @param data       is the map data loaded from the database 
     * @param Properties is the property from the data which are going to be shown. (Table columns headers)
     */
    public DBViewer(HashMap<Integer,DATA> data,List<String> Properties){
        init();
        TableCreator(data,Properties);
        
        
    }
    /**
     * Constructor for database viewer.
     * @param data    map of string values with ids loaded from database.
     * @param Cname   Table column header name.
     */
    public DBViewer(HashMap<Integer,String> data,String Cname){
        init();
        TableCreator(data,Cname);
        
    }
    private void init(){
        TB=new TableView();
        stage=new Stage();
        loadbtn=new Button("Add Selections");
        lbl=new Label("Select your data as rows:");
        GP=new GridPane();
        GP.add(TB, 0, 1,2,1);
        GP.setPrefWidth(TB.getPrefWidth());
        TB.prefWidthProperty().bind(stage.widthProperty().subtract(GP.getPadding().getLeft()).subtract(GP.getPadding().getRight()));
        TB.prefHeightProperty().bind(stage.heightProperty());
        GP.add(lbl, 0, 0,1,1);
        GP.add(loadbtn,1,0,1,1);
        scene=new Scene(GP);
        stage.setScene(scene);
        scene.getStylesheets().add(FINALS.STYLES+"TableViewer.css");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMaxWidth(Screen.getPrimary().getBounds().getWidth());
        stage.setMaxHeight(Screen.getPrimary().getBounds().getHeight());
        stage.setMinHeight(250);

    }
    /**
     * This function create the Table from data and property list.
     * @param data       is the map data loaded from the database 
     * @param Properties is the property from the data which are going to be shown. (Table columns headers)
     */
    private void TableCreator(HashMap<Integer,DATA> data,List<String> Properties){
       
        List<Integer> Keys=new ArrayList(data.keySet());
        List<DATA> Values=Keys.stream().map((e)->(data.get(e))).collect(Collectors.toList());
        List<TableColumn<DATA,String>> TBC=new ArrayList();
        Properties.forEach((e)->{TBC.add(new TableColumn(e));});
        TBC.forEach((e)->{e.setCellValueFactory(new PropertyValueFactory(e.getText()));}); 
        TB.setItems(FXCollections.observableArrayList(Values));
        TB.getColumns().addAll(TBC);          
        TBC1=TBC;
 
        
     
    }
    /**
     * Table creator for string maps. 
     * @param data    map of string values with ids loaded from database.
     * @param Cname   Table column header name.
     */
    private void TableCreator(HashMap<Integer,String> data,String Cname){        
        List<Integer> Keys=new ArrayList(data.keySet());
        List<Item> Values=Keys.stream().map((e)->{return new Item(data.get(e),e);}).collect(Collectors.toList());
        TableColumn<Item,String> ID=new TableColumn("ID");
        TableColumn<Item,String> Data=new TableColumn(Cname);
        ID.setCellValueFactory(new PropertyValueFactory("ID"));
        Data.setCellValueFactory(new PropertyValueFactory("VAL"));
        TB.setItems(FXCollections.observableArrayList(Values));        
        TB.getColumns().addAll(ID,Data);
    }
    /**
     * Access provider to the load button on the Database Viewer pane.
     * @return  loader button 
     */
    public Button getLoader(){
        return this.loadbtn;
    }
    /**
     * Access to single data selected.
     * @return DATA which is selected on the table view.
     */
    public DATA SelectedItem(){
        return (DATA)TB.getSelectionModel().getSelectedItem();
    }
    /**
     * Access to list of selected data
     * @return List of data which are selected on the table view.
     */
    public List<DATA> SelectedItems(){
        return (List<DATA>) TB.getSelectionModel().getSelectedItems();
    }
    /**
     * This method returns the ids of the selected data
     * @return List of ids for the Data selected on the table.
     */
    public List<Integer> SelectedIndices(){
        List<Item> items=(List<Item>)TB.getSelectionModel().getSelectedItems();
        return items.stream().map((e)->(e.getID())).collect(Collectors.toList());
    }
    /**
     * Set the selection mode of the table to multi.
     * @param multi true if the you want to set the mode to multi selection. false to set as single.
     */
    public void SetMultiSelctionmode(boolean multi){
        if (multi){TB.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);}else{TB.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);}
    }
    /**
     * This method show the table viewer and set height and width of the table
     * according to the data.
     */
    public void Show(){
        stage.show();
        double w=0.0;
        if (TBC1!=null){
            w=((List<Double>) TBC1.stream().map((e)->e.widthProperty().get()).collect(Collectors.toList())).stream().mapToDouble(Double::doubleValue).sum();
            double W=(w+GP.getPadding().getLeft()+GP.getPadding().getRight());
            if (W<150.0){
                TB.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }else{                
                stage.setMaxWidth((stage.getMaxWidth()>W*1.05)?W*1.05:stage.getMaxWidth());
                stage.setWidth(Math.min(stage.getMaxWidth(), W*1.05));
            }           
        }else{
            TB.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }         
        if(Height==0) Height=TB.getHeight()*(1+TB.getItems().size())+75;        
        stage.setHeight(Height);
        stage.centerOnScreen();   
    }
    /**
     * to have a list of SelectedStrings if the data are string maps.
     * @return List of strings which are selected from the table.
     */
    public List<String> SelectedStrings(){        
        List<Item> items=(List<Item>)TB.getSelectionModel().getSelectedItems();
        return items.stream().map((e)->(e.getVAL())).collect(Collectors.toList());
    }
  
}
