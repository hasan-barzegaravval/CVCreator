/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import com.mycompany.mavenproject3.Definitions.HEADER;
import com.mycompany.mavenproject3.Definitions.INFO;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
/**
 *
 * @author hasan
 */
public class HeaderTab extends ScrollableTab<HEADER,INFO,InfoPane>{
    private TextField Nametxt;
    private TextField Discriptiontxt;
    private Label Namelbl;
    private Label Discriptionlbl;
    private HEADER HEAD;
    private ExpandableSet<INFO,InfoPane> infoset;
    public HeaderTab(String Name,HEADER HD){
        HEAD=HD;
        init(Name);
        setDBView("HEADER");
        
    }
    
    @Override
    GridPane ImmutableSection() {
        GridPane GP=new GridPane();
        Nametxt=new TextField(HEAD.getName());
        Discriptiontxt=new TextField(HEAD.getDiscription());
        Namelbl=new Label("Name:");
        Discriptionlbl=new Label("Discription:");
        GP.add(Namelbl,0,0);
        GP.add(Nametxt, 1, 0);
        GP.add(Discriptionlbl,2,0);
        GP.add(Discriptiontxt,3,0);
        GP.add(getloadfromDBbtn(),4,0);
        GP.setVgap(10.0);
        GP.setHgap(10.0);        
        return GP;
    }
    @Override
    ExpandableSet<INFO, InfoPane> MutableSection() {        
        infoset=new ExpandableSet(InfoPane.class,INFO.class,"Links");
        infoset.SetInfoList(HEAD.getInfo());
        return infoset;
    }
    @Override
    public void setInfo(HEADER data) {
        HEAD.setID(data.getIDnoinsert());
        HEAD.setName(data.getName());
        HEAD.setDiscription(data.getDiscription());
        Nametxt.setText(HEAD.getName());
        Discriptiontxt.setText(HEAD.getDiscription());
        if ((data.getInfo()!=null)&&(!data.getInfo().isEmpty())) HEAD.setInfo(data.getInfo());
        infoset.SetInfoList(HEAD.getInfo());
    }
    @Override
    public HEADER getInfo() {
        HEAD.setName(Nametxt.getText());
        HEAD.setDiscription(Discriptiontxt.getText());
        HEAD.setInfo(infoset.getInfoList());
        return HEAD;
    }   

    
    
}
