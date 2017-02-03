package com.projectxi.berlemstudio.contentmanagement;

import java.io.Serializable;

/**
 * Created by patawat on 1/18/2017 AD.
 */

public class story implements Serializable{
    private String name;
    private String des;
    private String img_path;
    public story(String name, String des, String img_path){
        this.name = name;
        this.des = des;
        this.img_path = img_path;
    }

    public String getName(){
        return this.name;
    }
    public String getDes(){
        return this.des;
    }
    public String getImg_path(){
        return this.img_path;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDes(String des){
        this.des = des;
    }
    public void setImg_path(String Img_path){
        this.img_path = Img_path;
    }

}
