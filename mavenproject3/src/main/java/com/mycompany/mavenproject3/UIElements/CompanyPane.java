/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;
import com.mycompany.mavenproject3.Commons.FINALS;
import com.mycompany.mavenproject3.Commons.STRINGS;
import com.mycompany.mavenproject3.Definitions.COMPANY;
import com.mycompany.mavenproject3.Definitions.PROJECT;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
/**
 *
 * @author hasan
 */
public class CompanyPane extends PANETemplate<COMPANY> {
    
    private TextField Companytxt;
    private TextField StartDatetxt;
    private TextField EndDatetxt;
    private TextField Linktxt;
    private TextArea ResptxtA;
    private TextArea AchitxtA;
    private TitledPane RespTP;
    private TitledPane AchiTP;
    private ExpandableSet<PROJECT,ProjectPane> Projectsexp;
    private Label StartDatelbl;
    private Label EndDatelbl;
    private Label Companylbl;
    private Label Linklbl;
    private COMPANY COMP;
    private GridPane GP;
    private Button RESPLoadfromDB;
    private Button ACHIVLoadfromDB;
    
    public CompanyPane(){
        super("COMPANY");
    }
    public CompanyPane(COMPANY comp){
        super("COMPANY");
        Companytxt=new TextField(comp.getCompany());
        StartDatetxt=new TextField(comp.getStartDate());
        EndDatetxt=new TextField(comp.getEndDate());
        Linktxt=new TextField(comp.getLink());
        ResptxtA=new TextArea((comp.getResponsibilities()==null)?"":STRINGS.ListToString(comp.getResponsibilities(),'\n'));
        AchitxtA=new TextArea((comp.getAchievements()==null)?"":STRINGS.ListToString(comp.getAchievements(),'\n'));
        Projectsexp=new ExpandableSet(ProjectPane.class,PROJECT.class,"Projects");
        Projectsexp.SetInfoList(comp.getProjects());
        Companylbl=new Label("Company:");
        Linklbl=new Label("Link: ");
        RespTP=new TitledPane();       
        AchiTP=new TitledPane();
        RESPLoadfromDB=new Button();
        ACHIVLoadfromDB=new Button();
        RESPLoadfromDB.setTooltip(new Tooltip("Load from Database"));
        ACHIVLoadfromDB.setTooltip(new Tooltip("Load from Database"));
        RESPLoadfromDB.setOnAction((e)->DBLoadRESP());
        ACHIVLoadfromDB.setOnAction((e)->DBLoadACHIV());
        RESPLoadfromDB.getStyleClass().add("load_btn");
        ACHIVLoadfromDB.getStyleClass().add("load_btn");
        RespTP.setGraphic(RESPLoadfromDB);
        AchiTP.setGraphic(ACHIVLoadfromDB);  
        RespTP.setExpanded(false);
        AchiTP.setExpanded(false);
        RespTP.setText("Responsibilities:");
        AchiTP.setText("Achievements:");
        RespTP.setContent(ResptxtA);
        AchiTP.setContent(AchitxtA);
        StartDatelbl=new Label("From(Date):");
        EndDatelbl=new Label("To(Date):");
        GP=new GridPane();
        List<ColumnConstraints> col=new ArrayList();
        for (int i=0;i<4;i++)col.add((new ColumnConstraints()));
        col.forEach((e)->e.setPercentWidth(25));
        GP.getColumnConstraints().addAll(col);
        GP.add(Companylbl, 0, 0,1,1);
        GP.add(Companytxt,1,0,3,1);
        GP.add(Linklbl,0,1);
        GP.add(Linktxt,1,1,3,1);
        GP.add(StartDatelbl, 0, 2);
        GP.add(StartDatetxt, 1, 2);
        GP.add(EndDatelbl, 2, 2);
        GP.add(EndDatetxt, 3, 2);
        GP.add(RespTP, 0, 3,4,1);
        GP.add(AchiTP, 0, 4,4,1);
        GP.add(Projectsexp, 0,5,4,1);
        GP.setHgap(5.0);
        GP.setVgap(5.0);
        Companytxt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(textchange(newvalue)));
        this.setContent(GP);
        COMP=(COMPANY)comp.Clone();
        this.setText(textchange(COMP.getCompany()));   
    }

    @Override
    public COMPANY getInfo() {
        COMP.setCompany(Companytxt.getText());
        COMP.setStartDate(StartDatetxt.getText());
        COMP.setLink(Linktxt.getText());
        COMP.setEndDate(EndDatetxt.getText());
        COMP.setProjects(Projectsexp.getInfoList());
        COMP.setAchievements(STRINGS.StringToList(AchitxtA.getText(),"\n"));
        COMP.setResponsibilities(STRINGS.StringToList(ResptxtA.getText(),"\n"));
        return COMP;
    }
    @Override
    void DBInsert() {
        getInfo();
        COMP.insertMysql();
    }
    @Override
    void setInfo(COMPANY C){
        COMP.setID(C.getIDnoinsert());
        COMP.setEndDate(C.getEndDate());
        COMP.setLink(C.getLink());
        COMP.setStartDate(C.getStartDate());
        COMP.setCompany(C.getCompany());
        EndDatetxt.setText(C.getEndDate());
        StartDatetxt.setText(C.getStartDate());
        Companytxt.setText(C.getCompany());
    }
    void DBLoadRESP(){        
        DBViewer DBVIEW=FINALS.CurrentSession.getDBViewers().get("RESPONSIBILITIES");
        if (DBVIEW!=null){            
            DBVIEW.SetMultiSelctionmode(true);        
            DBVIEW.getLoader().setOnAction((e)->{
                if (this.COMP.getResponsibilities()==null){
                    this.COMP.setResponsibilities(new ArrayList());
                    this.COMP.setRESPIDS(new ArrayList());
                }
                this.COMP.getResponsibilities().addAll(DBVIEW.SelectedStrings());
                this.COMP.getRESPIDS().addAll(DBVIEW.SelectedIndices());
                this.ResptxtA.appendText(STRINGS.ListToString(DBVIEW.SelectedStrings(), '\n'));
            });
            DBVIEW.Show();
        }  
    };
    void DBLoadACHIV(){        
        DBViewer DBVIEW=FINALS.CurrentSession.getDBViewers().get("ACHIEVEMENTS");
        if (DBVIEW!=null){            
            DBVIEW.SetMultiSelctionmode(true);        
            DBVIEW.getLoader().setOnAction((e)->{
                if (this.COMP.getAchievements()==null){
                    this.COMP.setAchievements(new ArrayList());
                    this.COMP.setACHIDS(new ArrayList());
                }
                this.COMP.getAchievements().addAll(DBVIEW.SelectedStrings());
                this.COMP.getACHIDS().addAll(DBVIEW.SelectedIndices());
                this.AchitxtA.appendText(STRINGS.ListToString(DBVIEW.SelectedStrings(), '\n'));
            });
            DBVIEW.Show();
        }  
    };

    
    
}
