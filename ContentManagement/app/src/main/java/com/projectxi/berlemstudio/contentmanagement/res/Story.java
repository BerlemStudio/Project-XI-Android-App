package com.projectxi.berlemstudio.contentmanagement.res;

import java.util.ArrayList;

/**
 * Created by patawat on 2/8/2017 AD.
 */

public class Story {
    public long id;
    public String name;
    public String des;
    public String created_by;
    public String[] scene;

    public Story(long id, String name, String des, String created_by, String[] scene){
        this.name = name;
        this.des = des;
        this.id = id;
        this.created_by = created_by;
        this.scene = scene;

    }
    public String getName(){
        return this.name;
    }
    public String getDes(){
        return this.des;
    }
    public String[] getScene(){ return this.scene;}
    public void setScene(String[] scene){
        this.scene = scene;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDes(String des){
        this.des = des;
    }
    public String getCreated_by(){
        return this.created_by;
    }
    public void setCreated_by(String Creator){
        this.created_by = Creator;
    }

}
