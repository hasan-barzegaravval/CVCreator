/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3.UIElements;

import com.mycompany.mavenproject3.Definitions.WORKEXPERIENCE;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 *
 * @author hasan
 */
public class WorkExperienceTab extends ScrollableTab<List<WORKEXPERIENCE>,WORKEXPERIENCE,WorkExperiencePane> {
    private List<WORKEXPERIENCE> WEXP;
    private ExpandableSet<WORKEXPERIENCE,WorkExperiencePane> infoset;
    public WorkExperienceTab(String Name,List<WORKEXPERIENCE> data){
        WEXP=data;
        init(Name);
    }
    @Override
    GridPane ImmutableSection() {
        return null;
    }

    @Override
    ExpandableSet<WORKEXPERIENCE, WorkExperiencePane> MutableSection() {
        infoset=new ExpandableSet(WorkExperiencePane.class,WORKEXPERIENCE.class,"");
        infoset.SetInfoList(WEXP);
        return infoset;
    }

    @Override
    void setInfo(List<WORKEXPERIENCE> data) {
        WEXP=data;
        infoset.SetInfoList(WEXP);
    }

    @Override
    public List<WORKEXPERIENCE> getInfo() {
        WEXP=infoset.getInfoList();
        return WEXP;
    }
    
    
}
