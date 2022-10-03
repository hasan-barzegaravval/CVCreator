/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import com.mycompany.mavenproject3.Definitions.COMPANY;
import com.mycompany.mavenproject3.Definitions.WORKEXPERIENCE;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class WorkExperiencePane extends PANETemplate<WORKEXPERIENCE> {
    private TextField Positiontxt;
    private ExpandableSet<COMPANY,CompanyPane> Companiesexp;
    private Label Positionlbl;
    private WORKEXPERIENCE WORK;    
    private GridPane GP;
    public WorkExperiencePane(){
        super("WORKEXPERIENCE");
    }
    public WorkExperiencePane(WORKEXPERIENCE work){
        super("WORKEXPERIENCE");
        Positiontxt=new TextField(work.getPosition());
        Positionlbl=new Label("Position:");
        Companiesexp=new ExpandableSet(CompanyPane.class,COMPANY.class,"Companies");
        Companiesexp.SetInfoList(work.getCompanies());
        GP=new GridPane();        
        GP.add(Positionlbl, 0, 0);
        GP.add(Positiontxt, 1, 0);
        GP.add(Companiesexp,0,1,2,1);
        GP.setVgap(10.0);
        GP.setHgap(10.0);
        Positiontxt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(newvalue));
        this.setContent(GP);   
        WORK=(WORKEXPERIENCE)work.Clone();
        //WORK.setID(work.getID());
        this.setText(WORK.getPosition());
    }
    @Override
    public WORKEXPERIENCE getInfo() {
        WORK.setPosition(Positiontxt.getText());
        WORK.setCompanies(Companiesexp.getInfoList());
        return WORK;
    }
    @Override
    void DBInsert() {
        getInfo();
        WORK.insertMysql();
    }

    @Override
    void setInfo(WORKEXPERIENCE D) {
        WORK.setID(D.getIDnoinsert());
        WORK.setPosition(D.getPosition());
        Positiontxt.setText(D.getPosition());
    }

   
    
}
