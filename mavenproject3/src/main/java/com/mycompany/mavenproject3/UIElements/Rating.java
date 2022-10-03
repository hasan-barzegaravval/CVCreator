/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
/**
 * This class, represent a star rating UI. It creates a list of labels that contains
 * the rating star. for each star there is a label. it is possible to set the labels direction.
 * it uses star special characters. 
 * @author Hasan Barzegaravval
 * @version 1.0
 * @since   1.0
 */
public class Rating{
    private List<Label> Starlbl=new ArrayList();
    private int N;
    private GridPane GP;
    private String Orient;
    private int rate;
    private PropertyChangeSupport ChangeSupport=new PropertyChangeSupport(this);; 
    public Rating(){
        
    }
    /**
     * Constructor with inputs that returns a star rating pane.
     * @param n      number of total stars
     * @param orient orientation of the stars rating ("V" vertical, otherwise horizontal).
     */
    public Rating(int n,String orient){
        N=n;
        Orient=orient;
        GP=new GridPane();
        GP.setHgap(5.0);
        for(int i=0;i<N;i++){
            Label e=new Label("\u2606");
            e.setOnMouseClicked((Event event) -> {
                onclick(e);
            });
            e.setTextFill(Color.BLUE);           
            
            Starlbl.add(e);
            if (Orient.equals("V")){GP.add(e, i,0);}else{GP.add(e, 0,i);}
      
        }
    }
    /** 
     * on click function for each star label to change the rating and star graphic.
     * @param t the label on which click event occurs.
     */
    private void onclick(Label t){        
        rate=Starlbl.indexOf(t)+1;
        setRate(rate);
    }
    public GridPane getPane(){
        return GP;
    }
    public int GetRate(){
        return this.rate;
    }
    /**
     * method to set the rating. 
     * @param r is an integer corresponding to the rate. 
     */
    public void setRate(int r){
        this.rate=r;
        if (this.rate!=0){
        Starlbl.forEach((e)->e.setText("\u2606"));
        for(int i=0;i<this.rate;i++){
            Starlbl.get(i).setText("\u2605");            
        }
        ChangeSupport.firePropertyChange("rate", 0, this.rate); 
        }

    }
    /**
     * add a change Listener to the click on the labels.
     * @param Listener 
     */
    public void addChangeListner(PropertyChangeListener Listener){
        ChangeSupport.addPropertyChangeListener(Listener);
    }    
}
