/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Definitions.SKILL;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class SkillsTab extends ScrollableTab<List<SKILL>,SKILL,SkillsPane> {
    private  List<SKILL> SKI;
    private  ExpandableSet<SKILL, SkillsPane> infoset; 
    public SkillsTab(String name,List<SKILL> data){
        SKI=data;
        init(name);
    }
    @Override
    GridPane ImmutableSection() {
        return null;
    }
    @Override
    ExpandableSet<SKILL, SkillsPane> MutableSection() {
        infoset=new ExpandableSet( SkillsPane.class,SKILL.class,"");
        infoset.SetInfoList(SKI);
        return infoset;
    }
    @Override
    void setInfo(List<SKILL> data) {
        SKI=data;
        infoset.SetInfoList(SKI);
    }
    @Override
    public List<SKILL> getInfo() {
        SKI=infoset.getInfoList();
        return SKI;
    }
  
    
}
