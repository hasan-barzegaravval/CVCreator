/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Definitions.EDUCATION;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class EducationPane extends PANETemplate<EDUCATION> {
    private TextField Degreetxt;
    private TextField Instituetxt;
    private TextField Linktxt;
    private TextField StartDatetxt;
    private TextField EndDatetxt;
    private TextField Thesistxt;
    private TextField CGPAtxt;
    private TextArea CommenttxtA;
    private Label Degreelbl;
    private Label Instituelbl;
    private Label Linklbl;
    private Label StartDatelbl;
    private Label EndDatelbl;
    private Label Thesislbl;
    private Label CGPAlbl;
    private Label Commentlbl;
    private EDUCATION EDU;
    private GridPane GP;
    //private DBViewer<EDUCATION> DBView;
    public EducationPane(){
        super("EDUCATION");
    }
    public EducationPane(EDUCATION edu){
        super("EDUCATION");
        Degreetxt=new TextField(edu.getDegree());
        CGPAtxt=new TextField(edu.getCGPA());
        Instituetxt=new TextField(edu.getInstitue());
        Linktxt=new TextField(edu.getLink());
        StartDatetxt=new TextField(edu.getStartDate());
        EndDatetxt=new TextField(edu.getEndDate());
        Thesistxt=new TextField(edu.getThesis());
        CommenttxtA=new TextArea(edu.getComment());CommenttxtA.setWrapText(true);
        Degreelbl=new Label("Degree:");
        CGPAlbl=new Label("CGPA:");
        Instituelbl=new Label("Institue:");
        Linklbl=new Label("Institue Link:");
        StartDatelbl=new Label("From(Date):");
        EndDatelbl=new Label("To(Date):");
        Thesislbl=new Label("Thesis Title:");
        Commentlbl=new Label("Comment:");        
        GP=new GridPane();
        List<ColumnConstraints> column = new ArrayList();
        column.forEach((e)->e.setPercentWidth(100/6));
        //GP.setMaxWidth(this.getPrefWidth());
        GP.getColumnConstraints().addAll(column);
        GP.add(Degreelbl, 0, 0,1,1);
        GP.add(Degreetxt,1,0,5,1);
        GP.add(Instituelbl, 0, 1,1,1);
        GP.add(Instituetxt, 1, 1,5,1);
        GP.add(Linklbl, 0, 2,1,1);
        GP.add(Linktxt,1,2,5,1);
        GP.add(StartDatelbl,0,3,1,1);
        GP.add(StartDatetxt,1,3,1,1);
        GP.add(EndDatelbl,2,3,1,1);
        GP.add(EndDatetxt,3,3,1,1);
        GP.add(CGPAlbl, 4, 3,1,1);
        GP.add(CGPAtxt,5,3,1,1);
        GP.add(Thesislbl, 0, 4,1,1);
        GP.add(Thesistxt, 1, 4,5,1);
        GP.add(Commentlbl,0,5,1,1);
        GP.add(CommenttxtA,1,5,5,1);
        GP.setVgap(10.0);
        GP.setHgap(10.0);
        Degreetxt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(textchange(newvalue)));
        this.setContent(GP);
        EDU=(EDUCATION) edu.Clone();
        this.setText(textchange(EDU.getDegree()));
        
    }
    @Override
    public EDUCATION getInfo() {
        EDU.setCGPA(CGPAtxt.getText());
        EDU.setComment(CommenttxtA.getText());
        EDU.setDegree(Degreetxt.getText());
        EDU.setEndDate(EndDatetxt.getText());
        EDU.setStartDate(StartDatetxt.getText());
        EDU.setInstitue(Instituetxt.getText());
        EDU.setLink(Linktxt.getText());
        EDU.setThesis(Thesistxt.getText());
        return EDU;
    }

    @Override
    void DBInsert() {
        getInfo();
        EDU.insertMysql();
    }
//    @Override
//    void DBLoad(){
//        DBView=FINALS.CurrentSession.DBViewers.get("EDUCATION");
//        if (DBView!=null){            
//            DBView.SetMultiSelctionmode(false);        
//            DBView.getLoader().setOnAction((e)->{this.setInfo(DBView.SelectedItem());});
//            DBView.Show();
//        }        
//    }
    @Override
    void setInfo(EDUCATION edu){
        EDU=(EDUCATION)edu.Clone();
        Degreetxt.setText(edu.getDegree());
        CGPAtxt.setText(edu.getCGPA());
        CommenttxtA.setText(edu.getComment());
        Degreetxt.setText(edu.getDegree());
        EndDatetxt.setText(edu.getEndDate());
        StartDatetxt.setText(edu.getStartDate());
        Instituetxt.setText(edu.getInstitue());
        Linktxt.setText(edu.getLink());
        Thesistxt.setText(edu.getThesis());
    }   
}
