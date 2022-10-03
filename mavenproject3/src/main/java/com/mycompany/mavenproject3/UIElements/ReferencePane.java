/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import com.mycompany.mavenproject3.Definitions.REFERENCE;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class ReferencePane extends PANETemplate<REFERENCE> {
    private TextField Nametxt;
    private TextField Positiontxt;
    private TextField Companytxt;
    private TextField Phonetxt;
    private TextField Emailtxt;
    private TextArea CommenttxtA;
    private Label Namelbl;
    private Label Positionlbl;
    private Label Companylbl;
    private Label Phonelbl;
    private Label Emaillbl;
    private Label Commentlbl;
    private REFERENCE REF;
    private GridPane GP;
    public ReferencePane(){   
        super("REFERENCE");

    }
    public ReferencePane(REFERENCE ref){
        super("REFERENCE");
        Nametxt=new TextField(ref.Name);
        Positiontxt=new TextField(ref.Position);
        Companytxt=new TextField(ref.Company);
        Phonetxt=new TextField(ref.Phone);
        Emailtxt=new TextField(ref.Email);
        CommenttxtA=new TextArea(ref.Comment);CommenttxtA.setWrapText(true);
        Namelbl=new Label("Name:");
        Positionlbl=new Label("Position:");
        Companylbl=new Label("Company:");
        Phonelbl=new Label("Phone:");
        Emaillbl=new Label("Email:");
        Commentlbl=new Label("Comment:");
        GP=new GridPane();
        GP=new GridPane();
        ColumnConstraints[] column = {new ColumnConstraints(),new ColumnConstraints()};
            column[0].setPercentWidth(30);
            column[1].setPercentWidth(70);
            GP.getColumnConstraints().addAll(column);
        GP.add(Namelbl, 0, 0);
        GP.add(Nametxt,1,0);
        GP.add(Positionlbl, 0, 1);
        GP.add(Positiontxt, 1, 1);
        GP.add(Companylbl, 0, 2);
        GP.add(Companytxt,1,2);
        GP.add(Emaillbl,0,3);
        GP.add(Emailtxt,1,3);
        GP.add(Phonelbl,0,4);
        GP.add(Phonetxt,1,4);
        GP.add(Commentlbl,0,5);
        GP.add(CommenttxtA,1,5);
        GP.setVgap(10.0);
        GP.setHgap(10.0);
        Nametxt.textProperty().addListener((observable,oldvalue,newvalue)->setText(textchange(newvalue)));
        this.setContent(GP); 
        REF=(REFERENCE)ref.Clone();
        setText(textchange(REF.getName()));        
    }
    @Override
    public REFERENCE getInfo() {
        REF.Comment=CommenttxtA.getText();
        REF.Company=Companytxt.getText();
        REF.Email=Emailtxt.getText();
        REF.Phone=Phonetxt.getText();
        REF.Position=Positiontxt.getText();
        REF.Name=Nametxt.getText();
        return REF;
    }

    @Override
    void DBInsert() {
        getInfo();
        REF.insertMysql();
    }

    @Override
    void setInfo(REFERENCE D) {
        REF=(REFERENCE)D.Clone();
        CommenttxtA.setText(D.getComment());
        Companytxt.setText(D.getCompany());
        Emailtxt.setText(D.getEmail());
        Phonetxt.setText(D.getPhone());
        Positiontxt.setText(D.getPosition());
        Nametxt.setText(D.getName());
    }


    
    
}
