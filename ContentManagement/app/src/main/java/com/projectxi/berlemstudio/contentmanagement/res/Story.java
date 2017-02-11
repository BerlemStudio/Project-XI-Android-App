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
//    public ArrayList<Scene> storyList;

    public Story(long id, String name, String des, String created_by){
        this.name = name;
        this.des = des;
        this.id = id;
        this.created_by = created_by;
    }
    public String getName(){
        return this.name;
    }
    public String getDes(){
        return this.des;
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
