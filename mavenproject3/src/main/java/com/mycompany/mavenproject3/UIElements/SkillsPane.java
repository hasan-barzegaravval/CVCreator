/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Definitions.SKILL;
//import java.beans.PropertyChangeEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
//import java.beans.PropertyChangeListener;

/**
 *
 * @author hasan
 */
public class SkillsPane extends PANETemplate<SKILL>{
    private Label Skilllbl;
    private TextField Skilltxt;
    private Rating SkillRate;
    private SKILL Skill;
    private GridPane GP;
    private int MAXR=10;
    public SkillsPane(){
           super("SKILL");
           GP=new GridPane();
           Skilllbl=new Label("Skill Name:");
           Skilltxt=new TextField();           
           SkillRate=new Rating(5,"V");
           GP.add(Skilllbl,0,0);
           GP.add(Skilltxt,0,1);
           GP.add(SkillRate.getPane(),0,2);
           this.setContent(GP);
           Skilltxt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(newvalue+" "+SkillRate.GetRate()));
           SkillRate.addChangeListner((newvalue)->this.setText(Skilltxt.getText()+" "+newvalue));
           Skill=new SKILL();

    }
    public SkillsPane(SKILL S){
           super("SKILL");
           GP=new GridPane();
           Skilllbl=new Label("Skill Name:");
           Skilltxt=new TextField(S.getSkill());           
           SkillRate=new Rating(MAXR,"V");
           SkillRate.setRate(S.getRate());
           GP.add(Skilllbl,0,0);
           GP.add(Skilltxt,1,0);
           GP.add(SkillRate.getPane(),2,0);
           ColumnConstraints[] column = {new ColumnConstraints(),new ColumnConstraints(),new ColumnConstraints()};
            column[0].setPercentWidth(20);
            column[1].setPercentWidth(50);
            column[2].setPercentWidth(30);
            GP.getColumnConstraints().addAll(column);
           GP.setHgap(10.0);
           this.setContent(GP);
           Skilltxt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(newvalue+" "+SkillRate.GetRate()+"/"+MAXR));
           //SkillRate.addChangeListner((obs,old,newvalue)->listener(newvalue));           
           SkillRate.addChangeListner((event)->this.setText(Skilltxt.getText()+" "+event.getNewValue()+"/"+MAXR));
           Skill=(SKILL)S.Clone();
           this.setText(Skill.getSkill()+" "+Skill.getRate()+"/"+MAXR);
           //Skill.setID(S.getID());
    }
    public SkillsPane(int RateN,String Orient){
           super("SKILL");
           GP=new GridPane();
           MAXR=RateN;
           Skilllbl=new Label("Skill Name:");
           Skilltxt=new TextField();
           SkillRate=new Rating(RateN,Orient);
           GP.add(Skilllbl,0,0);
           GP.add(Skilltxt,0,1);
           GP.add(SkillRate.getPane(),0,2);
           this.setContent(GP);
           Skilltxt.textProperty().addListener((observable,oldvalue,newvalue)->this.setText(newvalue+" "+SkillRate.GetRate()));
           SkillRate.addChangeListner((newvalue)->this.setText(Skilltxt.getText()+" "+newvalue)); 
           Skill=new SKILL();
    }
    @Override
    public SKILL getInfo() {
        Skill.setSkill(Skilltxt.getText());
        Skill.setRate(SkillRate.GetRate());
        return Skill;
    }
    //private PropertyChangeListener P(Observable)
    @Override
    void DBInsert() {
        getInfo();
        Skill.insertMysql();
    }

    @Override
    void setInfo(SKILL D) {
        Skill=(SKILL)D.Clone();
        SkillRate.setRate(D.getRate());
        Skilltxt.setText(D.getSkill());
    }

    
}
