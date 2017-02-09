package com.projectxi.berlemstudio.contentmanagement.res;

import java.util.ArrayList;

/**
 * Created by patawat on 2/8/2017 AD.
 */

public class StoryGroup {
    public String name;
    public String des;
    public int age;
    public ArrayList<story> storyList;

    public StoryGroup(String name, String des, int age, ArrayList<story> storyList){
        this.name = name;
        this.des = des;
        this.age = age;
        this.storyList = storyList;
    }
    public int getAge(){
        return this.age;
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
    public void setAge(int age){
        this.age = age;
    }

}
