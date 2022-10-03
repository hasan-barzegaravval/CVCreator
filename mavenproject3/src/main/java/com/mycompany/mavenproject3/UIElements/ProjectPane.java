/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Definitions.PROJECT;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class ProjectPane extends PANETemplate<PROJECT> {
    private TextField Titletxt;
    private TextField Linktxt;
    private TextArea CommenttxtA;
    private TitledPane CommentTP;
    private Label Titlelbl;
    private Label Linklbl;
    private PROJECT PRO;
    private GridPane GP;
    public ProjectPane(){
        super("PROJECT");
    }
    public ProjectPane(PROJECT pro){
        super("PROJECT");
            Titletxt=new TextField(pro.getTitle());
            Linktxt=new TextField(pro.getLink());
            CommenttxtA=new TextArea(pro.getComment());CommenttxtA.setWrapText(true);
            CommentTP=new TitledPane();
            CommentTP.setText("Comment:");
            CommentTP.setContent(CommenttxtA);
            CommentTP.setExpanded(false);
            Titlelbl=new Label("Title:");
            Linklbl=new Label("Link:");
            GP=new GridPane();
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(25);
            GP.getColumnConstraints().addAll(column,column,column,column);
            GP.add(Titlelbl, 0, 0,1,1);
            GP.add(Titletxt, 1, 0,3,1);
            GP.add(Linklbl, 0, 1,1,1);
            GP.add(Linktxt, 1, 1,3,1);
            GP.add(CommentTP, 0,2,4,1);
            GP.setHgap(5.0);
            GP.setVgap(5.0);
            Titletxt.textProperty().addListener((observable,oldvalue,newvalue)->setText(textchange(newvalue)));
            this.setContent(GP);
            PRO=(PROJECT)pro.Clone();
            setText(textchange(PRO.getTitle()));
    }
    @Override
    public PROJECT getInfo() {
        PRO.setTitle(Titletxt.getText());
        PRO.setLink(Linktxt.getText());
        PRO.setComment(CommenttxtA.getText());
        return PRO;
    }

    @Override
    void DBInsert() {        
        PRO=getInfo(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        PRO.insertMysql();        
    }

    @Override
    void setInfo(PROJECT D) {
        PRO=(PROJECT)D.Clone();
        CommenttxtA.setText(D.getComment());
        Titletxt.setText(D.getTitle());
        Linktxt.setText(D.getLink());
    }

   
    
    
}
