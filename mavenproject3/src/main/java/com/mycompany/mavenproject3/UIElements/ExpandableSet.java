/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Commons.AlertException;
import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.Definitions.DATATemplate;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * This class is the template for the object of expandable type. 
 * expandable type is a set of views (TitledPanes) that are managed under a single 
 * set and they dynamically added or deleted. It is a subclass of anchor pane and 
 * has Accordion and buttons fo add delete and reposition the titled panes. 
 * It is possible to modify this class to be more generic using parameterized type. 
 * @author Hasan Barzegaravval
 * @version 1.0
 * @since   1.0
 * @param <DATA> It is the type of the data which is represented by each titled pan. example (PROJECT)
 * @param <PANE> It is the pane which represents the information in DATA. subclass of PANETemplate. Example (InfoPane)
 */
public class ExpandableSet<DATA extends DATATemplate,PANE extends PANETemplate> extends AnchorPane {
    private Button Upbtn;
    private Button Downbtn;
    private Button Addbtn;
    private Button Deletebtn;
    private Label Titlelbl;
    private Accordion Listacc;
    private List<PANE> PaneList=new ArrayList();
    private GridPane ButtonsGP;
    private Class<PANE> P;
    private Class<DATA> D;
    /**
     * Constructor of ExpandableSet. 
     * @param PaneClass Class of the PANE generic type.example (InfoPane.class)
     * @param DataClass Class of the DATA generic type.example (PROJECT.class)  
     * @param Title The Title of Set. 
     */
    public ExpandableSet(Class<PANE> PaneClass,Class<DATA> DataClass,String Title){
        P=PaneClass;D=DataClass;
        Titlelbl=new Label(Title);
        Upbtn=new Button("Up");
        Upbtn.getStyleClass().add("Up");        
        Downbtn=new Button("Down");
        Downbtn.getStyleClass().add("Down");
        Addbtn=new Button("Add");
        Addbtn.getStyleClass().add("Add");
        Deletebtn=new Button("Delete");
        Deletebtn.getStyleClass().add("Delete");
        Upbtn.setOnAction((e)->moveup());
        Downbtn.setOnAction((e)->movedown());
        Addbtn.setOnAction((e)->addtoacc());
        Deletebtn.setOnAction((e)->deleteacc());
        Listacc=new Accordion();
        ButtonsGP=new GridPane();
        ButtonsGP.add(Titlelbl, 0, 0);
        ButtonsGP.add(Addbtn, 1, 0);
        ButtonsGP.add(Deletebtn, 2, 0);
        ButtonsGP.add(Upbtn, 3,0);
        ButtonsGP.add(Downbtn, 4,0);
        ButtonsGP.setHgap(10.0);
        ButtonsGP.setVgap(10.0);        
        AnchorPane.setTopAnchor(ButtonsGP, 10.0);
        AnchorPane.setLeftAnchor(ButtonsGP,10.0);
        double h=ButtonsGP.getHeight();
        AnchorPane.setTopAnchor(Listacc,50.0);
        AnchorPane.setLeftAnchor(Listacc,10.0);
        this.addelement(ButtonsGP);
        this.addelement(Listacc);
        this.getStylesheets().add(FINALS.STYLES+"ExpandableSet.css");
    }
    /**
     * Add a Node to the ExpandableSet main pane.
     * @param n Node to added.
     */
    private void addelement(Node n){
        this.getChildren().add(n);
    }
    /**
     * Add an empty PANE (P) with Data class (D) to the expandable set. 
     */
    private void addtoacc(){ 
        try{         
            PANE added=(P.getConstructor(D)).newInstance(D.getConstructor().newInstance());
            PaneList.add(added);
            Listacc.getPanes().add(added);
        }catch(Exception ex){
             AlertException.ALERT("Error", ex);
        }        
    }   
    /**
     * delete selected PANEs from the expandable set. 
     */
    private void deleteacc(){
        List<PANE> T=new ArrayList();
        for(PANE t:PaneList){            
              if (t.isSelected()){
                 T.add(t);
              }
        }
            PaneList.removeAll(T);
            Listacc.getPanes().removeAll(T);
    }    
    /**
     * move selected pan up.single pane only.
     */
    private void moveup(){
        int i=0;
        for(PANE t:PaneList){            
              if (t.isSelected()){
                  i=Listacc.getPanes().indexOf(t);
                  break;                 
              }
        }    
        if (i>0){
            Listacc.getPanes().removeAll(PaneList);
            PANE t1=PaneList.get(i);
            PANE t2=PaneList.get(i-1);
            PaneList.set(i, t2);
            PaneList.set(i-1,t1);
            Listacc.getPanes().addAll(PaneList);
        }
    }    
    /**
     * move selected pan down.single pane only.
     */
    private void movedown(){
        int i=0;
        for(PANE t:PaneList){            
              if (t.isSelected()){
                  i=Listacc.getPanes().indexOf(t);
                  break;                 
              }
        }       
        if (i<PaneList.size()-1){
            Listacc.getPanes().removeAll(PaneList);
            PANE t1=PaneList.get(i);
            PANE t2=PaneList.get(i+1);
            PaneList.set(i, t2);
            PaneList.set(i+1,t1);
            Listacc.getPanes().addAll(PaneList);
        }
    }
    /**
     * get the information of the expandable set as a list of DATA
     * @return List of DATA objects corresponding to each PANE on the set. 
     */
    public List<DATA> getInfoList(){   
        List<DATA> DL=new ArrayList();
        PaneList.forEach((t)->DL.add((DATA)t.getInfo()));    
        return DL;
    }
    /**
     * set the information of the PANES UI elements on a set using a list of DATA
     * @param list list of DATA as input
     */
    public void SetInfoList(List<DATA> list){        
        if(list!=null){ 
        for (DATA t:list){
            try{         
                PANE added=(P.getConstructor(D)).newInstance(t);
                PaneList.add(added);
                Listacc.getPanes().add(added);
            }catch(Exception ex){                
                 AlertException.ALERT("Error", ex);
            }
        }
        }
    }
    
}
